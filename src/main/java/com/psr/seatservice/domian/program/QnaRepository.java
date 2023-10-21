package com.psr.seatservice.domian.program;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {

    List<Qna> findByProgram_ProgramNumOrderByCreateDateDesc(Long programNum);
}
