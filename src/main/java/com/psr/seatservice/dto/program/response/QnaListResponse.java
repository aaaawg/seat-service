package com.psr.seatservice.dto.program.response;

import com.psr.seatservice.domian.program.Qna;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@AllArgsConstructor
@Setter
public class QnaListResponse {
    private Long qnaNum;
    private String content;
    private boolean secret;
    private String writer;
    private Date createDate;
    private Date answerDate;
    private String answer;
    private boolean applicant;

    public QnaListResponse(Qna qna) {
        this.qnaNum = qna.getQnaNum();
        this.content = qna.getContent();
        this.secret = qna.isSecret();
        this.writer = qna.getUser().getUserId();
        this.createDate = qna.getCreateDate();
        this.answerDate = qna.getAnswerDate();
        this.answer = qna.getAnswer();
    }
}
