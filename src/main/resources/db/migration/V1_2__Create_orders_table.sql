create table orders (
    order_id integer not null auto_increment, customer_id integer, product_id integer, status varchar(255), eta date, store_id integer, vat float(23), total_cost float(23), street varchar(255), city varchar(255), country varchar(255), state varchar(255), post_code varchar(255), biker_id integer, rating integer, review varchar(255), primary key (order_id)
) engine = InnoDB;

alter table orders
add constraint UK_306w8sghgvp5hmjrqo21dscv7 unique (product_id);

alter table orders
add constraint FKaw3syk90oe0615hben2ta36i0 foreign key (biker_id) references bikers (id);

alter table orders
add constraint FKpxtb8awmi0dk6smoh2vp1litg foreign key (customer_id) references customers (id);

alter table orders
add constraint FKkp5k52qtiygd8jkag4hayd0qg foreign key (product_id) references products (product_id);

alter table orders
add constraint FKnqkwhwveegs6ne9ra90y1gq0e foreign key (store_id) references stores (id);