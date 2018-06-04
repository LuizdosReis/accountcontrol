DROP TABLE IF EXISTS account;

CREATE TABLE account
(
  id BIGSERIAL,
  description VARCHAR (50) NOT NULL,
  balance NUMERIC NOT NULL,
  user_id BIGINT NOT NULL,
  CONSTRAINT account_pk PRIMARY KEY (id),
  CONSTRAINT account_fk_client FOREIGN KEY (user_id) REFERENCES users (id)
);