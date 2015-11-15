drop database panenka;
create database panenka;

create table person (
  id int not null auto_increment,
  login varchar(20),
  password varchar(10),
  name varchar(20) not null,
  surname varchar(20) not null,
  email varchar(255),
  phone1 varchar(30),
  phone2 varchar(30),
  address varchar(255),
  member tinyint not null,
  admin tinyint not null default 0,
  manager tinyint not null default 0,
  granted_by int, 
  PRIMARY KEY (id)
);

insert into person values(1, 'jiri.stepan','test','Jiri','Stepan','jiri.stepan@etnetera.cz','602603906',null,'Fr. Kloze 1846, Kladno',1,1,1,null);
insert into person values(2, 'tereza.stepanova','test','Tereza','Stepan','thanusova@nfoaisa.com',null,null,'Fr. Kloze 1846, Kladno',1,0,0,null);
insert into person values(3, null,null,'Stepana','Malcova',null,null,null,null,0,0,0,1);

create table can_subscribe (
 subscriber int not null,
 subject int not null
);

insert into can_subscribe values(1,2);
insert into can_subscribe values(2,1);

create table reservation (
 id int not null auto_increment,
 date_from date not null,
 date_to date not null,
 date_reservation datetime not null,
 reserved_by int,
 PRIMARY KEY (id)
);

insert into reservation values(1,'2003-12-19','2003-12-26','2003-12-10',1);

create table aux_reservation_person(
 person_id int not null,
 reservation_id int not null
);

insert into aux_reservation_person values(1,1);
insert into aux_reservation_person values(2,1);
insert into aux_reservation_person values(3,1);

create table event_log (
id int not null auto_increment,
event_time datetime not null,
logged_user_id int not null,
logged_user_name varchar(60) not null,
event_type varchar(20) not null,
event_params text not null,
event_ip varchar(20) not null,
event_remote_host varchar(255),
event_user_cookie varchar(50),
PRIMARY KEY (id)
);