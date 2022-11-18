DROP DATABASE IF EXISTS `product_spring_mvc`;

CREATE DATABASE `product_spring_mvc`;

USE `product_spring_mvc`;

CREATE TABLE `category` (
    `id` BIGINT AUTO_INCREMENT,
    `name` VARCHAR(100) UNIQUE NOT NULL,
    `description` VARCHAR(255),
    PRIMARY KEY (`id`)
);

CREATE TABLE `product` (
    `id` BIGINT AUTO_INCREMENT,
    `name` VARCHAR(100) UNIQUE NOT NULL,
    `unit_price` INT NOT NULL,
    `category_id` BIGINT NOT NULL,
    `description` VARCHAR(255),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
);