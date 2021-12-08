import java.util.Scanner;

public class AlgorithmMain {
	
	private static int amount = 0;
	private static int out = 0;
	private static int temp = 0;
	
	public static void main(String args[]) {
		Scanner scnr = new Scanner(System.in);
		
		System.out.println("Select a job number: ");
		amount = scnr.nextInt();
		
		System.out.println("Outliers: ");
		out = scnr.nextInt();
		
		int time = 0;
		int randomArr = 0;
		int TQ = 0;
		boolean working = true;
		boolean notEdited = false;
		boolean taken = false;
		
		int outl[] = new int[out];
		
		int numList[] = new int[amount];
		int sjfList[] = new int[amount];
		int rrList[] = new int[amount];
		int arrivalTime[] = new int[amount];
		int arrivalSjf[] = new int[amount];
		
		int fcfsStart[] = new int[amount];
		int fcfsEnd[] = new int[amount];
		int sjfStart[] = new int[amount];
		int sjfEnd[] = new int[amount];
		int rrStart[] = new int[amount];
		int rrEnd[] = new int[amount];
		
		int fcfsWait[] = new int[amount];
		int fcfsRes[] = new int[amount];
		int fcfsTurn[] = new int[amount];
		int sjfWait[] = new int[amount];
		int sjfRes[] = new int[amount];
		int sjfTurn[] = new int[amount];
		int rrWait[] = new int[amount];
		int rrRes[] = new int[amount];
		int rrTurn[] = new int[amount];
		
		int avgWait = 0;
		int avgRes = 0;
		int avgTurn = 0;
		
		for(int i = 0; i < amount; i++) {	// Random burst time generator
			int random = RandomNumbers.randNum(4) + 5;
			numList[i] = random;
			sjfList[i] = random;
			rrList[i] = random;
		}
		
		for(int i = 0; i < out;) {
			int random = RandomNumbers.randNum(amount);
			for(int j = 0; j < out; j++) {
				if (outl[j] == random) {
					taken = true;
				}
			}
			if (taken == true) {
			}
			else {
				outl[i] = random;
				numList[i] = numList[i] * 3;
				sjfList[i] = sjfList[i] * 3;
				rrList[i] = rrList[i] * 3;
				i++;
			}
			taken = false;
		}
		
		arrivalTime[0] = 0;
		arrivalSjf[0] = 0; 
		for (int i = 1; i < amount; i++) {	//Random arrival time generator
			randomArr = (RandomNumbers.randNum(3) + 4);
			arrivalTime[i] = randomArr;
			arrivalSjf[i] = randomArr;
		}
		
		while(working) {	// Organize jobs in SJF from shortest to longest
			notEdited = true;
			for(int i = 0; i < amount; i++) {
				if(i == amount-1) {
				}
				else {
					if(sjfList[i] > sjfList[i+1]) {
						temp = sjfList[i];
						sjfList[i] = sjfList[i+1];
						sjfList[i+1] = temp;
						
						temp = arrivalSjf[i];
						arrivalSjf[i] = arrivalSjf[i+1];
						arrivalSjf[i+1] = temp;
						
						notEdited = false;
					}
				}
			}
			if(notEdited) {
				working = false;
				}
		}
		
		int Arr = 0;
		int ArrS = 0;
		for(int i = 0; i < amount; i++) {
			Arr = Arr + arrivalTime[i];
			ArrS = ArrS + arrivalSjf[i];
			arrivalTime[i] = Arr;
			arrivalSjf[i] = ArrS;
		}
		
		for(int i = 0; i < amount;) {	// Running jobs in "CPU" for FCFS
			if(time < arrivalTime[i]) {
				time++;
			}
			else {
				fcfsStart[i] = time;
				time = time + numList[i];
				fcfsEnd[i] = time;
				i++;
			}
		}
		
		for(int i = 0; i < amount; i++) { //Calculating wait time, response time, and turnaround time.
			fcfsTurn[i] = fcfsEnd[i] - arrivalTime[i];
			fcfsRes[i] = fcfsStart[i] - arrivalTime[i];
			fcfsWait[i] = fcfsTurn[i] - numList[i];
		}
		
		for(int i = 0; i < amount; i++) {
			avgWait = avgWait + fcfsWait[i];
			avgRes = avgRes + fcfsRes[i];
			avgTurn = avgTurn + fcfsTurn[i];
		}
		
		System.out.println("Average FCFS wait time:" + "\n" + (avgWait/amount));
		System.out.println("Average FCFS response time:" + "\n" + (avgRes/amount));
		System.out.println("Average FCFS turnaround time:" + "\n" + (avgTurn/amount));
		System.out.println("");
		
		avgWait = 0;
		avgRes = 0;
		avgTurn = 0;
		time = 0;
		
		for(int i = 0; i < amount;) {
			if(time < arrivalSjf[i]) {
				time++;
			}
			else if (time >= arrivalSjf[i]) {
				sjfStart[i] = time;
				time = time + sjfList[i];
				sjfEnd[i] = time;
				i++;
			}
		}
		
		for(int i = 0; i < amount; i++) { //Calculating wait time, response time, and turnaround time.
			sjfTurn[i] = sjfEnd[i] - arrivalSjf[i];
			sjfRes[i] = sjfStart[i] - arrivalSjf[i];
			sjfWait[i] = sjfTurn[i] - sjfList[i];
		}
		
		for(int i = 0; i < amount; i++) {
			avgWait = avgWait + sjfWait[i];
			avgRes = avgRes + sjfRes[i];
			avgTurn = avgTurn + sjfTurn[i];
		}
		
		System.out.println("Average SJF wait time:" + "\n" + (avgWait/amount));
		System.out.println("Average SJF response time:" + "\n" + (avgRes/amount));
		System.out.println("Average SJF turnaround time:" + "\n" + (avgTurn/amount) + "\n");
		
		avgWait = 0;
		avgRes = 0;
		avgTurn = 0;
		time = 0;
		
		for(int i = 0; i < amount; i++) {
			TQ = TQ + rrList[i];
		}
		
		TQ = TQ/amount;
		working = true;
		
		while(working) {
			for(int i = 0; i < amount;) {
				notEdited = true;
				if(time < arrivalTime[i]) { // Checking arrival time
					time++;
				}
				else if(rrList[i] == 0) {	// Checking if job is complete
					i++;
				}
				else if(rrStart[i] == 0) { // Checking if job has been started before
					notEdited = false;
					rrStart[i] = time;
					if(TQ >= rrList[i]) {
						time = time + rrList[i];
						rrList[i] = 0;
						rrEnd[i] = time;
					}
					else {
						time = time + TQ;
						rrList[i] = rrList[i] - TQ;
					}
					i++;
				}
				else {
					notEdited = false;
					if(TQ >= rrList[i]) {
						time = time + rrList[i];
						rrList[i] = 0;
						rrEnd[i] = time;
					}
					else {
						time = time + TQ;
						rrList[i] = rrList[i] - TQ;
					}
					i++;
				}
			}
			if(notEdited) {
				working = false;
			}
		}
		
		for(int i = 0; i < amount; i++) { //Calculating wait time, response time, and turnaround time.
			rrTurn[i] = rrEnd[i] - arrivalTime[i];
			rrRes[i] = rrStart[i] - arrivalTime[i];
			rrWait[i] = rrTurn[i] - rrList[i];
		}
		
		for(int i = 0; i < amount; i++) {
			avgWait = avgWait + rrWait[i];
			avgRes = avgRes + rrRes[i];
			avgTurn = avgTurn + rrTurn[i];
		}
		
		System.out.println("Average RR wait time:" + "\n" + (avgWait/amount));
		System.out.println("Average RR response time:" + "\n" + (avgRes/amount));
		System.out.println("Average RR turnaround time:" + "\n" + (avgTurn/amount));
	}
}
