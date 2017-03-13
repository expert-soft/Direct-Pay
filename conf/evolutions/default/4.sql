# Functions

# --- !Ups

-- create balances associated with currencies
create or replace function
currency_insert (
  a_currency varchar(16),
  a_position integer,
  a_is_fiat bool
) returns void as $$
declare
begin
  insert into currencies (currency, position, is_fiat) values (a_currency, a_position, a_is_fiat);;
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
  a_total_fee numeric(23,8),
  a_bank varchar(128),
  a_agency varchar(16),
  a_account varchar(16),
  a_doc1 varchar(128)
) returns boolean as $$
declare

begin
-- Create orders
  insert into orders (user_id, country_id, order_type, status, partner, currency, initial_value, total_fee, bank, agency, account, doc1) values (a_user_id, a_country_id, a_order_type, a_status, a_partner, a_currency, a_initial_value, a_total_fee, a_bank, a_agency, a_account, a_doc1);;

  if a_order_type = 'V' then
  end if;;
  if a_order_type = 'D' or a_order_type = 'DCS' then
    update balances set balance = balance + a_initial_value, hold = hold + a_initial_value where currency = a_currency and user_id = a_user_id;;
  end if;;
  if a_order_type = 'W' or a_order_type = 'W.' or a_order_type = 'RFW' or a_order_type = 'RFW.' then
    update balances set hold = hold + a_initial_value + a_total_fee where currency = a_currency and user_id = a_user_id;;
  end if;;

return true;;
  if a_order_type = 'C' then
  end if;;
  if a_order_type = 'S' then
  end if;;

  if a_order_type = 'F' then
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
  a_global_fee numeric(23,8)
) returns boolean as $$
declare
  b_user_id bigint;;
  b_order_type varchar(4);;
  b_currency varchar(16);;
  b_initial_value numeric(23,8);;
  b_net_value numeric(23,8);;
  b_crypto_currency varchar(16);;
-- Update orders and balances and registers (if V)
begin
  select user_id, order_type, currency, initial_value into b_user_id, b_order_type, b_currency, b_initial_value
    from orders where order_id = a_order_id;;
b_crypto_currency = b_currency;; -- system update should be at crypto-currency. It is being done at fiat for a while

-- At this point starts Order approved
  if a_status = 'OK' then
    if b_order_type = 'V' then
    end if;;
    if b_order_type = 'D' then
      update balances set balance = balance + 1000 + a_processed_value - a_global_fee - a_local_fee - b_initial_value, hold = hold + 1000 - b_initial_value where currency = b_currency and user_id = b_user_id;;
    end if;;
    if a_order_type = 'V' then
    end if;;
    if b_order_type = 'DCS' then
      update balances set balance = balance - b_initial_value + 1000, hold = hold + 1000 - b_initial_value where currency = b_currency and user_id = b_user_id;;
      update balances set balance = balance - b_initial_value + a_processed_value - a_global_fee - a_local_fee + 1000, hold = hold + 1000 - b_initial_value where currency = b_crypto_currency and user_id = 0;; -- System account
    end if;;
    if b_order_type = 'W' or b_order_type = 'W.' then
      update balances set balance = balance - a_processed_value - a_global_fee - a_local_fee + 1000, hold = hold + 1000 - a_processed_value - a_global_fee - a_local_fee where currency = b_currency and user_id = b_user_id;;
    end if;;
    if b_order_type = 'RFW' or b_order_type = 'RFW.' then
  --    update balances set balance = balance - a_processed_value - a_global_fee - a_local_fee + 1000, hold = hold + 1000 - a_processed_value - a_global_fee - a_local_fee where currency = b_currency and user_id = b_user_id;;
    end if;;
    update balances set balance = balance + a_local_fee where currency = b_currency and user_id = 1;; -- Local administrator account
    update balances set balance = balance + a_local_fee where currency = b_currency and user_id = 2;; -- Global administrator account
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


# --- !Downs

drop function if exists currency_insert(varchar(16), integer, bool) cascade;
drop function if exists create_order(Long, varchar(4), varchar(4), varchar(2), varchar(128)) cascade;
drop function if exists update_order(Long, varchar(2), numeric(23,8), varchar(128), numeric(23,8)) cascade;
drop function if exists change_manualauto(Long, bool) cascade;

-- security definer functions
