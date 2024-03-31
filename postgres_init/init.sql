CREATE SCHEMA IF NOT EXISTS balancing;
USE balancing;

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL NOT NULL,
    email VARCHAR(255),
    password VARCHAR(255),
    priopity INTEGER,
    role VARCHAR(255) check (role in ('ADMIN', 'USER')),
    roles VARCHAR(255),
    username VARCHAR(255),
    primary key (id));

INSERT INTO users (username, password, role, roles) VALUES ()

COPY inflation_data
FROM '/docker-entrypoint-initdb.d/inflation.csv'
DELIMITER ','
CSV HEADER;