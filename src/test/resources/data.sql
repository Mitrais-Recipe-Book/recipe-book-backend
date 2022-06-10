INSERT INTO roles (name) VALUES 'User';
INSERT INTO roles (name) VALUES 'Creator';

INSERT INTO users (email, full_name, password, profile_photo, profile_photo_type, username) 
VALUES('user1@mail.com', 'User 1', '$2a$10$sDM60diK4x0xIltTV9cHkeQaz8RfWgfnRDx0OhR9DK1/yN31y7yrm', null, null, 'user1');

INSERT INTO recipes (banner_image, banner_image_type, content, date_created, ingredients, is_draft, overview, title, title_lower_variant, video_url, views, user_id) 
VALUES(NULL, NULL, 'string', '2022-06-09', 'string', true, 'string', 'string', 'string', 'string', 0, 1);

