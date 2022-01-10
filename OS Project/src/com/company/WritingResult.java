package com.company;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public abstract class WritingResult {

    public static void writeExcel(ArrayList<Process> processArrayList) throws IOException {
        // Blank workbook
        Workbook workbook = new XSSFWorkbook();
        // Create a blank sheet
        Sheet sheet = workbook.createSheet();
        createHeaderRow(sheet);

        int rowCount = 1;
        for (Process process : processArrayList) {
            Row row = sheet.createRow(rowCount);
            writeProcess(process, row);
            ++rowCount;
        }
        try (FileOutputStream outputStream = new FileOutputStream(".\\result.xlsx")) {
            workbook.write(outputStream);
        }
    }

    private static void writeProcess(Process process, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(process.getProcessID());

        cell = row.createCell(1);
        cell.setCellValue(process.getArrivalTime());

        cell = row.createCell(2);
        cell.setCellValue(process.getPriority());

        cell = row.createCell(3);
        cell.setCellValue(process.getBurstTime());
    }

    private static void createHeaderRow(Sheet sheet) {

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        cellStyle.setFont(font);

        Row row = sheet.createRow(0);
        Cell cellProcessID = row.createCell(0);
        cellProcessID.setCellStyle(cellStyle);
        cellProcessID.setCellValue("ProcessID");

        Cell cellArrivalTime = row.createCell(1);
        cellArrivalTime.setCellStyle(cellStyle);
        cellArrivalTime.setCellValue("ArrivalTime");

        Cell cellPriority = row.createCell(2);
        cellPriority.setCellStyle(cellStyle);
        cellPriority.setCellValue("Priority");

        Cell cellBurstTime = row.createCell(3);
        cellBurstTime.setCellStyle(cellStyle);
        cellBurstTime.setCellValue("BurstTime");
    }

}
