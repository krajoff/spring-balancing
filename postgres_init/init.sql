CREATE SCHEMA IF NOT EXISTS balancing;
USE balancing;

INSERT INTO users (username, password, role, roles)
VALUES ('1', '1', 'USER', '1@1.ru', )

COPY inflation_data
FROM '/docker-entrypoint-initdb.d/inflation.csv'
DELIMITER ','
CSV HEADER;