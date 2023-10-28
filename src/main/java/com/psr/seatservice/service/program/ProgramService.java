package com.psr.seatservice.service.program;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psr.seatservice.domian.program.*;
import com.psr.seatservice.domian.user.User;
import com.psr.seatservice.dto.program.request.BizAddProgramRequest;
import com.psr.seatservice.dto.program.request.BizUpdateProgramBookingStatusRequest;
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

    public List<BizProgramListResponse> programs(Long user) {
        List<Program> programs = programRepository.findAllByUserId(user);
        return programs.stream()
                .map(BizProgramListResponse::new)
                .collect(Collectors.toList());
    }

    public Program getProgramInfo(Long programNum) {
        return programRepository.findById(programNum)
                .orElseThrow(IllegalArgumentException::new);
    }

    public Long addProgram(BizAddProgramRequest request, String result, String getTitleJsonString, User user) {
        Program program = new Program(request.getTitle(), request.getPlace(), request.getWay(), request.getTarget(), request.getTargetDetail(), request.getType(), request.getStartDate(),
                request.getEndDate(), request.getSeatingChart(), request.getSeatCol(), request.getPeopleNum(), request.getContents(), result, getTitleJsonString, user);
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
                request.getSeatCol(), request.getSeatingChart(), request.getWay());

        updateProgramViewingDateAndTime(request, programNum);
    }

    public void updateProgramViewingDateAndTime(BizUpdateProgramRequest request, Long programNum){
        if (request.getViewingDateAndTime() != null) {
            programViewingRepository.deleteByProgramNo(programNum);

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


    public List<ProgramListResponse> getProgramList(String type, String target) {
        List<ProgramListResponse> programs;

        if(type.equals("online") || type.equals("offline")) {
            if(target == null)
                programs = programRepository.findAllByType(type);
            else
                programs = programRepository.findAllByTarget(type, target);
        }
        else {
            if(target == null)
                programs = programRepository.findAllProgramAndImg();
            else
                programs = programRepository.findAllProgramAndImgByTarget(target);
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

    @Transactional
    public String addBooking(Long programNum, BookingRequest request, User user) {
        int count = getProgramBookingCount(programNum, request.getViewingDate(), request.getViewingTime());
        Program program = getProgramInfo(programNum);
        if(count < request.getPeopleNum() || request.getPeopleNum() == -1) {
            if(program.getTarget().equals("area")) {
                //신청대상이 지역일 경우 주소 확인
                boolean add = checkProgramTargetDetailAndUserAddress(program.getTargetDetail(), user.getAddress());
                if(!add)
                    return "신청대상에 해당하지 않습니다.";
            }

            int nonPCount = programBookingRepository.countByUser_IdAndStatus(user.getId(), "불참");
            if(nonPCount < 3) { //불참 횟수 확인
                if (!programBookingRepository.existsByProgramNumAndViewingDateAndViewingTimeAndUser_Id(programNum, request.getViewingDate(), request.getViewingTime(), user.getId())) { //해당 회차를 신청 했는지
                    if (request.getSeatNum() != null && programBookingRepository.existsByProgramNumAndSeatNumAndViewingDateAndViewingTime(programNum, request.getSeatNum(), request.getViewingDate(), request.getViewingTime())) {
                        return "이미 신청된 좌석입니다.";
                    } else {
                        ProgramBooking programBooking = new ProgramBooking(programNum, request.getViewingDate(), request.getViewingTime(), request.getSeatNum(), "예정", request.getProgramResponse(), user);
                        programBookingRepository.save(programBooking);
                        return null;
                    }
                } else
                    return "이미 해당 회차를 신청했습니다.";
            }
            else
                return "불참 횟수(3회)로 인해 신청할 수 없습니다.";
        }
        return "인원 마감으로 인해 신청할 수 없습니다.";
    }

    public boolean checkProgramTargetDetailAndUserAddress(String targetDetail, String address) {
        String addr = address.split(",")[0];
        String[] addr2 = addr.split(" ", 3);

        if(targetDetail.indexOf(" ") > 0) {
            return targetDetail.equals(addr2[0] + " " + addr2[1]);
        }
        else
            return targetDetail.equals(addr2[0]);
    }

    public List<BizProgramViewingDateAndTimeAndPeopleNumResponse> getProgramViewingDateAndTimeAndPeopleNum(Long programNum) {
        //프로그램 진행 날짜, 시간, 신청인원 목록
        return programViewingRepository.findViewingDateAndTimeAndPeopleNumByProgramNum(programNum);
    }

    public List<BizProgramBookingUserListResponse> getBookingUserList(Long programNum, String date, String time) {
        //프로그램을 예약한 사용자 목록
        return programBookingRepository.findByProgramNumAndViewingDateAndViewingTime(programNum, date, time);
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

    public List<ProgramListResponse> getProgramSearchResult(String keyword) {
        String str = "%" + keyword + "%";
        return programRepository.findAllByTitleLike(str);
    }

    public Long getBookingNumCount(Long programNum){
        return programBookingRepository.countByProgramNum(programNum);
    }

    public List<ProgramListResponse> getUserAroundProgramList(String area, String target, String detail){
        List<ProgramListResponse> programs;
        String str;

        if(target.equals("all")) {
            //프로그램 target = 제한없음, 프로그램 진행 장소 기준 - 대면 프로그램만
            str = (detail == null || detail.equals("01")) ? area.split(" ")[0] + "%" : area + "%";
            programs = programRepository.findAllByTargetAndTypeAndPlaceStartsWith(target, str, "offline");
        }
        else {
            //프로그램 target = 지역, 사용자 주소 = 지역
            if(detail == null) {
                str = area.split(" ")[0];
                programs = programRepository.findAllByTargetAndTargetDetailAll(target, str, area);
            }
            else {
                if(detail.equals("01") || detail.equals("02")) {
                    str = (detail.equals("01")) ? area.split(" ")[0] : area;
                    programs = programRepository.findAllByTargetAndTargetDetail(target, str);
                }
                else
                    programs = null;
            }
        }
        return programs;
    }
}
