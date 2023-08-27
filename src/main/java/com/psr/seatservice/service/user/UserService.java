package com.psr.seatservice.service.user;

import com.psr.seatservice.domian.user.User;
import com.psr.seatservice.domian.user.UserRepository;
import com.psr.seatservice.dto.user.request.AddUserRequest;
import com.psr.seatservice.dto.user.request.UserLoginRequest;
import com.psr.seatservice.dto.user.response.BookingDetailResponse;
import com.psr.seatservice.dto.user.response.BookingListResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(UserLoginRequest request) {
        User user = userRepository.findByUserIdAndPassword(request.getUserId(), request.getPassword());
        return user;
    }

    public void join(AddUserRequest request) {
        userRepository.save(new User(request.getUserId(), request.getPassword(), request.getName(),
                request.getAddress(), request.getPhone(), request.getEmail(), request.getRole()));
    }
    public List<BookingListResponse> getBookingByUserId(String userId){
        return userRepository.findProgramBookingInfoByUserId(userId);
    }
    public BookingDetailResponse getBookingDetailByUserId(String userId, Long bookingNum){
        return userRepository.findProgramBookingDetailByUserId(userId, bookingNum);
    }

}
