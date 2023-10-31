package com.psr.seatservice.domian.program;

import com.psr.seatservice.dto.program.response.ProgramListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.place, p.type, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.type = ?1 order by p.startDate")
    List<ProgramListResponse> findAllByType(String t);

    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.place, p.type, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.type = ?1 and p.target = ?2 order by p.startDate")
    List<ProgramListResponse> findAllByTarget(String t, String target);

    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.place, p.type, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId order by p.startDate")
    List<ProgramListResponse> findAllProgramAndImg();

    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.place, p.type, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.target = ?1 order by p.startDate")
    List<ProgramListResponse> findAllProgramAndImgByTarget(String target);

    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.place, p.type, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.title like ?1 order by p.startDate")
    List<ProgramListResponse> findAllByTitleLike(String str); //프로그램 검색

    List<Program> findAllByUserId(Long user);

    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.place, p.type, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.target = ?1 and p.place like ?2 and p.type = ?3 order by p.startDate")
    List<ProgramListResponse> findAllByTargetAndTypeAndPlaceStartsWith(String target, String area, String type);

    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.place, p.type, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.target = ?1 and p.targetDetail = ?2 order by p.startDate")
    List<ProgramListResponse> findAllByTargetAndTargetDetail(String target, String area);

    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.place, p.type, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.target = ?1 and p.targetDetail = ?2 or p.targetDetail = ?3 order by p.startDate")
    List<ProgramListResponse> findAllByTargetAndTargetDetailAll(String target, String area1, String area2);

    @Query("select title from Program where programNum=?1")
    String findTitleByProgramNum(Long programNum);
}
