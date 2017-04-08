# Functions

# --- !Ups

-- create balances associated with currencies
create or replace function
currency_insert (
  a_currency varchar(16),
  a_position integer
) returns void as $$
declare
begin
  insert into currencies (currency, position) values (a_currency, a_position);;
  insert into balances (user_id, currency) select id, a_currency from users;;
end;;
$$ language plpgsql volatile security definer set search_path = public, pg_temp cost 100;


create or replace function
create_order (
  a_user_id bigint,
  a_country_id varchar(4),
  a_order_type varchar(4),
  a_status varchar(2),
  a_partner varchar(128),
  a_currency varchar(16),
  a_initial_value numeric(23,8),
  a_local_fee numeric(23,8),
  a_global_fee numeric(23,8),
  a_bank varchar(128),
  a_agency varchar(16),
  a_account varchar(16),
  a_doc1 varchar(128),
  a_image_id bigint
) returns boolean as $$
declare
  b_order_id bigint;;
  b_total_fee numeric(23,8);;
  a_system_id bigint;;
  a_local_admin_id bigint;;
  a_global_admin_id bigint;;
begin
-- Create orders
  a_system_id = 0;;
  a_local_admin_id = 1;;
  a_global_admin_id = 2;;
  b_total_fee = a_local_fee + a_global_fee;;
  insert into orders (user_id, country_id, order_type, status, partner, currency, initial_value, total_fee, bank, agency, account, doc1, image_id) values (a_user_id, a_country_id, a_order_type, a_status, a_partner, a_currency, a_initial_value, b_total_fee, a_bank, a_agency, a_account, a_doc1, a_image_id) returning order_id into b_order_id;;

  if a_order_type = 'V' then
  end if;;
  if a_order_type = 'D' or a_order_type = 'DCS' then
    update balances set balance = balance + a_initial_value, hold = hold + a_initial_value where currency = a_currency and user_id = a_user_id;;
  end if;;
  if a_order_type = 'W' or a_order_type = 'W.' then
    update balances set hold = hold + a_initial_value + b_total_fee where currency = a_currency and user_id = a_user_id;;
  end if;;

  if a_order_type = 'C' then
    update balances set balance = balance - a_initial_value, balance_c = balance_c + a_initial_value where currency = a_currency and user_id = a_user_id;;
    update balances set balance = balance + a_initial_value, balance_c = balance_c - a_initial_value where currency = a_currency and user_id = a_system_id;; -- System account
    update orders set status = 'OK', closed = current_timestamp, processed_by = a_system_id, net_value = a_initial_value, comment = '***** System-processed Order *****' where order_id = b_order_id;;
  end if;;
-- fees should be charged for deposit and withdraw when order update and at send when sending and to fiat when order creation
  if a_order_type = 'S' then
    -- maybe send fees should not be charged here... Just when send confirmed
    update balances set balance_c = balance_c + a_local_fee where currency = a_currency and user_id = a_local_admin_id;; -- Local administrator account
    update balances set balance_c = balance_c + a_global_fee where currency = a_currency and user_id = a_global_admin_id;; -- Global administrator account
  end if;;
  if a_order_type = 'F' then
    update balances set balance = balance + a_initial_value - b_total_fee, balance_c = balance_c - a_initial_value where currency = a_currency and user_id = a_user_id;;
    update balances set balance = balance - a_initial_value, balance_c = balance_c + a_initial_value where currency = a_currency and user_id = a_system_id;; -- System account
    update balances set balance = balance + a_local_fee where currency = a_currency and user_id = a_local_admin_id;; -- Local administrator account
    update balances set balance = balance + a_global_fee where currency = a_currency and user_id = a_global_admin_id;; -- Global administrator account
    update orders set status = 'OK', closed = current_timestamp, processed_by = a_system_id, net_value = a_initial_value - b_total_fee, comment = '***** System-processed Order *****' where order_id = b_order_id;;
  end if;;
  if a_order_type = 'RFW' or a_order_type = 'RFW.' then
  end if;;

  return true;;

end;;
$$ language plpgsql volatile security definer set search_path = public, pg_temp cost 100;


-- This function updates orders, updates balance fiat, updates balance crypto, updates system balances (fees)
create or replace function
update_order (
  a_order_id bigint,
  a_status varchar(2),
  a_processed_value numeric(23,8),
  a_comment varchar(128),
  a_local_fee numeric(23,8),
  a_global_fee numeric(23,8),
  a_admin_id bigint
) returns boolean as $$
declare
  b_user_id bigint;;
  b_order_type varchar(4);;
  b_currency varchar(16);;
  b_initial_value numeric(23,8);;
  b_net_value numeric(23,8);;
  b_crypto_currency varchar(16);;
  a_system_id bigint;;
  a_local_admin_id bigint;;
  a_global_admin_id bigint;;
begin
  a_system_id = 0;;
  a_local_admin_id = 1;;
  a_global_admin_id = 2;;
-- Update orders and balances and registers (if V)
  select user_id, order_type, currency, initial_value into b_user_id, b_order_type, b_currency, b_initial_value
    from orders where order_id = a_order_id;;
b_crypto_currency = b_currency;; -- system update should be at crypto-currency. It is being done at fiat for a while

-- At this point starts Order approved
  if a_status = 'OK' then
    if b_order_type = 'V' then
    end if;;
-- fees should be charged for deposit and withdraw when order update and at send when sending and to fiat when order creation
    if b_order_type = 'D' then
      update balances set balance = balance + 1000 - b_initial_value + a_processed_value - a_global_fee - a_local_fee, hold = hold + 1000 - b_initial_value where currency = b_currency and user_id = b_user_id;;
      update orders set status = 'OK', closed = current_timestamp, processed_by = a_admin_id, net_value = a_processed_value - a_global_fee - a_local_fee, comment = a_comment where order_id = a_order_id;;
    end if;;
    if b_order_type = 'DCS' then
      update balances set balance = balance + 1000 - b_initial_value, hold = hold + 1000 - b_initial_value, balance_c = balance_c + a_processed_value - a_global_fee - a_local_fee where currency = b_currency and user_id = b_user_id;;
      -- missing Send operation, but fee already charged
      update balances set balance = balance + a_processed_value - a_global_fee - a_local_fee + 1000, hold = hold + 1000 - a_processed_value where currency = b_crypto_currency and user_id = a_system_id;; -- System account
    end if;;
    if b_order_type = 'W' or b_order_type = 'W.' then
      update balances set balance = balance - a_processed_value - a_global_fee - a_local_fee + 1000, hold = hold + 1000 - a_processed_value - a_global_fee - a_local_fee where currency = b_currency and user_id = b_user_id;;
      update orders set status = 'OK', closed = current_timestamp, processed_by = a_admin_id, net_value = a_processed_value - a_global_fee - a_local_fee, comment = a_comment where order_id = a_order_id;;
    end if;;
    if b_order_type = 'RFW' or b_order_type = 'RFW.' then
  --    update balances set balance = balance - a_processed_value - a_global_fee - a_local_fee + 1000, hold = hold + 1000 - a_processed_value - a_global_fee - a_local_fee where currency = b_currency and user_id = b_user_id;;
    end if;;
    update balances set balance = balance + a_local_fee where currency = b_currency and user_id = a_local_admin_id;; -- Local administrator account
    update balances set balance = balance + a_global_fee where currency = b_currency and user_id = a_global_admin_id;; -- Global administrator account
  end if;;
return true;;

-- After this point is order lock (withdrwals need to be locked till actual withdrawal performed)


-- After this point is order rejection

    update balances set balance = balance + a_net_value
    where currency = (select currency FROM orders where order_id = a_order_id)
    and user_id = (select user_id FROM orders where order_id = a_order_id);;

  return true;;
end;;
$$ language plpgsql volatile security definer set search_path = public, pg_temp cost 100;



create or replace function
update_personal_info (
  a_user_id bigint,
  a_first_name varchar(64),
  a_middle_name varchar(128),
  a_last_name varchar(128),
  a_doc1 varchar(256),
  a_doc2 varchar(256),
  a_doc3 varchar(256),
  a_doc4 varchar(256),
  a_doc5 varchar(256),
  a_bank varchar(16),
  a_agency varchar(16),
  a_account varchar(16),
  a_partner varchar(128),
  a_partner_account varchar(64),
  a_manualauto_mode bool
) returns boolean as $$
begin
  update users_name_info set first_name = a_first_name, middle_name = a_middle_name, last_name = a_last_name, doc1 = a_doc1, doc2 = a_doc2, doc3 = a_doc3, doc4 = a_doc4, doc5 = a_doc5 where user_id = a_user_id;;
  update users_connections set bank = a_bank, agency = a_agency, account = a_account, partner = a_partner, partner_account = a_partner_account where user_id=a_user_id;;
  update users set manualauto_mode = a_manualauto_mode where id=a_user_id;;

  return true;;
end;;
$$ language plpgsql volatile security definer set search_path = public, pg_temp cost 100;

create or replace function
change_manualauto (
  a_id bigint,
  a_manualauto_mode bool
) returns boolean as $$
begin
  if a_id = 0 then
    raise 'User id 0 is not allowed to use this function.';;
  end if;;
  update users set manualauto_mode = a_manualauto_mode
  where id=a_id;;
  return true;;
end;;
$$ language plpgsql volatile security definer set search_path = public, pg_temp cost 100;

create or replace function
insert_user_image (
  a_name varchar(256),
  a_data bytea
) returns bigint as $$
declare
  b_image_id bigint;;
begin
  insert into image (name, data) values (a_name, a_data) returning image_id into strict b_image_id;;
  return b_image_id;;
end;;
$$ language plpgsql volatile security invoker set search_path = public, pg_temp cost 100;

# --- !Downs

drop function if exists currency_insert(varchar(16), integer, bool) cascade;
drop function if exists create_order(Long, varchar(4), varchar(4), varchar(2), varchar(128)) cascade;
drop function if exists update_order(Long, varchar(2), numeric(23,8), varchar(128), numeric(23,8)) cascade;
drop function if exists update_personal_info(Long, varchar(64), varchar(128), varchar(128)) cascade;
drop function if exists change_manualauto(Long, bool) cascade;
drop function if exists insert_user_image (varchar(256), bytea) cascade;

-- security definer functions
