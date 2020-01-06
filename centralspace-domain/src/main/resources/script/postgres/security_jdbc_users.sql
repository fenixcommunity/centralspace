-- helpers:

-- delete from authorities;
-- delete from users;
-- delete from persistent_logins;
-- delete from groups;
-- delete from group_members;
-- delete from group_authorities;
--
-- select * from users;
-- select * from authorities;
-- select * from persistent_logins;
-- select * from groups;
-- select * from group_members;
-- select * from group_authorities;
--
-- insert into users(username, password, enabled)
-- VALUES ('DB_USER','$2a$10$NBGo.1m0MIf15nigGSchYOMs7B30GvPD2Okv0uRjFmlM8UkkMCy66', true);
-- insert into authorities(username, authority) VALUES ('DB_USER', 'ROLE_DB_USER');
--
-- insert into groups(id,group_name) values (2,'ROLE_DB_USER');
-- insert into group_members(id,username, group_id) VALUES (3,'DB_USER', 2);
-- insert into group_authorities(group_id, authority) VALUES (2, 'ROLE_DB_USER');
-- insert into group_authorities(group_id, authority) VALUES (2, 'ROLE_SWAGGER');

select * from users;
