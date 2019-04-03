CREATE TABLE db.tb_role
(
    id bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    authority varchar(255)
);
INSERT INTO db.tb_role (id, authority) VALUES (1, 'ROLE_ADMIN');
INSERT INTO db.tb_role (id, authority) VALUES (2, 'ROLE_DBA');
INSERT INTO db.tb_role (id, authority) VALUES (3, 'ROLE_USER');