package com.psr.seatservice.service.seat;

import com.psr.seatservice.domian.seat.Seat;
import com.psr.seatservice.domian.seat.SeatRepository;
import com.psr.seatservice.dto.seat.response.SeatInfoResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService {
    private SeatRepository seatRepository;
    public SeatService(SeatRepository seatRepository) {this.seatRepository = seatRepository;}
    public List<SeatInfoResponse> findAll() {
        List<Seat> seats = seatRepository.findAll();
        return seats.stream()
                .map(SeatInfoResponse::new)
                .collect(Collectors.toList());
    }

    public List<SeatInfoResponse> findByPro(Long proNum){
        List<Seat> list = new ArrayList<>();
        for(Seat a : seatRepository.findAll()){
            if(a.getProgram_Num()==proNum){
                list.add(a);
            }
        }
        return list.stream()
                .map(SeatInfoResponse::new)
                .collect(Collectors.toList());
    }
}
