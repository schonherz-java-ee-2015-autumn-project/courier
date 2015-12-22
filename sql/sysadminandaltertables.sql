/*create sysadmin*/

use courier;
insert into user(fullname,username) values ("sysadmin","sysadmin");
delete from user_role where user_id = 1;
delete from user where id = 1;
update user set id = 1 where username="sysadmin";