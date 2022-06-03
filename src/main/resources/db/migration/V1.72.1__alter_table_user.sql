alter table user
CHANGE COLUMN first_name first_name VARCHAR(50) NOT NULL,
CHANGE COLUMN last_name last_name VARCHAR(50) NOT NULL,
CHANGE COLUMN email email VARCHAR(50) NOT NULL,
CHANGE COLUMN password password VARCHAR(150) NOT NULL;
