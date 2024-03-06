create table products (
    product_id integer not null auto_increment,name varchar(255),price float(23) not null,
 
 quantity integer not null,
 store_id integer,
 
 primary key (product_id)
) engine = InnoDB;

alter table products
add constraint FKgcyffheofvmy2x5l78xam63mc foreign key (store_id) references stores (id);