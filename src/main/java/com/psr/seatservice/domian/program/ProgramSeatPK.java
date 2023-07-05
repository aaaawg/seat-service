package com.psr.seatservice.domian.program;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class ProgramSeatPK implements Serializable {
    private Long programNo;
    private int seatRow;
    private int seatCol;
}
