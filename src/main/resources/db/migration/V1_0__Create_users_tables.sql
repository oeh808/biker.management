create table admins (
    id integer not null, name varchar(255), email varchar(255), phone_number varchar(255), password varchar(255), primary key (id)
) engine = InnoDB;

create table back_office_users (
    id integer not null, name varchar(255), email varchar(255), phone_number varchar(255), password varchar(255), primary key (id)
) engine = InnoDB;

create table bikers (
    id integer not null, name varchar(255), email varchar(255), phone_number varchar(255), password varchar(255), street varchar(255), city varchar(255), country varchar(255), state varchar(255), post_code varchar(255), primary key (id)
) engine = InnoDB;

create table customer_addresses (
    customer_id integer not null, street varchar(255), city varchar(255), country varchar(255), state varchar(255), post_code varchar(255)
) engine = InnoDB;

create table customers (
    id integer not null, name varchar(255), email varchar(255), phone_number varchar(255), password varchar(255), primary key (id)
) engine = InnoDB;

create table stores (
    id integer not null, name varchar(255), email varchar(255), phone_number varchar(255), password varchar(255), street varchar(255), city varchar(255), country varchar(255), state varchar(255), post_code varchar(255), primary key (id)
) engine = InnoDB;

create table list_of_roles (
    user_roles_user_id integer not null, roles varchar(255)
) engine = InnoDB;

create table user_sequence (next_val bigint) engine = InnoDB;

insert into user_sequence values (50);

create table user_roles (
    user_id integer not null, primary key (user_id)
) engine = InnoDB;

alter table admins
add constraint UK_47bvqemyk6vlm0w7crc3opdd4 unique (email);

alter table admins
add constraint UK_jld98mhubn4q39ac763tdb8oh unique (phone_number);

alter table back_office_users
add constraint UK_56giksqh700gijw2sydpeaf9b unique (email);

alter table back_office_users
add constraint UK_2ri9j1s9m9f652854323xb4f0 unique (phone_number);

alter table bikers
add constraint UK_114i9b8ex9xh1kt9xkjfr8hqr unique (email);

alter table bikers
add constraint UK_qn8f0mk2tisjm9cme9xguksno unique (phone_number);

alter table customers
add constraint UK_rfbvkrffamfql7cjmen8v976v unique (email);

alter table customers
add constraint UK_6v6x92wb400iwh6unf5rwiim4 unique (phone_number);

alter table stores
add constraint UK_wd47de9f9671f8jfnh1ovob3 unique (email);

alter table stores
add constraint UK_m9stdcu26niccyuycrtr9hxwd unique (phone_number);

alter table customer_addresses
add constraint FKrvr6wl9gll7u98cda18smugp4 foreign key (customer_id) references customers (id);

alter table list_of_roles
add constraint FKqa5fswbne6o98puelhs266ob2 foreign key (user_roles_user_id) references user_roles (user_id);