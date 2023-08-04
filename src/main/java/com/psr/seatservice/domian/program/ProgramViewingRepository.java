package com.psr.seatservice.domian.program;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramViewingRepository extends JpaRepository<ProgramViewing, ProgramViewingPK> {
    List<ProgramViewing> findByProgramNo(Long num);

    ProgramViewing findByProgramNoAndViewingDateAndViewingTime(Long programNum, String viewingDate, String viewingTime);
}
