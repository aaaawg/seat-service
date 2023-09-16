package com.psr.seatservice.controller.program;

import com.psr.seatservice.domian.program.Program;
import com.psr.seatservice.dto.program.request.BizUpdateProgramBookingStatusRequest;
import com.psr.seatservice.dto.program.response.*;
import com.psr.seatservice.dto.files.FileDto;
import com.psr.seatservice.dto.program.request.BizAddProgramRequest;
import com.psr.seatservice.dto.program.request.BizUpdateProgramRequest;
import com.psr.seatservice.dto.program.response.BizProgramListResponse;
import com.psr.seatservice.dto.program.response.ProgramInfoResponse;
import com.psr.seatservice.service.files.FilesService;
import com.psr.seatservice.service.program.ProgramService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/business/program")
public class BizUserProgramController {
    private final ProgramService programService;
    private final FilesService fileService;

    public BizUserProgramController(ProgramService programService, FilesService fileService) {
        this.programService = programService;
        this.fileService = fileService;
    }

    //기업 사용자 - 해당 사용자가 등록한 프로그램 목록 표시
    @GetMapping
    public String programs(Model model) {
        //등록한 사용자 아이디 추가하기
        List<BizProgramListResponse> programs = programService.programs();
        model.addAttribute("programs", programs);
        return "program/bizProgramList";
    }

    @GetMapping("/add")
    public String addProgram() {
        return "program/bizAddProgram";
    }

    @PostMapping( "/add")
    public String addProgram(BizAddProgramRequest request, @RequestParam("file") List<MultipartFile> files
    , @RequestParam(value="formHtml") String formHtml, @RequestParam("getTitleJson") String getTitleJsonString) throws IOException {
        Long proNum = programService.addProgram(request, formHtml, getTitleJsonString);

        //System.out.println("test Json Data: "+getTitleJsonString);
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
    public String updateProgramInfo(@PathVariable Long programNum, Model model) {
        Program program = programService.getProgramInfo(programNum);

        FileDto fileDto;
        List<FileDto> list = fileService.getFileByProNum(program.getProgramNum());
        fileDto = new FileDto();
        if(list != null) {
            fileDto.setFilename("InImage");
            model.addAttribute("fileList", list);
        } else {
            fileDto.setFilename("NoInImage");
        }

        model.addAttribute("programInfo", new ProgramInfoResponse(program));
        model.addAttribute("file", fileDto);
        return "program/bizUpdateProgramInfo";
    }

    //프로그램 정보 수정
    @PostMapping("/update/{programNum}")
    public String updateProgramInfo(@PathVariable Long programNum, BizUpdateProgramRequest request, @RequestParam("file") List<MultipartFile> files) throws IOException {
        programService.updateProgramInfo(programNum, request);
        String savePath = System.getProperty("user.dir") + "\\files";

        //파일 수정
        // 먼저 있던 사진들 지우고 새로 추가하기
        //삭제
        fileService.deleteFile(programNum);

        //실제로 파일 삭제
        /*File fileToDelete = new File(filePath);

        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                System.out.println("파일이 성공적으로 삭제되었습니다.");
            } else {
                System.err.println("파일을 삭제하는 중 오류가 발생했습니다.");
            }
        } else {
            System.err.println("파일이 존재하지 않습니다.");
        }*/

        /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
        if (!new File(savePath).exists()) {
            try{new File(savePath).mkdir();}
            catch(Exception e){e.getStackTrace();}
        }
        //추가
        for (MultipartFile multipartFile : files) {
            fileService.saveFiles(multipartFile, programNum);
        }

        //return "redirect:/business/program/info/{programNum}";
        return "redirect:/program/"+ programNum;
    }

    @GetMapping("/seat")
    public String createSeatingChart() {
        return "program/bizCreateSeatingChart";
    }

    //프로그램 진행 날짜, 시간별 신청인원수 목록
    @GetMapping("/{programNum}")
    public String viewingAndPeopleNumList(@PathVariable Long programNum, Model model) {
        List<BizProgramViewingDateAndTimeAndPeopleNumResponse> list = programService.getProgramViewingDateAndTimeAndPeopleNum(programNum);
        model.addAttribute("programs", list);
        model.addAttribute("programNum", programNum);
        return "program/bizProgramViewingList";
    }

    @GetMapping("/{programNum}/booking")
    public String showBookingUserListPage(@PathVariable Long programNum, @RequestParam String date, @RequestParam String time, Model model){
        List<BizProgramBookingUserListResponse> list = programService.getBookingUserList(programNum, date, time);
        boolean checkSC = programService.checkSeatingChart(programNum);

        model.addAttribute("userList", list);
        model.addAttribute("checkSC", checkSC);
        model.addAttribute("programNum", programNum);
        return "program/bizBookingUserList";
    }

    @PutMapping("/{programNum}/booking")
    public @ResponseBody ResponseEntity<Object> updateBookingStatus(@PathVariable Long programNum, @RequestBody BizUpdateProgramBookingStatusRequest request){
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
}
