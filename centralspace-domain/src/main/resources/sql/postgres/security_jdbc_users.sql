--  -- users table structure
--  CREATE TABLE users (
--   username VARCHAR(50) NOT NULL,
--   password VARCHAR(500) NOT NULL,
--   enabled boolean NOT NULL,
--  PRIMARY KEY (username)
--  );
--
--
-- -- authorities table structure
-- CREATE TABLE authorities (
--   username VARCHAR(50) NOT NULL,
--   authority VARCHAR(50) NOT NULL,
--   PRIMARY KEY (username),
--   CONSTRAINT authorities_ibfk_1 FOREIGN KEY (username)
--   REFERENCES users (username)
-- );

delete from authorities;
delete from users;

todo!!! how init first!