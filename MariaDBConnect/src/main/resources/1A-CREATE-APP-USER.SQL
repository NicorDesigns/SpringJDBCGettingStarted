DROP USER app1@localhost;

CREATE USER app1@localhost IDENTIFIED BY 'app1password2';

GRANT ALL PRIVILEGES ON charitydb.* TO app1@locahost;

SELECT USER
FROM mysql.user;

