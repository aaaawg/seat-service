package com.psr.seatservice.domian.program;

import com.psr.seatservice.dto.program.response.BizProgramViewingDateAndTimeAndPeopleNumResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgramViewingRepository extends JpaRepository<ProgramViewing, ProgramViewingPK> {
    List<ProgramViewing> findByProgramNo(Long num);

    @Query(value = "select new com.psr.seatservice.dto.program.response.BizProgramViewingDateAndTimeAndPeopleNumResponse(v.programNo, v.viewingDate, v.viewingTime, sum(case when b.status != '취소' then 1 else 0 end)) from ProgramViewing v " +
            "left outer join ProgramBooking b on v.programNo = b.programNum and v.viewingDate = b.viewingDate and v.viewingTime = b.viewingTime where v.programNo = ?1 " +
            "group by v.viewingDate, v.viewingTime") //count에 case 지원X(조건 설정 불가) -> sum 사용
    List<BizProgramViewingDateAndTimeAndPeopleNumResponse> findViewingDateAndTimeAndPeopleNumByProgramNum(Long programNo);

    void deleteByProgramNo(Long programNum);
}
