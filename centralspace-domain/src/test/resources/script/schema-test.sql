insert into address(id, country, creation_date, update_date)
values (88, 'Poland', NOW(), NOW());

insert into account(id, login, mail, creation_date, update_date, address_id)
values (99, 'LOGINQUERY', 'text@mail.com', NOW(), NOW(), 88);

insert into account(id, login, mail, creation_date, update_date, address_id)
values (991, 'LOGIN_OTHER_1', 'mail_other_1@mail.com', NOW(), NOW(), 88);

insert into account(id, login, mail, creation_date, update_date, address_id)
values (992, 'LOGIN_OTHER_2', 'mail_other_2@mail.com', NOW(), NOW(), 88);

insert into account(id, login, mail, creation_date, update_date, address_id)
values (993, 'LOGIN_OTHER_3', 'mail_other_3@mail.com', NOW(), NOW(), 88);