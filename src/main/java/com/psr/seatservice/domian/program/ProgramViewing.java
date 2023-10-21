package com.psr.seatservice.domian.program;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@IdClass(ProgramViewingPK.class)
public class ProgramViewing {
    @Id
    @Column(name = "program_num")
    private Long programNo;

    @Id
    @Column(name = "viewing_date")
    private String viewingDate;

    @Id
    @Column(name = "viewing_time")
    private String viewingTime;

    @ManyToOne(targetEntity = Program.class)
    @JoinColumn(name = "program_num", insertable = false, updatable = false)
    private Program program;

    @OneToMany(mappedBy = "programViewing", cascade = CascadeType.ALL)
    private List<ProgramBooking> programBookings = new ArrayList<>();

    public ProgramViewing(Long programNum, String date, String time) {
        this.programNo = programNum;
        this.viewingDate = date;
        this.viewingTime = time;
    }
}
