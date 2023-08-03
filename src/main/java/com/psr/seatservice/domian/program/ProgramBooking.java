package com.psr.seatservice.domian.program;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Target;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProgramBooking {
    //개인 사용자가 프로그램 예약한 정보
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingNum;

    private String userId;

    private Integer seatNum;

    @CreationTimestamp
    private Timestamp bookingDate;

    @ManyToOne(targetEntity = ProgramViewing.class)
    @JoinColumns({
            @JoinColumn(name = "program_num", referencedColumnName = "program_num", insertable = false, updatable = false),
            @JoinColumn(name = "viewing_date", referencedColumnName = "viewing_date", insertable = false, updatable = false),
            @JoinColumn(name = "viewing_time", referencedColumnName = "viewing_time",insertable = false, updatable = false)
    })
    private ProgramViewing programViewing;

    public ProgramBooking(Integer seatNum) {
        this.seatNum = seatNum;
    }

    public ProgramBooking(ProgramViewing programViewing, Integer seatNum) {
        this.programViewing = programViewing;
        this.seatNum = seatNum;
    }
}
