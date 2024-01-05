CREATE DATABASE `ecommercedb` ;
USE  `ecommercedb`;
-- USER TABLE..

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`),
  UNIQUE KEY `email_UNIQUE` (`email`)
);

-- ROLE TABLE

CREATE TABLE `role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `role_name_UNIQUE` (`role_name`) );
  
-- USER_ROLE TABLE

CREATE TABLE `user_roles` (
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  INDEX `user_id_fk1_idx` (`user_id` ) ,
  INDEX `role_id_fk2_idx` (`role_id` ) ,
  CONSTRAINT `user_id_role_id_comp_pk`
	PRIMARY KEY (`user_id`,`role_id`),
  CONSTRAINT `user_id_fk1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `role_id_fk2`
    FOREIGN KEY (`role_id`)
    REFERENCES `role` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);
  
    
-- PERMISSION TABLE

CREATE TABLE `permission` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `permission_name` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `permission_name_UNIQUE` (`permission_name`) );
  
  
-- ROLES_PERMISSION TABLE

CREATE TABLE `roles_permission` (
  `role_id` INT NOT NULL,
  `permission_id` INT NOT NULL,
  INDEX `role_id_fk3_idx` (`role_id` ) ,
  INDEX `permission_id_fk4_idx` (`permission_id`) ,
  CONSTRAINT `role_id_permission_id_comp_pk`
	PRIMARY KEY(`role_id`,`permission_id`),
  CONSTRAINT `role_id_fk3`
    FOREIGN KEY (`role_id`)
    REFERENCES `role` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `permission_id_fk4`
    FOREIGN KEY (`permission_id`)
    REFERENCES `permission` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);
    
    
    -- VALUES INSERTED..
    
    -- ROLES

INSERT INTO `role` (`role_name`) VALUES ('SUPERADMIN');
	
    -- PERMISSIONS
INSERT INTO `permission` (`permission_name`) VALUES ('WRITE_USER');
INSERT INTO `permission` (`permission_name`) VALUES ('READ_USER');
INSERT INTO `permission` (`permission_name`) VALUES ('WRITE_ROLE');
INSERT INTO `permission` (`permission_name`) VALUES ('READ_ROLE');
INSERT INTO `permission` (`permission_name`) VALUES ('WRITE_PERMISSION');
INSERT INTO `permission` (`permission_name`) VALUES ('READ_PERMISSION');

	-- USERS
INSERT INTO 
`user`
 (`first_name`, `last_name`, `user_name`, `email`, `password`, `enabled`)
 VALUES
 ('Super', 'Admin', 'SuperAdmin',
  'super.admin@rspl.com',

	-- USER_ROLES
    INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES ('1', '1');

	-- ROLES_PERMISSION
INSERT INTO `roles_permission` (`role_id`, `permission_id`) VALUES ('1', '1');
INSERT INTO `roles_permission` (`role_id`, `permission_id`) VALUES ('1', '2');
INSERT INTO `roles_permission` (`role_id`, `permission_id`) VALUES ('1', '3');
INSERT INTO `roles_permission` (`role_id`, `permission_id`) VALUES ('1', '4');
INSERT INTO `roles_permission` (`role_id`, `permission_id`) VALUES ('1', '5');
INSERT INTO `roles_permission` (`role_id`, `permission_id`) VALUES ('1', '6');

