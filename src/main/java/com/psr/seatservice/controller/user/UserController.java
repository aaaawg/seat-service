package com.psr.seatservice.controller.user;

import com.psr.seatservice.SessionConst;
import com.psr.seatservice.domian.user.User;
import com.psr.seatservice.dto.user.request.AddUserRequest;
import com.psr.seatservice.dto.user.request.IdCheckRequest;
import com.psr.seatservice.dto.user.response.BookingDetailResponse;
import com.psr.seatservice.dto.user.response.BookingListResponse;
import com.psr.seatservice.service.program.ProgramService;
import com.psr.seatservice.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final ProgramService programService;

    public UserController(UserService userService, ProgramService programService) {
        this.userService = userService;
        this.programService = programService;
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/join")
    public String join() {
        return "user/join";
    }

    //사용자 회원가입 처리
    @PostMapping("/join")
    public String userJoin(AddUserRequest request) {
        userService.join(request);
        return "redirect:/login";
    }

    //에약리스트 확인 중
    @GetMapping("/myPage")
    public String userBookingList(@AuthenticationPrincipal User user, Model model){
        List<BookingListResponse> bookingProgram = userService.getBookingByUserId(user);
        model.addAttribute("lists", bookingProgram);
        return "user/bookingList";
    }
    @GetMapping("/myPage/{bookingNum}")
    public String userBookingDetail(@PathVariable Long bookingNum, @AuthenticationPrincipal User user, Model model){
        BookingDetailResponse detailResponse = userService.getBookingDetailByUserId(user, bookingNum);
        model.addAttribute("detail", detailResponse);
        System.out.println("test: "+detailResponse.getProgramName()+", "+ detailResponse.getBookingNum());
        return "user/bookingDetail";
    }
    @PostMapping("/myPage/{bookingNum}")
    public String userBookingDelete(@PathVariable Long bookingNum){
        System.out.println("test num: "+bookingNum);
        programService.BookingDelete(bookingNum);
        return "user/bookingList";
    }

    @PostMapping("/join/id")
    public @ResponseBody ResponseEntity<Object> checkId(@RequestBody IdCheckRequest request) {
        //아이디 중복 검사
        if(userService.checkUserId(request.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); //409 이미 존재하는 아이디
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
