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
```
create table user_admin(
    user_num bigint not null auto_increment,
    address varchar(255),
    name varchar(255),
    password varchar(255),
    phone varchar(255),
    user_id varchar(255),
    primary key (user_num)
);
```
```
create table  files (
  id bigint NOT NULL auto_increment,
  filename varchar(300) NOT NULL,
  filepath varchar(255) NOT NULL,
  origfilename varchar(255) NOT NULL,
  post_id bigint NOT NULL,
  PRIMARY KEY (id),
  foreign key(post_id) references program(program_num)
)
```
```
create table seat_table(
    id bigint not null auto_increment,
    program_num bigint not null,
    checking boolean dafault false,
    s_row int,
    s_col int,
    primary key(id),
    foreign key(program_num) references program(program_num)
);
```
```
