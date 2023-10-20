drop database if exists production;
create database production;
use production;

-- create tables and relationships
create table app_user (
    app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    enabled bit not null default(1)
);

create table app_role (
    app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
        primary key (app_user_id, app_role_id),
    constraint fk_app_user_role_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_app_user_role_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
);

create table category (
	category_id int primary key auto_increment,
    `name` varchar(25) not null
);

create table recipe (
    recipe_id int primary key auto_increment,
    `name` varchar(25) not null,
    ingredients varchar(250) not null,
    instructions varchar(1000) not null,
    stat varchar(10) not null,
    app_user_id int not null,
    category_id int not null,
	constraint fk_recipe_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
	constraint fk_recipe_category_id
		foreign key (category_id)
        references category (category_id)
);

create table recipe_comment (
	comment_id int primary key auto_increment, 
    content varchar(250) not null,
    app_user_id int not null,
    recipe_id int not null,
    constraint fk_comment_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
	constraint fk_comment_recipe_id
		foreign key (recipe_id)
        references recipe(recipe_id)
);


insert into app_role (`name`) values
    ('USER'),
    ('ADMIN');

-- passwords are set to "P@ssw0rd!"
insert into app_user (username, password_hash, enabled)
    values
    ('john@smith.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('sally@jones.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1);

insert into app_user_role
    values
    (1, 2),
    (2, 1);
    
insert into category (name)
	values
    ('breakfast'),
    ('lunch'),
    ('dinner');

insert into recipe (name, ingredients, instructions, stat,  app_user_id, category_id)
	values
    ('scrambled eggs', 'eggs, milk', 'break eggs into bowl, add in a little bit of milk and whisk together, cook in pan over fire', 'posted',  1, 1);
