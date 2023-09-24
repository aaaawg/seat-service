package com.psr.seatservice.service.program;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psr.seatservice.domian.program.*;
import com.psr.seatservice.domian.user.User;
import com.psr.seatservice.dto.program.request.BizAddProgramRequest;
import com.psr.seatservice.dto.program.request.BizUpdateProgramRequest;
import com.psr.seatservice.dto.program.response.*;
import com.psr.seatservice.dto.user.request.BookingRequest;
import com.psr.seatservice.dto.user.response.BookingListResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.psr.seatservice.SessionConst;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;
    private final ProgramViewingRepository programViewingRepository;
    private final ProgramBookingRepository programBookingRepository;

    public ProgramService(ProgramRepository programRepository, ProgramViewingRepository programViewingRepository, ProgramBookingRepository programBookingRepository) {
        this.programRepository = programRepository;
        this.programViewingRepository = programViewingRepository;
        this.programBookingRepository = programBookingRepository;
    }

    public List<BizProgramListResponse> programs() {
        List<Program> programs = programRepository.findAll();
        return programs.stream()
                .map(BizProgramListResponse::new)
                .collect(Collectors.toList());
    }

    public Program getProgramInfo(Long programNum) {
        return programRepository.findById(programNum)
                .orElseThrow(IllegalArgumentException::new);
    }

    public Long addProgram(BizAddProgramRequest request, String result, String getTitleJsonString) {
        Program program = new Program(request.getTitle(), request.getPlace(), request.getTarget(), request.getType(), request.getStartDate(), request.getEndDate(), request.getSeatingChart(),
                request.getSeatCol(), request.getPeopleNum(), result, getTitleJsonString);
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
                request.getStartDate(), request.getEndDate(), request.getType(), request.getPeopleNum(),
                request.getSeatCol(), request.getSeatingChart());

    }

    public void addProgramViewingDateAndTime(BizAddProgramRequest request, Long programNum) {
        if(request.getViewingDateAndTime() != null) {
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

    public Long getProgramBookingCount(Long programNum, String date, String time) {
        return programBookingRepository.countByBookingList(programNum, date, time);
    }

    public List<Integer> getBookingList(Long programNum, String viewingDate, String viewingTime) {
        List<ProgramBooking> programBookings = programBookingRepository.findProgramBookingList(programNum, viewingDate, viewingTime);
        List<Integer> list = new ArrayList<>();
        for (ProgramBooking programBooking : programBookings) {
            list.add(programBooking.getSeatNum());
        }
        return list;
    }

    public void addBooking(Long programNum, BookingRequest request, String userId) {
        ProgramBooking programBooking = new ProgramBooking(programNum, request.getViewingDate(), request.getViewingTime(), request.getSeatNum(), request.getProgramResponse(), userId);
        programBookingRepository.save(programBooking);
    }

    public String getProgramForm(Long programNum){
        return programRepository.findById(programNum).get().getProgramHtml();
    }

    @Transactional
    public void updateProgramForm(Long programNum, String request) {
        Program program = programRepository.findById(programNum)
                .orElseThrow(IllegalAccessError::new);
        program.updateForm(request);
    }
    @Transactional
    public void updateProgramFormTitle(Long programNum, String getTitleJsonString){
        Program program = programRepository.findById(programNum).orElse(null);

        program.updateJsonFrom(getTitleJsonString);
        programRepository.save(program);
    }

    //Json 값넘기기 Test
    public JsonNode getJson(Long programNum){
        Program program = programRepository.findById(programNum).orElse(null);
        String re = program.getProgramQuestion();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonData = null;
        try {
            if(re!=null) {
                jsonData = objectMapper.readTree(re);
            }
            if(program != null){
                System.out.println("Json Change Data : "+jsonData);
                return jsonData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    @Transactional
    public void BookingDelete(Long bookingNum){
        programBookingRepository.deleteByBookingNum(bookingNum);
    }

    public Long getBookingNumCount(Long programNum){
        return programBookingRepository.countByProgramNum(programNum);
    }
}
