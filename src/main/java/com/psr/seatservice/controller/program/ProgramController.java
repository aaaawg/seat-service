package com.psr.seatservice.controller.program;

import com.psr.seatservice.SessionConst;
import com.psr.seatservice.domian.program.Program;
import com.psr.seatservice.domian.user.User;
import com.psr.seatservice.dto.files.FileDto;
import com.psr.seatservice.dto.program.response.ProgramListResponse;
import com.psr.seatservice.dto.program.response.ProgramInfoResponse;
import com.psr.seatservice.service.files.FilesService;
import com.psr.seatservice.dto.program.response.ProgramViewingDateAndTimeResponse;
import com.psr.seatservice.service.program.ProgramService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProgramController {
    private final ProgramService programService;
    private final FilesService filesService;

    public ProgramController(ProgramService programService, FilesService filesService) {
        this.programService = programService;
        this.filesService = filesService;
    }

    //메인 페이지 - 프로그램 목록 표시
    @GetMapping("/")
    public String main(Model model, HttpServletRequest request) {
        List<ProgramListResponse> programs = programService.mainPrograms();
        HttpSession session = request.getSession(false);
        if (session != null) {
            User logUser = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);
            model.addAttribute("name",logUser.getName());
        }
        model.addAttribute("programs", programs);
        return "program/programList";
    }

    //프로그램 상세정보 
    @GetMapping("/program/{programNum}")
    public String programInfo(@PathVariable Long programNum, Model model) throws IllegalArgumentException{
        Program program = programService.programInfo(programNum);
        System.out.println(program.getFileId());
        FileDto fileDto;
        if(program.getFileId() != null) fileDto = filesService.getFile(program.getFileId());
        else {
            fileDto = new FileDto();
            fileDto.setFilename("NoInImage");
        }

        List<ProgramViewingDateAndTimeResponse> viewing = programService.getProgramViewingDateAndTime(programNum);
        model.addAttribute("programInfo", new ProgramInfoResponse(program));
        model.addAttribute("file",fileDto);
        model.addAttribute("programViewing", viewing);
        return "program/programInfo";
    }

    @GetMapping("/booking/{programNum}")
    public String booking(@PathVariable Long programNum) {
        return "program/programSeatBooking";
    }
}
