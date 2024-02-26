-- FIXME: Disable db manipulation by spring before production
create sequence user_sequence start with 1 increment by 1;

create table admins (
    id integer not null, name varchar(255), email varchar(255) unique, phone_number varchar(255) unique, primary key (id)
);

create table back_office_users (
    id integer not null, name varchar(255), email varchar(255) unique, phone_number varchar(255) unique, primary key (id)
);

create table bikers (
    id integer not null, name varchar(255), email varchar(255) unique, phone_number varchar(255) unique, current_location varchar(255), primary key (id)
);

create table user_info (
    id integer not null, username varchar(255), password varchar(255), roles varchar(255), primary key (id)
);