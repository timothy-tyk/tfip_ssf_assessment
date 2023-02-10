## SQL statements

create database pizza;
create table pizza(
id int not null auto_increment,
pizza_name varchar(64) not null,
size enum('sm','md','lg') not null,
primary key(id)
);
create table orders(
id int not null auto_increment,
hex_id varchar(8) not null,
pizza_id int not null,
pizza_qty int not null,
name varchar(64) not null,
address varchar(64) not null,
phone_number varchar(8) not null,
rush boolean not null default false,
comments varchar(128),
pizza_cost int,
total_cost int,
primary key(id),
foreign key(pizza_id) references pizza(id)
);
