package com.psr.seatservice.controller.program;

import com.psr.seatservice.domian.program.Program;
import com.psr.seatservice.dto.files.FileDto;
import com.psr.seatservice.dto.program.request.BizAddProgramRequest;
import com.psr.seatservice.dto.program.request.BizUpdateProgramRequest;
import com.psr.seatservice.dto.program.response.BizProgramListResponse;
import com.psr.seatservice.dto.program.response.ProgramInfoResponse;
import com.psr.seatservice.service.files.FilesService;
import com.psr.seatservice.service.program.ProgramService;
import com.psr.seatservice.util.MD5Generator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    //프로그램 정보 등록
    @PostMapping( "/add")
    public String addProgram(BizAddProgramRequest request, @RequestParam("file") MultipartFile files) {
        try{
            String origFilename = files.getOriginalFilename();
            String filename = new MD5Generator(origFilename).toString() + origFilename;
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
            String filePath = savePath + "\\" + filename;
            files.transferTo(new File(filePath));

            FileDto fileDto = new FileDto(origFilename,filename,filePath);

            Long id = fileService.saveFile(fileDto);
            request.setFileId(id);
            programService.addProgram(request);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return "redirect:";
    }

    @GetMapping("/update/{programNum}")
    public String updateProgramInfo(@PathVariable Long programNum, Model model) {
        Program program = programService.programInfo(programNum);
        model.addAttribute("programInfo", new ProgramInfoResponse(program));
        return "program/bizUpdateProgramInfo";
    }

    //프로그램 정보 수정
    @PostMapping("/update/{programNum}")
    public String updateProgramInfo(@PathVariable Long programNum, BizUpdateProgramRequest request) {
        programService.updateProgramInfo(programNum, request);
        return "redirect:/business/program/info/{programNum}";
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
}
