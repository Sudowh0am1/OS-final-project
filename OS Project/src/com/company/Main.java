package com.company;

import java.io.*;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        ArrayList<Process> data;
        //generate process
        RequestGenerator generator = new RequestGenerator();
        generator.generateProcess();
        data = generator.getP();
//       data = setReadyqueue(generator.getP());
        readingExcel();
        System.out.println("time unit : "+ Algorithm.timeUnit);

        do {
            int choose;
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n ----------------------------------------------------------------------------------------------------------");
            System.out.println("choose the scheduling methode:\n" + "1. FIFO\n" + "2. Preemptive SJF\n" + "3. Non-Preemptive SJF\n"
                    + "4. RR (with specified time quantum)\n" + "5. Priority with preemption\n" + "6. Non-Preemptive priority\n" +
                    "7. Exit");

            choose = scanner.nextInt();
            switch (choose) {
                case 1:
                    Algorithm.fifo_scheduling(data);
                    break;
                case 2:
                    Algorithm.preemptive_sjf(data);
                    break;
                case 3:
                    Algorithm.nonPreemptive_sjf(data);
                    break;
                case 4:
                    Algorithm.roundRobin(data);
                    break;
                case 5:
                    Algorithm.preemptive_priority(data);
                    break;
                case 6:
                    Algorithm.nonPreemptive_priority(data);
                    break;
                case 7:
                    System.exit(0);
            }
        }while(true);
    }



    // reads the entire Excel file
    public static ArrayList<Process> readingExcel() {
        ArrayList<Process> processes = new ArrayList<>();

        try {
            //obtaining bytes from the file
            FileInputStream fileInputStream = new FileInputStream(".\\Process.xlsx");
            //creating Workbook instance that refers to .xlsx file
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            //creating a Sheet object to retrieve object
            Sheet sheet = workbook.getSheetAt(0);
            int rowNumber = 0;
            //iterating over Excel file
            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                int round = 0;

                if (rowNumber > 0) {
                    Process process = new Process(0);

                    // process id
                    while (cellIterator.hasNext() && round == 0) {
                        Cell cell = cellIterator.next();
                        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                            process.ProcessID = (int) cell.getNumericCellValue();
                            //  System.out.println("process id : "+ process.getProcessID());
                            round++;
                        }
                    }
                    // arrival time
                    while (cellIterator.hasNext() && round == 1) {
                        Cell cell = cellIterator.next();
                        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                            process.ArrivalTime = (int) cell.getNumericCellValue();
                            // System.out.println("process arrival time : "+ process.getArrivalTime());
                            round++;
                        }
                    }
                    // priority
                    while (cellIterator.hasNext() && round == 2) {
                        Cell cell = cellIterator.next();
                        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                            process.Priority = (int) cell.getNumericCellValue();
                            // System.out.println("process priority : "+ process.getPriority());
                            round++;
                        }
                    }
                    // cpu time portion
                    while (cellIterator.hasNext() && round == 3) {
                        Cell cell = cellIterator.next();
                        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                            process.BurstTime = (int) cell.getNumericCellValue();
                            // System.out.println("process cpu time portion : "+ process.getTimeRequiredOfCPU());
                            round++;
                        }
                    }
                    processes.add(process);
                }
                rowNumber++;
            }
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processes;
    }

    //method defined for reading a cell and get the value
    public double ReadCellData(int vRow, int vColumn) {
        double value;
        Workbook wbook = null;
        try {
            //reading data from a file in the form of bytes
            FileInputStream fis = new FileInputStream(".\\Process.xlsx");
            //creates an XSSFWorkbook object by buffering the whole stream into the memory
            wbook = new XSSFWorkbook(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        //getting the XSSFSheet object at given index
        Sheet sheet = wbook.getSheetAt(0);
        //returns the logical row
        Row row = sheet.getRow(vRow);
        //getting the cell representing the given column
        Cell cell = row.getCell(vColumn);
        value = cell.getNumericCellValue();
        return value;
    }

    public static ArrayList<Process> setReadyqueue(ArrayList<Process> processes) {
        ArrayList<Process> readyProcesses = new ArrayList<>();
        Queue<Process> ready  = new PriorityQueue<> (100);

        for (int i=0; i<100; i++){
            ready.add(processes.get(i));
        }
        readyProcesses.add(ready.remove());

        for (int j=100; ready.size()!=0; j++){
            ready.add(processes.get(j));
            readyProcesses.add(ready.remove());
        }
        return readyProcesses;
    }

}


