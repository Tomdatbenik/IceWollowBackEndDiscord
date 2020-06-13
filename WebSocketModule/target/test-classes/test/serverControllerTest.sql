INSERT INTO `user` (`id`, `display_name`, `email`) VALUES (1, 'test user', 'test@test.com');
INSERT INTO server (`id`, `name`, `owner`, `code`) VALUES (4, 'test server', '1', '123');
INSERT INTO `server_users` (`iwserver_id`, `users_id`) VALUES (4,1);
INSERT INTO server (`id`, `name`, `owner`, `code`) VALUES (5, 'test server', '1', '512');
INSERT INTO server (`id`, `name`, `owner`, `code`) VALUES (6, 'test server', '1', '561');