package com.psr.seatservice.service.program;

import com.psr.seatservice.domian.program.Program;
import com.psr.seatservice.domian.program.ProgramRepository;
import com.psr.seatservice.dto.program.request.AdminAddProgramRequest;
import com.psr.seatservice.dto.program.request.AdminUpdateProgramRequest;
import com.psr.seatservice.dto.program.response.ProgramAdminResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;

    public ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Transactional
    public List<ProgramAdminResponse> programs() {
        List<Program> programs = programRepository.findAll();
        return programs.stream()
                .map(ProgramAdminResponse::new)
                .collect(Collectors.toList());
    }

    public Program programInfo(Long programNum) {
        return programRepository.findById(programNum)
                .orElseThrow(IllegalArgumentException::new);
    }

    public void addProgram(AdminAddProgramRequest request) {
        programRepository.save(new Program(request.getTitle(), request.getPlace(), request.getTarget(),
                request.getStartDate(), request.getEndDate()));
    }

    @Transactional
    public void updateProgramInfo(Long programNum, AdminUpdateProgramRequest request) {
        Program program = programRepository.findById(programNum)
                .orElseThrow(IllegalAccessError::new);
        program.updateInfo(request.getTitle(), request.getPlace(), request.getTarget(),
                request.getStartDate(), request.getEndDate());
    }
}
