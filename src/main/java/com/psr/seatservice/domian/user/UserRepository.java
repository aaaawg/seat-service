package com.psr.seatservice.domian.user;

import com.psr.seatservice.dto.user.response.BookingDetailResponse;
import com.psr.seatservice.dto.user.response.BookingListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT new com.psr.seatservice.dto.user.response.BookingListResponse(p.title, pb.viewingDate, pb.viewingTime, pb.bookingNum) FROM ProgramBooking pb JOIN Program p ON pb.programNum = p.programNum WHERE pb.user.id = ?1")
    List<BookingListResponse> findProgramBookingInfoByUserId(Long id);

    @Query("SELECT new com.psr.seatservice.dto.user.response.BookingDetailResponse(p.programNum, p.title, p.endDate, p.place, p.type, p.way, pb.bookingDate, pb.updateDate, pb.reason, pb.status, pb.viewingDate, pb.viewingTime, pb.seatNum, pb.bookingNum) FROM ProgramBooking pb JOIN Program p ON pb.programNum = p.programNum WHERE pb.user.id=?1 AND pb.bookingNum = ?2")
    BookingDetailResponse findProgramBookingDetailByUserId(Long id, Long bookingNum);

    boolean existsByUserId(String userId);

    User findByUserId(String userId);
}
