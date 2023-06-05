package com.psr.seatservice.domian.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserIdAndPassword(String userId, String password);
}
