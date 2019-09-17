-- auto Generated on 2019-08-12
-- DROP TABLE IF EXISTS my_user;
CREATE TABLE my_user(
	id INT (11) NOT NULL AUTO_INCREMENT COMMENT 'id',
	login_name VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'loginName',
	`password` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'password',
	user_name VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'userName',
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'my_user';
