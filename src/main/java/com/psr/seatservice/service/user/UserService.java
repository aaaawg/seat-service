package com.psr.seatservice.service.user;

import com.psr.seatservice.domian.user.User;
import com.psr.seatservice.domian.user.UserRepository;
import com.psr.seatservice.dto.user.request.AddBizUserRequest;
import com.psr.seatservice.dto.user.request.UserLoginRequest;
import org.springframework.stereotype.Service;

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

    public void join(AddBizUserRequest request) {
        userRepository.save(new User(request.getUserId(), request.getPassword(), request.getName(),
                request.getAddress(), request.getPhone(), request.getEmail(), request.getRole()));
    }
}
