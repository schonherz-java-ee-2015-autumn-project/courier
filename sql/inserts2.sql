use courier;

delete from cargo_address;
delete from address_item;
delete from address;
delete from cargo;
delete from item;
delete from restaurant;

ALTER TABLE cargo_address AUTO_INCREMENT = 1;
ALTER TABLE address_item AUTO_INCREMENT = 1;
ALTER TABLE address AUTO_INCREMENT = 1;
ALTER TABLE cargo AUTO_INCREMENT = 1;
ALTER TABLE item AUTO_INCREMENT = 1;
ALTER TABLE restaurant AUTO_INCREMENT = 1;

insert into address (address, deadline,  payment) values ("Debrecen, Piac utca 68", '2015-12-07 14:36:12',  "Készpénz");
insert into address (address, deadline,  payment) values ("Debrecen, Böszörményi út 68", '2015-12-07 14:36:12',  "Bankkártya");
insert into address (address, deadline,  payment) values ("Debrecen, Bihari utca 25", '2015-12-07 14:36:12', "Utalvány");
insert into address (address, deadline,  payment) values ("Debrecen, Kassai út 26", '2015-12-07 14:36:12', "Készpénz");
insert into address (address, deadline,  payment) values ("Debrecen, Baross Gábor utca 27", '2015-12-07 14:36:12', "SZÉP");
insert into address (address, deadline,  payment) values ("Debrecen, Kishatár utca 7", '2015-12-07 14:36:12', "Készpénz");
insert into address (address, deadline,  payment) values ("Debrecen, Derék utca 31", '2015-12-07 14:36:12', "Bankkártya");

insert into restaurant (name, address, phone) values ("Rozsdás Rákolló", "Debrecen, Piac utca 17", "+36202050010");
insert into restaurant (name, address, phone) values ("Kaja tanya", "Debrecen, Bethlen utca 46", "+36205780066");
insert into restaurant (name, address, phone) values ("Csodajó Falatozó", "Debrecen, Böszörményi út 138", "+36202050020");
insert into restaurant (name, address, phone) values ("Gastro-Bistró", "Debrecen, Hatvan utca 2", "+36205780066");

insert into item (name, price, quantity) values ('Jókai bableves', '1090', '1');
insert into item (name, price, quantity) values ('Kecskesajtos rizottógolyó', '2490', '1');
insert into item (name, price, quantity) values ('Húsfaló Pizza', '1390', '1');
insert into item (name, price, quantity) values ('Málnakrémleves túrógombóccal', '990', '1');
insert into item (name, price, quantity) values ('Marhahúsleves', '1190', '1');
insert into item (name, price, quantity) values ('Uborkasaláta', '490', '1');
insert into item (name, price, quantity) values ('Harcsa brassói', '2290', '1');
insert into item (name, price, quantity) values ('Magyaros Pizza', '1590', '1');
insert into item (name, price, quantity) values ('Répatorta', '1190', '1');
insert into item (name, price, quantity) values ('Görög Salátatál', '1390', '1');
insert into item (name, price, quantity) values ('Sajttorta rumos meggyel', '1190', '1');
insert into item (name, price, quantity) values ('Sütőtök krémleves', '1390', '1');
insert into item (name, price, quantity) values ('Káposztasaláta', '450', '1');
insert into item (name, price, quantity) values ('Tanyasi csirkemell', '3590', '1');
insert into item (name, price, quantity) values ('Ötsajtos Pizza', '1590', '1');





insert into cargo (status, restaurant_id) values ("Szabad", 1);
insert into address_item (Address_id, items_id) values (1, 1);
insert into address_item (Address_id, items_id) values (1, 2);
insert into cargo_address (Cargo_id, addresses_id) values (1, 1);
insert into address_item (Address_id, items_id) values (5, 11);
insert into address_item (Address_id, items_id) values (5, 12);
insert into cargo_address (Cargo_id, addresses_id) values (1, 5);

insert into cargo (status, restaurant_id) values ("Szabad", 2);
insert into address_item (Address_id, items_id) values (2, 3);
insert into address_item (Address_id, items_id) values (2, 4);
insert into address_item (Address_id, items_id) values (2, 5);
insert into cargo_address (Cargo_id, addresses_id) values (2, 2);
insert into address_item (Address_id, items_id) values (6, 13);
insert into cargo_address (Cargo_id, addresses_id) values (2, 6);
insert into address_item (Address_id, items_id) values (7, 14);
insert into cargo_address (Cargo_id, addresses_id) values (2, 7);


insert into cargo (status, restaurant_id) values ("Szabad", 3);
insert into address_item (Address_id, items_id) values (3, 6);
insert into address_item (Address_id, items_id) values (3, 7);
insert into address_item (Address_id, items_id) values (3, 8);
insert into cargo_address (Cargo_id, addresses_id) values (3, 3);


insert into cargo (status, restaurant_id) values ("Szabad", 4);
insert into address_item (Address_id, items_id) values (4, 9);
insert into address_item (Address_id, items_id) values (4, 10);
insert into cargo_address (Cargo_id, addresses_id) values (4, 4);


















