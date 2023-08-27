package com.psr.seatservice.controller.user;

import com.psr.seatservice.SessionConst;
import com.psr.seatservice.domian.user.User;
import com.psr.seatservice.dto.user.request.AddUserRequest;
import com.psr.seatservice.dto.user.request.UserLoginRequest;
import com.psr.seatservice.dto.user.response.BookingDetailResponse;
import com.psr.seatservice.dto.user.response.BookingListResponse;
import com.psr.seatservice.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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
    public String login(UserLoginRequest request, HttpServletRequest httpServletRequest, Model model) {
        User loginUser = userService.login(request);
        if(loginUser != null && loginUser.getRole().equals("biz")) {
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);
            User logUser = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);

            model.addAttribute("name",logUser.getName());
             //return "program/bizProgramList";
            return "program/programList";
        }
        else {
            return "program/programList";
        }
    }

    @GetMapping("/logout")
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

    //사용자 회원가입 처리
    @PostMapping("/join")
    public String userJoin(AddUserRequest request) {
        userService.join(request);
        return "program/programList";
    }
    //에약리스트 확인 중
    @GetMapping("/myPage")
    public String userBookingList(HttpSession session, Model model){
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);
        List<BookingListResponse> bookingProgram = userService.getBookingByUserId(loginUser.getUserId());
        model.addAttribute("lists", bookingProgram);
        return "user/bookingList";
    }
    @GetMapping("/myPage/{bookingNum}")
    public String userBookingDetail(@PathVariable Long bookingNum,HttpSession session, Model model){
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);
        BookingDetailResponse detailResponse = userService.getBookingDetailByUserId(loginUser.getUserId(),bookingNum);
        model.addAttribute("detail", detailResponse);
        System.out.println("test: "+detailResponse.getProgramName()+", "+ detailResponse.getBookingNum());
        return "user/bookingDetail";
    }
}
