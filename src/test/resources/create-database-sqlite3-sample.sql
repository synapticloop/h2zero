--  - - - - thoughtfully generated by synapticloop h2zero - - - -
--     with the use of synapticloop templar templating language
--            (sql/sql-create-database-sqlite3.templar)

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

create unique index user_type_id_user_type_idx_unq on user_type(id_user_type);
create unique index user_type_nm_user_type_idx_unq on user_type(nm_user_type);


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
	id_user_title INTEGER not null PRIMARY KEY AUTOINCREMENT,
	nm_user_title varchar(32) not null,
	num_order_by int not null
);



-- The user_title table is defined as being constant
-- insert the values

insert into user_title values(1, 'Mr.', 1);
insert into user_title values(2, 'Mrs.', 2);
insert into user_title values(3, 'Miss', 3);
insert into user_title values(4, 'Dr.', 4);


drop table if exists user;
create table user (
	id_user INTEGER not null PRIMARY KEY AUTOINCREMENT,
	id_user_type bigint not null,
	fl_is_alive boolean null default '0',
	num_age int not null,
	nm_username varchar(64) not null,
	txt_address_email varchar(256) not null,
	txt_password varchar(32) not null,
	dtm_signup datetime null,
	foreign key (id_user_type) references user_type (id_user_type)
);

create unique index user_nm_username_idx_unq on user(nm_username);
create unique index user_txt_address_email_idx_unq on user(txt_address_email);
create index user_id_user_type_idx on user(id_user_type);
create index user_fl_is_alive_idx on user(fl_is_alive);


--
-- This model maps to the pet type table in the database
--
drop table if exists pet_type;
create table pet_type (
	id_pet_type INTEGER not null PRIMARY KEY AUTOINCREMENT,
	nm_pet_type varchar(64) not null,
	txt_desc_pet_type varchar(64) not null
);

create unique index pet_type_nm_pet_type_idx_unq on pet_type(nm_pet_type);


--
-- This model maps to the pet table in the database
--
drop table if exists pet;
create table pet (
	id_pet INTEGER not null PRIMARY KEY AUTOINCREMENT,
	nm_pet varchar(64) not null,
	num_age int not null,
	flt_weight float(6,1) null,
	dt_birthday date null,
	img_photo blob null
);



drop table if exists user_pet;
create table user_pet (
	id_user_pet INTEGER not null PRIMARY KEY AUTOINCREMENT,
	id_user bigint not null,
	id_pet bigint not null,
	foreign key (id_user) references user (id_user),
	foreign key (id_pet) references pet (id_pet)
);

create index user_pet_id_user_idx on user_pet(id_user);
create index user_pet_id_pet_idx on user_pet(id_pet);


drop table if exists author_status;
create table author_status (
	id_author_status INTEGER not null PRIMARY KEY AUTOINCREMENT,
	txt_author_status varchar(256) not null,
	txt_desc_author_status varchar(256) not null
);

create unique index author_status_txt_author_status_idx_unq on author_status(txt_author_status);
create unique index author_status_txt_desc_author_status_idx_unq on author_status(txt_desc_author_status);


-- The author_status table is defined as being constant
-- insert the values

insert into author_status values(1, 'WAITING', 'Waiting for the number of followers for the author to be hit');
insert into author_status values(2, 'TO_BE_EVALUATED', 'Author is waiting to be evaluated.');
insert into author_status values(3, 'IGNORED', 'Author is being ignored.');
insert into author_status values(4, 'FOLLOWED', 'Author is followed.');


drop table if exists author;
create table author (
	id_author INTEGER not null PRIMARY KEY AUTOINCREMENT,
	id_author_status bigint null,
	txt_id_author varchar(256) not null,
	nm_author varchar(256) not null,
	nm_username varchar(256) not null,
	txt_bio varchar(512) not null,
	txt_url_cache_image varchar(512) not null,
	num_following bigint null,
	num_followers bigint null,
	dtm_started_following datetime null,
	fl_is_updating boolean null default '0',
	fl_author_is_following_user boolean null default '0',
	fl_author_is_followed_by_user boolean null default '0',
	foreign key (id_author_status) references author_status (id_author_status)
);

create unique index author_txt_id_author_idx_unq on author(txt_id_author);


drop table if exists all_types;
create table all_types (
	id_all_types INTEGER not null PRIMARY KEY AUTOINCREMENT,
	test_bigint bigint null,
	test_boolean boolean null,
	test_date date null,
	test_datetime datetime null,
	test_double double null,
	test_float float null,
	test_int int null,
	test_integer integer null,
	test_mediumint mediumint null,
	test_numeric numeric null,
	test_smallint smallint null,
	test_text text null,
	test_tinyint tinyint null,
	test_varchar varchar(128) null
);



drop view if exists user_user_type;

create view user_user_type as  select  u.nm_username,  u.id_user,  ut.nm_user_type from  user u,  user_type ut  where  u.id_user_type = ut.id_user_type ;

