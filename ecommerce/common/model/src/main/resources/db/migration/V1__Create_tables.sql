CREATE TABLE products (
          id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
          product_data_id varchar(255),
          brand varchar(64),
          created_at date,
          updated_at date
);

CREATE TABLE shoppers (
          id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
          shopper_data_id varchar(255),
          created_at date,
          updated_at date
);

CREATE TABLE products_categories (
         id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
         product_id bigint,
         category varchar(64),
         created_at date,
         updated_at date
);

CREATE TABLE scores (
        id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
        shopper_id bigint,
        product_id bigint,
        score double,
        created_at date,
        updated_at date
);

CREATE UNIQUE INDEX PRODUCT_DATA_ID ON products (product_data_id);
CREATE UNIQUE INDEX SHOPPER_DATA_ID ON shoppers (shopper_data_id);
CREATE INDEX CATEGORY ON products_categories (category);