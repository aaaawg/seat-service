insert into user(`user_id`, `password`, `name`, `address`, `phone`, `email`, `role`, `birth`) values ('aa', 'abc', '일이삼', '경기도 가평군', '010-0000-0000', 'abc@g.com', 'BIZ', '2011-01-01');
insert into program(`end_date`, `place`, `start_date`, `target`, `title`, `type`,`seating_chart`,`seat_col`, `people_num`, `create_date`, `user_id`) values ('2024-06-30', '서울 성북구 아리랑로 4 (동선동4가), 3층', '2023-06-07', '제한없음', '직업특강', '오프라인', '[1,1,0,1,1,1,1,0,1,1,1,1,0,1,1]',5,12,NOW(), 1);
insert into program_viewing(`program_num`, `viewing_date`, `viewing_time`) values (1, '2023-06-06', '17:00');
insert into program_viewing(`program_num`, `viewing_date`, `viewing_time`) values (1, '2023-06-06', '19:00');
insert into program_viewing(`program_num`, `viewing_date`, `viewing_time`) values (1, '2023-06-08', '17:00');
insert into program_viewing(`program_num`, `viewing_date`, `viewing_time`) values (1, '2023-06-08', '20:00');
insert into program_viewing(`program_num`, `viewing_date`, `viewing_time`) values (1, '2023-08-15', '12:00');
