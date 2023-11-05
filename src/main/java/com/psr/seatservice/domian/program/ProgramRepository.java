package com.psr.seatservice.domian.program;

import com.psr.seatservice.dto.program.response.ProgramListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.place, p.type, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.type = ?1 and p.endDate >= current_date order by p.startDate")
    List<ProgramListResponse> findAllByType(String t);

    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.place, p.type, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.type = ?1 and p.target = ?2 and p.endDate >= current_date order by p.startDate")
    List<ProgramListResponse> findAllByTarget(String t, String target);

    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.place, p.type, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.endDate >= current_date order by p.startDate")
    List<ProgramListResponse> findAllProgramAndImg();

    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.place, p.type, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.target = ?1 and p.endDate >= current_date order by p.startDate")
    List<ProgramListResponse> findAllProgramAndImgByTarget(String target);

    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.place, p.type, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.title like ?1 order by p.startDate")
    List<ProgramListResponse> findAllByTitleLike(String str); //프로그램 검색

    List<Program> findAllByUserId(Long user);

    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.place, p.type, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.target = ?1 and p.place like ?2 and p.type = ?3 and p.endDate >= current_date order by p.startDate")
    List<ProgramListResponse> findAllByTargetAndTypeAndPlaceStartsWith(String target, String area, String type);

    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.place, p.type, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.target = ?1 and p.targetDetail = ?2 and p.endDate >= current_date order by p.startDate")
    List<ProgramListResponse> findAllByTargetAndTargetDetail(String target, String area);

    @Query("select title from Program where programNum=?1")
    String findTitleByProgramNum(Long programNum);

    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.place, p.type, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.type = ?1 and p.target = ?2 and p.targetDetail like ?3 and p.endDate >= current_date order by p.startDate")
    List<ProgramListResponse> findAllByAreaAndAreaDetail(String type, String area, String str); //각 지역별 목록

    @Query("select new com.psr.seatservice.dto.program.response.ProgramListResponse(p.programNum, p.title, p.startDate, p.endDate, p.place, p.type, f.filename, p.target, p.targetDetail)" +
            "from Program p left outer join Files f on p.programNum = f.postId where p.target = ?1 and p.targetDetail like ?2 and p.endDate >= current_date order by p.startDate")
    List<ProgramListResponse> findAllByAreaAndAreaDetail(String area, String str);    //각 지역별 목록
}
