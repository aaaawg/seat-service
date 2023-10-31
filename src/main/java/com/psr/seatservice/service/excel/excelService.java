package com.psr.seatservice.service.excel;

import com.psr.seatservice.domian.program.ProgramBookingRepository;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class excelService {
    private final ProgramBookingRepository programBookingRepository;

    public excelService(ProgramBookingRepository programBookingRepository) {
        this.programBookingRepository = programBookingRepository;
    }

    private void createExcelDownload(Long programNum){

        try{
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("신청서 결과");

            //숫자 포맷은 아래 numberCellStyle을 적용시킬 것이다다
            CellStyle numberCellStyle = workbook.createCellStyle();
            numberCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

            //파일명
            final String fileName = "신청서 결과";

            //헤더
            final String[] header = {"이름"};


            Row row = sheet.createRow(0);
            for (int i = 0; i < header.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(header[i]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
