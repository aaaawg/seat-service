package com.psr.seatservice.controller.program;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psr.seatservice.SessionConst;
import com.psr.seatservice.domian.program.Program;
import com.psr.seatservice.domian.user.User;
import com.psr.seatservice.dto.files.FileDto;
import com.psr.seatservice.dto.program.request.ProgramSeatingChartRequest;
import com.psr.seatservice.dto.program.response.*;
import com.psr.seatservice.dto.user.request.BookingRequest;
import com.psr.seatservice.service.files.FilesService;
import com.psr.seatservice.service.program.ProgramService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String programInfo(@PathVariable Long programNum, Model model) throws IllegalArgumentException {
        Program program = programService.getProgramInfo(programNum);
        FileDto fileDto;
        List<FileDto> list = filesService.getFileByProNum(program.getProgramNum());
        fileDto = new FileDto();
        if(list != null) {
            fileDto.setFilename("InImage");
            model.addAttribute("fileList", list);
        } else {
            fileDto.setFilename("NoInImage");
        }

        List<ProgramViewingDateAndTimeResponse> viewing = programService.getProgramViewingDateAndTime(programNum);
        model.addAttribute("programInfo", new ProgramInfoResponse(program));
        model.addAttribute("file", fileDto);
        model.addAttribute("programViewing", viewing);
        return "program/programInfo";
    }

    @GetMapping("/booking/{programNum}")
    public String booking(@PathVariable Long programNum, Model model) {
        List<ProgramViewingDateAndTimeResponse> viewing = programService.getProgramViewingDateAndTime(programNum);
        model.addAttribute("programViewing", viewing);
        return "program/programBooking";
    }

    @ResponseBody
    @PostMapping("/booking/seat")
    public String getSeatingChart(@RequestBody ProgramSeatingChartRequest request, Model model) throws JsonProcessingException {
        Program program = programService.getProgramInfo(request.getProgramNum());
        Long bookingCount = programService.getProgramBookingCount(request.getProgramNum(), request.getViewingDate(), request.getViewingTime());
        ProgramBookingInfoResponse bookingInfoResponse;
        ObjectMapper objectMapper = new ObjectMapper();
        String json;

        if (program.getSeatingChart() != null) {
            List<Integer> list = programService.getBookingList(request.getProgramNum(), request.getViewingDate(), request.getViewingTime());
            bookingInfoResponse = new ProgramBookingInfoResponse(program.getSeatingChart(), program.getSeatCol(), bookingCount, list);
        } else {
            bookingInfoResponse = new ProgramBookingInfoResponse(bookingCount);
        }

        json = objectMapper.writeValueAsString(bookingInfoResponse);
        return json;
    }

    @ResponseBody
    @PostMapping("/booking/{programNum}")
    public String addBooking(@PathVariable Long programNum, BookingRequest request) {
        //programBooking 테이블에 예약 데이터 추가
        programService.addBooking(programNum, request);
        return "1";
    }

    @GetMapping("/program/{programNum}/form")
    public String programForm(@PathVariable Long programNum, Model model){
        model.addAttribute("ProgramForm",programService.getProgramForm(programNum));
        return "program/programForm";
    }

    @GetMapping("/program/{programNum}/formEdit")
    public String programFormEdit(@PathVariable Long programNum, Model model){
        model.addAttribute("ProgramForm",programService.getProgramForm(programNum));
        return "program/programEdit";
    }
    @PostMapping("/program/{programNum}/formEdit")
    public String programFormEdit(@PathVariable Long programNum, @RequestParam(value="formHtml") String request){
        programService.updateProgramForm(programNum,request);
        return "redirect:/program/{programNum}/form";
    }
}
