package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int choose;
        Scanner scanner = new Scanner(System.in);
        System.out.println("choose the scheduling methode:\n"+ "1. FIFO\n"+ "2. Preemptive SJF\n"+ "3. Non-Preemptive SJF\n"
        + "4. RR (with specified time quantum)\n"+ "5. Priority with preemption\n"+ "6. Non-Preemptive priority\n");

        choose = scanner.nextInt();
        switch (choose) {
            case 1 : fifo_scheduling();
            case 2 : preemptive_sjf();
            case 3 : nonPreemptive_sjf();
            case 4 : roundRobin();
            case 5 : preemption_priority();
            case 6 : nonPreemptive_priority();
        }
    }

    // reads data from excel file
    public static void importing_data() {

    }

    // the function that measures time
    public static void given_function() {
        int temp =0;
        for (int i=0; i < 10000; i++) {
            if (i % 2 == 0)
            temp = i / 2;
            else
            temp = 2 * i;
        }
    }

    public static void fifo_scheduling() {

    }

    public static void preemptive_sjf() {

    }

    public static void nonPreemptive_sjf() {

    }

    public static void roundRobin() {

    }

    public static void preemption_priority() {

    }

    public static void nonPreemptive_priority() {

    }


}


