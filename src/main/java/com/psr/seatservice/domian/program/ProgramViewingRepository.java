package com.psr.seatservice.domian.program;

import com.psr.seatservice.dto.program.response.BizProgramViewingDateAndTimeAndPeopleNumResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgramViewingRepository extends JpaRepository<ProgramViewing, ProgramViewingPK> {
    List<ProgramViewing> findByProgramNo(Long num);

    ProgramViewing findByProgramNoAndViewingDateAndViewingTime(Long programNum, String viewingDate, String viewingTime);

    @Query(value = "select new com.psr.seatservice.dto.program.response.BizProgramViewingDateAndTimeAndPeopleNumResponse(v.viewingDate, v.viewingTime, coalesce(count(b.bookingNum), 0)) from ProgramViewing v " +
            "left outer join ProgramBooking b on v.programNo = b.programNum and v.viewingDate = b.viewingDate and v.viewingTime = b.viewingTime where v.programNo = ?1 " +
            "group by v.viewingDate, v.viewingTime")
    List<BizProgramViewingDateAndTimeAndPeopleNumResponse> findViewingDateAndTimeAndPeopleNumByProgramNum(Long programNo);
}
