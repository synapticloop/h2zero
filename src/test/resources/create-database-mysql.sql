--  - - - - thoughtfully generated by synapticloop h2zero - - - -
--     with the use of synapticloop templar templating language
--              (sql-create-database-mysql.templar)

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
	primary key(id_user_type),
	unique index (id_user_type),
	unique index (nm_user_type)
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


--
-- This is the user title table, which is a constant-generated table for some 
-- of the user titles.  This enables quick and easy lookups from within the code
-- for values that do not change.
--
drop table if exists user_title;
show warnings;

create table user_title (
	id_user_title bigint not null auto_increment,
	nm_user_title varchar(32) not null,
	num_order_by int not null,
	primary key(id_user_title)
) engine=innodb default charset=UTF8;

-- show any warnings that are applicable
show warnings;

-- The user_title table is defined as being constant
-- insert the values

SET SESSION SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
insert into user_title values(1, 'Mr.', 1);
SET SESSION SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
insert into user_title values(2, 'Mrs.', 2);
SET SESSION SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
insert into user_title values(3, 'Miss', 3);
SET SESSION SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
insert into user_title values(4, 'Dr.', 4);


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
	index (id_user_type),
	index (fl_is_alive),
	foreign key (id_user_type) references user_type (id_user_type)
) engine=innodb default charset=UTF8;

-- show any warnings that are applicable
show warnings;

--
-- This model maps to the pet type table in the database
--
drop table if exists pet_type;
show warnings;

create table pet_type (
	id_pet_type bigint not null auto_increment,
	nm_pet_type varchar(64) not null,
	txt_desc_pet_type varchar(64) not null,
	primary key(id_pet_type),
	unique index (nm_pet_type)
) engine=innodb default charset=UTF8;

-- show any warnings that are applicable
show warnings;

--
-- This model maps to the pet table in the database
--
drop table if exists pet;
show warnings;

create table pet (
	id_pet bigint not null auto_increment,
	nm_pet varchar(64) not null,
	num_age int not null,
	flt_weight float(6,1) null,
	dt_birthday date null,
	img_photo blob null,
	primary key(id_pet)
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

drop table if exists all_types;
show warnings;

create table all_types (
	id_all_types bigint not null auto_increment,
	test_bigint bigint null,
	test_blob blob null,
	test_bool bool null,
	test_char char null,
	test_boolean boolean null,
	test_binary binary null,
	test_varbinary varbinary null,
	test_date date null,
	test_datetime datetime null,
	test_dec dec null,
	test_decimal decimal null,
	test_double double null,
	test_float float null,
	test_int int null,
	test_integer integer null,
	test_longtext longtext null,
	test_mediumblob mediumblob null,
	test_mediumint mediumint null,
	test_mediumtext mediumtext null,
	test_numeric numeric null,
	test_smallint smallint null,
	test_time time null,
	test_text text null,
	test_timestamp timestamp null,
	test_tinyint tinyint null,
	test_tinytext tinytext null,
	test_varchar varchar null,
	test_year year null,
	primary key(id_all_types)
) engine=innodb default charset=UTF8;

-- show any warnings that are applicable
show warnings;

drop view if exists user_user_type;

show warnings;

create view user_user_type as
 select 
u.nm_user, 
u.id_user, 
ut.nm_user_type
from 
user u, 
user_type ut
 where 
u.id_user_type = ut.id_user_type
;

show warnings;

