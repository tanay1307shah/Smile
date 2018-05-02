drop database if exists smile;
create database smile;
use smile;

CREATE TABLE Users(
	userId INT(10) not null auto_increment unique,
    userName varchar(500) not null unique,
    pwd varchar(100) not null,
    fullName VARCHAR(500) not null,
    age VARCHAR(2) not null,
    class VARCHAR(2) not null,
    gender VARCHAR(10) not null,
    race VARCHAR(100) not null,
    fatherNum VARCHAR(11) not null,
    motherNum VARCHAR(11) not null,
    teacherNum VARCHAR(11) not null,
    imgUrl VARCHAR(1000) not null
);