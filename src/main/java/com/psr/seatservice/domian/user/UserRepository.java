package com.psr.seatservice.domian.user;

import com.psr.seatservice.dto.user.response.BookingListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserIdAndPassword(String userId, String password);

    @Query("SELECT new com.psr.seatservice.dto.user.response.BookingListResponse(p.title, pb.viewingDate, pb.viewingTime) FROM ProgramBooking pb JOIN Program p ON pb.programNum = p.programNum WHERE pb.userId = ?1")
    List<BookingListResponse> findProgramBookingInfoByUserId(String userId);
}
