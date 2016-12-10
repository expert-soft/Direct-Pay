# Initial database

# --- !Ups

create extension pgcrypto;

grant select on play_evolutions to public;
revoke create on schema public from public;

create table currencies (
    currency varchar(8) not null primary key,
    position int not null -- used for displaying
);

create table users (
    id bigint primary key,
    created timestamp(3) default current_timestamp not null,
    email varchar(256) not null,
    on_mailing_list bool default false not null,
    tfa_enabled bool default false not null,
    verification int default 0 not null,
    pgp text,
    active bool default true not null
);
create unique index unique_lower_email on users (lower(email));

create table users_name_info (
    user_id bigint not null,
    name varchar(256) not null,
    surname varchar(256) not null,
    middle_name varchar(256),
    prefix varchar(16),
    foreign key (user_id) references users(id),
    primary key (user_id)
);

create table users_address_info (
    user_id bigint not null,
    country varchar(256) not null,
    address varchar(256) not null,
    city varchar(256) not null,
    state varchar(256) not null,
    zip varchar(8) not null,
    type varchar(16) not null,
    foreign key (user_id) references users(id),
    primary key (user_id)
);

create table users_passwords (
    user_id bigint not null,
    password varchar(256) not null, -- salt is a part of the password field
    created timestamp(3) default current_timestamp not null,
    foreign key (user_id) references users(id),
    primary key (user_id, created)
);

create table users_tfa_secrets (
    user_id bigint not null,
    tfa_secret varchar(256),
    created timestamp(3) default current_timestamp not null,
    foreign key (user_id) references users(id),
    primary key (user_id, created)
);

create table users_backup_otps (
    user_id bigint not null,
    otp int not null,
    created timestamp(3) default current_timestamp not null,
    foreign key (user_id) references users(id),
    primary key (user_id, otp)
);

create table trusted_action_requests (
    email varchar(256),
    is_signup boolean not null,
    primary key (email, is_signup)
);

create table totp_tokens_blacklist (
    user_id bigint not null,
    token int not null,
    expiration timestamp(3) not null,
    foreign key (user_id) references users(id)
);
create index totp_tokens_blacklist_expiration_idx on totp_tokens_blacklist(expiration desc);

create sequence event_log_id_seq;
create table event_log (
    id bigint default nextval('event_log_id_seq') primary key,
    created timestamp(3) default current_timestamp not null,
    email varchar(256),
    user_id bigint,
    ip inet,
    browser_headers text, -- these can be parsed later to produce country info
    browser_id text, -- result of deanonymization
    ssl_info text, -- what ciphers were offered, what cipher was accepted, etc.
    type text, -- one of: login_partial_success, login_success, login_failure, logout, session_expired
    foreign key (user_id) references users(id)
);
create index login_log_idx on event_log(user_id, created desc, id desc) where type in ('login_success', 'login_failure', 'logout', 'session_expired');

create table tokens (
    token varchar(256) not null primary key,
    email varchar(256) not null,
    creation timestamp(3) not null,
    expiration timestamp(3) not null,
    is_signup bool not null
);
create index tokens_expiration_idx on tokens(expiration desc);

create table balances (
    user_id bigint not null,
    currency varchar(8) not null,
    balance numeric(23,8) default 0 not null,
    hold numeric(23,8) default 0 not null,
    constraint positive_balance check(balance >= 0),
    constraint positive_hold check(hold >= 0),
    constraint no_hold_above_balance check(balance >= hold),
    foreign key (user_id) references users(id),
    foreign key (currency) references currencies(currency),
    primary key (user_id, currency)
);

create table country (
    country_id int not null,
    country_code varchar(4) not null,
    country_name varchar(32) not null,
    country_local_name varchar(32),
    site_name varchar(32) not null,
    site_url1 varchar(128) not null,
    site_url2 varchar(128),
    language_name varchar(32) not null,
    language_code varchar(4),
    currency_symbol varchar(4),
    currency_code varchar(8) not null,
    currency_crypto varchar(32) not null,
    currency_name varchar(32) not null,
    currency_name_plural varchar(32) not null,
    currency_approximate_value numeric(23,8) default 0 not null,
    critical_value numeric(23,8) default 0 not null,
    primary key (country_id)
);

create table country_docs (
    country_id int not null,
    doc1_name varchar(32),
    doc1_required boolean not null,
    doc1_ispicture boolean not null,
    doc1_format varchar(32),
    doc2_name varchar(32),
    doc2_required boolean not null,
    doc2_ispicture boolean not null,
    doc2_format varchar(32),
    doc3_name varchar(32),
    doc3_required boolean not null,
    doc3_ispicture boolean not null,
    doc3_format varchar(32),
    doc4_name varchar(32),
    doc4_required boolean not null,
    doc4_ispicture boolean not null,
    doc4_format varchar(32),
    doc5_name varchar(32),
    doc5_required boolean not null,
    doc5_ispicture boolean not null,
    doc5_format varchar(32),
    primary key (country_id)
);

create table banks (
    bank_id int not null,
    country_id int not null,
    country_code varchar(4) not null,
    bank_code varchar(8) not null,
    bank_name varchar(32) not null,
    primary key (bank_id, country_id)
);
--     fee_global" varchar(256) not null,
--     fee_local" varchar(256) not null,
--     fee_global_deposit_percent numeric(7,3) default 0 not null,
--     fee_local_deposit_percent numeric(7,3) default 0 not null,
--     fee_local_deposit_nominal numeric(7,3) default 0 not null,
--     fee_global_withdrawal_percent numeric(7,3) default 0 not null,
--     fee_local_withdrawal_percent numeric(7,3) default 0 not null,
--     fee_local_withdrawal_nominal numeric(7,3) default 0 not null,
--     fee_global_send_percent numeric(7,3) default 0 not null,
--     fee_local_send_percent numeric(7,3) default 0 not null,
--     fee_global_tofiat_percent numeric(7,3) default 0 not null,
--     fee_local_tofiat_percent numeric(7,3) default 0 not null,
--     fee_local_doc_verification numeric(23,8) default 0 not null,
--
--     appearance_pic1 varchar(256) not null,
--     appearance_pic2 varchar(256) not null,
--     appearance_color1 varchar(32),
--     appearance_color2 varchar(32),
--     appearance_color3 varchar(32),
--     appearance_color4 varchar(32),
--     appearance_color5 varchar(32),

create table orders (
    order_id bigint not null,
    user_id bigint not null,
    country_id int not null,
    user_email varchar(256) not null,
    type varchar(4) not null,
    creation int not null, -- timestamp(3) not null,
    primary key (order_id)
);


# --- !Downs
drop table if exists balances cascade;
drop table if exists currencies cascade;
drop table if exists tokens cascade;
drop table if exists country cascade;
drop table if exists country_docs cascade;
drop table if exists banks cascade;
drop table if exists orders cascade;
drop table if exists users cascade;
drop table if exists users_name_info cascade;
drop table if exists users_address_info cascade;
drop table if exists users_passwords cascade;
drop table if exists users_api_keys cascade;
drop table if exists users_backup_otps cascade;
drop table if exists users_tfa_secrets cascade;
drop table if exists totp_tokens_blacklist cascade;
drop table if exists event_log cascade;
drop table if exists withdrawal_limits cascade;
drop table if exists trusted_action_requests cascade;
drop sequence if exists event_log_id_seq cascade;
drop extension pgcrypto;
