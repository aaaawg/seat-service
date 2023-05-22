package com.psr.seatservice.controller.user;

import com.psr.seatservice.dto.user.request.AddAdminUserRequest;
import com.psr.seatservice.service.user.AdminUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminUserController {
    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping("/join")
    public String join() {
        return "user/addAdminUser";
    }

    @PostMapping("/join")
    public String join(AddAdminUserRequest request) {
        adminUserService.join(request);
        return "redirect:/admin/join";
    }

    @GetMapping("/login")
    public String login() {
        return "program/adminPrograms";
    }
}
