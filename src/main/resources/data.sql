insert into program(`end_date`, `place`, `start_date`, `target`, `title`, `type`,`seating_chart`,`seat_col`, `people_num`, `create_date`) values ('2023-06-30', '경기 가평군, 3층', '2023-06-07', '제한없음', '직업특강', '오프라인', '[1,1,0,1,1,1,1,0,1,1,1,1,0,1,1]',5,12,NOW());
insert into program_viewing(`program_num`, `viewing_date`, `viewing_time`) values (1, '2023-06-06', '17:00');
insert into program_viewing(`program_num`, `viewing_date`, `viewing_time`) values (1, '2023-06-06', '19:00');
insert into program_viewing(`program_num`, `viewing_date`, `viewing_time`) values (1, '2023-06-08', '17:00');
insert into program_viewing(`program_num`, `viewing_date`, `viewing_time`) values (1, '2023-06-08', '20:00');
insert into program_viewing(`program_num`, `viewing_date`, `viewing_time`) values (1, '2023-08-15', '12:00');
insert into program_booking(`program_num`, `booking_date`, `seat_num`, `user_id`, `viewing_date`, `viewing_time`, `status`) values (1, NOW(), 2, 'aa', '2023-06-08', '17:00', 'P');
insert into program_booking(`program_num`, `booking_date`, `seat_num`, `user_id`, `viewing_date`, `viewing_time`, `status`) values (1, NOW(), 2, 'aa', '2023-06-06', '17:00', 'P');
insert into program_booking(`program_num`, `booking_date`, `seat_num`, `user_id`, `viewing_date`, `viewing_time`, `status`) values (1, NOW(), 5, 'bb', '2023-06-06', '17:00', 'P');
insert into program_booking(`program_num`, `booking_date`, `seat_num`, `user_id`, `viewing_date`, `viewing_time`, `status`) values (1, NOW(), 12, 'aa', '2023-06-06', '19:00', 'P');
insert into user(`user_id`, `password`, `name`, `address`, `phone`, `email`, `role`, `birth`) values ('aa', 'abc', '일이삼', '경기도 가평군', '010-0000-0000', 'abc@g.com', 'USER', '2011-01-01');
insert into user(`user_id`, `password`, `name`, `address`, `phone`, `email`, `role`, `birth`) values ('bb', 'abc', '사오육', '경기도', '010-1111-1111', 'a@nnn.com', 'USER', '2009-01-01');
insert into program(`end_date`, `place`, `start_date`, `target`, `title`, `type`, `people_num`, `create_date`) values ('2023-06-30', '경기도', '2023-06-07', '제한없음', '취업특강', '오프라인',100,NOW());
insert into program_viewing(`program_num`, `viewing_date`, `viewing_time`) values (2, '2023-08-20', '20:00');
insert into program_viewing(`program_num`, `viewing_date`, `viewing_time`) values (2, '2023-08-23', '20:00');