package com.psr.seatservice.service.program;

import com.psr.seatservice.domian.program.*;
import com.psr.seatservice.dto.program.request.BizAddProgramRequest;
import com.psr.seatservice.dto.program.request.BizUpdateProgramRequest;
import com.psr.seatservice.dto.program.response.BizProgramListResponse;
import com.psr.seatservice.dto.program.response.ProgramListResponse;
import com.psr.seatservice.dto.program.response.ProgramInfoResponse;
import com.psr.seatservice.dto.program.response.ProgramViewingDateAndTimeResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;
    private final ProgramViewingRepository programViewingRepository;

    public ProgramService(ProgramRepository programRepository, ProgramViewingRepository programViewingRepository) {
        this.programRepository = programRepository;
        this.programViewingRepository = programViewingRepository;
    }

    public List<BizProgramListResponse> programs() {
        List<Program> programs = programRepository.findAll();
        return programs.stream()
                .map(BizProgramListResponse::new)
                .collect(Collectors.toList());
    }

    public Program programInfo(Long programNum) {
        return programRepository.findById(programNum)
                .orElseThrow(IllegalArgumentException::new);
    }

    public Long addProgram(BizAddProgramRequest request) {
        Program program = new Program(request.getTitle(), request.getPlace(), request.getTarget(), request.getType(), request.getStartDate(),
                request.getEndDate());
        programRepository.save(program);
        Long programNum = program.getProgramNum();

        addProgramViewingDateAndTime(request, programNum);
        return program.getProgramNum();
    }

    @Transactional
    public void updateProgramInfo(Long programNum, BizUpdateProgramRequest request) {
        Program program = programRepository.findById(programNum)
                .orElseThrow(IllegalAccessError::new);
        program.updateInfo(request.getTitle(), request.getPlace(), request.getTarget(),
                request.getStartDate(), request.getEndDate());
    }

    public void addProgramViewingDateAndTime(BizAddProgramRequest request, Long programNum) {
        if(request.getViewingDateAndTime() != null) {
            int size = request.getViewingDateAndTime().size();
            System.out.println("size :" + size);
            String dateAndTime, date, time;
            List<ProgramViewing> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                dateAndTime = request.getViewingDateAndTime().get(i);
                date = dateAndTime.substring(0, 10);
                time = dateAndTime.substring(11);
                ProgramViewing pk = new ProgramViewing(programNum, date, time);
                list.add(pk);
            }
            programViewingRepository.saveAll(list);
        }
    }

    public List<ProgramListResponse> mainPrograms() {
        List<Program> programs = programRepository.findAll();
        return programs.stream()
                .map(ProgramListResponse::new)
                .collect(Collectors.toList());
    }

    public List<ProgramViewingDateAndTimeResponse> getProgramViewingDateAndTime(Long programNum) {
        List<ProgramViewing> programViewings = programViewingRepository.findByProgramNo(programNum);
        return programViewings.stream()
                .map(ProgramViewingDateAndTimeResponse::new)
                .collect(Collectors.toList());
    }

    public List<ProgramInfoResponse> findA() {
        //...
        List<Program> programs = programRepository.findAll();
        return programs.stream()
                .map(ProgramInfoResponse::new)
                .collect(Collectors.toList());
    }
}
