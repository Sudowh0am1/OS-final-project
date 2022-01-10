package com.company;

import java.util.Random;

public class Process implements Comparable {
    protected int ProcessID;
    protected int ArrivalTime;
    protected int Priority;
    protected int BurstTime;
    private static int pID = 1;
    protected int waitingTime;
    protected int turnaroundTime;
    protected int remainingTime;
    protected int responseTime;
    // round-robin algorithm
    protected int rrCounter;
    protected int runningTime;
    protected boolean completion;


    Random generator = new Random();

    public Process(int priorityOfProcess) {
        this.ProcessID = pID;
        this.ArrivalTime = generator.nextInt(50);
        this.Priority = priorityOfProcess;
        this.BurstTime = generator.nextInt(100) + 1;
        this.waitingTime = 0;
        this.remainingTime = BurstTime;
        this.turnaroundTime = 0;
        this.responseTime = 0;
        pID++;
    }

    public int getBurstTime() {
        return BurstTime;
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

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public int getResponseTime() {return responseTime;}

    @Override
    public String toString() {
        return "Process{" +
                "ProcessID=" + ProcessID +
                ", ArrivalTime=" + ArrivalTime +
                ", Priority=" + Priority +
                ", BurstTime=" + BurstTime +
                '}';
    }

    @Override
    public int compareTo(Object object) {
        return 0;
    }
}
