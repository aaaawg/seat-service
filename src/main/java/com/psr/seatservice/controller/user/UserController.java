package com.psr.seatservice.controller.user;

import com.psr.seatservice.domian.user.User;
import com.psr.seatservice.dto.files.FileDto;
import com.psr.seatservice.dto.user.request.AddUserRequest;
import com.psr.seatservice.dto.user.request.IdCheckRequest;
import com.psr.seatservice.dto.user.response.BookingDetailResponse;
import com.psr.seatservice.dto.user.response.BookingListResponse;
import com.psr.seatservice.service.files.FilesService;
import com.psr.seatservice.service.program.ProgramService;
import com.psr.seatservice.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final ProgramService programService;
    private final FilesService filesService;

    public UserController(UserService userService, ProgramService programService, FilesService filesService) {
        this.userService = userService;
        this.programService = programService;
        this.filesService = filesService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) Boolean error, Model model) {
        if(error != null && error) {
            model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
        }
        return "user/login";
    }

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("user", new AddUserRequest());
        return "user/addUser";
    }

    @PostMapping("/join")
    public String userJoin(@Valid @ModelAttribute("user") AddUserRequest request, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("userRole", request.getRole());
            return "user/addUser";
        }
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
        FileDto file = filesService.getFile(detailResponse.getProgramNum());

        if(file != null)
            detailResponse.setFilename(file.getFilename());
        model.addAttribute("detail", detailResponse);
        return "user/bookingDetail";
    }
    @DeleteMapping("/myPage/{bookingNum}")
    public @ResponseBody ResponseEntity<Void> userBookingDelete(@PathVariable Long bookingNum){
        programService.bookingDelete(bookingNum);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/join/id")
    public @ResponseBody ResponseEntity<Void> checkId(@RequestBody IdCheckRequest request) {
        //아이디 중복 검사
        if(userService.checkUserId(request.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); //409 이미 존재하는 아이디
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
