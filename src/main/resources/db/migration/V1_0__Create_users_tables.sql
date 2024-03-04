-- FIXME: Disable db manipulation by spring before production
create sequence user_sequence start with 50 increment by 1;

create table admins (
    id integer not null, name varchar(255), email varchar(255) unique, password varchar(255), phone_number varchar(255) unique, primary key (id)
);

create table back_office_users (
    id integer not null, name varchar(255), email varchar(255) unique, password varchar(255), phone_number varchar(255) unique, primary key (id)
);

create table bikers (
    id integer not null, name varchar(255), email varchar(255) unique, password varchar(255), phone_number varchar(255) unique, street varchar(255), city varchar(255), state varchar(255), post_code varchar(255), country varchar(255), primary key (id)
);

create table customer_addresses (
    customer_id integer not null, street varchar(255), city varchar(255), state varchar(255), post_code varchar(255), country varchar(255)
);

create table customers (
    id integer not null, name varchar(255), email varchar(255) unique, password varchar(255), phone_number varchar(255) unique, primary key (id)
);

alter table if exists customer_addresses
add constraint FKrvr6wl9gll7u98cda18smugp4 foreign key (customer_id) references customers;

create table user_roles (
    user_id integer not null, roles varchar(255), primary key (user_id)
);