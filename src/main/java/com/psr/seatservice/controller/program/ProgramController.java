package com.psr.seatservice.controller.program;

import com.psr.seatservice.domian.program.Program;
import com.psr.seatservice.dto.program.response.ProgramListResponse;
import com.psr.seatservice.dto.program.response.ProgramInfoResponse;
import com.psr.seatservice.service.program.ProgramService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProgramController {
    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    //메인 페이지 - 프로그램 목록 표시
    @GetMapping("/")
    public String main(Model model) {
        List<ProgramListResponse> programs = programService.mainPrograms();
        model.addAttribute("programs", programs);
        return "program/programList";
    }

    //프로그램 상세정보 
    @GetMapping("/program/{programNum}")
    public String programInfo(@PathVariable Long programNum, Model model) {
        Program program = programService.programInfo(programNum);
        model.addAttribute("programInfo", new ProgramInfoResponse(program));
        return "program/programInfo";
    }
}
