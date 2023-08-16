package com.psr.seatservice.domian.program;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgramBookingRepository extends JpaRepository<ProgramBooking, Long> {
    @Query("SELECT count(*) FROM ProgramBooking b WHERE b.programViewing.programNo = ?1 AND b.programViewing.viewingDate = ?2 AND b.programViewing.viewingTime = ?3")
    int countByProgramBooking(Long num, String date, String time);

    @Query("SELECT new ProgramBooking(b.seatNum) FROM ProgramBooking b WHERE b.programViewing.programNo = ?1 AND b.programViewing.viewingDate = ?2 AND b.programViewing.viewingTime = ?3")
    List<ProgramBooking> findProgramBookingList(Long num, String date, String time);


}
