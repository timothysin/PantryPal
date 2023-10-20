drop database if exists recipes_test;
create database recipes_test;
use recipes_test;

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



DELIMITER //

CREATE PROCEDURE set_known_good_state()
BEGIN
    DELETE FROM recipe_comment;
    DELETE FROM recipe;
    DELETE FROM category;
    DELETE FROM app_user_role;
    DELETE FROM app_role;
    DELETE FROM app_user;

    ALTER TABLE app_role AUTO_INCREMENT = 1;
    ALTER TABLE app_user AUTO_INCREMENT = 1;
    ALTER TABLE category AUTO_INCREMENT = 1;
    ALTER TABLE recipe AUTO_INCREMENT = 1;
    ALTER TABLE recipe_comment AUTO_INCREMENT = 1;

    -- Add initial data
    INSERT INTO app_role (`name`)
    VALUES
    ('TEST_ROLE_1'),
    ('TEST_ROLE_2');

    INSERT INTO app_user (username, password_hash, enabled)
    VALUES
    ('appuser1@app.com', 'password_hash_1', 1),
    ('appuser2@app.com', 'password_hash_2', 1);

    INSERT INTO app_user_role (app_user_id, app_role_id)
    VALUES
    (1, 1),
    (2, 2);

    INSERT INTO category (`name`)
    VALUES
		('Category #1'),
        ('Category #2'),
        ('Category #3'),
        ('Category #4'),
        ('Category #5'),
        ('Category #6'),
        ('Category #7');

    INSERT INTO recipe (`name`, ingredients, instructions, stat, app_user_id, category_id)
    VALUES 
		('Recipe 1', 'Ingredient 1, Ingredient 2', 'Instructions for Recipe 1', 'status', 1, 1),
        ('Recipe 2', 'Ingredient 1, Ingredient 2', 'Instructions for Recipe 2', 'status', 1, 1),
        ('Recipe 3', 'Ingredient 1, Ingredient 2', 'Instructions for Recipe 3', 'status', 1, 1);

    -- Example: INSERT INTO recipe_comment (content, app_user_id, saved_recipe_id)
    -- VALUES ('Comment 1', 1, 1);

    -- Add community_recipes data here
END//

DELIMITER ;