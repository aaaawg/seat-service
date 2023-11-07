package com.psr.seatservice.domian.area;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Integer> {
    List<Area> findAllByArea1(String area);
}
