create table orders (
    order_id integer generated by default as identity, product varchar(255), status varchar(255), eta date, vat float(24), price float(24), customer_id integer, store_id integer, biker_id integer, rating integer, review varchar(255), primary key (order_id)
);

alter table if exists orders
add constraint FKaw3syk90oe0615hben2ta36i0 foreign key (biker_id) references bikers;

alter table if exists orders
add constraint FKpxtb8awmi0dk6smoh2vp1litg foreign key (customer_id) references customers;

alter table if exists orders
add constraint FKnqkwhwveegs6ne9ra90y1gq0e foreign key (store_id) references stores;