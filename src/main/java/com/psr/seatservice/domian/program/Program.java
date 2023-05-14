package com.psr.seatservice.domian.program;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programNum;
    @Column(length = 100)
    private String title; //프로그램 제목
    @Column(length = 100)
    private String place; //장소
    @Column(length = 10)
    private String target; //신청대상
    @Temporal(value = TemporalType.DATE)
    private Date startDate; //신청시작일
    @Temporal(value = TemporalType.DATE)
    private Date endDate; //신청마감일

    //private String contents; //프로그램 상세 내용
    //private String notice; //예약 공지
    //private poster;

    protected Program() {}

    public Program(String title, String place, String target, Date startDate, Date endDate) {
        this.title = title;
        this.place = place;
        this.target = target;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
