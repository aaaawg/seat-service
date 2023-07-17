package com.psr.seatservice.domian.files;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilesRepository extends JpaRepository<Files, Long>{
    List<Files> findBypostId(Long num);
}
