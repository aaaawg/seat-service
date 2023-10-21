package com.psr.seatservice.domian.program;

import com.psr.seatservice.domian.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class) //날짜 자동 삽입
@Getter
@Setter
@NoArgsConstructor
public class ProgramBooking {
    //개인 사용자가 프로그램 예약한 정보
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingNum;

    private Integer seatNum;

    @Column(name = "program_num")
    private Long programNum;

    @Column(name = "viewing_date")
    private String viewingDate;

    @Column(name = "viewing_time")
    private String viewingTime;

    private String status; //참가, 불참, 예약취소, 예정
    private String reason; //취소 사유

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    private Date bookingDate;

    @LastModifiedDate
    private Date updateDate;

    @ManyToOne(targetEntity = ProgramViewing.class)
    @JoinColumns({
            @JoinColumn(name = "program_num", referencedColumnName = "program_num", insertable = false, updatable = false),
            @JoinColumn(name = "viewing_date", referencedColumnName = "viewing_date", insertable = false, updatable = false),
            @JoinColumn(name = "viewing_time", referencedColumnName = "viewing_time", insertable = false, updatable = false)
    })
    private ProgramViewing programViewing;

    @Column(columnDefinition = "json", name = "program_response")
    private String programResponse;

    public ProgramBooking(Integer seatNum) {
        this.seatNum = seatNum;
    }

    public ProgramBooking(Long programNum, String viewingDate, String viewingTime, Integer seatNum, String status, String programResponse, User user) {
        this.programNum = programNum;
        this.viewingDate = viewingDate;
        this.viewingTime = viewingTime;
        this.seatNum = seatNum;
        this.status = status;
        this.programResponse = programResponse;
        this.user = user;
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public void updateCancelReason(String reason) {
        this.reason = reason;
        this.seatNum = null;
    }
}
