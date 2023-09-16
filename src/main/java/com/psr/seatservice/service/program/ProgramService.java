package com.psr.seatservice.service.program;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psr.seatservice.domian.program.*;
import com.psr.seatservice.dto.program.request.BizUpdateProgramBookingStatusRequest;
import com.psr.seatservice.dto.program.response.BizProgramViewingDateAndTimeAndPeopleNumResponse;
import com.psr.seatservice.dto.program.request.BizAddProgramRequest;
import com.psr.seatservice.dto.program.request.BizUpdateProgramRequest;
import com.psr.seatservice.dto.program.response.*;
import com.psr.seatservice.dto.user.request.BookingRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        Program program = new Program(request.getTitle(), request.getPlace(), request.getTarget(), request.getType(), request.getStartDate(),
                request.getEndDate(), request.getSeatingChart(), request.getSeatCol(), request.getPeopleNum(), request.getContents(), result, getTitleJsonString);
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
        if (request.getViewingDateAndTime() != null) {
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

    public List<ProgramListResponse> getProgramList(String t) {
        List<ProgramListResponse> programs;

        if(t.equals("online")) {
            programs = programRepository.findAllByType("온라인");
        } else if (t.equals("offline")) {
            programs = programRepository.findAllByType("오프라인");
        } else {
            programs = programRepository.findAllProgramAndImg();
        }

        return programs;
    }

    public List<ProgramViewingDateAndTimeResponse> getProgramViewingDateAndTime(Long programNum) {
        List<ProgramViewing> programViewings = programViewingRepository.findByProgramNo(programNum);
        return programViewings.stream()
                .map(ProgramViewingDateAndTimeResponse::new)
                .collect(Collectors.toList());
    }

    public int getProgramBookingCount(Long programNum, String date, String time) {
        return programBookingRepository.countByProgramBooking(programNum, date, time);
    }

    public List<Integer> getBookedSeats(Long programNum, String viewingDate, String viewingTime) {
        List<ProgramBooking> programBookings = programBookingRepository.findProgramBookingList(programNum, viewingDate, viewingTime);
        List<Integer> list = new ArrayList<>();
        for (ProgramBooking programBooking : programBookings) {
            list.add(programBooking.getSeatNum());
        }
        return list;
    }

    public int addBooking(Long programNum, BookingRequest request, String userId) {
        int count = getProgramBookingCount(programNum, request.getViewingDate(), request.getViewingTime());
        if(count < request.getPeopleNum() || request.getPeopleNum() == -1) {
            if (programBookingRepository.existsByProgramNumAndSeatNumAndViewingDateAndViewingTime(programNum, request.getSeatNum(), request.getViewingDate(), request.getViewingTime())) {
                return 1;
            }
            else {
                ProgramBooking programBooking = new ProgramBooking(programNum, request.getViewingDate(), request.getViewingTime(), request.getSeatNum(), "예정", request.getProgramResponse(), userId);
                programBookingRepository.save(programBooking);
                return 2;
            }
        }
        return 0;
    }

    public List<BizProgramViewingDateAndTimeAndPeopleNumResponse> getProgramViewingDateAndTimeAndPeopleNum(Long programNum) {
        //프로그램 진행 날짜, 시간, 신청인원 목록
        List<BizProgramViewingDateAndTimeAndPeopleNumResponse> list = programViewingRepository.findViewingDateAndTimeAndPeopleNumByProgramNum(programNum);
        return list;
    }

    public List<BizProgramBookingUserListResponse> getBookingUserList(Long programNum, String date, String time) {
        //프로그램을 예약한 사용자 목록
        List<BizProgramBookingUserListResponse> list = programBookingRepository.findByProgramNumAndViewingDateAndViewingTime(programNum, date, time);
        return list;
    }

    public boolean checkSeatingChart(Long num) {
        if(programRepository.findById(num).get().getSeatingChart() == null)
            return false;
        return true;
    }

    @Transactional
    public void updateBookingStatus(BizUpdateProgramBookingStatusRequest request) {
        for (int i = 0; i < request.getBookingNumList().size(); i++) {
            Long bookingNum = Long.valueOf(request.getBookingNumList().get(i));
            ProgramBooking programBooking = programBookingRepository.findById(bookingNum).orElseThrow();
            programBooking.updateStatus(request.getStatus());

            if(request.getReason() != null) {
                programBooking.updateCancelReason(request.getReason());
            }
        }
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

    public void updateProgramFormTitle(Long programNum, String getTitleJsonString){
        Program program = programRepository.findById(programNum).orElse(null);;
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

    public List<ProgramListResponse> getProgramSearchResult(String keyword) {
        String str = "%" + keyword + "%";
        return programRepository.findAllByTitleLike(str);
    }
}
