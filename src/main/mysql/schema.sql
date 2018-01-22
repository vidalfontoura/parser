CREATE DATABASE wallet_hub;

CREATE TABLE wallet_hub.log (
	id int NOT NULL AUTO_INCREMENT,
    date DATETIME,
    ip VARCHAR(255),
    request VARCHAR(255),
    status INT,
    agent VARCHAR(255),
    PRIMARY KEY (ID)
);