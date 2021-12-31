package com.company;

import java.util.*;

public class Algorithm {
// Throughput
// CPU utilization
// Average waiting time
// Average turnaround time
// Average response time

    public static void fifo_scheduling(ArrayList<Process> arrayList) {
        ArrayList<Process> list = new ArrayList<>(arrayList);
        ArrayList<Integer> waitingTime = new ArrayList<>();
        ArrayList<Integer> responseTime = new ArrayList<>();
        ArrayList<Integer> turnaroundTime = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        int avg_waitingTime;
        int avg_responseTime;
        int avg_turnaroundTime;

        for (Process value : list) {
            map.put(value.getProcessID(), value.getArrivalTime());
        }

        List<Map.Entry<Integer, Integer>> theList = new ArrayList(map.entrySet());
        theList.sort(Map.Entry.comparingByValue());
        //theList.forEach(System.out::println);
        System.out.println("the order of processes : ");
        for (int j = 0; j < theList.size(); j++) {
            System.out.print(theList.get(j).getKey() + " , ");
        }
        System.out.println();

        int burst_time = 0;
        for (int i = 0; i < list.size(); i++) {
            burst_time += list.get(i).BurstTime;
            System.out.println("burst : " + burst_time);

            int waiting_time = burst_time - list.get(i).getArrivalTime();
            if (waiting_time < 0) {
                waiting_time = 0;
            }
            waitingTime.add(waiting_time);
            System.out.print(waiting_time + " , ");
        }

        int sum = 0;
        for (Integer integer : waitingTime) {
            sum += integer;
        }
        avg_waitingTime = sum/waitingTime.size();
    }

    public static void preemptive_sjf(ArrayList<Process> processArrayList) {   //SRT

    }

    public static void nonPreemptive_sjf(ArrayList<Process> processArrayList) {    //SJF

        sortByArrivalTime(processArrayList);
        sortByBurstTime(processArrayList);

        System.out.println("the order of processes : ");
        for (int j = 0; j < processArrayList.size(); j++) {
            System.out.print(processArrayList.get(j).ProcessID + " , ");
        }


//        Completion Time: Time at which process completes its execution.
////      Turn Around Time: Time Difference between completion time and arrival time. Turn Around Time = Completion Time – Arrival Time
////      Waiting Time(W.T): Time Difference between turn around time and burst time.
////      Waiting Time = Turn Around Time – Burst Time

    }


    public static void roundRobin() {

    }

    public static void preemption_priority() {

    }

    public static void nonPreemptive_priority() {

    }

    public static ArrayList<Process> sortByArrivalTime(ArrayList<Process> list){
        ArrayList<Process> processes = list;

        Collections.sort(processes, arrivalTimeComparator);

        return processes;
    }

    public static ArrayList<Process> sortByBurstTime(ArrayList<Process> list){
        ArrayList<Process> jobs = list;

        Collections.sort(jobs, burstTimeComparator);

        return jobs;
    }

    public static Comparator<Process> arrivalTimeComparator = new Comparator<Process>() {

        public int compare(Process p1, Process p2) {
            int arrivalTime1 = p1.getArrivalTime();
            int arrivalTime2 = p2.getArrivalTime();

            //ascending order
            return arrivalTime1 - arrivalTime2;
        }};

    public static Comparator<Process> burstTimeComparator = new Comparator<Process>() {

        public int compare(Process p1, Process p2) {
            int burstTime1 = p1.getBurstTime();
            int burstTime2 = p2.getBurstTime();

            return burstTime1 - burstTime2;
        }};

}
