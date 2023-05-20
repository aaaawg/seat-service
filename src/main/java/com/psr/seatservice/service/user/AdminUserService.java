package com.psr.seatservice.service.user;

import com.psr.seatservice.domian.user.AdminUser;
import com.psr.seatservice.domian.user.AdminUserRepository;
import com.psr.seatservice.dto.user.request.AddAdminUserRequest;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService {
    private AdminUserRepository adminUserRepository;

    public AdminUserService(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    public void join(AddAdminUserRequest request) {
        adminUserRepository.save(new AdminUser(request.getUserId(), request.getPassword(), request.getName(), request.getAddress(), request.getPhone()));
    }
}
