use courier;

create table supplier(
id bigint primary key auto_increment,
name text,
address text,
email text,
phone text
);

insert into supplier (name, address, email, phone) values ('ExpressIt', 'Gyorsposta utca 12', 'expressit@gmail.com', '+36202020020');

create table user_supplier(
id bigint primary key auto_increment,
User_id bigint,
supplier_id bigint,
foreign key (supplier_id) references supplier(id), 
foreign key (User_id) references user(id)
);

insert into user_supplier (User_id, supplier_id) values (1,1);
/*alter table user add column supplier bigint, add foreign key (supplier) references supplier(id);*/
update user set supplier_id = 1 where id = 1;