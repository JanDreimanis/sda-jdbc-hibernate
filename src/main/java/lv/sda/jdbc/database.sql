-- copy & paste and run these scripts in your MySql workbench

create database examples;

use examples;

create table teacher (
	id int auto_increment primary key,
    first_name varchar(255),
    last_name varchar(255)
);

insert into teacher (first_name, last_name) values
   ('John', 'Doe'),
   ('Jane', 'Foo'),
   ('Bar', 'Bazz');

create table departments (
    id int primary key auto_increment,
    department varchar(100)
);

insert into departments values
   (1, 'HR'),
   (2, 'IT'),
   (3, 'Marketing'),
   (4, 'Sys Admins');

create table employees (
	id int primary key auto_increment,
    first_name varchar(100),
    last_name varchar(100),
    department_id int,
    foreign key (department_id) references departments(id)
);

insert into employees values
   (1, 'Janis', 'Logins', 2),
   (2, 'Juris', 'Krastins', 2),
   (3, 'Ieva', 'Liepina', 1),
   (4, 'Jana', 'Spidola', null);
