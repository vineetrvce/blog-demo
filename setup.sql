DROP DATABASE IF EXISTS blogdb;
CREATE DATABASE blogdb;
USE blogdb;
CREATE TABLE blogs (id int not null auto_increment, header varchar(255) null, text varchar(4000) null, primary key (id));
