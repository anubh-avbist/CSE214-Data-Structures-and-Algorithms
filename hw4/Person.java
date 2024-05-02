/**
* This class implements a Person, indicating their plan based on the number of the maximum lines they can join.
* It also temporarily indicates the number of lines they are queue'd in or currently riding.
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/


import java.util.ArrayList;
import java.util.List;

public class Person {
    private int number;
    private int maxLines;
    private ArrayList<Ride> rides;
    private Status status;
    private String[] plan = {"Regular", "Silver", "Gold"};


    /**
     * Constructor method initializes a person with a unique number within their plan, 
     * as well as their plan indicated by the number of maximum lines they can be a part of.
     * @param number
     * @param maxLines
     */
    public Person(int number, int maxLines){
        if(number<=0){
            throw new IllegalArgumentException();
        }
        this.number = number;
        this.maxLines = maxLines;
        status = Status.AVAILABLE;
        rides = new ArrayList<Ride>();
    }

    // Standard getter and setter methods for all instance variables.

    public int getNumber(){
        return number;
    }

    public int getMaxLines() {
        return maxLines;
    }
    
    public ArrayList<Ride> getRides() {
        return rides;
    }

    public Status getStatus() {
        return status;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    public void setMaxLines(int maxLines) {
        this.maxLines = maxLines;
    }

    public void setRides(ArrayList<Ride> rides) {
        this.rides = rides;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String toString(){
        return plan[maxLines-1] + " " + number;
    }
}