create table order_history (
    id integer not null auto_increment, order_creation_date date, status enum(
        'AWAITING_APPROVAL', 'PREPARING', 'WAITING_FOR_PICK_UP', 'EN_ROUTE', 'DELIVERED'
    ), estimated_time_of_arrival date, biker_id integer, updated_at date, order_id integer, primary key (id)
) engine = InnoDB;

alter table order_history
add constraint FK5d0eul6sqp6u2ri0boxuksx5m foreign key (biker_id) references bikers (id);

alter table order_history
add constraint FKnw2ljd8jnpdc9y2ild52e79t2 foreign key (order_id) references orders (order_id);