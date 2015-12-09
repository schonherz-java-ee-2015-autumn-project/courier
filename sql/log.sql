use courier;


CREATE TABLE log
(
id bigint primary key auto_increment,
user_id bigint NOT NULL,
sessionId text not null,
loginDate datetime,
logoutDate datetime,
FOREIGN KEY (user_id) REFERENCES user(id)
)