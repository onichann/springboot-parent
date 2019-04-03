CREATE TABLE db.tb_user
(
    id bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    login_name varchar(255),
    password varchar(255),
    username varchar(255)
);
INSERT INTO db.tb_user (id, login_name, password, username) VALUES (1, 'admin', '496f616f747cb39d1ee17c4286c0b8273dc52d24014337ed2421988df7956f6ee0dbcdd35b3d895d', '管理员');
INSERT INTO db.tb_user (id, login_name, password, username) VALUES (2, 'user', '1df8d4cbb8562c0fe30d35c0e03b01703467d6aa007485edf7858110c0b4b13afb25f28c4353a0c5', '用户');