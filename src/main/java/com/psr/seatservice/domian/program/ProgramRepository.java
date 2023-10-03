package com.psr.seatservice.domian.program;

import com.psr.seatservice.dto.program.response.ProgramListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.type, p.place, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.type = ?1 order by p.startDate")
    List<ProgramListResponse> findAllByType(String t);

    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.type, p.place, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId order by p.startDate")
    List<ProgramListResponse> findAllProgramAndImg();

    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.type, p.place, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.title like ?1 order by p.startDate")
    List<ProgramListResponse> findAllByTitleLike(String str);

    List<Program> findAllByUserId(Long user);
}
