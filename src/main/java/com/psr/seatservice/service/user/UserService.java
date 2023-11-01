package com.psr.seatservice.service.user;

import com.psr.seatservice.domian.user.Role;
import com.psr.seatservice.domian.user.User;
import com.psr.seatservice.domian.user.UserRepository;
import com.psr.seatservice.dto.user.request.AddUserRequest;
import com.psr.seatservice.dto.user.response.BookingDetailResponse;
import com.psr.seatservice.dto.user.response.BookingListResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void join(AddUserRequest request) {
        String password = bCryptPasswordEncoder.encode(request.getPassword());
        Role role;

        if(request.getRole().equals("user"))
            role = Role.USER;
        else
            role = Role.BIZ;

        if(request.getBirth() != null)
            userRepository.save(new User(request.getUserId(), password, request.getName(), request.getAddress(), request.getPhone(), request.getEmail(), role, request.getBirth()));
        else
            userRepository.save(new User(request.getUserId(), password, request.getName(), request.getAddress(), request.getPhone(), request.getEmail(), role));
    }

    public List<BookingListResponse> getBookingByUserId(User user){
        return userRepository.findProgramBookingInfoByUserId(user.getId());
    }
    public BookingDetailResponse getBookingDetailByUserId(User user, Long bookingNum){
        BookingDetailResponse b = userRepository.findProgramBookingDetailByUserId(user.getId(), bookingNum);

        //신청 마감일 하루 전날 구하기
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");

        Calendar cal = Calendar.getInstance();
        cal.setTime(b.getEndDate());
        cal.add(Calendar.DATE, -1);

        b.setYDate(format.format(cal.getTime()));

        return b;
    }

    public boolean checkUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }
}
