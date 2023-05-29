package com.psr.seatservice.domian.seat;

import lombok.Data;

import javax.persistence.*;
@Entity
@Data
@Table(name = "seat_table")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long program_Num;   // 연결된 프로그램 번호
    private boolean checking;  //구매 여부
    private Integer s_row;
    private Integer s_col;

    protected Seat() {}

    public Seat(Long seatId, Long program_Num, boolean check,Integer row, Integer col){
        this.id = seatId;
        this.program_Num = program_Num;
        this.checking = check;
        this.s_row = row;
        this.s_col = col;
    }

}
