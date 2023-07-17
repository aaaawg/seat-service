package com.psr.seatservice.domian.program;

import com.psr.seatservice.domian.files.Files;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@NoArgsConstructor
@Entity
@Getter
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
    @Column(length = 7)
    private String type; //온라인 or 오프라인

    @OneToMany(mappedBy = "program")
    private List<ProgramViewing> programViewings = new ArrayList<>();

    @OneToOne(mappedBy = "program")
    private ProgramSeat programSeat;

    //private String contents; //프로그램 상세 내용
    //private String notice; //예약 공지


    //프로그램 등록 작성자 아이디

    public Program(String title, String place, String target, String type, Date startDate, Date endDate) {
        this.title = title;
        this.place = place;
        this.target = target;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateInfo(String title, String place, String target, Date startDate, Date endDate) {
        this.title = title;
        this.place = place;
        this.target = target;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
