create table PERSON (
    	ID int primary key,
	firstname varchar(100),
	lastname varchar(100),
	address_id int
);

create table address (
	id int primary key,
	city varchar(100),
	country varchar(100),
	street varchar(100)
);

