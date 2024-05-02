/**
* This class runs the main method to start the simulation of the SevenFlags amusement park.
* It allows the user to indicate the number of customers within each plan, as well as details about the 4 main rides.
* Runs a simulation of all the rides, moving customers in and out of virtual and holding lines and onto rides.
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SevenFlags{
    public static Person[] goldCustomers;
    public static Person[] silverCustomers;
    public static Person[] regularCustomers;
    public static Person[][] allCustomers;
    public static String[] plans = {"Regular", "Silver", "Gold"};
    public static String[] rideNames = {"Blue Scream of Death", "Kingda Knuth", "i386 Tower of Terror", "GeForce"};
    public static String[] ridesAbbrv = {"BSOD", "KK", "iToT", "GF"};
    public static final int NUM_RIDES = 4;
    public static Scanner scanner = new Scanner(System.in);
    public static double[] probabilities = new double[NUM_RIDES];

    public static int[] averageRides = new int[plans.length];
    public static int[] completedRides = new int[NUM_RIDES];


    /**
     * Main method that starts the simulation.
     * @param args
     */
    public static void main(String[] args){




        System.out.println("Welcome to Seven Flags!");

        /**
         * Initializes variables based on user input.
         */
        Ride[] rides = new Ride[NUM_RIDES];
        
        int numCustomers[] = new int[plans.length];
        for(int i = 0; i < plans.length; i++){
            numCustomers[i]=-1;
            while(numCustomers[i]<0){
                System.out.print("Please enter the number of " + plans[i] + " customers: ");
                numCustomers[i] = scanner.nextInt();
                if(numCustomers[i]<0){
                    System.out.println("Please enter a non-zero number!");
                }
            }
        }

        int simulationLength = -1;
        while(simulationLength<=0){
            System.out.print("Please enter simulation length: ");
            simulationLength = scanner.nextInt();
            if (simulationLength <=0){
                System.out.println("Please enter a positive number!");
            }
        }



        for(int i = 0; i < NUM_RIDES; i++){
            int holdingSize = -1;
            int capacity = -1;
            int duration = -1;
            Boolean nonNegative = false;
            while(!nonNegative){

                System.out.print("Please enter the duration of " + rideNames[i] + ": ");
                duration = scanner.nextInt();
                System.out.print("Please enter the capacity of " + rideNames[i] + ": ");
                capacity = scanner.nextInt();
                System.out.print("Please enter the holding queue size for " + rideNames[i] + ": ");
                holdingSize = scanner.nextInt();
                rides[i] = new Ride(ridesAbbrv[i], duration, capacity, holdingSize);

            
                if(duration <= 0 || capacity <= 0 || holdingSize <= 0){
                    System.out.println("Please enter positive values!");

                } else {
                    nonNegative = true;
                }
            }
        } 

        boolean probabilityCondition = false;
        while(!probabilityCondition){
            for(int i = 0; i < NUM_RIDES; i++){
                System.out.print("Enter the probability of going into " + rideNames[i] + ":");
                probabilities[i] = scanner.nextDouble();
            }

            double probabilitySum = 0;
            boolean negativeValue = false;
            for(int i = 0; i < probabilities.length; i++){
                if(probabilities[i]<0){
                    negativeValue = true;
                }
                probabilitySum+=probabilities[i];
            }
            if(probabilitySum==1 && negativeValue==false){
                probabilityCondition = true;
                break;
            } else {
                System.out.println("Please retry with proper values! The probabilities should sum to 1, and there should be no negative probabilities");
            }
        }

        goldCustomers = new Person[numCustomers[2]];
        silverCustomers = new Person[numCustomers[1]];
        regularCustomers = new Person[numCustomers[0]];

        for(int i = 0; i < goldCustomers.length; i++){
            goldCustomers[i] = new Person(i+1,3);
        }
        
        for(int i = 0; i < silverCustomers.length; i++){
            silverCustomers[i] = new Person(i+1,2);
        }
        for(int i = 0; i < regularCustomers.length; i++){
            regularCustomers[i] = new Person(i+1,1);
        }

        allCustomers = new Person[3][];
        allCustomers[0] = regularCustomers;
        allCustomers[1] = silverCustomers;
        allCustomers[2] = goldCustomers;

        for(int time = 0; time < simulationLength; time++){


            /**
             * Starts the simulation, and updates each time step
             */
            // Simulating:

            for (int i = 0; i < NUM_RIDES; i++) {
                Ride ride = rides[i];
                ride.setTimeLeft(ride.getTimeLeft()-1); // Decrements time

                if(ride.getTimeLeft()== 0){ // Get all people off the ride and into the lines
                    for (Person p : ride.getPeopleOnRide()) {
                        p.setStatus(Status.AVAILABLE);
                        p.getRides().remove(0);

                        // Update statistics
                        completedRides[i]++;
                        averageRides[p.getMaxLines()-1]++; 
                    }
                    ride.getPeopleOnRide().clear();

                }
                
            }

            // Assign people to queues and lines

            for(int i = 0; i < plans.length; i++){
                for(int j = 0; j < plans.length-i; j++){
                    Person[] currentPlan = allCustomers[plans.length-j-1];
                    for(int k = 0; k < currentPlan.length; k++){
                        Person currentPerson = currentPlan[k];
                        if(currentPerson.getRides().size()<currentPerson.getMaxLines()){
                            Ride chosenRide = RandomGenerator.selectRide(rides, probabilities);
                            if(currentPerson.getStatus()!=Status.AVAILABLE){
                                chosenRide.getVirtualLine().enqueue(currentPerson);
                            } else if(chosenRide.getHoldingQueue().size()<chosenRide.getHoldingQueue().getMaxSize()){
                                chosenRide.getHoldingQueue().enqueue(currentPerson);
                                currentPerson.setStatus(Status.HOLDING);
                            } else {
                                chosenRide.getVirtualLine().enqueue(currentPerson);
                            }
                            currentPerson.getRides().add(chosenRide);
                        }
                    }
                }
            }

            // Push people onto rides

            for(Ride ride: rides){
                if(ride.getTimeLeft() == 0){

                    while(!ride.getHoldingQueue().isEmpty()){
                        if(ride.getPeopleOnRide().size()>= ride.getCapacity()){
                            break;
                        }
                        // Push holding queue man into line
                        Person nextRider = ride.getHoldingQueue().dequeue();
                        nextRider.setStatus(Status.ONRIDE);
                        ride.getPeopleOnRide().add(nextRider);

                        // Push virtual person into hold
                        if(!ride.getVirtualLine().isEmpty()){
                            for(int i = 0; i < ride.getVirtualLine().size(); i++){
                                if(((Person)ride.getVirtualLine().peek()).getStatus()==Status.AVAILABLE){
                                    Person nextHoldPerson = (Person) ride.getVirtualLine().dequeue();
                                    nextHoldPerson.setStatus(Status.HOLDING);
                                    ride.getHoldingQueue().enqueue(nextHoldPerson);
                                    break;
                                } else {
                                    ride.getVirtualLine().enqueue(ride.getVirtualLine().dequeue());
                                }
                            }
                        }
                    }
                }
            }

            // Send rides off
            for (Ride ride : rides) {
                if(ride.getTimeLeft()==0){
                    ride.setTimeLeft(ride.getDuration());
                }
            }

        
            // PRINTING

            System.out.println("\n----------------------------------------------\n");
            System.out.println("At time " + (time+1) + ":");
            for(int j = 0; j < NUM_RIDES; j++){
                Ride currentRide = rides[j];

                // Time Remaining
                System.out.println(rideNames[j] + " - Time remaining: " + currentRide.getTimeLeft());

                // On Ride
                String peopleOnRideString = "On Ride:";
                for(int k = 0; k < currentRide.getPeopleOnRide().size(); k++){
                    peopleOnRideString+=currentRide.getPeopleOnRide().get(k).toString();
                    if(k<currentRide.getPeopleOnRide().size()-1){
                        peopleOnRideString+=", ";
                    }
                }
                System.out.println(peopleOnRideString);

                // Holding Queue
                String holdingQueueString = "Holding Queue:" + currentRide.getHoldingQueue().toString();
                System.out.println(holdingQueueString);

                // Virtual QUeue
                String virtualQueueString = "Virtual Queue:" + currentRide.getVirtualLine().toString();
                System.out.println(virtualQueueString);


                System.out.println("---------");
            }

            for(int j = 0; j < plans.length; j++){
                System.out.println(plans[j] + " Customers");
                System.out.printf(String.format("%-8s", "Num"));
                for(int k = 1; k <= j+1; k++){
                    System.out.printf("%-8s","Line " + k);
                }
                System.out.printf(String.format("%-8s %n", "Status"));
                System.out.println("---------");
                for(int k = 0; k < allCustomers[j].length; k++){
                    Person currentCustomer = allCustomers[j][k];
                    System.out.printf(String.format("%-8s",currentCustomer.getNumber()));
                    
                    for(int l = 0; l < currentCustomer.getRides().size(); l++){
                        System.out.printf("%-8s", currentCustomer.getRides().get(l).getName()); 
                    }
                    System.out.printf("%-8s %n", currentCustomer.getStatus());
                }
            }
        }     

        System.out.println("...........At the end of the simulation...........");
        for(int i = plans.length-1; i >= 0; i--){
            System.out.println("On average, " + plans[i] + " customers have taken " + String.format("%.2f",(((double)averageRides[i])/ ((double)allCustomers[i].length))) + " rides.");
        }
        for(int i = 0; i < rideNames.length; i++){
            System.out.println(ridesAbbrv[i] + " has completed rides for " + completedRides[i] + " people.");
        }
    }
}

