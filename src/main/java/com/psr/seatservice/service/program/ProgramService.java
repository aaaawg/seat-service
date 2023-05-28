package com.psr.seatservice.service.program;

import com.psr.seatservice.domian.program.*;
import com.psr.seatservice.dto.program.request.AdminAddProgramRequest;
import com.psr.seatservice.dto.program.request.AdminUpdateProgramRequest;
import com.psr.seatservice.dto.program.response.AdminProgramResponse;
import com.psr.seatservice.dto.program.response.MainProgramResponse;
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

    @Transactional
    public List<AdminProgramResponse> programs() {
        List<Program> programs = programRepository.findAll();
        return programs.stream()
                .map(AdminProgramResponse::new)
                .collect(Collectors.toList());
    }

    public Program programInfo(Long programNum) {
        return programRepository.findById(programNum)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void addProgram(AdminAddProgramRequest request) {
        Program program = new Program(request.getTitle(), request.getPlace(), request.getTarget(), request.getType(), request.getStartDate(), request.getEndDate());
        programRepository.save(program);
        Long programNum = program.getProgramNum();

        addProgramViewingDateAndTime(request, programNum);
    }

    @Transactional
    public void updateProgramInfo(Long programNum, AdminUpdateProgramRequest request) {
        Program program = programRepository.findById(programNum)
                .orElseThrow(IllegalAccessError::new);
        program.updateInfo(request.getTitle(), request.getPlace(), request.getTarget(),
                request.getStartDate(), request.getEndDate());
    }

    public void addProgramViewingDateAndTime(AdminAddProgramRequest request, Long programNum) {
        int size = request.getViewingDateAndTime().size();
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

    public List<MainProgramResponse> mainPrograms() {
        List<Program> programs = programRepository.findAll();
        return programs.stream()
                .map(MainProgramResponse::new)
                .collect(Collectors.toList());
    }
}
