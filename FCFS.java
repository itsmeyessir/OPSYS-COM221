import java.util.Scanner;
public class FCFS {
   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);

       System.out.print("Enter the number of processes: ");
       int n = scanner.nextInt();
      
       int[] arrivalTime = new int[n];
       int[] burstTime = new int[n];
       int[] waitingTime = new int[n];
       int[] turnAroundTime = new int[n];
       int[] completionTime = new int[n];

       System.out.println("Enter the arrival and burst time for each process:");
       for (int i = 0; i < n; i++) {
           System.out.print("Process " + (i + 1) + " Arrival Time: ");
           arrivalTime[i] = scanner.nextInt();
           System.out.print("Process " + (i + 1) + " Burst Time: ");
           burstTime[i] = scanner.nextInt();
       }

       completionTime[0] = arrivalTime[0] + burstTime[0];
       for (int i = 1; i < n; i++) {

           if (completionTime[i - 1] < arrivalTime[i]) {
               completionTime[i] = arrivalTime[i] + burstTime[i];
           } else {
               completionTime[i] = completionTime[i - 1] + burstTime[i];
           }
       }
 
       for (int i = 0; i < n; i++) {
           turnAroundTime[i] = completionTime[i] - arrivalTime[i];
           waitingTime[i] = turnAroundTime[i] - burstTime[i];
       }

       int totalWaitingTime = 0, totalTurnAroundTime = 0;
       for (int i = 0; i < n; i++) {
           totalWaitingTime += waitingTime[i];
           totalTurnAroundTime += turnAroundTime[i];
       }
       double averageWaitingTime = (double) totalWaitingTime / n;
       double averageTurnAroundTime = (double) totalTurnAroundTime / n;

       double throughput = (double) n / completionTime[n - 1];

       System.out.println("\nProcess\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time\tCompletion Time");
       for (int i = 0; i < n; i++) {
           System.out.println("P" + (i + 1) + "\t\t" + arrivalTime[i] + "\t\t" + burstTime[i] + "\t\t" + waitingTime[i] + "\t\t" + turnAroundTime[i] + "\t\t" + completionTime[i]);
       }
       System.out.println("\nTotal Waiting Time: " + totalWaitingTime);
       System.out.println("Average Waiting Time: " + averageWaitingTime);
       System.out.println("Total Turnaround Time: " + totalTurnAroundTime);
       System.out.println("Average Turnaround Time: " + averageTurnAroundTime);
       System.out.println("Throughput: " + throughput);
   }
}