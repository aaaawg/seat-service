package com.psr.seatservice.domian.program;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
public class ProgramBooking {
    //개인 사용자가 프로그램 예약한 정보
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingNum;

    private String userId;

    private Integer seatNum;

    @Column(name = "program_num")
    private Long programNum;

    @Column(name = "viewing_date")
    private String viewingDate;

    @Column(name = "viewing_time")
    private String viewingTime;

    private String status; //참가(P), 불참(NonP), 예약 취소(C)
    private String reason; //취소 사유

    @CreationTimestamp
    private Timestamp bookingDate;

    @ManyToOne(targetEntity = ProgramViewing.class)
    @JoinColumns({
            @JoinColumn(name = "program_num", referencedColumnName = "program_num", insertable = false, updatable = false),
            @JoinColumn(name = "viewing_date", referencedColumnName = "viewing_date", insertable = false, updatable = false),
            @JoinColumn(name = "viewing_time", referencedColumnName = "viewing_time", insertable = false, updatable = false)
    })
    private ProgramViewing programViewing;

    public ProgramBooking(Integer seatNum) {
        this.seatNum = seatNum;
    }

    public ProgramBooking(Long programNum, String viewingDate, String viewingTime, Integer seatNum) {
        this.programNum = programNum;
        this.viewingDate = viewingDate;
        this.viewingTime = viewingTime;
        this.seatNum = seatNum;
    }
}
