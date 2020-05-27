package com.neu.bloodbank.excelview;

import java.util.List;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.neu.bloodbank.pojo.RequestBlood;

public class ExcelBuilder extends AbstractExcelView {
	 
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
            HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        List<RequestBlood> requestList = (List<RequestBlood>) model.get("bloodRequests");
         
        // create a new Excel sheet
        HSSFSheet sheet = workbook.createSheet("BloodRequestList");
        sheet.setDefaultColumnWidth(30);
        System.out.println("BR-------x-x--xS  1");
        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
        
        
        CellStyle styleDate = workbook.createCellStyle();
        Font fontDate = workbook.createFont();
        fontDate.setFontName("Arial");
        styleDate.setFillForegroundColor(HSSFColor.BLUE.index);
        styleDate.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        fontDate.setBold(true);
        fontDate.setColor(HSSFColor.WHITE.index);
        styleDate.setFont(fontDate);
        styleDate.setDataFormat(
        	    workbook.getCreationHelper().createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));
        
       
         
        HSSFRow header1 = sheet.createRow(0);
         
        header1.createCell(0).setCellValue("Request Date");
        header1.getCell(0).setCellStyle(styleDate);
         
        header1.createCell(1).setCellValue("BloodType");
        header1.getCell(1).setCellStyle(style);
         
        header1.createCell(2).setCellValue("BloodAmount");
        header1.getCell(2).setCellStyle(style);
         
        header1.createCell(3).setCellValue("Hospital Name");
        header1.getCell(3).setCellStyle(style);
         
        header1.createCell(4).setCellValue("Status");
        header1.getCell(4).setCellStyle(style);
         
        
        int rc = 1;
       
        for (RequestBlood requests : requestList) {
        	
//        	System.out.println("vbnm  "+requests.getRequestDate().getDate()+ "    "+ requests.getRequestDate().toString());
            HSSFRow newRow = sheet.createRow(rc++);
            newRow.createCell(0).setCellValue(requests.getRequestDate().toString());
            newRow.createCell(1).setCellValue(requests.getBloodType());
            newRow.createCell(2).setCellValue(requests.getBloodAmount());
            newRow.createCell(3).setCellValue(requests.getHospital().getName());
            newRow.createCell(4).setCellValue(requests.getConfirmation());
        }
        System.out.println("EXCEL SHEET DOWNLOADED");
    }
    
    
 
}
