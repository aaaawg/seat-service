```
create database seat_service;  
```
```
create table program(  
    program_num bigint not null auto_increment,  
    type varchar(7),
    title varchar(100),  
    place varchar(100),  
    target varchar(10),  
    start_date date,  
    end_date date,  
    primary key(program_num)  
);
```
```
create table program_viewing(
    program_num bigint not null,
    viewing_date varchar(255) not null,
    viewing_time varchar(255) not null,
    primary key(program_num, viewing_date, viewing_time),
    foreign key(program_num) references program(program_num)
);
```