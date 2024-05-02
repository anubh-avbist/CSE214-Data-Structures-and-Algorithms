/**
* VirtualLine models a Queue ADT, by extending the ArrayList ADT.
* The front of the queue is at the lowest index in the list.
* The line has no maximum size. It indicates where customers go if the holding queue is full or if they are not available.
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/

import java.util.ArrayList;

public class VirtualLine<Person> extends ArrayList<Person>{

    private int currentSize;

    /**
     * Default constructor that initializes the current size to 0.
     */
    public VirtualLine(){
        currentSize=0;
    }

    /**
     * Adds a person to the back of the line.
     * @param p The person added to the line
     * @custom.postconditions: The rest of the line is unaltered.
     */
    public void enqueue(Person p){
        super.add(p);
        currentSize++;
    }

    /**
     * Removes the person in the front of the line
     * @return the person in the front of the line
     * @custom.postconditions: Everyone is shifted forward in the line.
     * @custom.preconditions: The line cannot be empty.
     */
    public Person dequeue(){

        if(currentSize!=0){
            currentSize--;
        }
        return super.remove(0);
        
    }

    /**
     * Returns if the line is empty.
     */
    public boolean isEmpty(){
        return super.isEmpty();
    }

    /**
     * 
     * @return Returns the person in the front of the queue.
     */
    public Person peek(){
        return (Person) get(0);
    }


    /**
     * ToString method iterates across the queue by dequeuing into a temporary queue and requeuing into the same queu. 
     * It returns the string that indicates all customers in the virtual line.
     * @custom.postconditions The queue must be unaltered after printing.
     */
    public String toString(){
        String s = "";

        HoldingQueue temporaryQueue = new HoldingQueue();
        while(this.currentSize > 0){
            s+=this.peek();
            temporaryQueue.enqueue(this.dequeue());

            if(this.currentSize!=0){
                s+= ", ";
            }
        }
        while(temporaryQueue.size() > 0){
            enqueue((Person) temporaryQueue.dequeue());
        }
        return s;
    }
}
