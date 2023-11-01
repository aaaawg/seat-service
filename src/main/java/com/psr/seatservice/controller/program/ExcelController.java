package com.psr.seatservice.controller.program;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.psr.seatservice.service.excel.excelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ExcelController {
    private final excelService excelService;

    public ExcelController(com.psr.seatservice.service.excel.excelService excelService) {
        this.excelService = excelService;
    }

    @GetMapping("/{programNum}/userResponse/download")
    public ResponseEntity getUsersResponse(HttpServletResponse response, @RequestParam String date, @RequestParam String time, boolean excelDownload, @PathVariable Long programNum){
        return ResponseEntity.ok(excelService.getUsersResponse(response, excelDownload, programNum, date, time));
    }
}
