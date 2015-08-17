--  - - - - thoughtfully generated by synapticloop h2zero - - - -
--     with the use of synapticloop templar templating language
--              (sql-create-database.templar)

-- the following will stop mysql from outputting 'notes' as warnings
set sql_notes=0;
drop database if exists sample;
create database sample;

use sample;

--
-- This is the user type table, which is a constant-generated table for all
-- of the user types.  This enables quick and easy lookups from within the code
-- for values that do not change.
--
drop table if exists user_type;
show warnings;

create table user_type (
	id_user_type bigint not null auto_increment,
	nm_user_type varchar(32) not null,
	primary key(id_user_type)
) engine=innodb default charset=UTF8;

-- show any warnings that are applicable
show warnings;

-- The user_type table is defined as being constant
-- insert the values

SET SESSION SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
insert into user_type values(1, 'normal');
SET SESSION SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
insert into user_type values(2, 'special');
SET SESSION SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
insert into user_type values(3, 'admin');
SET SESSION SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
insert into user_type values(4, 'super admin');


drop table if exists user;
show warnings;

create table user (
	id_user bigint not null auto_increment,
	id_user_type bigint not null,
	fl_is_alive boolean null default '0',
	num_age int not null,
	nm_username varchar(64) not null,
	txt_address_email varchar(256) not null,
	txt_password varchar(32) not null,
	dtm_signup datetime null,
	primary key(id_user),
	unique index (nm_username),
	unique index (txt_address_email(255)),
	unique index (txt_password),
	index (id_user_type),
	index (fl_is_alive),
	foreign key (id_user_type) references user_type (id_user_type)
) engine=innodb default charset=UTF8;

-- show any warnings that are applicable
show warnings;

drop table if exists pet;
show warnings;

create table pet (
	id_pet bigint not null auto_increment,
	nm_pet boolean null default '0',
	num_age int not null,
	flt_weight float(6,1) null,
	dt_birthday date null,
	primary key(id_pet),
	index (nm_pet)
) engine=innodb default charset=UTF8;

-- show any warnings that are applicable
show warnings;

drop table if exists user_pet;
show warnings;

create table user_pet (
	id_user_pet bigint not null auto_increment,
	id_user bigint not null,
	id_pet bigint not null,
	primary key(id_user_pet),
	index (id_user),
	index (id_pet),
	foreign key (id_user) references user (id_user),
	foreign key (id_pet) references pet (id_pet)
) engine=innodb default charset=UTF8;

-- show any warnings that are applicable
show warnings;

