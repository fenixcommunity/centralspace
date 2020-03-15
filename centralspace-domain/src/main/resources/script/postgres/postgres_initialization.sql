-- CREATE OR REPLACE FUNCTION table_exist(schema text, tbl text)
--     RETURNS boolean AS $$ DECLARE exist boolean;
-- BEGIN
--     --     SELECT CASE WHEN (
-- --             SELECT ...
-- --         ) THEN 1
-- --                 ELSE 0 END into exist;
--     SELECT to_regclass($1 || '.' || $2) is not null into exist;
--     RETURN exist;
-- END;
-- $$ LANGUAGE plpgsql
--     SECURITY DEFINER
-- SET search_path = admin, pg_temp;
--
-- --
-- SELECT CASE
--            WHEN (
--                table_exist('public', 'account')
--                ) THEN (select a.login from account a limit 1)
--            ELSE 'not exist' END;


select * from user;

-- IF TABLES GENERATED:
INSERT INTO address(id, creation_date, update_date, city, country)
SELECT 888, NOW(), NOW(), 'Berlin', 'Germany'
WHERE NOT EXISTS ( SELECT id FROM address WHERE id = 888 );

INSERT INTO account(id, login, mail, creation_date, update_date, addressid)
SELECT 999, 'loginQuery', 'text@mail.com', NOW(), NOW(), 888
WHERE NOT EXISTS ( SELECT id FROM account WHERE id = 999 );