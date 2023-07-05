package com.psr.seatservice.domian.program;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@IdClass(ProgramSeatPK.class)
public class ProgramSeat {
    //기업사용자가 생성한 좌석표 정보
    @Id
    @Column(name = "program_num")
    private Long programNo;

    @Id
    private int seatRow;

    @Id
    private int seatCol;

    @OneToOne(targetEntity = Program.class)
    @JoinColumn(name = "program_num", insertable = false, updatable = false)
    private Program program;
}
