package com.psr.seatservice.service.user;

import com.psr.seatservice.domian.user.User;
import com.psr.seatservice.domian.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String userId) {
        //userId를 사용하여 사용자 정보를 가져옴
        User user = userRepository.findByUserId(userId);
        if(user == null)
            throw new UsernameNotFoundException(userId);
        return user;
    }
}
