DROP TABLE IF EXISTS category;

CREATE TABLE category
(
  id BIGSERIAL,
  description VARCHAR (50) NOT NULL,
  type VARCHAR(3) NOT NULL,
  CONSTRAINT category_pk PRIMARY KEY (id)
);