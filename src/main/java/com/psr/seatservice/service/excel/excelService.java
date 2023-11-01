package com.psr.seatservice.service.excel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psr.seatservice.domian.program.ProgramBookingRepository;
import com.psr.seatservice.domian.program.ProgramRepository;
import com.psr.seatservice.dto.excel.ExcelDTO;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Service
public class excelService {
    private final ProgramBookingRepository programBookingRepository;
    private final ProgramRepository programRepository;

    public excelService(ProgramBookingRepository programBookingRepository, ObjectMapper objectMapper,ProgramRepository programRepository) {
        this.programBookingRepository = programBookingRepository;
        this.objectMapper = objectMapper;
        this.programRepository = programRepository;
    }

    private final ObjectMapper objectMapper;

    public Object getUsersResponse(HttpServletResponse response, boolean excelDownload, Long programNum, String date, String time) {
            createExcelDownload(response, programNum, date, time);
            return null; //없으면 에러!
    }

    private void createExcelDownload(HttpServletResponse response, Long programNum, String date, String time){
        String title = programRepository.findTitleByProgramNum(programNum);

        List<ExcelDTO> list = programBookingRepository.findExcelDTOByProgramNum(programNum,date,time);

        try{
            Workbook workbook = new XSSFWorkbook();
            String sanitizedTime = time.replaceAll("[\\s:]", "_");
            Sheet sheet = workbook.createSheet(title+"_"+date+"_"+sanitizedTime);

            //숫자 포맷은 아래 numberCellStyle을 적용시킬 것이다다
            CellStyle numberCellStyle = workbook.createCellStyle();
            numberCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

            //파일명
            final String fileName = title+"_"+date+time+"결과";

            //헤더
            String[] header = {"이름","생년월일","전화번호","이메일","신청일"};
            int j=5;
            //QUESTION JSON
            ObjectMapper objectMapperQ = new ObjectMapper();

            if (list.get(0).getProgramQuestion()!=null) {
                Map<String, String> jsonMap = objectMapperQ.readValue(list.get(0).getProgramQuestion(), Map.class);

                String[] newArr = new String[jsonMap.size()+5];
                for(int i=0;i< header.length;i++){
                    newArr[i] = header[i];
                }
                for(int i=0;i< jsonMap.size();i++){
                    newArr[j++] = jsonMap.get(""+i);
                }
                header = newArr;
            }

            Row row = sheet.createRow(0);
            for (int i = 0; i < header.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(header[i]);
            }

            //바디
            for (int i = 0; i < list.size(); i++) {
                row = sheet.createRow(i + 1);  //헤더 이후로 데이터가 출력되어야하니 +1

                ExcelDTO excelDTO = list.get(i);

                Cell cell = null;
                cell = row.createCell(0);
                cell.setCellValue(excelDTO.getName());

                cell = row.createCell(1);
                cell.setCellValue(excelDTO.getBirth());

                cell = row.createCell(2);
                cell.setCellValue(excelDTO.getPhone());

                cell = row.createCell(3);
                cell.setCellValue(excelDTO.getEmail());

                cell = row.createCell(4);
                cell.setCellValue(excelDTO.getBookingDate().toString());

                ObjectMapper objectMapperR = new ObjectMapper();
                Map<String, String> jsonMap = objectMapperR.readValue(list.get(i).getProgramResponse(), Map.class);
                int p = 5;
                for(int k=0; k<jsonMap.size(); k++){
                    cell = row.createCell(p++);
                    cell.setCellValue(jsonMap.get(""+k));
                }
            }

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8")+".xlsx");

            workbook.write(response.getOutputStream());
            workbook.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
