use courier;

insert into address (address, deadline,  payment, status) values ("Debrecen, Piac utca 68", '2015-12-07 14:36:12',  1, 1);
insert into address (address, deadline,  payment, status) values ("Debrecen, Böszörményi út 68", '2015-12-07 14:36:12',  3, 1);
insert into item (name, price, quantity) values ('Csirke', '1400', '1');
insert into item (name, price, quantity) values ('Kolbász', '500', '1');
insert into item (name, price, quantity) values ('Pizza', '1450', '1');
insert into item (name, price, quantity) values ('Bab gulyás', '800', '1');
insert into item (name, price, quantity) values ('Rakott krumpli', '900', '1');
insert into item (name, price, quantity) values ('Paradicsom leves', '400', '1');
insert into restaurant (name, address, phone) values ("Rozsdás Rákolló", "Debrecen, Piac utca 17", "+36202050010");
insert into restaurant (name, address, phone) values ("Kaja tanya", "Debrecen, Bethlen utca 46", "+36205780066");
insert into cargo (status, restaurant_id) values (1, 1);
insert into address_item (Address_id, items_id) values (1, 1);
insert into address_item (Address_id, items_id) values (1, 2);
insert into cargo_address (Cargo_id, addresses_id) values (1, 1);

insert into cargo (status, restaurant_id) values (1, 2);
insert into address_item (Address_id, items_id) values (2, 3);
insert into address_item (Address_id, items_id) values (2, 4);
insert into address_item (Address_id, items_id) values (2, 5);
insert into cargo_address (Cargo_id, addresses_id) values (2, 2);
insert into address (address, deadline,  payment, status) values ("Debrecen, Füredi út 95", '2015-12-07 14:36:12', 4, 1);
insert into address_item (Address_id, items_id) values (3, 6);
insert into cargo_address (Cargo_id, addresses_id) values (2, 3);