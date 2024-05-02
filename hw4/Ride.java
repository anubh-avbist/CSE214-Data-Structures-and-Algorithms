/**
* This class implements a Ride, indicating its current state in time (timeLeft and people in each queue/list).
* The class also indicates permanent info about the ride, such as its duration, name and capacity.
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/


import java.util.ArrayList;
import java.util.List;

public class Ride {
    private int duration;
    private int timeLeft;
    private String name;
    private VirtualLine virtualLine;
    private HoldingQueue holdingQueue;
    private List<Person> peopleOnRide;
    private int numOfRides;
    private int capacity;

    /**
     * Default constructor that initializes the permanent info (duration, name and capacity)
     * The constructor sets the time to 1, so that the customers can immediately be on ride when the simulation starts.
     * It initializes all queues and an Arraylist to indicate all the people in the ride.
     * @param name
     * @param duration
     * @param capacity
     * @param holdingSize
     */
    public Ride(String name, int duration, int capacity, int holdingSize){
        this.name = name;
        this.duration = duration;
        peopleOnRide = new ArrayList<Person>();
        holdingQueue = new HoldingQueue(holdingSize);
        virtualLine = new VirtualLine<Person>();
        //this.timeLeft = duration;
        this.timeLeft = 1;
        numOfRides = 0;
        this.capacity = capacity;


    }

    // Standard Getters and Setters.

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getDuration() {
        return duration;
    }

    public int getTimeLeft() {
        return timeLeft;
    }
    
    public String getName() {
        return name;
    }

    public VirtualLine getVirtualLine() {
        return virtualLine;
    }

    public HoldingQueue getHoldingQueue() {
        return holdingQueue;
    }

    public ArrayList<Person> getPeopleOnRide() {
        return (ArrayList<Person>) peopleOnRide;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVirtualLine(VirtualLine virtualLine) {
        this.virtualLine = virtualLine;
    }

    public void setHoldingQueue(HoldingQueue holdingQueue) {
        this.holdingQueue = holdingQueue;
    }

    public void setPeopleOnRide(ArrayList<Person> peopleOnRide) {
        this.peopleOnRide = peopleOnRide;
    }
    
    public int getNumOfRides() {
        return numOfRides;
    }

    public void setNumOfRides(int numOfRides) {
        this.numOfRides = numOfRides;
    }


    public String toString(){
        return " This ride is called : " + getName() + " with capacity : " + getCapacity()
            + " and duration : " + getDuration() + "and max size" + getHoldingQueue().getMaxSize();
        
    }
}



