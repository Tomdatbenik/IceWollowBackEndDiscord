INSERT INTO `user` (`id`, `display_name`, `email`) VALUES (1, 'test user', 'test@test.com');
INSERT INTO server (`id`, `name`, `owner`, `code`) VALUES (1, 'test server', '1', '123');
INSERT INTO voice_channel(`id`, `name`) VALUES(1,'test');
INSERT INTO server_voice_channels(`voice_channels`, `id`) VALUES(1,1);
INSERT INTO server (`id`, `name`, `owner`, `code`) VALUES (2, 'test server', '1', '512');
INSERT INTO server (`id`, `name`, `owner`, `code`) VALUES (3, 'test server', '1', '561');