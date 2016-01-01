use courier;

delete from cargo_address;
delete from address_addressdetails;
delete from addressdetails;
delete from address;
delete from cargo;
delete from item;
delete from restaurant;

ALTER TABLE cargo_address AUTO_INCREMENT = 1;
ALTER TABLE address_addressdetails AUTO_INCREMENT = 1;
ALTER TABLE addressdetails AUTO_INCREMENT = 1;
ALTER TABLE address AUTO_INCREMENT = 1;
ALTER TABLE cargo AUTO_INCREMENT = 1;
ALTER TABLE item AUTO_INCREMENT = 1;
ALTER TABLE restaurant AUTO_INCREMENT = 1;

insert into restaurant (name, address, phone) values ("Rozsdás Rákolló", "Debrecen, Piac utca 17", "+36202050010");
insert into restaurant (name, address, phone) values ("Kaja tanya", "Debrecen, Bethlen utca 46", "+36205780066");
insert into restaurant (name, address, phone) values ("Csodajó Falatozó", "Debrecen, Böszörményi út 138", "+36202050020");
insert into restaurant (name, address, phone) values ("Gastro-Bistró", "Debrecen, Hatvan utca 2", "+36205780066");

insert into restaurant (name, address, phone) values ("Lucullus Étterem", "Piac u. 41", "+36202050011");
insert into restaurant (name, address, phone) values ("Pálma Étterem", "Simonyi út 44", "+36202050012");
insert into restaurant (name, address, phone) values ("Barabás Étterem", "Vár u. 11", "+36202050013");
insert into restaurant (name, address, phone) values ("Calico Jack Pub", "Bem tér 15", "+36202050014");
insert into restaurant (name, address, phone) values ("Viktória Étterem", "Oláh Gábor u. 3", "+36202050015");
insert into restaurant (name, address, phone) values ("Csokonai Étterem", "Kossuth u. 21", "+36202050016");

insert into address (address, deadline,  payment) values ("Debrecen, Piac utca 68", '2015-12-07 14:36:12',  "Cash");
insert into address (address, deadline,  payment) values ("Debrecen, Böszörményi út 68", '2015-12-07 14:36:12',  "Card");
insert into address (address, deadline,  payment) values ("Debrecen, Bihari utca 25", '2015-12-07 14:36:12', "Voucher");
insert into address (address, deadline,  payment) values ("Debrecen, Kassai út 26", '2015-12-07 14:36:12', "Cash");
insert into address (address, deadline,  payment) values ("Debrecen, Baross Gábor utca 27", '2015-12-07 14:36:12', "SZEP");
insert into address (address, deadline,  payment) values ("Debrecen, Kishatár utca 7", '2015-12-07 14:36:12', "Cash");
insert into address (address, deadline,  payment) values ("Debrecen, Derék utca 31", '2015-12-07 14:36:12', "Card");
insert into address (address, deadline,  payment, status) values ("Debrecen, Zsák utca 3", '2015-12-25 14:36:12',  "Cash", "Delivered");
insert into address (address, deadline,  payment, status) values ("Debrecen, Veres utca 44", '2015-12-25 14:36:12',  "Card", "Delivered");
insert into address (address, deadline,  payment, status) values ("Debrecen, Kishatár utca 6", '2015-12-25 14:36:12',  "SZEP", "Delivered");
insert into address (address, deadline,  payment, status) values ("Debrecen, Csokonai utca 42", '2015-12-25 14:36:12',  "Cash", "Delivered");
insert into address (address, deadline,  payment, status) values ("Debrecen, Jerikó utca 12", '2015-12-25 14:36:12',  "Cash", "Delivered");
insert into address (address, deadline,  payment, status) values ("Debrecen, Egyetem sugárút 99", '2015-12-26 14:36:12',  "Voucher", "Delivered");
insert into address (address, deadline,  payment, status) values ("Debrecen, Kartács utca 22", '2015-12-26 14:36:12',  "Cash", "Delivered");
insert into address (address, deadline,  payment, status) values ("Debrecen, Kartács utca 22", '2015-12-26 14:36:12',  "Card", "Failed");
insert into address (address, deadline,  payment, status) values ("Debrecen, Éden utca 32", '2015-12-27 14:36:12',  "Voucher", "Delivered");
insert into address (address, deadline,  payment, status) values ("Debrecen, Derék utca 12", '2015-12-27 14:36:12',  "Card", "Delivered");
insert into address (address, deadline,  payment, status) values ("Debrecen, Zöld utca 26", '2015-12-27 14:36:12',  "Cash", "Delivered");
insert into address (address, deadline,  payment, status) values ("Debrecen, Kurucz utca 112", '2015-12-27 14:36:12',  "Cash", "Delivered");
insert into address (address, deadline,  payment, status) values ("Debrecen, Hadházi út 151", '2015-12-27 14:36:12',  "Card", "Delivered");
insert into address (address, deadline,  payment, status) values ("Debrecen, Mikszáth Kálmán utca 20", '2015-12-27 14:36:12',  "Cash", "Delivered");
insert into address (address, deadline,  payment, status) values ("Debrecen, Csákány utca 2", '2015-12-27 14:36:12',  "Cash", "Delivered");

insert into item (name, price) values ('Jókai bableves', '1090');
insert into item (name, price) values ('Kecskesajtos rizottógolyó', '2490');
insert into item (name, price) values ('Húsfaló Pizza', '1390');
insert into item (name, price) values ('Málnakrémleves túrógombóccal', '990');
insert into item (name, price) values ('Marhahúsleves', '1190');
insert into item (name, price) values ('Uborkasaláta', '490');
insert into item (name, price) values ('Harcsa brassói', '2290');
insert into item (name, price) values ('Magyaros Pizza', '1590');
insert into item (name, price) values ('Répatorta', '1190');
insert into item (name, price) values ('Görög Salátatál', '1390');
insert into item (name, price) values ('Sajttorta rumos meggyel', '1190');
insert into item (name, price) values ('Sütőtök krémleves', '1390');
insert into item (name, price) values ('Káposztasaláta', '450');
insert into item (name, price) values ('Tanyasi csirkemell', '3590');
insert into item (name, price) values ('Ötsajtos Pizza', '1590');
insert into item (name, price) values ('Gulyásleves', '800');
insert into item (name, price) values ('Zöldborsó főzelék', '450');
insert into item (name, price) values ('vajas kifli', '10');
insert into item (name, price) values ('lekvár', '50');
insert into item (name, price) values ('Krumpli főzelék', '400');
insert into item (name, price) values ('Sült pulykamell', '1190');
insert into item (name, price) values ('Paradicsomleves', '890');
insert into item (name, price) values ('Krumplipüré', '790');
insert into item (name, price) values ('Fűszeres rövidkaraj', '990');
insert into item (name, price) values ('Főtt rizs', '490');
insert into item (name, price) values ('Zöldségleves', '390');
insert into item (name, price) values ('Tojásrántotta', '290');
insert into item (name, price) values ('Paradicsomos káposzta', '790');
insert into item (name, price) values ('Borsóleves', '790');
insert into item (name, price) values ('Rakott karfiol', '1190');
insert into item (name, price) values ('Fasírt', '890');
insert into item (name, price) values ('Frankfurti leves', '1290');
insert into item (name, price) values ('Káposztás tészta', '1190');
insert into item (name, price) values ('Pörkölt', '1390');
insert into item (name, price) values ('Tejszínes kukorica', '890');
insert into item (name, price) values ('Sült krumpli', '990');
insert into item (name, price) values ('Krumplis tészta', '790');
insert into item (name, price) values ('Rántott hús', '890');
insert into item (name, price) values ('Sült csirkemell', '890');
insert into item (name, price) values ('Bolognai spagetti', '1990');
insert into item (name, price) values ('Brokkolifőzelék', '1290');

insert into addressdetails(quantity, item_id) values (1, 1);
insert into addressdetails(quantity, item_id) values (1, 2);
insert into addressdetails(quantity, item_id) values (1, 3);
insert into addressdetails(quantity, item_id) values (1, 4);
insert into addressdetails(quantity, item_id) values (1, 5);
insert into addressdetails(quantity, item_id) values (1, 6);
insert into addressdetails(quantity, item_id) values (1, 7);
insert into addressdetails(quantity, item_id) values (1, 8);
insert into addressdetails(quantity, item_id) values (1, 9);
insert into addressdetails(quantity, item_id) values (1, 10);
insert into addressdetails(quantity, item_id) values (1, 11);
insert into addressdetails(quantity, item_id) values (1, 11);
insert into addressdetails(quantity, item_id) values (1, 13);
insert into addressdetails(quantity, item_id) values (1, 14);
insert into addressdetails(quantity, item_id) values (2, 15);
insert into addressdetails(quantity, item_id) values (1, 16);
insert into addressdetails(quantity, item_id) values (1, 17);
insert into addressdetails(quantity, item_id) values (10, 18);
insert into addressdetails(quantity, item_id) values (1, 19);
insert into addressdetails(quantity, item_id) values (1, 20);
insert into addressdetails(quantity, item_id) values (2, 21);
insert into addressdetails(quantity, item_id) values (1, 22);
insert into addressdetails(quantity, item_id) values (1, 23);
insert into addressdetails(quantity, item_id) values (1, 24);
insert into addressdetails(quantity, item_id) values (2, 25);
insert into addressdetails(quantity, item_id) values (1, 26);
insert into addressdetails(quantity, item_id) values (1, 27);
insert into addressdetails(quantity, item_id) values (3, 28);
insert into addressdetails(quantity, item_id) values (1, 29);
insert into addressdetails(quantity, item_id) values (2, 30);
insert into addressdetails(quantity, item_id) values (12, 31);
insert into addressdetails(quantity, item_id) values (1, 32);
insert into addressdetails(quantity, item_id) values (1, 33);
insert into addressdetails(quantity, item_id) values (1, 34);
insert into addressdetails(quantity, item_id) values (1, 35);
insert into addressdetails(quantity, item_id) values (1, 36);
insert into addressdetails(quantity, item_id) values (1, 37);
insert into addressdetails(quantity, item_id) values (4, 38);
insert into addressdetails(quantity, item_id) values (1, 39);
insert into addressdetails(quantity, item_id) values (1, 40);
insert into addressdetails(quantity, item_id) values (1, 41);



insert into cargo (status, restaurant_id, user_id) values ("Delivered", 1, 3);
insert into address_addressdetails (Address_id, details_id) values (1, 1);
insert into address_addressdetails (Address_id, details_id) values (1, 2);

insert into cargo_address (Cargo_id, addresses_id) values (1, 1);
insert into address_addressdetails (Address_id, details_id) values (5, 11);
insert into address_addressdetails (Address_id, details_id) values (5, 12);
insert into cargo_address (Cargo_id, addresses_id) values (1, 5);

insert into cargo (status, restaurant_id) values ("Free", 2);

insert into address_addressdetails (Address_id, details_id) values (2, 3);
insert into address_addressdetails (Address_id, details_id) values (2, 4);
insert into address_addressdetails (Address_id, details_id) values (2, 5);

insert into cargo_address (Cargo_id, addresses_id) values (2, 2);
insert into address_addressdetails (Address_id, details_id) values (6, 13);
insert into cargo_address (Cargo_id, addresses_id) values (2, 6);
insert into address_addressdetails (Address_id, details_id) values (7, 14);
insert into cargo_address (Cargo_id, addresses_id) values (2, 7);

insert into cargo (status, restaurant_id) values ("Free", 3);
insert into address_addressdetails (Address_id, details_id) values (3, 6);
insert into address_addressdetails (Address_id, details_id) values (3, 7);
insert into address_addressdetails (Address_id, details_id) values (3, 8);
insert into cargo_address (Cargo_id, addresses_id) values (3, 3);

insert into cargo (status, restaurant_id) values ("Free", 4);
insert into address_addressdetails (Address_id, details_id) values (4, 9);
insert into address_addressdetails (Address_id, details_id) values (4, 10);

insert into cargo_address (Cargo_id, addresses_id) values (4, 4);

insert into cargo (status, restaurant_id, user_id) values ("Delivered", 5, 2);
insert into address_addressdetails (Address_id, details_id) values (8, 16);
insert into address_addressdetails (Address_id, details_id) values (8, 17);
insert into cargo_address (Cargo_id, addresses_id) values (5, 8);
insert into address_addressdetails (Address_id, details_id) values (9, 18);
insert into address_addressdetails (Address_id, details_id) values (9, 19);
insert into cargo_address (Cargo_id, addresses_id) values (5, 9);

insert into cargo (status, restaurant_id, user_id) values ("Delivered", 6, 2);
insert into address_addressdetails (Address_id, details_id) values (10, 20);
insert into cargo_address (Cargo_id, addresses_id) values (6, 10);
insert into address_addressdetails (Address_id, details_id) values (11, 21);
insert into address_addressdetails (Address_id, details_id) values (11, 22);
insert into address_addressdetails (Address_id, details_id) values (11, 23);
insert into cargo_address (Cargo_id, addresses_id) values (6, 11);
insert into address_addressdetails (Address_id, details_id) values (12, 24);
insert into address_addressdetails (Address_id, details_id) values (12, 25);
insert into cargo_address (Cargo_id, addresses_id) values (6, 12);

insert into cargo (status, restaurant_id, user_id) values ("Delivered", 7, 2);
insert into address_addressdetails (Address_id, details_id) values (13, 26);
insert into address_addressdetails (Address_id, details_id) values (13, 27);
insert into cargo_address (Cargo_id, addresses_id) values (7, 13);

insert into cargo (status, restaurant_id, user_id) values ("Delivered", 8, 2);
insert into address_addressdetails (Address_id, details_id) values (14, 28);
insert into cargo_address (Cargo_id, addresses_id) values (8, 14);
insert into address_addressdetails (Address_id, details_id) values (15, 29);
insert into cargo_address (Cargo_id, addresses_id) values (8, 15);

insert into cargo (status, restaurant_id, user_id) values ("Delivered", 9, 2);
insert into address_addressdetails (Address_id, details_id) values (16, 30);
insert into cargo_address (Cargo_id, addresses_id) values (9, 16);
insert into address_addressdetails (Address_id, details_id) values (17, 31);
insert into address_addressdetails (Address_id, details_id) values (17, 32);
insert into address_addressdetails (Address_id, details_id) values (17, 33);
insert into cargo_address (Cargo_id, addresses_id) values (9, 17);
insert into address_addressdetails (Address_id, details_id) values (18, 34);
insert into address_addressdetails (Address_id, details_id) values (18, 35);
insert into cargo_address (Cargo_id, addresses_id) values (9, 18);
insert into address_addressdetails (Address_id, details_id) values (19, 36);
insert into cargo_address (Cargo_id, addresses_id) values (9, 19);

insert into cargo (status, restaurant_id, user_id) values ("Delivered", 10, 2);
insert into address_addressdetails (Address_id, details_id) values (20, 37);
insert into cargo_address (Cargo_id, addresses_id) values (10, 20);
insert into address_addressdetails (Address_id, details_id) values (21, 38);
insert into address_addressdetails (Address_id, details_id) values (21, 39);
insert into address_addressdetails (Address_id, details_id) values (21, 40);
insert into address_addressdetails (Address_id, details_id) values (21, 41);
insert into cargo_address (Cargo_id, addresses_id) values (10, 21);
