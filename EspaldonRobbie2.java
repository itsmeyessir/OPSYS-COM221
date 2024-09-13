import java.util.InputMismatchException;
import java.util.Scanner;
 
class Process {
    int pid, arrivalTime, burstTime, exitTime, turnAroundTime, waitingTime;
 
    Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}
 
public class EspaldonRobbie2 {
 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            int n = 0;
            boolean validInput = false;
 
            while (!validInput) {
                try {
                    System.out.print("Enter the number of processes (3 to 5): ");
                    n = sc.nextInt();
 
                    if (n >= 3 && n <= 5) {
                        validInput = true; 
                    } else {
                        System.out.println("Invalid input. Please enter a number between 3 and 5.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.next(); 
                }
            }
 
            Process[] processes = new Process[n];
            for (int i = 0; i < n; i++) {
                System.out.print("Enter arrival time for Process P" + (i + 1) + ": ");
                int arrivalTime = sc.nextInt();
                System.out.print("Enter burst time for Process P" + (i + 1) + ": ");
                int burstTime = sc.nextInt();
                processes[i] = new Process(i + 1, arrivalTime, burstTime);
            }
            
            sjfNonPreemptive(processes, n);
 
            System.out.print("Try again? (y/n): ");
        } while (sc.next().equalsIgnoreCase("y"));
        sc.close();
    }
 
    public static void sjfNonPreemptive(Process[] processes, int n) {
        int[] isCompleted = new int[n];
        int currentTime = 0, completed = 0;
        float avgTurnAroundTime = 0, avgWaitingTime = 0;
 
        while (completed != n) {
            int minBurst = Integer.MAX_VALUE, idx = -1;
 
            for (int i = 0; i < n; i++) {
                if (processes[i].arrivalTime <= currentTime && isCompleted[i] == 0) {
                    if (processes[i].burstTime < minBurst) {
                        minBurst = processes[i].burstTime;
                        idx = i;
                    }
                }
            }
 
            if (idx != -1) {
                processes[idx].exitTime = currentTime + processes[idx].burstTime;
                processes[idx].turnAroundTime = processes[idx].exitTime - processes[idx].arrivalTime;
                processes[idx].waitingTime = processes[idx].turnAroundTime - processes[idx].burstTime;
 
                avgTurnAroundTime += processes[idx].turnAroundTime;
                avgWaitingTime += processes[idx].waitingTime;
 
                currentTime = processes[idx].exitTime;
                isCompleted[idx] = 1;
                completed++;
            } else {
                currentTime++;
            }
        }
 
        display(processes, n, avgTurnAroundTime, avgWaitingTime);
    }
 
    public static void display(Process[] processes, int n, float avgTurnAroundTime, float avgWaitingTime) {
        System.out.println("Process ID\tArrival Time\tBurst Time\tExit Time\tTurn Around Time\tWaiting Time");
        for (Process p : processes) {
            System.out.println("P" + p.pid + "\t\t" + p.arrivalTime + "\t\t" + p.burstTime + "\t\t" + p.exitTime + "\t\t" + p.turnAroundTime + "\t\t\t" + p.waitingTime);
        }
        System.out.println("Average Turn Around Time: " + (avgTurnAroundTime / n) + " unit");
        System.out.println("Average Waiting Time: " + (avgWaitingTime / n) + " unit");
    }
}
