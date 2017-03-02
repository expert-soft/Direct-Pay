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
  a_id bigint,
  a_country_id varchar(4),
  a_order_type varchar(4),
  a_status varchar(2),
  a_partner varchar(128),
  a_currency varchar(8),
  a_initial_value numeric(23,8),
  a_total_fee numeric(23,8),
  a_bank varchar(128),
  a_agency varchar(16),
  a_account varchar(16),
  a_doc1 varchar(128)
) returns void as $$
declare
begin
  insert into orders (user_id, country_id, order_type, status, partner, currency, initial_value, total_fee, bank, agency, account, doc1) values (a_id, a_country_id, a_order_type, a_status, a_partner, a_currency, a_initial_value, a_total_fee, a_bank, a_agency, a_account, a_doc1);;
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
  update users set manualauto_mode=a_manualauto_mode
  where id=a_id;;
  return true;;
end;;
$$ language plpgsql volatile security definer set search_path = public, pg_temp cost 100;




# --- !Downs

drop function if exists currency_insert(varchar(16), integer, bool) cascade;
drop function if exists create_order(Long, varchar(4), varchar(4), varchar(2), varchar(128), varchar(8), numeric(23,8), varchar(128), varchar(16), varchar(16), varchar(128)) cascade;
drop function if exists change_manualauto(Long, bool) cascade;

-- security definer functions
