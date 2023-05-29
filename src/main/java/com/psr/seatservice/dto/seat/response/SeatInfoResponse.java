package com.psr.seatservice.dto.seat.response;

import com.psr.seatservice.domian.seat.Seat;
import lombok.Data;

@Data
public class SeatInfoResponse {
    private Long id;
    private Long program_Num;   // 연결된 프로그램 번호
    private boolean checking;  //구매 여부
    private Integer s_row;
    private Integer s_col;

    public SeatInfoResponse(Seat seat){
        this.id = seat.getId();
        this.program_Num = seat.getProgram_Num();
        this.checking = seat.isChecking();
        this.s_row = seat.getS_row();
        this.s_col = seat.getS_col();
    }

}
