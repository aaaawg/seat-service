package com.psr.seatservice.service.program;

import com.psr.seatservice.domian.program.*;
import com.psr.seatservice.domian.user.User;
import com.psr.seatservice.dto.program.request.AddAnswerRequest;
import com.psr.seatservice.dto.program.request.AddQnaRequest;
import com.psr.seatservice.dto.program.response.QnaListResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QnaService {
    private final QnaRepository qnaRepository;
    private final ProgramRepository programRepository;
    private final ProgramBookingRepository programBookingRepository;

    public QnaService(QnaRepository qnaRepository, ProgramRepository programRepository, ProgramBookingRepository programBookingRepository) {
        this.qnaRepository = qnaRepository;
        this.programRepository = programRepository;
        this.programBookingRepository = programBookingRepository;
    }

    @Transactional
    public Qna addQna(Long programNum, AddQnaRequest request, User user) {
        Program program = programRepository.findById(programNum).orElseThrow();
        Qna qna = new Qna(request.getContent(), request.isSecret(), program, user);
        return qnaRepository.save(qna);
    }

    @Transactional
    public List<QnaListResponse> getQnaList(Long programNum) {
        Program program = programRepository.findById(programNum).orElseThrow();
        List<Qna> qnaList = qnaRepository.findByProgram_ProgramNumOrderByCreateDateDesc(program.getProgramNum());
        List<QnaListResponse> qnaListResponses = qnaList.stream().map(QnaListResponse::new).collect(Collectors.toList());

        for (QnaListResponse q : qnaListResponses) {
            boolean applicant = programBookingRepository.existsByProgramNumAndUser_userId(program.getProgramNum(), q.getWriter());
            q.setApplicant(applicant);
        }

        return qnaListResponses;
    }

    @Transactional
    public void addAnswer(Long qnaNum, AddAnswerRequest request) {
        Qna qna = qnaRepository.findById(qnaNum).orElseThrow();
        qna.addAnswer(request.getAnswer());
    }

    @Transactional
    public boolean deleteQna(Long qnaNum, User user) {
        Qna qna = qnaRepository.findById(qnaNum).orElseThrow();
        if(qna.getUser().getUserId().equals(user.getUsername())) {
            qnaRepository.deleteById(qnaNum);
            return true;
        }
        return false;
    }
}
