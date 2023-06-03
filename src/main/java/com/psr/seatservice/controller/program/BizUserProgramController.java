package com.psr.seatservice.controller.program;

import com.psr.seatservice.domian.program.Program;
import com.psr.seatservice.dto.program.request.BizAddProgramRequest;
import com.psr.seatservice.dto.program.request.AdminUpdateProgramRequest;
import com.psr.seatservice.dto.program.response.AdminProgramResponse;
import com.psr.seatservice.dto.program.response.ProgramInfoAdminResponse;
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

    @GetMapping
    public String programs(Model model) {
        List<AdminProgramResponse> programs = programService.programs();
        model.addAttribute("programs", programs);
        return "program/bizUserPrograms";
    }

    @GetMapping("/info/{programNum}")
    public String programInfo(@PathVariable Long programNum, Model model) {
        Program program = programService.programInfo(programNum);
        model.addAttribute("programInfo", new ProgramInfoAdminResponse(program));
        return "program/adminProgramInfo";
    }

    @GetMapping("/add")
    public String addProgram() {
        return "program/bizUserAddProgram";
    }

    @PostMapping( "/add")
    public String addProgram(BizAddProgramRequest request) {
        programService.addProgram(request);
        return "redirect:";
    }

    @GetMapping("/info/update/{programNum}")
    public String updateProgramInfo(@PathVariable Long programNum, Model model) {
        Program program = programService.programInfo(programNum);
        model.addAttribute("programInfo", new ProgramInfoAdminResponse(program));
        return "program/adminUpdateProgram";
    }

    @PostMapping("/info/update/{programNum}")
    public String updateProgramInfo(@PathVariable Long programNum, AdminUpdateProgramRequest request) {
        programService.updateProgramInfo(programNum, request);
        return "redirect:/business/program/info/{programNum}";
    }

    @GetMapping("/booking/{programNum}")
    public String bookingUserList(@PathVariable Long programNum) {
        return "user/bookingUserList";
    }
}
