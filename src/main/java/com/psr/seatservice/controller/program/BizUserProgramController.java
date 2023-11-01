package com.psr.seatservice.controller.program;

import com.fasterxml.jackson.databind.JsonNode;
import com.psr.seatservice.domian.program.Program;
import com.psr.seatservice.domian.user.User;
import com.psr.seatservice.dto.files.FileDto;
import com.psr.seatservice.dto.program.request.BizAddProgramRequest;
import com.psr.seatservice.dto.program.request.BizUpdateProgramBookingStatusRequest;
import com.psr.seatservice.dto.program.request.BizUpdateProgramRequest;
import com.psr.seatservice.dto.program.response.*;
import com.psr.seatservice.service.files.FilesService;
import com.psr.seatservice.service.program.ProgramService;
import com.psr.seatservice.service.user.UserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/business/program")
public class BizUserProgramController {
    private final ProgramService programService;
    private final FilesService fileService;
    private final UserDetailService userDetailService;

    public BizUserProgramController(ProgramService programService, FilesService fileService, UserDetailService userDetailService) {
        this.programService = programService;
        this.fileService = fileService;
        this.userDetailService = userDetailService;
    }

    //기업 사용자 - 해당 사용자가 등록한 프로그램 목록 표시
    @GetMapping
    public String programs(@AuthenticationPrincipal User user, Model model) {
        List<BizProgramListResponse> programs = programService.programs(user.getId());
        model.addAttribute("programs", programs);
        return "program/bizProgramList";
    }

    @GetMapping("/add")
    public String addProgram(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("userAddr", user.getAddress());
        return "program/bizAddProgram";
    }

    @PostMapping( "/add")
    public String addProgram(BizAddProgramRequest request, @RequestParam("file") List<MultipartFile> files
    , @RequestParam(value="formHtml", required = false) String formHtml, @RequestParam(value = "getTitleJson", required = false) String getTitleJsonString, @AuthenticationPrincipal User user) throws IOException {
        System.out.println("FORM: "+formHtml);
        Long proNum = programService.addProgram(request, formHtml, getTitleJsonString, user);
        //programService.addProgramFormTitle(proNum, getTitleJsonString);

        /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            String savePath = System.getProperty("user.dir") + "\\files";
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) {
                try{
                    new File(savePath).mkdir();
                }
                catch(Exception e){
                    e.getStackTrace();
                }
            }

            for (MultipartFile multipartFile : files) {
                fileService.saveFiles(multipartFile, proNum);
            }

        return "redirect:";
    }

    @GetMapping("/update/{programNum}")
    public String updateProgramInfo(@AuthenticationPrincipal User user, @PathVariable Long programNum, Model model) {
        Program program = programService.getProgramInfo(programNum);
        List<ProgramViewingDateAndTimeResponse> viewTime = programService.getProgramViewingDateAndTime(programNum);

        FileDto fileDto;
        List<FileDto> list = fileService.getFileByProNum(program.getProgramNum());
        fileDto = new FileDto();
        if(!list.isEmpty()) {
            fileDto.setFilename("InImage");
            model.addAttribute("fileList", list);
        } else {
            fileDto.setFilename("NoInImage");
        }

        Long bookingCount = programService.getBookingNumCount(programNum);
        List<ProgramBookingDateTimeResponse> dateTime =programService.getDateTime(programNum);

        model.addAttribute("userAddr", user.getAddress());
        model.addAttribute("programInfo", new ProgramInfoUpdateResponse(program));
        model.addAttribute("viewTime",viewTime);
        model.addAttribute("file", fileDto);
        model.addAttribute("bookingCount", bookingCount);
        model.addAttribute("dateTime",dateTime);
        return "program/bizUpdateProgramInfo";
    }

    //프로그램 정보 수정
    @PostMapping("/update/{programNum}")
    public String updateProgramInfo(@PathVariable Long programNum, BizUpdateProgramRequest request, @RequestParam("file") List<MultipartFile> files,
                                    @RequestParam(value ="deleteFile", required = false) List<String> deleteFiles,
                                    @RequestParam(value ="deleteFile2", required = false) List<String> deleteFiles2) throws IOException {
        if(request.getSeatingChart().isEmpty()){request.setSeatingChart(null);}

        List<ProgramViewingDateAndTimeResponse> viewTime = programService.getProgramViewingDateAndTime(programNum);
        List<String> viewingDateAndTime = new ArrayList<>();
        List<String> viewingDateAndTimeDel = new ArrayList<>();
        List<String> viewingDateAndTimeAll = new ArrayList<>();

        for(int i=0;i<request.getViewingDateAndTime().size();i++){
            String re = request.getViewingDateAndTime().get(i);
            boolean notInRequest = true;
            for (ProgramViewingDateAndTimeResponse view : viewTime) {
                String result = view.getViewingDate()+'T'+view.getViewingTime();
                if (re.equals(result)) {
                    notInRequest = false;
                    viewingDateAndTimeAll.add(re);
                    break; // request.getViewingDateAndTime()에 있는 경우 루프를 종료
                }
            }
            if (notInRequest) {
                // request.getViewingDateAndTime()에 없는 경우
                if(re != null) {
                    viewingDateAndTime.add(re);
                    viewingDateAndTimeAll.add(re);
                }
            }
        }

        for(int i=0;i< viewTime.size();i++){
            String re = viewTime.get(i).getViewingDate()+'T'+viewTime.get(i).getViewingTime();
            boolean notInRequest = false;
            for(int j=0;j< viewingDateAndTimeAll.size(); j++){
                if(re.equals(viewingDateAndTimeAll.get(j))){
                    notInRequest = true;
                    break;
                }
            }
            if(notInRequest == false){
                System.out.println("Del: " + re);
                viewingDateAndTimeDel.add(re);
            }
        }
        request.setViewingDateAndTime(viewingDateAndTime);

        programService.updateProgramInfo(programNum, request);
        //삭제
        programService.deleteViewing(programNum, viewingDateAndTimeDel);

        String savePath = System.getProperty("user.dir");

        if (files.get(0).getOriginalFilename().equals("")){
            if (deleteFiles!=null && deleteFiles2!=null) {
            }else if(deleteFiles==null){
                fileService.deleteFile(programNum);
                //실제로 파일 삭제
                if (deleteFiles2 != null) {
                    int num = deleteFiles2.size();
                    for (int i = 0; i < num; i++) {
                        File fileToDelete = new File(savePath + deleteFiles2.get(i));
                        if (fileToDelete.exists()) {
                            fileToDelete.delete();
                        }
                    }
                }
            }
        }else {
            if (deleteFiles == null) {
                if (deleteFiles2 != null) {
                    //삭제
                    fileService.deleteFile(programNum);
                    //실제로 파일 삭제
                    int num = deleteFiles2.size();
                    for (int i = 0; i < num; i++) {
                        File fileToDelete = new File(savePath + deleteFiles2.get(i));
                        if (fileToDelete.exists()) {
                            fileToDelete.delete();
                        }
                    }
                }
            }
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            //추가
            for (MultipartFile multipartFile : files) {
                fileService.saveFiles(multipartFile, programNum);
            }
        }
        //return "redirect:/business/program/info/{programNum}";
        return "redirect:/program/"+ programNum;
    }


    @GetMapping("/seat")
    public String createSeatingChart() {
        return "program/bizCreateSeatingChart";
    }

    @GetMapping("/{programNum}")
    public @ResponseBody ResponseEntity<Object> viewingAndPeopleNumList(@PathVariable Long programNum) {
        List<BizProgramViewingDateAndTimeAndPeopleNumResponse> list = programService.getProgramViewingDateAndTimeAndPeopleNum(programNum);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/{programNum}/booking")
    public String showBookingUserListPage(@PathVariable Long programNum, @RequestParam String date, @RequestParam String time, Model model){
        List<BizProgramBookingUserListResponse> list = programService.getBookingUserList(programNum, date, time);
        boolean checkSC = programService.checkSeatingChart(programNum);

        model.addAttribute("userList", list);
        model.addAttribute("checkSC", checkSC);
        model.addAttribute("programNum", programNum);
        model.addAttribute("bookingNum",programService.getProgramBookingCount(programNum, date, time));
        return "program/bizBookingUserList";
    }

    @PutMapping("/{programNum}/booking")
    public @ResponseBody ResponseEntity<Void> updateBookingStatus(@PathVariable Long programNum, @RequestBody BizUpdateProgramBookingStatusRequest request){
        programService.updateBookingStatus(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{programNum}/seat")
    public String showSeatingChart(@PathVariable Long programNum, @RequestParam String date, @RequestParam String time, Model model) {
        Program program = programService.getProgramInfo(programNum);
        List<Integer> list = programService.getBookedSeats(programNum, date, time);

        BizProgramBookingInfoResponse bookingInfoResponse = new BizProgramBookingInfoResponse(program.getSeatingChart(), list, program.getSeatCol(), program.getTitle(), date, time);
        model.addAttribute("seats", bookingInfoResponse);
        return "program/bizSeatingChart";
    }

    @GetMapping("/updateSeat")
    public String updateSeatingChart() {
        return "program/bizUpdateSeatingChart";
    }

    @GetMapping("/{programNum}/{bookingNum}/userResponse")
    public String bookingUserResponse(@PathVariable Long programNum, @PathVariable Long bookingNum, Model model){
        JsonNode programQuestion = programService.getQuestionJson(programNum);
        JsonNode programResponse = programService.getResponseJson(bookingNum);
        String name = programService.getBookingUserName(bookingNum);

        model.addAttribute("question",programQuestion);
        model.addAttribute("response",programResponse);
        model.addAttribute("userName",name);
        return "program/bizBookingUserFormResult";
    }

    //관리자 폼
    @GetMapping("/{programNum}/formEdit")
    public String programFormEdit(@PathVariable Long programNum, Model model) {
        model.addAttribute("ProgramForm", programService.getProgramForm(programNum));
        //Json 넘겨보기
        model.addAttribute("ProgramJson", programService.getQuestionJson(programNum));
        return "program/programEdit";
    }

    @PostMapping("/{programNum}/formEdit")
    public String programFormEdit(@PathVariable Long programNum, @RequestParam(value = "formHtml", required = false) String request,
                                  @RequestParam(value = "getTitleJson", required = false) String getTitleJsonString) {
        programService.updateProgramForm(programNum, request);
        if (getTitleJsonString.equals("{}")) {
            getTitleJsonString = null;
        }
        programService.updateProgramFormTitle(programNum, getTitleJsonString);
        return "redirect:/program/" + programNum;
    }
}
