--  - - - - thoughtfully generated by synapticloop h2zero - - - -
--     with the use of synapticloop templar templating language
--          (sql/sql-create-database-cockroach.templar)

--  - - - - thoughtfully generated by synapticloop h2zero - - - -
--     with the use of synapticloop templar templating language
--             (sql/sql-create-database-base.templar)

set session sql_safe_updates = false;
drop database if exists sample;
create database sample;

use sample;

--
-- This is the user type table, which is a constant-generated table for all
-- of the user types.  This enables quick and easy lookups from within the code
-- for values that do not change.
--
drop table if exists user_type;
create table user_type (
	id_user_type bigint not null,
	nm_user_type varchar(32) not null,
	primary key(id_user_type)
);

-- The user_type table is defined as being constant
-- insert the values

insert into user_type values(1, 'normal');
insert into user_type values(2, 'special');
insert into user_type values(3, 'admin');
insert into user_type values(4, 'super admin');


--
-- This is the user title table, which is a constant-generated table for some 
-- of the user titles.  This enables quick and easy lookups from within the code
-- for values that do not change.
--
drop table if exists user_title;
create table user_title (
	id_user_title bigint not null,
	nm_user_title varchar(32) not null,
	num_order_by int not null,
	primary key(id_user_title)
);

-- The user_title table is defined as being constant
-- insert the values

insert into user_title values(1, 'Mr.', 1);
insert into user_title values(2, 'Mrs.', 2);
insert into user_title values(3, 'Miss', 3);
insert into user_title values(4, 'Dr.', 4);


drop table if exists user_user;
create table user_user (
	id_user_user bigint not null,
	id_user_type bigint not null,
	fl_is_alive boolean null default '0',
	num_age int not null,
	nm_username varchar(64) not null unique,
	txt_address_email varchar(256) not null unique,
	txt_password varchar(32) not null,
	ts_signup timestamp null,
	primary key(id_user_user),
	index (id_user_type),
	index (fl_is_alive),
	foreign key (id_user_type) references user_type (id_user_type)
);

--
-- This model maps to the pet table in the database
--
drop table if exists pet;
create table pet (
	id_pet bigint not null,
	nm_pet varchar(64) not null,
	num_age int not null,
	flt_weight numeric(6,1) null,
	dt_birthday date null,
	img_photo blob null,
	primary key(id_pet)
);

drop table if exists user_user_pet;
create table user_user_pet (
	id_user_user_pet bigint not null,
	id_user_user bigint not null,
	id_pet bigint not null,
	primary key(id_user_user_pet),
	index (id_user_user),
	index (id_pet),
	foreign key (id_user_user) references user_user (id_user_user),
	foreign key (id_pet) references pet (id_pet)
);

drop table if exists all_types;
create table all_types (
	id_all_types bigserial not null,
	num_smallint smallint null,
	num_integer integer null,
	num_bigint bigint null,
	num_decimal decimal null,
	num_numeric numeric null,
	flt_real real null,
	dbl_real double precision null,
	num_serial serial not null,
	num_smallserial smallserial not null,
	num_bigserial bigserial not null,
	primary key(id_all_types)
);

drop view if exists user_user_type;

create view user_user_type as  select uu.nm_username, ut.nm_user_type from user_user uu, user_type ut where uu.id_user_type = ut.id_user_type;

