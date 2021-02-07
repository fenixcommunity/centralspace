insert into address(id, country, creation_date, update_date)
select 88, 'Poland', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM address WHERE id = 88);

insert into account(id, login, mail, creation_date, update_date, address_id, blocked)
select 99, 'LOGINQUERY', 'text@mail.com', NOW(), NOW(), 88, false
WHERE NOT EXISTS (SELECT 1 FROM account WHERE id = 99);

insert into account(id, login, mail, creation_date, update_date, address_id, blocked)
select 100, 'ADMIN_USER', 'admin@mail.com', NOW(), NOW(), 88, false
WHERE NOT EXISTS (SELECT 1 FROM account WHERE id = 100);

insert into account(id, login, mail, creation_date, update_date, address_id, blocked)
select 991, 'LOGIN_OTHER_1', 'mail_other_1@mail.com', NOW(), NOW(), 88, false
WHERE NOT EXISTS (SELECT 1 FROM account WHERE id = 991);

insert into account(id, login, mail, creation_date, update_date, address_id, blocked)
select 992, 'LOGIN_OTHER_2', 'mail_other_2@mail.com', NOW(), NOW(), 88, false
WHERE NOT EXISTS (SELECT 1 FROM account WHERE id = 992);

insert into account(id, login, mail, creation_date, update_date, address_id, blocked)
select 993, 'LOGIN_OTHER_3', 'mail_other_3@mail.com', NOW(), NOW(), 88, false
WHERE NOT EXISTS (SELECT 1 FROM account WHERE id = 993);

-- DELETE * from account where id in(99, 100, 991, 992, 993)
-- DELETE * from address where id in(88)