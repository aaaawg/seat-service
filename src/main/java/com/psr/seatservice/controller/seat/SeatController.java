package com.psr.seatservice.controller.seat;

import com.psr.seatservice.dto.seat.response.SeatInfoResponse;
import com.psr.seatservice.service.seat.SeatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
public class SeatController {
    private SeatService seatService;
    public SeatController(SeatService seatService) { this.seatService = seatService;}

    @GetMapping("/List/{pro}")
    public List<SeatInfoResponse> getSeat(@PathVariable("pro") Long proNum){
        List<SeatInfoResponse> a = seatService.findByPro(proNum);

        return seatService.findByPro(proNum);
    }
//관리자 페이지 설정하기
}
