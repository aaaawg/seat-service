package com.psr.seatservice.controller.program;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psr.seatservice.SessionConst;
import com.psr.seatservice.domian.program.Program;
import com.psr.seatservice.domian.user.User;
import com.psr.seatservice.dto.ErrorResponse;
import com.psr.seatservice.dto.files.FileDto;
import com.psr.seatservice.dto.program.request.PeopleCountRequest;
import com.psr.seatservice.dto.program.request.ProgramSeatingChartRequest;
import com.psr.seatservice.dto.program.response.*;
import com.psr.seatservice.dto.user.request.BookingRequest;
import com.psr.seatservice.service.files.FilesService;
import com.psr.seatservice.service.program.ProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProgramController {
    private final ProgramService programService;
    private final FilesService filesService;

    public ProgramController(ProgramService programService, FilesService filesService) {
        this.programService = programService;
        this.filesService = filesService;
    }

    @GetMapping("/")
    public String main(Model model) {
        List<ProgramListResponse> programs = programService.getProgramList("main");
        model.addAttribute("programs", programs);
        return "program/programList";
    }

    @GetMapping("/online")
    public String onlineProgramList(Model model) {
        List<ProgramListResponse> programs = programService.getProgramList("online");
        model.addAttribute("programs", programs);
        return "program/programList";
    }

    @GetMapping("/offline")
    public String offlineProgramList(Model model) {
        List<ProgramListResponse> programs = programService.getProgramList("offline");
        model.addAttribute("programs", programs);
        return "program/programList";
    }

    //프로그램 상세정보 
    @GetMapping("/program/{programNum}")
    public String programInfo(@PathVariable Long programNum, Model model) throws IllegalArgumentException {
        Program program = programService.getProgramInfo(programNum);
        List<FileDto> list = filesService.getFileByProNum(program.getProgramNum());
        FileDto fileDto = new FileDto();

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
        model.addAttribute("ProgramForm",programService.getProgramForm(programNum));
        return "program/programBooking";
    }

    @PostMapping("/booking/seat")
    public @ResponseBody String getSeatingChart(@RequestBody ProgramSeatingChartRequest request, Model model) throws JsonProcessingException {
        Program program = programService.getProgramInfo(request.getProgramNum());
        int bookingCount = programService.getProgramBookingCount(request.getProgramNum(), request.getViewingDate(), request.getViewingTime());
        ProgramBookingInfoResponse bookingInfoResponse;
        ObjectMapper objectMapper = new ObjectMapper();
        String json;

        if (program.getSeatingChart() != null) {
            List<Integer> list = programService.getBookedSeats(request.getProgramNum(), request.getViewingDate(), request.getViewingTime());
            bookingInfoResponse = new ProgramBookingInfoResponse(program.getSeatingChart(), program.getSeatCol(), bookingCount, list);
        } else {
            bookingInfoResponse = new ProgramBookingInfoResponse(bookingCount);
        }

        json = objectMapper.writeValueAsString(bookingInfoResponse);
        return json;
    }

    @PostMapping("/booking/{programNum}")
    public @ResponseBody ResponseEntity<Object> addBooking(@PathVariable Long programNum, @RequestBody BookingRequest request, @AuthenticationPrincipal User user) {
        //programBooking 테이블에 예약 데이터 추가
        if(user != null) {
            int result = programService.addBooking(programNum, request, user);
            if(result == 2)
                return ResponseEntity.status(HttpStatus.OK).build();
            else if(result == 1)
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("이미 예약된 좌석 입니다."));
            else
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("인원이 마감되었습니다."));
        }
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("로그인 후 이용할 수 있습니다.")); //로그인 상태가 아닐 경우
    }

    //관리자 폼
    @GetMapping("/program/{programNum}/formEdit")
    public String programFormEdit(@PathVariable Long programNum, Model model){
        model.addAttribute("ProgramForm",programService.getProgramForm(programNum));
        //Json 넘겨보기
        model.addAttribute("ProgramJson",programService.getJson(programNum));
        return "program/programEdit";
    }
    @PostMapping("/program/{programNum}/formEdit")
    public String programFormEdit(@PathVariable Long programNum, @RequestParam(value="formHtml") String request,
                                  @RequestParam("getTitleJson") String getTitleJsonString){
        programService.updateProgramForm(programNum,request);
        programService.updateProgramFormTitle(programNum,getTitleJsonString);
        return "redirect:/program/"+ programNum;
    }

    @PostMapping("/program/peopleCount")
    public @ResponseBody int peopleCount(@RequestBody PeopleCountRequest request) {
        //프로그램 상세정보 - 날짜, 시간 선택 시 예약한 사람 수 표시
        return programService.getProgramBookingCount(request.getProgramNum(), request.getViewingDate(), request.getViewingTime());
    }

    @GetMapping("/program/search")
    public String programKeywordSearch(@RequestParam String keyword, Model model) {
        List<ProgramListResponse> list = programService.getProgramSearchResult(keyword);
        model.addAttribute("list", list);
        return "program/programSearch";
    }
}
