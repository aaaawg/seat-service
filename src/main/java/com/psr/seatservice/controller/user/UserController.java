package com.psr.seatservice.controller.user;

import com.psr.seatservice.SessionConst;
import com.psr.seatservice.domian.user.User;
import com.psr.seatservice.dto.user.request.AddBizUserRequest;
import com.psr.seatservice.dto.user.request.UserLoginRequest;
import com.psr.seatservice.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    //로그인
    @PostMapping("/login")
    public String login(UserLoginRequest request, HttpServletRequest httpServletRequest) {
        User loginUser = userService.login(request);
        if(loginUser != null && loginUser.getRole().equals("biz")) {
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);
            System.out.println("idid: " + SessionConst.LOGIN_MEMBER);

            session.getAttributeNames().asIterator()
                    .forEachRemaining(name -> System.out.println("session name=" + name + "value=" + session.getAttribute(name)));
            return "program/bizProgramList";
        }
        else {
            System.out.println("noidid: " + SessionConst.LOGIN_MEMBER);
            return "program/programList";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "program/programList";
    }

    @GetMapping("/join")
    public String join() {
        return "user/join";
    }

    @GetMapping("/join/business")
    public String bizUserJoinForm() {
        return "user/addBizUser";
    }

    //기업 사용자 회원가입 처리
    @PostMapping("/join/business")
    public String bizUserJoin(AddBizUserRequest request) {
        userService.join(request);
        return "program/programList";
    }

    @GetMapping("/join/user")
    public String userJoinForm() {
        return "user/addUser";
    }


}
