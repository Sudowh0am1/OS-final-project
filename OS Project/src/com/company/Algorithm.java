package com.company;

import java.io.IOException;
import java.util.*;

public class Algorithm {
// Throughput
// CPU utilization
// Average waiting time
// Average turnaround time
// Average response time

    static int timeUnit = time_measurement();

    // the function that measures time
    public static void given_function() {
        int temp = 0;
        for (int i = 0; i < 10000; i++) {
            if (i % 2 == 0)
                temp = i / 2;
            else
                temp = 2 * i;
        }
    }

    public static int time_measurement() {

        int startTime = (int) System.nanoTime();
        // System.out.println("start time : " + startTime);
        given_function();
        int endTime = (int) System.nanoTime();
        // System.out.println("end time : " + endTime);
        int duration = (endTime - startTime) / 10000;  // micro second
        return duration;
    }

    public static void fifo_scheduling(ArrayList<Process> arrayList) throws IOException {
        ArrayList<Process> list = new ArrayList<>(arrayList);
        ArrayList<Integer> arrivals = new ArrayList<>();
        ArrayList<Integer> waitingTime = new ArrayList<>();
        ArrayList<Integer> responseTime = new ArrayList<>();
        ArrayList<Integer> turnaroundTime = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        double avg_waitingTime;
        double avg_responseTime;
        double avg_turnaroundTime;
        int time = 0;

        ArrayList<Process> processOrder = new ArrayList<>();

        for (Process value : list) {
            map.put(value.getProcessID(), value.getArrivalTime());
        }

        List<Map.Entry<Integer, Integer>> theList = new ArrayList(map.entrySet());
        theList.sort(Map.Entry.comparingByValue());
        //theList.forEach(System.out::println);

        sortByArrivalTime(list);
        WritingResult.writeExcel(list);

//        System.out.println("the order of processes : ");
//        for (int j = 0; j < theList.size(); j++) {
//            System.out.print(theList.get(j).getKey() + " , ");
//        }
//        System.out.println();

        /// ---- finding the required time ---- ///
        for (int i = 0; i < list.size(); i++) {
            arrivals.add(list.get(i).getArrivalTime());
        }
        Collections.sort(arrivals);
        time = arrivals.get(0);

        int processNum;
        int waiting_time = 0;
        // System.out.println("initial time : " + time);

        for (int k = 0; k < theList.size(); k++) {
            processNum = theList.get(k).getKey();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getProcessID() == processNum) {
                    time += list.get(i).getBurstTime();
                    turnaroundTime.add(time - list.get(i).getArrivalTime());
                    if (processNum == theList.get(0).getKey()) {
                        waiting_time = 0;
                    } else {
                        waiting_time = time - list.get(i).getBurstTime() - list.get(i).getArrivalTime();
                        if (waiting_time < 0) {
                            waiting_time = 0;
                        }
                    }
                    waitingTime.add(waiting_time);
                    // System.out.println("waiting time for process " + processNum + " is : " + waiting_time);
                }
            }
        }
        int sum = 0;
        for (Integer integer : waitingTime) {
            sum += integer;
        }
        avg_waitingTime = (sum) / waitingTime.size();
        avg_responseTime = avg_waitingTime;

        sum = 0;
        for (int i = 0; i < turnaroundTime.size(); i++) {
            sum += turnaroundTime.get(i);
        }
        avg_turnaroundTime = (sum) / turnaroundTime.size();

        MeasurementSout(arrayList, avg_waitingTime, avg_responseTime, avg_turnaroundTime);
        WritingResult.writeExcel(arrayList);
    }

    // Method to find the waiting time for all processes in SJF
    static void findWaitingTime_SJF(ArrayList<Process> processArrayList) {
        int complete = 0, time = 0, minm = Integer.MAX_VALUE;
        int shortest = 0, finish_time;
        boolean check = false;

        // Process until all processes gets completed
        while (complete != processArrayList.size()) {

            // Find process with minimum
            // remaining time among the
            // processes that arrives till the
            // current time`
            for (int j = 0; j < processArrayList.size(); j++) {
                if ((processArrayList.get(j).getArrivalTime() <= time) &&
                        (processArrayList.get(j).getRemainingTime() < minm) && processArrayList.get(j).getRemainingTime() > 0) {
                    minm = processArrayList.get(j).getRemainingTime();
                    shortest = j;
                    check = true;
                }
            }

            if (check == false) {
                time++;
                continue;
            }

            // Reduce remaining time by one
            int R_time = processArrayList.get(shortest).getRemainingTime();
            R_time--;
            processArrayList.get(shortest).setRemainingTime(R_time);

            // Update minimum
            minm = processArrayList.get(shortest).getRemainingTime();
            if (minm == 0)
                minm = Integer.MAX_VALUE;

            // If a process gets completely executed
            if (processArrayList.get(shortest).getRemainingTime() == 0) {

                // Increment complete
                complete++;
                check = false;

                // Find finish time of current
                // process
                finish_time = time + 1;

                // Calculate waiting time
                processArrayList.get(shortest).setWaitingTime(
                        finish_time -
                                processArrayList.get(shortest).getBurstTime() -
                                processArrayList.get(shortest).getArrivalTime()
                );

                if (processArrayList.get(shortest).getWaitingTime() < 0)
                    processArrayList.get(shortest).setWaitingTime(0);
            }
            // Increment time
            time++;
        }
    }

    // Method to calculate turn around time in SJF
    static void findTurnAroundTime_SJF(ArrayList<Process> processArrayList) {
        // calculating turnaround time by adding BurstTime + waitingTime
        for (int i = 0; i < processArrayList.size(); i++)
            processArrayList.get(i).setTurnaroundTime(processArrayList.get(i).getBurstTime() + processArrayList.get(i).getWaitingTime());
    }

    public static void preemptive_sjf(ArrayList<Process> processArrayList) throws IOException {   //SRT

        int total_waitingTime = 0, total_turnaroundTime = 0;
        float avg_waitingTime;
        float avg_responseTime;
        float avg_turnaroundTime;

        // Function to find waiting time of all processes
        findWaitingTime_SJF(processArrayList);

        // Function to find turn around time for all processes
        findTurnAroundTime_SJF(processArrayList);

        // Calculate total waiting time and total turnaround time
        for (int i = 0; i < processArrayList.size(); i++) {
            total_waitingTime = total_waitingTime + processArrayList.get(i).getWaitingTime();
            total_turnaroundTime = total_turnaroundTime + processArrayList.get(i).getTurnaroundTime();
        }

        int sum = 0;
        for (int i = 0; i < processArrayList.size(); i++) {
            sum +=processArrayList.get(i).getWaitingTime()-processArrayList.get(i).getArrivalTime();
        }
        avg_responseTime = (float)sum /  (float) processArrayList.size();

        avg_waitingTime = (float) total_waitingTime / (float) processArrayList.size();
        avg_turnaroundTime = (float) total_turnaroundTime / (float) processArrayList.size();

        MeasurementSout(processArrayList, avg_waitingTime, avg_responseTime, avg_turnaroundTime);

        WritingResult.writeExcel(processArrayList);
    }

    public static void nonPreemptive_sjf(ArrayList<Process> processArrayList) throws IOException {    //SJF
        sortByArrivalTime(processArrayList);
        sortByBurstTime(processArrayList);

        ArrayList<Integer> waitingTime = new ArrayList<>();
        ArrayList<Integer> turnaroundTime = new ArrayList<>();
        ArrayList<Integer> completionTime = new ArrayList<>();
        int num = processArrayList.size();
        float avg_waitingTime;
        float avg_responseTime;
        float avg_turnaroundTime;
        int temp, val;

//        Completion Time: Time at which process completes its execution.
        completionTime.add(processArrayList.get(0).getBurstTime());

//        Turn Around Time = Completion Time – Arrival Time
        turnaroundTime.add(completionTime.get(0) - processArrayList.get(0).getArrivalTime());

//      Waiting Time = Turn Around Time – Burst Time
        waitingTime.add(turnaroundTime.get(0) - processArrayList.get(0).getBurstTime());

        for (int i = 1; i < num; i++) {
            val = i;
            temp = completionTime.get(i - 1);
            int low = processArrayList.get(i).getBurstTime();

            for (int j = i; j < num; j++) {
                if (temp >= processArrayList.get(j).getArrivalTime() && low >= processArrayList.get(j).getBurstTime()) {
                    low = processArrayList.get(j).getBurstTime();
                    val = j;
                }
            }

            completionTime.add(temp + processArrayList.get(val).getBurstTime());
            turnaroundTime.add(completionTime.get(i) - processArrayList.get(val).getArrivalTime());
            waitingTime.add(turnaroundTime.get(i) - processArrayList.get(val).getBurstTime());
        }

        int sum = 0;
        for (Integer integer : waitingTime) {
            sum += integer;
        }
        avg_waitingTime = (float) sum / (float) waitingTime.size();

        sum = 0;
        for (int i = 0; i < processArrayList.size(); i++) {
            sum += waitingTime.get(i)-processArrayList.get(i).getArrivalTime();
        }
        avg_responseTime = (float)sum /  (float) processArrayList.size();

        sum = 0;
        for (int i = 0; i < turnaroundTime.size(); i++) {
            sum += turnaroundTime.get(i);
        }
        avg_turnaroundTime = (float) sum / (float) turnaroundTime.size();


        MeasurementSout(processArrayList, avg_waitingTime, avg_responseTime, avg_turnaroundTime);

        WritingResult.writeExcel(processArrayList);

    }

    public static void roundRobin(ArrayList<Process> processes) throws IOException {
        ArrayList<Process> processOrder = new ArrayList<>();

        double avg_waitingTime;
        double avg_responseTime;
        double avg_turnaroundTime;

        ArrayList<Process> arrayList = new ArrayList<>(processes);
        sortByArrivalTime(arrayList);

        int time = arrayList.get(0).getArrivalTime();

        for (Process process : arrayList) {
            process.rrCounter = 0;
        }

        for (Process item : arrayList) {
            item.remainingTime = item.getBurstTime();
        }

        while (arrayList.size() != 0) {
            for (int i = 0; i < arrayList.size(); i++) {

                if (arrayList.get(i).getRemainingTime() <= timeUnit) {
                    time += arrayList.get(i).getRemainingTime();

                    if (arrayList.get(i).rrCounter == 0) {
                        arrayList.get(i).responseTime = time - arrayList.get(i).getArrivalTime();
                        arrayList.get(i).waitingTime = arrayList.get(i).responseTime;
                        arrayList.get(i).turnaroundTime = time - arrayList.get(i).getArrivalTime();
                    } else {
                        arrayList.get(i).runningTime += arrayList.get(i).getRemainingTime();
                        arrayList.get(i).turnaroundTime = time - arrayList.get(i).getArrivalTime();
                        arrayList.get(i).waitingTime = time - arrayList.get(i).runningTime - arrayList.get(i).getArrivalTime();
                    }
                    arrayList.get(i).completion = true;
                    processOrder.add(arrayList.get(i));

                    arrayList.remove(i);

                } else {
                    if (arrayList.get(i).getRemainingTime() == arrayList.get(i).getBurstTime()) {
                        arrayList.get(i).responseTime = time - arrayList.get(i).getArrivalTime();
                    }
                    time += timeUnit;
                    arrayList.get(i).runningTime += timeUnit;
                    arrayList.get(i).rrCounter++;
                    arrayList.get(i).remainingTime = arrayList.get(i).remainingTime - timeUnit;
                }
            }
        }

//        System.out.println("the order of process completion : ");
//        for (int j = 0; j < processOrder.size(); j++) {
//            System.out.print(processOrder.get(j).getProcessID() + " , ");
//        }

        WritingResult.writeExcel(processOrder);

        int sum = 0;
        for (Process value : processOrder) {
            sum += value.getWaitingTime();
        }
        avg_waitingTime = sum * timeUnit / processOrder.size();
        sum = 0;
        for (Process process : processOrder) {
            sum += process.getResponseTime();
        }
        avg_responseTime = sum * timeUnit / processOrder.size();
        sum = 0;
        for (Process process : processOrder) {
            sum += process.getTurnaroundTime();
        }
        avg_turnaroundTime = sum * timeUnit / processOrder.size();

        MeasurementSout(processOrder, avg_waitingTime, avg_responseTime, avg_turnaroundTime);
        WritingResult.writeExcel(arrayList);
    }

    private static void findWaitingTime_priority(ArrayList<Process> processArrayList) {
        int complete = 0, time = 0, minm = Integer.MAX_VALUE;
        int highestPriority = 0, finish_time;
        boolean check = false;

        // Process until all processes gets
        // completed
        while (complete != processArrayList.size()) {

            // Find process with minimum priority among the processes that arrives till the current time
            for (int j = 0; j < processArrayList.size(); j++) {
                if ((processArrayList.get(j).getArrivalTime() <= time) &&
                        (processArrayList.get(j).getPriority() < minm) && processArrayList.get(j).getRemainingTime() > 0) {
                    minm = processArrayList.get(j).getRemainingTime();
                    highestPriority = j;
                    check = true;
                }
            }
            if (check == false) {
                time++;
                continue;
            }

            // Reduce remaining time by one
            int R_time = processArrayList.get(highestPriority).getRemainingTime();
            R_time--;
            processArrayList.get(highestPriority).setRemainingTime(R_time);

            // Update minimum
            minm = processArrayList.get(highestPriority).getRemainingTime();
            if (minm == 0)
                minm = Integer.MAX_VALUE;

            // If a process gets completely executed
            if (processArrayList.get(highestPriority).getRemainingTime() == 0) {
                // Increment complete
                complete++;
                check = false;
                // Find finish time of current process
                finish_time = time + 1;

                // Calculate waiting time
                processArrayList.get(highestPriority).setWaitingTime(
                        finish_time - processArrayList.get(highestPriority).getBurstTime() -
                                processArrayList.get(highestPriority).getArrivalTime()
                );

                if (processArrayList.get(highestPriority).getWaitingTime() < 0)
                    processArrayList.get(highestPriority).setWaitingTime(0);
            }
            // Increment time
            time++;
        }
    }

    private static void findTurnAroundTime_priority(ArrayList<Process> processArrayList) {
        // calculating turnaround time by adding BurstTime + waitingTime
        for (int i = 0; i < processArrayList.size(); i++)
            processArrayList.get(i).setTurnaroundTime(processArrayList.get(i).getBurstTime() + processArrayList.get(i).getWaitingTime());
    }

    public static void preemptive_priority(ArrayList<Process> processArrayList) throws IOException {
        int total_waitingTime = 0, total_turnaroundTime = 0;
        float avg_waitingTime;
        float avg_responseTime;
        float avg_turnaroundTime;

        // Function to find waiting time of all processes
        findWaitingTime_priority(processArrayList);

        // Function to find turn around time for all processes
        findTurnAroundTime_priority(processArrayList);

        // Calculate total waiting time and total turnaround time
        for (int i = 0; i < processArrayList.size(); i++) {
            total_waitingTime = total_waitingTime + processArrayList.get(i).getWaitingTime();
            total_turnaroundTime = total_turnaroundTime + processArrayList.get(i).getTurnaroundTime();
        }

        avg_waitingTime = (float) total_waitingTime / (float) processArrayList.size();
        avg_turnaroundTime = (float) total_turnaroundTime / (float) processArrayList.size();

        int sum = 0;
        for (int i = 0; i < processArrayList.size(); i++) {
            sum += processArrayList.get(i).getWaitingTime()-processArrayList.get(i).getArrivalTime();
        }
        avg_responseTime = (float)sum /  (float) processArrayList.size();

        MeasurementSout(processArrayList, avg_waitingTime, avg_responseTime, avg_turnaroundTime);

        WritingResult.writeExcel(processArrayList);

    }

    public static void nonPreemptive_priority(ArrayList<Process> arrayList) throws IOException {
        //  ArrayList<Process> readyQueue = new ArrayList<>();
        ArrayList<Integer> waitingTime = new ArrayList<>();
        ArrayList<Integer> responseTime = new ArrayList<>();
        ArrayList<Integer> turnaroundTime = new ArrayList<>();
        double avg_waitingTime;
        double avg_responseTime;
        double avg_turnaroundTime;

        sortByArrivalTime(arrayList);
        sortByPriority(arrayList);

//        System.out.println("the order of processes : ");
//        for (Process process : arrayList) {
//            System.out.print(process.getProcessID() + " , ");
//        }
//        System.out.println();
        WritingResult.writeExcel(arrayList);


//        for (int i = 0; i < 100; i++) {
//            readyQueue.add(arrayList.get(i));
//        }

        int time = arrayList.get(0).getArrivalTime();
        int processNum;
        int waiting_time = 0;

        for (int i = 0; i < arrayList.size(); i++) {
            processNum = arrayList.get(i).getProcessID();
            time += arrayList.get(i).getBurstTime();
            turnaroundTime.add(time - arrayList.get(i).getArrivalTime());
            if (processNum == arrayList.get(0).getProcessID()) {
                waiting_time = 0;
            } else {
                waiting_time = time - arrayList.get(i).getBurstTime() - arrayList.get(i).getArrivalTime();
                if (waiting_time < 0) {
                    waiting_time = 0;
                }
            }
            waitingTime.add(waiting_time);
            // System.out.println("waiting time for process " + processNum + " is : " + waiting_time);
        }
        // System.out.println("time unit : "+ timeUnit);
        int sum = 0;
        for (Integer integer : waitingTime) {
            sum += integer;
        }
        avg_waitingTime = (sum) / waitingTime.size();
        avg_responseTime = avg_waitingTime;

        sum = 0;
        for (int i = 0; i < turnaroundTime.size(); i++) {
            sum += turnaroundTime.get(i);
        }
        avg_turnaroundTime = (sum ) / turnaroundTime.size();

        MeasurementSout(arrayList, avg_waitingTime, avg_responseTime, avg_turnaroundTime);
        WritingResult.writeExcel(arrayList);
    }

    public static int elapsedTime(ArrayList<Process> List) {
        int sum = 0;
        for (int i = 0; i < List.size(); i++) {
            sum += List.get(i).getBurstTime();
        }
        return sum;
    }


    public static ArrayList<Process> sortByArrivalTime(ArrayList<Process> list) {
        ArrayList<Process> processes = list;

        Collections.sort(processes, arrivalTimeComparator);

        return processes;
    }

    public static ArrayList<Process> sortByBurstTime(ArrayList<Process> list) {
        ArrayList<Process> jobs = list;

        Collections.sort(jobs, burstTimeComparator);

        return jobs;
    }

    public static ArrayList<Process> sortByPriority(ArrayList<Process> list) {

        ArrayList<Process> processes = new ArrayList<>(list);
        Collections.sort(processes, priorityComparator);
        return processes;
    }

    public static Comparator<Process> arrivalTimeComparator = new Comparator<Process>() {

        public int compare(Process p1, Process p2) {
            int arrivalTime1 = p1.getArrivalTime();
            int arrivalTime2 = p2.getArrivalTime();

            //ascending order
            return arrivalTime1 - arrivalTime2;
        }
    };

    public static Comparator<Process> burstTimeComparator = new Comparator<Process>() {

        public int compare(Process p1, Process p2) {
            int burstTime1 = p1.getBurstTime();
            int burstTime2 = p2.getBurstTime();

            return burstTime1 - burstTime2;
        }
    };

    public static Comparator<Process> priorityComparator = new Comparator<Process>() {
        @Override
        public int compare(Process p1, Process p2) {
            int priority1 = p1.getPriority();
            int priority2 = p2.getPriority();
            return priority1 - priority2;
        }
    };


    public static void MeasurementSout(ArrayList<Process> arrayList, double avg_waiting, double avg_response, double avg_turnaround) {

        System.out.println("\n|the number of process : " + arrayList.size());
        System.out.println("|elapsed time : " + elapsedTime(arrayList)*timeUnit);
        System.out.println("|Throughput : " + (float) arrayList.size() / elapsedTime(arrayList) * 100 + "%");
        System.out.println("|CPU utilization : " + elapsedTime(arrayList)/elapsedTime(arrayList)*100 + "%");
        System.out.println("|average waiting time : " + avg_waiting*timeUnit);
        System.out.println("|average turnaround time : " + avg_turnaround*timeUnit);
        System.out.println("|average response time : " + avg_response*timeUnit);
    }
}
