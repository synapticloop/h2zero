--        - - - - thoughtfully generated by synapticloop h2zero - - - -        
--          with the use of synapticloop templar templating language
--                 (/sql/sql-create-database-sqlite3.templar)

--
-- This is the user type table, which is a constant-generated table for all
-- of the user types.  This enables quick and easy lookups from within the code
-- for values that do not change.
--
drop table if exists user_type;
create table user_type (
	id_user_type INTEGER not null PRIMARY KEY AUTOINCREMENT,
	nm_user_type varchar(32) not null
);



-- The user_type table is defined as being constant
-- insert the values

insert into user_type values(1, 'normal');
insert into user_type values(2, 'special');
insert into user_type values(3, 'admin');
insert into user_type values(4, 'super admin');


drop table if exists user;
create table user (
	id_user INTEGER not null PRIMARY KEY AUTOINCREMENT,
	id_user_type bigint not null,
	nm_user varchar(64) not null,
	num_age int not null,
	fl_is_active boolean null default '1',
	dtm_signup datetime null,
	foreign key (id_user_type) references user_type (id_user_type)
);

create index user_id_user_type_idx on user(id_user_type);
create index user_fl_is_active_idx on user(fl_is_active);


drop table if exists pet;
create table pet (
	id_pet INTEGER not null PRIMARY KEY AUTOINCREMENT,
	id_user bigint not null,
	nm_pet varchar(64) not null,
	dt_birthday datetime null,
	foreign key (id_user) references user (id_user)
);

create unique index pet_nm_pet_idx_unq on pet(nm_pet);
create index pet_id_user_idx on pet(id_user);

