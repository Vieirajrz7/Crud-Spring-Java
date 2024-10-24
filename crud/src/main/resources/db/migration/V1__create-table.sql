CREATE TABLE product (
    id BIGINT PRIMARY KEY NOT NULL UNIQUE,
    name TEXT NOT NULL,
    price_in_cents INT NOT NULL 
);