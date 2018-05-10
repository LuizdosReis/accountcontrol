ALTER TABLE category
  DROP COLUMN IF EXISTS user_id;
DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
  id       BIGSERIAL,
  username VARCHAR(50)  NOT NULL,
  name     VARCHAR(256) NOT NULL,
  password VARCHAR(256) NOT NULL,
  CONSTRAINT users_pk PRIMARY KEY (id),
  CONSTRAINT users_u_username UNIQUE (username)
);

ALTER TABLE category
  ADD COLUMN IF NOT EXISTS user_id BIGINT NOT NULL;

ALTER TABLE category
  ADD CONSTRAINT category_fk_user FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE role
(
  id   BIGSERIAL,
  name VARCHAR(50) NOT NULL,
  CONSTRAINT role_pk PRIMARY KEY (id)
);

CREATE TABLE users_roles
(
  user_id  BIGINT NOT NULL,
  roles_id BIGINT NOT NULL,
  CONSTRAINT users_roles_pk PRIMARY KEY (user_id, roles_id),
  CONSTRAINT users_roles_fk_client FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT users_roles_fk_roles FOREIGN KEY (roles_id) REFERENCES role (id)
);