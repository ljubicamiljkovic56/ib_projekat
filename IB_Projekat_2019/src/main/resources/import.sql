INSERT INTO USERS (id, password, certificate, email, active) VALUES (1, 'user1', 'cer1', 'user1@example.com', true);
INSERT INTO USERS (id, password, certificate, email, active) VALUES (2, 'user2', 'cer2', 'user2@example.com', true);

INSERT INTO USER_AUTHORITY (user_id, name) VALUES (1, 'Regular');
INSERT INTO USER_AUTHORITY (user_id, name) VALUES (2, 'Admin');
