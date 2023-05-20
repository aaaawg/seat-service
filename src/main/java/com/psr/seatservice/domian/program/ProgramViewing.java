package com.psr.seatservice.domian.program;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@IdClass(ProgramViewingPK.class)
public class ProgramViewing {
    @Id
    @Column(name = "program_num")
    private Long programNo;
    @Id
    private String viewingDate;
    @Id
    private String viewingTime;
    @ManyToOne(targetEntity = Program.class)
    @JoinColumn(name = "program_num", insertable = false, updatable = false)
    private Program program;

    public ProgramViewing(Long programNum, String date, String time) {
        this.programNo = programNum;
        this.viewingDate = date;
        this.viewingTime = time;
    }
}
