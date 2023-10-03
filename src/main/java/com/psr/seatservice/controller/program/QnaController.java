package com.psr.seatservice.controller.program;

import com.psr.seatservice.domian.user.User;
import com.psr.seatservice.dto.program.request.AddAnswerRequest;
import com.psr.seatservice.dto.program.request.AddQnaRequest;
import com.psr.seatservice.service.program.QnaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class QnaController {

    private final QnaService qnaService;

    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @PostMapping("/qna/{programNum}")
    public ResponseEntity<Object> addQna(@PathVariable Long programNum, @RequestBody AddQnaRequest request, @AuthenticationPrincipal User user) {
        if(user.getUsername() != null || request.getContent() != null) {
            qnaService.addQna(programNum, request, user);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/answer/{qnaNum}")
    public ResponseEntity<Object> addAnswer(@PathVariable Long qnaNum, @RequestBody AddAnswerRequest request, @AuthenticationPrincipal User user) {
        if(user.getUsername() != null || request.getAnswer() != null) {
            qnaService.addAnswer(qnaNum, request);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/qna/{qnaNum}")
    public ResponseEntity<Object> deleteQna(@PathVariable Long qnaNum, @AuthenticationPrincipal User user) {
        if(user.getUsername() != null) {
            if(qnaService.deleteQna(qnaNum, user))
                return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
