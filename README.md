create database seat_service;

create table program(
    program_num bigint not null auto_increment,
    title varchar(100),
    place varchar(100),
    target varchar(10),
    start_date date,
    end_date date,
    primary key (programNum)
);
