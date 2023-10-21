package com.psr.seatservice.domian.program;

import com.psr.seatservice.dto.program.response.BizProgramBookingUserListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgramBookingRepository extends JpaRepository<ProgramBooking, Long> {
    @Query("SELECT count(*) FROM ProgramBooking b WHERE b.programViewing.programNo = ?1 AND b.programViewing.viewingDate = ?2 AND b.programViewing.viewingTime = ?3 AND b.status != '취소'")
    int countByProgramBooking(Long num, String date, String time);

    @Query("SELECT new ProgramBooking(b.seatNum) FROM ProgramBooking b WHERE b.programViewing.programNo = ?1 AND b.programViewing.viewingDate = ?2 AND b.programViewing.viewingTime = ?3")
    List<ProgramBooking> findProgramBookingList(Long num, String date, String time);

    @Query("select new com.psr.seatservice.dto.program.response.BizProgramBookingUserListResponse(u.name, u.phone, u.birth, u.email, u.address, b.seatNum, b.bookingNum, b.bookingDate, b.updateDate, b.status) " +
            "from User u, ProgramBooking b where u.id = b.user.id and b.programNum = ?1 and b.viewingDate = ?2 and b.viewingTime = ?3")
    List<BizProgramBookingUserListResponse> findByProgramNumAndViewingDateAndViewingTime(Long programNum, String viewingDate, String viewingTime);

    void deleteByBookingNum(Long bookingNum);

    @Query("SELECT count(*) FROM ProgramBooking WHERE programNum = ?1")
    Long countByProgramNum(Long programNum);


    boolean existsByProgramNumAndSeatNumAndViewingDateAndViewingTime(Long programNum, Integer seatNum, String viewingDate, String viewingTime);

    boolean existsByProgramNumAndUser_userId(Long programNum, String writer);
    boolean existsByProgramNumAndViewingDateAndViewingTimeAndUser_Id(Long programNum, String viewingDate, String viewingTime, Long userId);

    int countByUser_IdAndStatus(Long id, String status);
}
