package com.psr.seatservice.controller.program;

import com.psr.seatservice.domian.program.Program;
import com.psr.seatservice.dto.program.request.BizAddProgramRequest;
import com.psr.seatservice.dto.program.request.BizUpdateProgramRequest;
import com.psr.seatservice.dto.program.response.BizProgramListResponse;
import com.psr.seatservice.dto.program.response.ProgramInfoResponse;
import com.psr.seatservice.service.program.ProgramService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/business/program")
public class BizUserProgramController {
    private final ProgramService programService;

    public BizUserProgramController(ProgramService programService) {
        this.programService = programService;
    }

    //기업 사용자 - 해당 사용자가 등록한 프로그램 목록 표시
    @GetMapping
    public String programs(Model model) {
        List<BizProgramListResponse> programs = programService.programs();
        model.addAttribute("programs", programs);
        return "program/bizProgramList";
    }

    @GetMapping("/add")
    public String addProgram() {
        return "program/bizAddProgram";
    }

    //프로그램 정보 등록
    @PostMapping( "/add")
    public String addProgram(BizAddProgramRequest request) {
        programService.addProgram(request);
        return "redirect:";
    }

    @GetMapping("/update/{programNum}")
    public String updateProgramInfo(@PathVariable Long programNum, Model model) {
        Program program = programService.programInfo(programNum);
        model.addAttribute("programInfo", new ProgramInfoResponse(program));
        return "program/bizUpdateProgramInfo";
    }

    //프로그램 정보 수정
    @PostMapping("/update/{programNum}")
    public String updateProgramInfo(@PathVariable Long programNum, BizUpdateProgramRequest request) {
        programService.updateProgramInfo(programNum, request);
        return "redirect:/business/program/info/{programNum}";
    }

    //해당 프로그램을 예약한 개인 사용자 목록 조회
    @GetMapping("/booking/{programNum}")
    public String bookingUserList(@PathVariable Long programNum) {
        return "user/bizBookingUserList";
    }
}
