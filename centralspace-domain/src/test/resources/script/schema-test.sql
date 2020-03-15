insert into address(id, country, creation_date, update_date)
values (88, 'Poland', NOW(), NOW());

insert into account(id, login, mail, creation_date, update_date, address_id)
values (99, 'loginQuery', 'text@mail.com', NOW(), NOW(), 88);