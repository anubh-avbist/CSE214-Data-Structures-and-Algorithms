/**
* The Package defines each package used in the program.
* Each package has a weight, date, and recipient, which are accessed through mutuators and getters.
* 
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/

public class Package {

    private String recipient;
    private int arrivalDate;
    private double weight;
    private int correctStack;


    /**
     * Default constructor for packages sets default values for paramters.
     * Automatically sets correct stack to floor until calculated.
     * 
     * @custom.postconditions: New package is initialized at floor stack.
     */
    public Package(){
        this.recipient = "";
        this.arrivalDate = 0;
        this.weight = 0;
        correctStack = 5;
    }

    public Package(String recipient, double weight, int arrivalDate){
        this.recipient = recipient;
        this.arrivalDate = arrivalDate;
        this.weight = weight;
        correctStack = 5;
    }

    /**
     * List of getters and setter methods.
     */

    public double getWeight() {
        return weight;
    }
    public int getArrivalDate() {
        return arrivalDate;
    }
    public String getRecipient() {
        return recipient;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public void setArrivalDate(int arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String toString(){
        return this.recipient + " " + this.arrivalDate;
    }
    public int getCorrectStack(){
        return correctStack;
    }
    public void setCorrectStack(int correctStack){
        this.correctStack = correctStack;
    }

}

