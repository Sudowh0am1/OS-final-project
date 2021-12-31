package com.company;

import java.util.Random;

public class Process {
    protected int ProcessID;
    protected int ArrivalTime;
    protected int Priority;
    protected int BurstTime;
    private static int pID = 1;
    protected int waitingTime;
    protected int turnaroundTime;
    protected int responseTime;

    Random generator = new Random();

    public Process(int priorityOfProcess){
        this.ProcessID = pID ;
        this.ArrivalTime = generator.nextInt(100);
        this.Priority = priorityOfProcess;
        this.BurstTime = generator.nextInt(100) + 1;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.responseTime = 0;
        pID++;
    }

    public int getBurstTime() {
        return BurstTime;
    }

    public void setBurstTime(int burstTime) {
        BurstTime = burstTime;
    }

    public int getProcessID() {
        return ProcessID;
    }

    public int getArrivalTime() {
        return ArrivalTime;
    }

    public int getPriority() {
        return Priority;
    }


    @Override
    public String toString() {
        return "Process{" +
                "ProcessID=" + ProcessID +
                ", ArrivalTime=" + ArrivalTime +
                ", Priority=" + Priority +
                ", BurstTime=" + BurstTime +
                '}';
    }
}
