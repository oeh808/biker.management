create sequence user_sequence start with 1 increment by 1;

create table admins (
    id integer not null, email varchar(255) unique, name varchar(255), phone_number varchar(255) unique, primary key (id)
);

create table back_office_users (
    id integer not null, email varchar(255) unique, name varchar(255), phone_number varchar(255) unique, primary key (id)
);

create table bikers (
    id integer not null, current_location varchar(255), email varchar(255) unique, name varchar(255), phone_number varchar(255) unique, primary key (id)
);