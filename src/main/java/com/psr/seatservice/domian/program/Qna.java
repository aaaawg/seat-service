package com.psr.seatservice.domian.program;

import com.psr.seatservice.domian.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaNum;
    private String question;
    private String answer;
    private boolean secret;
    @CreatedDate
    private Date createDate;
    @LastModifiedDate
    private Date answerDate;
    @ManyToOne
    @JoinColumn(name = "program_num")
    private Program program;
    @ManyToOne
    @JoinColumn(name = "writer")
    private User user;

    public Qna(String question, boolean secret, Program program, User user) {
        this.question = question;
        this.secret = secret;
        this.program = program;
        this.user = user;
    }

    public void addAnswer(String answer) {
        this.answer = answer;
    }
}
