INSERT INTO roles (name) VALUES 'User';
INSERT INTO roles (name) VALUES 'Creator';

INSERT INTO users VALUES (1, 'user1@mail.com', 'User 1', '$2a$10$sDM60diK4x0xIltTV9cHkeQaz8RfWgfnRDx0OhR9DK1/yN31y7yrm', null, null, 'user1');
INSERT INTO users VALUES (10, 'faristest@test.com', 'faris test', 'passsssssssssssssssssssssssssssssssssssssssss', NULL, NULL, 'faristest');
INSERT INTO users VALUES (11, 'faristest2@test.com', 'faris test 2', 'passsssssssssssssssssssssssssssssssssssssssss', NULL, NULL, 'faristest2');
INSERT INTO users VALUES (12, 'adimastest@gmail.com', 'adimastest', 'passsssssssssssssssssssssssssssssssssssssssss', NULL, NULL, 'adimastest');
INSERT INTO users VALUES (16, 'aaaaa@mail.com', 'aaaaaaiai', 'passsssssssssssssssssssssssssssssssssssssssss', NULL, NULL, 'testingggg');
INSERT INTO users VALUES (17, 'laptophp@gmail.com', 'laptoppp', 'passsssssssssssssssssssssssssssssssssssssssss', NULL, NULL, 'laptophp');

INSERT INTO tags VALUES (1, 'breakfast');
INSERT INTO tags VALUES (2, 'light meal');
INSERT INTO tags VALUES (3, 'indonesia seafood');
INSERT INTO tags VALUES (4, 'western');

INSERT INTO recipes VALUES (1, NULL, NULL, 'string', '2022-06-09', 'string', true, 'string', 'string', 'string', 'string', 0, 1);

INSERT INTO recipes_tags VALUES (1, 1);
INSERT INTO recipes_tags VALUES (1, 3);


INSERT INTO users_follows VALUES (10, 1);
INSERT INTO users_follows VALUES (17, 1);
INSERT INTO users_follows VALUES (11, 1);
INSERT INTO users_follows VALUES (16, 1);

INSERT INTO users_roles VALUES (1, 1);
INSERT INTO users_roles VALUES (1, 2);
INSERT INTO users_roles VALUES (10, 1);
INSERT INTO users_roles VALUES (11, 1);
INSERT INTO users_roles VALUES (11, 2);
INSERT INTO users_roles VALUES (12, 1);
INSERT INTO users_roles VALUES (16, 1);
INSERT INTO users_roles VALUES (17, 1);

