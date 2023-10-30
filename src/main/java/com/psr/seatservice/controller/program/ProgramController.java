package com.psr.seatservice.controller.program;

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
import com.psr.seatservice.service.program.QnaService;
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
    private final QnaService qnaService;

    public ProgramController(ProgramService programService, FilesService filesService, QnaService qnaService) {
        this.programService = programService;
        this.filesService = filesService;
        this.qnaService = qnaService;
    }

    @GetMapping("/")
    public String main(@RequestParam(required = false) String target, Model model) {
        List<ProgramListResponse> programs = programService.getProgramList("main", target);
        model.addAttribute("programs", programs);
        model.addAttribute("pType", null);
        return "program/programList";
    }

    @GetMapping("/{type}")
    public String programListByType(@PathVariable String type, @RequestParam(required = false) String target, Model model) {
        List<ProgramListResponse> programs = programService.getProgramList(type, target);
        model.addAttribute("programs", programs);
        model.addAttribute("pType", type);
        return "program/programList";
    }

    //프로그램 상세정보 
    @GetMapping("/program/{programNum}")
    public String programInfo(@PathVariable Long programNum, Model model) throws IllegalArgumentException {
        Program program = programService.getProgramInfo(programNum);
        List<FileDto> list = filesService.getFileByProNum(program.getProgramNum());
        FileDto fileDto = new FileDto();
        List<QnaListResponse> qnaList = qnaService.getQnaList(program.getProgramNum());

        if (list.size() != 0) {
            fileDto.setFilename("InImage");
            model.addAttribute("fileList", list);
        } else {
            fileDto.setFilename("NoInImage");
        }
        boolean ch = programService.checkSeatingChart(programNum);

        List<ProgramViewingDateAndTimeResponse> viewing = programService.getProgramViewingDateAndTime(programNum);
        model.addAttribute("programInfo", new ProgramInfoResponse(program));
        model.addAttribute("file", fileDto);
        model.addAttribute("programViewing", viewing);
        model.addAttribute("chSC", ch);
        model.addAttribute("qnaList", qnaList);

        return "program/programInfo";
    }

    @GetMapping("/booking/{programNum}")
    public String booking(@PathVariable Long programNum, Model model) {
        List<ProgramViewingDateAndTimeResponse> viewing = programService.getProgramViewingDateAndTime(programNum);
        model.addAttribute("programViewing", viewing);
        model.addAttribute("ProgramForm", programService.getProgramForm(programNum));
        return "program/programBooking";
    }

    @PostMapping("/booking/seat")
    public @ResponseBody ProgramBookingInfoResponse getSeatingChart(@RequestBody ProgramSeatingChartRequest request) {
        Program program = programService.getProgramInfo(request.getProgramNum());
        int bookingCount = programService.getProgramBookingCount(request.getProgramNum(), request.getViewingDate(), request.getViewingTime());
        ProgramBookingInfoResponse bookingInfoResponse;

        if (program.getSeatingChart() != null) {
            List<Integer> list = programService.getBookedSeats(request.getProgramNum(), request.getViewingDate(), request.getViewingTime());
            bookingInfoResponse = new ProgramBookingInfoResponse(program.getSeatingChart(), program.getSeatCol(), bookingCount, list);
        } else {
            bookingInfoResponse = new ProgramBookingInfoResponse(bookingCount);
        }

        return bookingInfoResponse;
    }

    @PostMapping("/booking/{programNum}")
    public @ResponseBody ResponseEntity<Object> addBooking(@PathVariable Long programNum, @RequestBody BookingRequest request, @AuthenticationPrincipal User user) {
        //programBooking 테이블에 예약 데이터 추가
        String result = programService.addBooking(programNum, request, user);
        if (result == null)
            return ResponseEntity.status(HttpStatus.OK).build();
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(result));
    }

    //관리자 폼
    @GetMapping("/program/{programNum}/formEdit")
    public String programFormEdit(@PathVariable Long programNum, Model model) {
        model.addAttribute("ProgramForm", programService.getProgramForm(programNum));
        //Json 넘겨보기
        model.addAttribute("ProgramJson", programService.getJson(programNum));
        return "program/programEdit";
    }

    @PostMapping("/program/{programNum}/formEdit")
    public String programFormEdit(@PathVariable Long programNum, @RequestParam(value = "formHtml", required = false) String request,
                                  @RequestParam(value = "getTitleJson", required = false) String getTitleJsonString) {
        programService.updateProgramForm(programNum, request);
        if (getTitleJsonString.equals("{}")) {
            getTitleJsonString = null;
        }
        programService.updateProgramFormTitle(programNum, getTitleJsonString);
        return "redirect:/program/" + programNum;
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

    @GetMapping( "/around/{target}")
    public String userAroundProgramListByTarget(@PathVariable String target, @RequestParam(required = false) String detail, @AuthenticationPrincipal User user, Model model) {
        String[] arr = user.getAddress().split(" ");
        String area = arr[0] + " " + arr[1];

        if (target.equals("all") || target.equals("area")) {
            List<ProgramListResponse> placeList = programService.getUserAroundProgramList(area, target, detail);
            model.addAttribute("programs", placeList);
            model.addAttribute("target", target);
            model.addAttribute("userArea", area);
        }
        return "program/userAroundProgramList";
    }
}
