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


# --- !Downs

drop function if exists currency_insert(varchar(16), integer, bool) cascade;

-- security definer functions
