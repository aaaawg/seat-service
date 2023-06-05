package com.psr.seatservice.controller.program;

import com.psr.seatservice.dto.program.response.MainProgramResponse;
import com.psr.seatservice.service.program.ProgramService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProgramController {
    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("/")
    public String main(Model model) {
        List<MainProgramResponse> programs = programService.mainPrograms();
        model.addAttribute("programs", programs);
        return "program/programList";
    }
}
