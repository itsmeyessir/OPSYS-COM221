import java.util.*;
class Process {
   int pid; // Process ID
   int arrivalTime; // Arrival time
   int burstTime; // Burst time
   int remainingTime; // Remaining burst time
   int completionTime; // Completion time
   int turnaroundTime; // Turnaround time
   int waitingTime; // Waiting time
   public Process(int pid, int arrivalTime, int burstTime) {
       this.pid = pid;
       this.arrivalTime = arrivalTime;
       this.burstTime = burstTime;
       this.remainingTime = burstTime; // Initially, remaining time is the burst time
   }
}
public class RoundRobin {
   public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       boolean tryAgain = true;
       while (tryAgain) {
           System.out.print("ESPALDON & FAT || ROUND ROBIN ALGORITHM GROUPINGS\nEnter the number of processes (4-6): ");
           int n = sc.nextInt();
           if (n < 4 || n > 6) {
               System.out.println("Please enter between 4 to 6 processes.");
               continue;
           }
           System.out.print("Enter time quantum: ");
           int timeQuantum = sc.nextInt();
           Process[] processes = new Process[n];
           // Input arrival time and burst time for each process
           for (int i = 0; i < n; i++) {
               System.out.print("Enter arrival time for P" + (i + 1) + ": ");
               int arrivalTime = sc.nextInt();
               System.out.print("Enter burst time for P" + (i + 1) + ": ");
               int burstTime = sc.nextInt();
               processes[i] = new Process(i + 1, arrivalTime, burstTime);
           }
           // Gantt chart and time
           List<String> ganttChart = new ArrayList<>();
           List<Integer> timeChart = new ArrayList<>();
           int currentTime = 0;
           int totalTurnaroundTime = 0;
           int totalWaitingTime = 0;
           int completed = 0;
           Queue<Process> readyQueue = new LinkedList<>();
           boolean[] inQueue = new boolean[n]; // Tracks whether a process is already in the queue
           while (completed < n) {
               // Add processes that have arrived by currentTime to the queue
               for (int i = 0; i < n; i++) {
                   if (processes[i].arrivalTime <= currentTime && processes[i].remainingTime > 0 && !inQueue[i]) {
                       readyQueue.add(processes[i]);
                       inQueue[i] = true;
                   }
               }
               if (!readyQueue.isEmpty()) {
                   Process currentProcess = readyQueue.poll();
                   // Execute the process for either the time quantum or remaining time
                   int executedTime = Math.min(timeQuantum, currentProcess.remainingTime);
                   currentProcess.remainingTime -= executedTime;
                   currentTime += executedTime;
                   ganttChart.add("P" + currentProcess.pid);
                   timeChart.add(currentTime);
                   // If the process finishes
                   if (currentProcess.remainingTime == 0) {
                       currentProcess.completionTime = currentTime;
                       currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                       currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                       totalTurnaroundTime += currentProcess.turnaroundTime;
                       totalWaitingTime += currentProcess.waitingTime;
                       completed++;
                   }
                   // Re-add any processes that arrive during execution
                   for (int i = 0; i < n; i++) {
                       if (processes[i].arrivalTime <= currentTime && processes[i].remainingTime > 0 && !inQueue[i]) {
                           readyQueue.add(processes[i]);
                           inQueue[i] = true;
                       }
                   }
                   // Re-add the current process if it has remaining time
                   if (currentProcess.remainingTime > 0) {
                       readyQueue.add(currentProcess);
                   }
               } else {
                   // If no process is ready, increment time
                   currentTime++;
               }
           }
           // Print process details
           System.out.println("\nProcess\tArrival\tBurst\tCompletion\tTurnaround\tWaiting");
           for (Process p : processes) {
               System.out.println("P" + p.pid + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" +
                       p.completionTime + "\t\t" + p.turnaroundTime + "\t\t" + p.waitingTime);
           }
           // Print Gantt chart
           System.out.println("\nGantt Chart:");
           System.out.print("Processes: ");
           for (String p : ganttChart) {
               System.out.print(p + " ");
           }
           System.out.println("\nTime:      0 " + timeChart.toString().replaceAll("[\\[\\],]", ""));
           // Calculate and print averages only if the time quantum is 2
           if (timeQuantum == 2) {
               double avgTurnaroundTime = (double) totalTurnaroundTime / n;
               double avgWaitingTime = (double) totalWaitingTime / n;
               System.out.println("\nAverage Turnaround Time: " + String.format("%.3f", avgTurnaroundTime));
               System.out.println("Average Waiting Time: " + String.format("%.3f", avgWaitingTime));
           } else {
               System.out.println("\nTime quantum is not 2, skipping average turnaround and waiting time calculation.");
           }
           // Ask user if they want to try again
           System.out.print("\nDo you want to try again? (yes/no): ");
           String response = sc.next().toLowerCase();
           tryAgain = response.equals("yes");
       }
       sc.close();
   }
}
