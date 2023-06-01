package com.psr.seatservice.controller.program;

import com.psr.seatservice.domian.program.Program;
import com.psr.seatservice.dto.program.request.AdminAddProgramRequest;
import com.psr.seatservice.dto.program.request.AdminUpdateProgramRequest;
import com.psr.seatservice.dto.program.response.AdminProgramResponse;
import com.psr.seatservice.dto.program.response.ProgramInfoAdminResponse;
import com.psr.seatservice.service.program.ProgramService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/admin/program")
public class ProgramAdminController {
    private final ProgramService programService;

    public ProgramAdminController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping
    public String programs(Model model) {
        List<AdminProgramResponse> programs = programService.programs();
        model.addAttribute("programs", programs);
        return "program/adminPrograms";
    }

    @GetMapping("/info/{programNum}")
    public String programInfo(@PathVariable Long programNum, Model model) {
        Program program = programService.programInfo(programNum);
        model.addAttribute("programInfo", new ProgramInfoAdminResponse(program));
        return "program/adminProgramInfo";
    }

    @GetMapping("/add")
    public String addProgram() {
        return "program/adminAddProgram";
    }

    @PostMapping( "/add")
    public String addProgram(AdminAddProgramRequest request) {
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
        return "redirect:/admin/program/info/{programNum}";
    }


    //화면에 프로그램 전체 전송
    @GetMapping("/List")
    public List<ProgramInfoAdminResponse> getAll(){
        return programService.findA();
    }
}
