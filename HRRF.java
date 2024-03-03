package Main;
import java.util.*;

public class HRRF {
	
public static void main(String[] args) {
	
	Scanner scan = new Scanner(System.in);
	System.out.print("Please enter number of processes: ");
    int no_of_processes = scan.nextInt();
    String[] process_names = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "X", "W", "Y", "Z"};
    String[] processes = new String[no_of_processes];
    int[] burst_times = new int[no_of_processes];
    int[] arrival_times = new int[no_of_processes];

    int totalTime = 0;
    for (int i = 0; i < burst_times.length; i++) {
        processes[i] = process_names[i];
        System.out.print("Please enter burst time for "+processes[i]+":" );
        burst_times[i] = scan.nextInt();
        System.out.print("Please enter arrival time for "+processes[i]+":" );
        arrival_times[i] = scan.nextInt();
        totalTime += burst_times[i];
    }
    scan.close();

    int[] remainingTime = Arrays.copyOf(burst_times, burst_times.length);
    int currentTime = 0;
    ArrayList<String> schedule = new ArrayList<>();

    while (currentTime < totalTime) {
        double maxRatio = Double.MIN_VALUE;
        int maxIndex = -1;

        for (int i = 0; i < no_of_processes; i++) {
            if (arrival_times[i] <= currentTime && remainingTime[i] > 0) {
                double ratio = calculatePriority(remainingTime[i], currentTime - arrival_times[i]);
                if (ratio > maxRatio) {
                    maxRatio = ratio;
                    maxIndex = i;
                }
            }
        }

        if (maxIndex == -1) {
            currentTime++;
        } else {
            schedule.add(processes[maxIndex]);
            remainingTime[maxIndex]--;
            currentTime++;
        }
    }

    // Display schedule
    System.out.println("Schedule of processes after applying HRRF algorithm:");
    for (String process : schedule) {
        System.out.print(process + " ");
    }

    // Calculate average waiting time
    double totalWaitingTime = 0;
    for (int i = 0; i < no_of_processes; i++) {
        totalWaitingTime += calculateWaitingTime(arrival_times[i], totalTime - burst_times[i]);
    }

    double avgWaitingTime = totalWaitingTime / no_of_processes;
    System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
}

public static double calculateWaitingTime(int arrivalTime, int spentTime) {
    int waitingTime = spentTime - arrivalTime;
    return waitingTime;
}

public static double calculatePriority(int burst_time, int waitingTime) {
    double priority = (burst_time + waitingTime) / (double) burst_time;
    return priority;
}
}
