CREATE TABLE db.tb_user_role
(
    user_id bigint(20) NOT NULL,
    role_id bigint(20) NOT NULL
);
CREATE INDEX FK7vn3h53d0tqdimm8cp45gc0kl ON db.tb_user_role (user_id);
CREATE INDEX FKea2ootw6b6bb0xt3ptl28bymv ON db.tb_user_role (role_id);
INSERT INTO db.tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO db.tb_user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO db.tb_user_role (user_id, role_id) VALUES (2, 3);