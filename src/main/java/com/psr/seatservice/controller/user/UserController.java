package com.psr.seatservice.controller.user;

import com.psr.seatservice.domian.user.User;
import com.psr.seatservice.dto.user.request.AddAdminUserRequest;
import com.psr.seatservice.dto.user.request.UserLoginRequest;
import com.psr.seatservice.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(UserLoginRequest request) {
        User loginUser = userService.login(request);
        if(loginUser != null && loginUser.getRole().equals("admin"))
            return "program/adminPrograms";
        else
            return "program/programList";
    }

    @GetMapping("/admin/join")
    public String join() {
        return "user/addAdminUser";
    }

    @PostMapping("/admin/join")
    public String join(AddAdminUserRequest request) {
        userService.join(request);
        return "program/adminPrograms";
    }



}
