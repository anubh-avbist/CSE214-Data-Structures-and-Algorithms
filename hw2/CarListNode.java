/**
* This class creates a Node for the CarList linked list. 
* These nodes define a doubly linked list, as they can access their data, as well as both the previous and next nodes.
*    
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/

public class CarListNode {
    private Car data;
    private CarListNode next;
    private CarListNode prev;

    /**
     * Default constructor that places a car object into a ListNode.
     * @param initData - Car object for the list.
     * @custom.preconditions: The initData cannot be null. 
     * @custom.postconditions: "This CarListNode has been initialized to wrap the indicated Car, and prev and next have been set to null." (from hw2.html doc)
     */

    public CarListNode(){
        
        prev = null;
        next = null;
        data = null;
    }

    public CarListNode(Car initData){
        if(initData == null){
            throw new IllegalArgumentException();
        }
        prev = null;
        next = null;
        data = initData;
    }

    public Car getData(){
        return data;
    }

    public CarListNode getNext(){
        return next;
    }

    public CarListNode getPrev(){
        return prev;
    }

    public void setNext(CarListNode nextNode){
        next = nextNode;
    }

    public void setPrev(CarListNode prevNode){
        prev = prevNode;
    }
}
