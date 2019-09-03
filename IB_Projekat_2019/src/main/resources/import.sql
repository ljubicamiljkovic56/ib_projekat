DROP SCHEMA IF EXISTS ib;
CREATE SCHEMA ib DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE ib;

CREATE TABLE users(
	id INT AUTO_INCREMENT,
    username VARCHAR(10) NOT NULL,
	password VARCHAR(10) NOT NULL, 
	certificate VARCHAR(30) NOT NULL, 
	email VARCHAR(20) NOT NULL, 
	active TINYINT(1),
	authority VARCHAR(10) NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO USERS (id, username, password, certificate, email, active, authority) VALUES (1, 'user1','user1', 'cer1', 'user1@example.com', 0, 'Regular');
INSERT INTO USERS (id, username, password, certificate, email, active, authority) VALUES (2, 'user2','user2', 'cer2', 'user2@example.com', 1, 'Admin');

CREATE TABLE userauthority(
	id INT AUTO_INCREMENT,
	name VARCHAR(10),
    PRIMARY KEY(id)
);
INSERT INTO USERAUTHORITY (id, name) VALUES (1, 'Regular');
INSERT INTO USERAUTHORITY (id, name) VALUES (2, 'Admin');

CREATE TABLE UA (
	userid INT,
    authorityid INT,
    FOREIGN KEY (userid) REFERENCES users(id),
	FOREIGN KEY (authorityid) REFERENCES userauthority(id)
);
INSERT INTO UA (userid, authorityid) VALUES (1, 1);