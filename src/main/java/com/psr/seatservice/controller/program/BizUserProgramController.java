package com.psr.seatservice.controller.program;

import com.psr.seatservice.domian.program.Program;
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
    , @RequestParam(value="formHtml", required = false) String formHtml, @RequestParam(value="getTitleJson", required = false) String getTitleJsonString) throws IOException {
        Long proNum = programService.addProgram(request, formHtml, getTitleJsonString);

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

        Long bookingCount = programService.getBookingNumCount(programNum);

        model.addAttribute("programInfo", new ProgramInfoResponse(program));
        model.addAttribute("file", fileDto);
        model.addAttribute("bookingCount", bookingCount);
        return "program/bizUpdateProgramInfo";
    }

    //프로그램 정보 수정
    @PostMapping("/update/{programNum}")
    public String updateProgramInfo(@PathVariable Long programNum, BizUpdateProgramRequest request, @RequestParam("file") List<MultipartFile> files,
                                    @RequestParam(value ="deleteFile", required = false) List<String> deleteFiles,
                                    @RequestParam(value ="deleteFile2", required = false) List<String> deleteFiles2) throws IOException {
        System.out.println("TEst: "+ request.getPeopleNum());

        programService.updateProgramInfo(programNum, request);
        String savePath = System.getProperty("user.dir");
        //1. 변경 안하는 경우 - files files.get(0).getOriginalFilename().equals("")), deleteFiles O, 삭제X 추가X @
        //2. 사진 없다가 새로 추가 - files O, deleteFiles X, 삭제X 추가O @
        //3. 사진 있었고 새로 변경 - files O, deleteFiles O, 삭제O 추가O @
        //4. 사진 없애기 - files X, deleteFiles X, deleteFiles2 O, 삭제O 추가X @
        if (files.get(0).getOriginalFilename().equals("")){
            if (deleteFiles==null && deleteFiles2==null) {

            }else if(deleteFiles==null){
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
        }else {
            if (deleteFiles!=null){
                //삭제
                fileService.deleteFile(programNum);
                //실제로 파일 삭제
                int num = deleteFiles.size();
                for (int i = 0; i < num; i++) {
                    File fileToDelete = new File(savePath + deleteFiles.get(i));
                    if (fileToDelete.exists()) {
                        fileToDelete.delete();
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

    //해당 프로그램을 예약한 개인 사용자 목록 조회
    @GetMapping("/booking/{programNum}")
    public String bookingUserList(@PathVariable Long programNum) {
        return "user/bizBookingUserList";
    }

    @GetMapping("/updateSeat")
    public String updateSeatingChart() {
        return "program/bizUpdateSeatingChart";
    }
}
