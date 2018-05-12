package com.thakur.excelcreator;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {

    private static String[] columns = {"Sr Number", "Contract Number", "Hello Value", "Email Id"}; //Header Values

    public static void main(String[] args)
    {
	    	try{
	        // Create a Workbook
	        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
	
	        /* CreationHelper helps us create instances for various things like DataFormat, 
	           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
	        CreationHelper createHelper = workbook.getCreationHelper();
	
	        // Create a Sheet
	        Sheet sheet = workbook.createSheet("Contract Load Testing");
	
	        // Create a Font for styling header cells
	        Font headerFont = workbook.createFont();
	        headerFont.setBold(true);
	        headerFont.setFontHeightInPoints((short) 12);
	        headerFont.setColor(IndexedColors.RED.getIndex()); //Set Red Color
	
	        // Create a CellStyle with the font
	        CellStyle headerCellStyle = workbook.createCellStyle();
	        headerCellStyle.setFont(headerFont);
	
	        // Create a Row
	        Row headerRow = sheet.createRow(0);
	
	        // Creating cells
	        for(int i = 0; i < columns.length; i++) {
	            Cell cell = headerRow.createCell(i);
	            cell.setCellValue(columns[i]);
	            cell.setCellStyle(headerCellStyle);
	        }
	
	        // Create Cell Style for formatting Date
	        CellStyle dateCellStyle = workbook.createCellStyle();
	        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
	
	        // Create Other rows and cells with employees data
	        int rowNum = 1;
	        int Count;
	        
	        //Set values to cells
	        for(Count=0;Count<=100;Count++) 
	        {
	        	
	        	Row row = sheet.createRow(rowNum++); // Move to next row
	        	row.createCell(0).setCellValue("SrNo "+Count);
	        	row.createCell(1).setCellValue("ContractNameNo "+Count);
	        	row.createCell(2).setCellValue("Hello Hello");
	        	row.createCell(3).setCellValue("abc@gmail.com");
	        	
	        }
	
			// Resize all columns to fit the content size
	        for(int i = 0; i < columns.length; i++) {
	            sheet.autoSizeColumn(i);
	        }
	
	        // Write the output to a file
	        FileOutputStream fileOut = new FileOutputStream("Contract-load-test-sample.xlsx");
	        workbook.write(fileOut);
	        fileOut.close();
	
	        // Closing the workbook
	        workbook.close();
	    }
    	catch(IOException e)
        {
        	System.out.println(e.getMessage());
        }
    }
    
}