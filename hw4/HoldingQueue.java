/**
* This class extemds the VirtualLine to implement a Holding Queue for each ride 
* It is the same a Virtual Line, except it has a max size, and indicates that customers have the HOLDING status.
* Some methods are not written as they are inherited from the parent class, VirtualLine.
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/

public class HoldingQueue extends VirtualLine{
    private int maxSize;

    public HoldingQueue(){
        super();
    }
    

    /**
     * Default constructor calls the super method, and adds a maximum size parameter.
     * @param maxSize
     */
    public HoldingQueue(int maxSize){
        super();
        this.maxSize = maxSize;
    }

    /**
     * Enqueues a person
     * @custom.preconditions: the number of people in the line must be below the max queue size.
     * @param p
     */
    public void enqueue(Person p){
        if(size()<maxSize){
            super.enqueue(p);
        } else{
            System.out.println("The Queue is full!");
        }
    }

    /**
     * Returns the front person in the line.
     */
    public Person dequeue(){
        return (Person) super.dequeue();
    }


    // Standard getter and setter methods. 
    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
