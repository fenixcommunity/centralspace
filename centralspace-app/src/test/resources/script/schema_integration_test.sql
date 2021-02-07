--ADMIN TEST
insert into account(id, login, mail, creation_date, update_date, blocked, address_id)
select 99999991, 'ADMIN_TEST', 'admin@mail.com', NOW(), NOW(), false, (select id from address limit 1)
WHERE NOT EXISTS (SELECT 1 FROM account WHERE login = 'ADMIN_TEST');

insert into password(id ,password, password_type, creation_date, update_date, account_id)
select 99999993, '{bcrypt}$2a$10$wqjJ5OIemcMPg./n5.jBnOlj4DmIWOcPxjVkS2KY5vmjZS0Wycb4C',
       'TO_CENTRALSPACE', NOW(), NOW(), (select id FROM account WHERE login = 'ADMIN_TEST')
    WHERE NOT EXISTS (SELECT 1 FROM password p
    join account a on a.id = p.account_id
    WHERE a.login = 'ADMIN_TEST');

insert into role(id, name, creation_date, update_date)
select 9999981,'ROLE_ADMIN', NOW(), NOW()
    WHERE NOT EXISTS (SELECT 1 FROM role WHERE name='ROLE_ADMIN');

insert into role(id, name, creation_date, update_date)
select 9999982, 'ROLE_CREATE_ACCOUNT', NOW(), NOW()
    WHERE NOT EXISTS (SELECT 1 FROM role WHERE name='ROLE_CREATE_ACCOUNT');

insert into role(id, name, creation_date, update_date)
select 9999983, 'ROLE_FLUX_GETTER', NOW(), NOW()
    WHERE NOT EXISTS (SELECT 1 FROM role WHERE name='ROLE_FLUX_GETTER');

insert into role(id, name, creation_date, update_date)
select 9999984, 'ROLE_FLUX_EDITOR', NOW(), NOW()
    WHERE NOT EXISTS (SELECT 1 FROM role WHERE name='ROLE_FLUX_EDITOR');

insert into role_group(id, name, creation_date, update_date)
select 9999971, 'ADMIN_USER', NOW(), NOW()
    WHERE NOT EXISTS (SELECT 1 FROM role_group WHERE name='ADMIN_USER');

insert into role_to_role_group(role_group_id, role_id)
select (select id from role_group where name='ADMIN_USER'), (select id from role where name='ROLE_CREATE_ACCOUNT')
    WHERE NOT EXISTS (
    SELECT 1 FROM role_to_role_group
    join role r on r.id = role_to_role_group.role_id
    join role_group rg on rg.id = role_to_role_group.role_group_id
    WHERE rg.name='ADMIN_USER' and r.name='ROLE_CREATE_ACCOUNT');

insert into role_to_role_group(role_group_id, role_id)
select (select id from role_group where name='ADMIN_USER'), (select id from role where name='ROLE_ADMIN')
    WHERE NOT EXISTS (
    SELECT 1 FROM role_to_role_group
    join role r on r.id = role_to_role_group.role_id
    join role_group rg on rg.id = role_to_role_group.role_group_id
    WHERE rg.name='ADMIN_USER' and r.name='ROLE_ADMIN');

insert into role_to_role_group(role_group_id, role_id)
select (select id from role_group where name='ADMIN_USER'), (select id from role where name='ROLE_FLUX_GETTER')
    WHERE NOT EXISTS (
    SELECT 1 FROM role_to_role_group
    join role r on r.id = role_to_role_group.role_id
    join role_group rg on rg.id = role_to_role_group.role_group_id
    WHERE rg.name='ADMIN_USER' and r.name='ROLE_FLUX_GETTER');

insert into role_to_role_group(role_group_id, role_id)
select (select id from role_group where name='ADMIN_USER'), (select id from role where name='ROLE_FLUX_EDITOR')
    WHERE NOT EXISTS (
    SELECT 1 FROM role_to_role_group
    join role r on r.id = role_to_role_group.role_id
    join role_group rg on rg.id = role_to_role_group.role_group_id
    WHERE rg.name='ADMIN_USER' and r.name='ROLE_FLUX_EDITOR');

insert into account_to_role_group(account_id,role_group_id)
select (select id from account where login = 'ADMIN_TEST'), (select id from role_group where name='ADMIN_USER')
    WHERE NOT EXISTS (SELECT 1 FROM account_to_role_group
    join account a on a.id = account_to_role_group.account_id
    join role_group rg on rg.id = account_to_role_group.role_group_id
                  where login = 'ADMIN_TEST' and rg.name='ADMIN_USER');


-- BASIC_USER_TEST
insert into account(id, login, mail, creation_date, update_date, blocked, address_id)
select 89999991, 'BASIC_USER_TEST', 'basic@mail.com', NOW(), NOW(), false, (select id from address limit 1)
WHERE NOT EXISTS (SELECT 1 FROM account WHERE login = 'BASIC_USER_TEST');

insert into password(id ,password, password_type, creation_date, update_date, account_id)
select 89999993, '{bcrypt}$2a$10$wqjJ5OIemcMPg./n5.jBnOlj4DmIWOcPxjVkS2KY5vmjZS0Wycb4C', -- password1212@oqBB
       'TO_CENTRALSPACE', NOW(), NOW(), (select id FROM account WHERE login = 'BASIC_USER_TEST')
    WHERE NOT EXISTS (SELECT 1 FROM password p
    join account a on a.id = p.account_id
    WHERE a.login = 'BASIC_USER_TEST');

insert into role(id, name, creation_date, update_date)
select 8999981,'ROLE_BASIC', NOW(), NOW()
    WHERE NOT EXISTS (SELECT 1 FROM role WHERE name='ROLE_BASIC');

insert into role_group(id, name, creation_date, update_date)
select 8999971, 'BASIC_USER', NOW(), NOW()
    WHERE NOT EXISTS (SELECT 1 FROM role_group WHERE name='BASIC_USER');

insert into role_to_role_group(role_group_id, role_id)
select (select id from role_group where name='BASIC_USER'), (select id from role where name='ROLE_BASIC')
    WHERE NOT EXISTS (
    SELECT 1 FROM role_to_role_group
    join role r on r.id = role_to_role_group.role_id
    join role_group rg on rg.id = role_to_role_group.role_group_id
    WHERE rg.name='BASIC_USER' and r.name='ROLE_BASIC');

insert into account_to_role_group(account_id,role_group_id)
select (select id from account where login = 'BASIC_USER_TEST'), (select id from role_group where name='BASIC_USER')
    WHERE NOT EXISTS (SELECT 1 FROM account_to_role_group
    join account a on a.id = account_to_role_group.account_id
    join role_group rg on rg.id = account_to_role_group.role_group_id
                  where login = 'BASIC_USER_TEST' and rg.name='BASIC_USER');

-- DELETE * from password where id in(99999993, 89999993)
-- DELETE * from account where id in(99999991, 89999991)
-- DELETE * from account_to_role_group where account_id in(99999991, 89999991)
