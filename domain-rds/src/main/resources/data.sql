/*
    The user's password is a combination of the user name and 'password' string.
    For example, the password for user 'user0@email.com' is 'user0_password'.
*/

INSERT INTO user_tb (email, password, username, login_count, last_login_at, created_at)
VALUES ('user1@email.com', '$2y$04$l69hV6oe73jyyqG4loIxn.CPllSCmtK1rm1DqaAzyGNQa5OjIcAO.', 'USER1', 3,
        TIMESTAMP '2022-12-30 12:00:00', TIMESTAMP '2022-08-20 12:00:00');
INSERT INTO user_tb (email, password, username, login_count, last_login_at, created_at)
VALUES ('user2@email.com', '$2y$04$TTY2vK2XBaaeJ.y7KFSxduLRqEgWG0GGfIxckxfvhJF6raTFtjNee', 'USER2', 1,
        TIMESTAMP '2023-01-16 13:30:00', TIMESTAMP '2022-09-21 13:30:00');
INSERT INTO user_tb (email, password, username, login_count, last_login_at, created_at)
VALUES ('user3@email.com', '$2y$04$Tc6lbiW40YQsNIMthnUQ2.KDepu9S99.QBK9KBrWiB0gkaikkNteG', 'USER3', 5,
        TIMESTAMP '2022-01-11 09:00:00', TIMESTAMP '2022-10-11 09:00:00');
INSERT INTO user_tb (email, password, username, login_count, last_login_at, created_at)
VALUES ('user4@email.com', '$2y$04$dvoBUkubgojxmYJ2/0r/eO/o456NP5sPOfwh7BJ1CK2BiWGGBI2Sm', 'USER4', 3,
        TIMESTAMP '2022-12-06 19:20:00', TIMESTAMP '2022-12-03 19:20:00');
INSERT INTO user_tb (email, password, username, login_count, last_login_at, created_at)
VALUES ('user5@email.com', '$2y$04$42Qk3gJwKjhddpFQjZXXpe8hU01453cIMVyx5Qqndg.iEl3x/TnOq', 'USER5', 6,
        null, TIMESTAMP '2022-12-29 14:50:00');

INSERT INTO user_roles_tb (user_id, role)
VALUES (1, 'USER');
INSERT INTO user_roles_tb (user_id, role)
VALUES (2, 'USER');
INSERT INTO user_roles_tb (user_id, role)
VALUES (3, 'USER');
INSERT INTO user_roles_tb (user_id, role)
VALUES (4, 'USER');
INSERT INTO user_roles_tb (user_id, role)
VALUES (5, 'USER');

INSERT INTO ebook_tb (user_id, uuid, name, type, download_count, created_at, expired_at, is_exist)
VALUES (1, '550e8400-e29b-41d4-a716-446655440001', 'ebook-1', 'EPUB2', 3, TIMESTAMP '2022-11-29 12:00:00',
        TIMESTAMP '2022-12-29 12:00:00', false);
INSERT INTO ebook_tb (user_id, uuid, name, type, download_count, created_at, expired_at, is_exist)
VALUES (2, '550e8400-e29b-41d4-a716-446655440002', 'ebook-2', 'EPUB2', 2, TIMESTAMP '2022-11-30 12:30:00',
        TIMESTAMP '2023-12-30 12:30:00', false);
INSERT INTO ebook_tb (user_id, uuid, name, type, download_count, created_at, expired_at, is_exist)
VALUES (4, '550e8400-e29b-41d4-a716-446655440003', 'ebook-3', 'EPUB2', 1, TIMESTAMP '2022-12-05 17:00:00',
        TIMESTAMP '2023-01-04 17:00:00', false);
INSERT INTO ebook_tb (user_id, uuid, name, type, download_count, created_at, expired_at, is_exist)
VALUES (2, '550e8400-e29b-41d4-a716-446655440004', 'ebook-4', 'EPUB2', 5, TIMESTAMP '2022-12-08 08:20:00',
        TIMESTAMP '2023-01-07 08:20:00', false);
INSERT INTO ebook_tb (user_id, uuid, name, type, download_count, created_at, expired_at, is_exist)
VALUES (3, '550e8400-e29b-41d4-a716-446655440005', 'ebook-5', 'EPUB2', 0, TIMESTAMP '2023-01-10 20:10:00',
        TIMESTAMP '2023-02-09 20:10:00', true);
INSERT INTO ebook_tb (user_id, uuid, name, type, download_count, created_at, expired_at, is_exist)
VALUES (4, '550e8400-e29b-41d4-a716-446655440006', 'ebook-6', 'EPUB2', 1, TIMESTAMP '2023-01-12 16:40:00',
        TIMESTAMP '2023-02-11 16:40:00', true);
INSERT INTO ebook_tb (user_id, uuid, name, type, download_count, created_at, expired_at, is_exist)
VALUES (2, '550e8400-e29b-41d4-a716-446655440007', 'ebook-7', 'EPUB2', 2, TIMESTAMP '2023-01-14 13:20:00',
        TIMESTAMP '2023-02-13 13:20:00', true);
