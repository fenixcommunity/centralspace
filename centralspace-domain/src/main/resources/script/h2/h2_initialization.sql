-- DROP
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS group_authorities;
DROP TABLE IF EXISTS group_members;

-- CREATE
CREATE TABLE users
(
    username VARCHAR(50)  NOT NULL,
    password VARCHAR(400) NOT NULL,
    enabled  BOOLEAN      NOT NULL DEFAULT true,
    PRIMARY KEY (username)
);
--
CREATE TABLE authorities
(
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username)
);
--
CREATE UNIQUE INDEX ix_auth_username
    on authorities (username, authority);
--
create table groups
(
    id         bigint generated by default as identity (start with 1) primary key,
    group_name varchar(50) not null
);
--
create table group_authorities
(
    group_id  bigint      not null,
    authority varchar(50) not null,
    constraint fk_group_authorities_group foreign key (group_id) references groups (id)
);
--
create table group_members
(
    id       bigint generated by default as identity (start with 1) primary key,
    username varchar(50) not null,
    group_id bigint      not null,
    constraint fk_group_members_group foreign key (group_id) references groups (id)
);

-- INSERT
insert into users(username, password, enabled)
VALUES ('db_user', '$2a$10$NBGo.1m0MIf15nigGSchYOMs7B30GvPD2Okv0uRjFmlM8UkkMCy66', true);
--
insert into authorities(username, authority)
VALUES ('db_user', 'ROLE_DB_USER');
--
insert into groups(id, group_name)
values (2, 'ROLE_DB_USER');
--
insert into group_members(id, username, group_id)
VALUES (3, 'db_user', 2);
--
insert into group_authorities(group_id, authority)
VALUES (2, 'ROLE_DB_USER');
insert into group_authorities(group_id, authority)
VALUES (2, 'ROLE_SWAGGER');

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