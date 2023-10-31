package com.psr.seatservice.domian.program;

import com.psr.seatservice.domian.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
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
    @Column(length = 100, nullable = false)
    private String title; //프로그램 제목
    @Column(length = 100, nullable = false)
    private String place; //장소
    private String way;
    @Column(length = 10, nullable = false)
    private String target; //신청대상
    private String targetDetail;
    @Temporal(value = TemporalType.DATE)
    private Date startDate; //신청시작일
    @Temporal(value = TemporalType.DATE)
    private Date endDate; //신청마감일
    @Column(length = 7, nullable = false)
    private String type; //온라인 or 오프라인

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<ProgramViewing> programViewings = new ArrayList<>();

    @Lob
    private String contents; //프로그램 상세 내용

    @Column(columnDefinition = "LONGTEXT")
    private String programHtml; //html text

    @Column(columnDefinition = "json")
    private String programQuestion;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private Timestamp createDate;

    @Lob
    private String seatingChart;
    private Integer seatCol;
    @Column(nullable = false)
    private int peopleNum; //모집인원

    public Program(String title, String place, String way, String target, String targetDetail, String type, Date startDate, Date endDate, String seatingChart, Integer seatCol, int peopleNum, String contents, String programHtml, String programQuestion, User user) {
        this.title = title;
        this.place = place;
        this.way = way;
        this.target = target;
        this.targetDetail = targetDetail;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.seatingChart = seatingChart;
        this.seatCol = seatCol;
        this.peopleNum = peopleNum;
        this.contents = contents;
        this.programHtml = programHtml;
        this.programQuestion = programQuestion;
        this.user = user;
    }
    public void updateInfo(String title, String place, String target, Date startDate, Date endDate, String type, int peopleNum,
                           Integer seatCol, String seatingChart, String way, String contents, String targetDetail) {
        this.title = title;
        this.place = place;
        this.target = target;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.peopleNum = peopleNum;
        this.seatCol = seatCol;
        this.seatingChart = seatingChart;
        this.way = way;
        this.contents = contents;
        this.targetDetail = targetDetail;
    }

    public void updateForm(String programHtml){
        this.programHtml = programHtml;
    }
    public void updateJsonFrom(String programQuestion){ this.programQuestion = programQuestion; }
}
