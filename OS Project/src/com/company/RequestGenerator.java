package com.company;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class RequestGenerator {
    private ArrayList<Process> p = new ArrayList<>();
    private ArrayList<Integer> rndnum = new ArrayList<>();

    public RequestGenerator(){}

    public void generateProcess() throws IOException {
        int processCount = 100;
        for (int i=1; i<=processCount; ++i){
            rndnum.add(i);
        }
        Collections.shuffle(rndnum);
        for (int i=0; i<processCount; ++i){
            p.add(new Process(rndnum.get(i)));
        }
        writeExcel(getP());
    }

    public void writeExcel(ArrayList<Process> processArrayList) throws IOException {
        // Blank workbook
        Workbook workbook = new XSSFWorkbook();
        // Create a blank sheet
        Sheet sheet = workbook.createSheet();
        createHeaderRow(sheet);

        int rowCount = 1;

        for (Process aProcess : processArrayList) {
            Row row = sheet.createRow(rowCount);
            writeProcess(aProcess, row);
            ++rowCount;
        }

        try (FileOutputStream outputStream = new FileOutputStream(".\\Process.xlsx")) {
            workbook.write(outputStream);
        }
    }

    private void writeProcess(Process aProcess, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(aProcess.getProcessID());

        cell = row.createCell(1);
        cell.setCellValue(aProcess.getArrivalTime());

        cell = row.createCell(2);
        cell.setCellValue(aProcess.getPriority());

        cell = row.createCell(3);
        cell.setCellValue(aProcess.getBurstTime());
    }

    private void createHeaderRow(Sheet sheet) {

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


    public ArrayList<Process> getP() {
        return p;
    }

    public void setP(ArrayList<Process> p) {
        this.p = p;
    }

}
