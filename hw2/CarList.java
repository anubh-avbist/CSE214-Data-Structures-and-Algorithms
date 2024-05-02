/**
* This class creates a Doubly Linked list, dependent on CarListNode objects.
* The list allows access to the head and tail of a list, as well as a cursor object to traverse.
* The cursor is used to insert and remove nodes from the list.
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/
public class CarList {
    private CarListNode head;
    private CarListNode tail;
    private CarListNode cursor;
    private int carCount = 0;
    private String name;


    /**
     * Default constructor initializes head, tail, and cursor to null.
     */
    public CarList(){
        head = null;
        tail = null;
        cursor = null;
    }
    

    /**
     * Initializes CarList with a name for reference.
     * @param name
     */

    public CarList(String name){
        head = null;
        tail = null;
        cursor = null;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    /**
     * returns the number of cars in the list in O(1) time, as carCount is constantly updated.
     * @return carCount variable
     */

    public int numCars(){
        return carCount;
    }

    public CarListNode getCursor(){
        return cursor;
    }

    public Car getCursorCar() throws EndOfListException{
        if(cursor == null){
            throw new EndOfListException("The list is empty");
        }
        return cursor.getData();
    }

    public void resetCursorToHead(){
        cursor = head;
    }

    /**
     * Moves cursor forward in the list if possible.
     * @custom.preconditions: The cursor and its next node must be initialized.
     * @custom.postconditions: Sets the cursor to the next node in the list.
     * @throws EndOfListException
     */
    public void cursorForward() throws EndOfListException{
        if(cursor == null){
            throw new EndOfListException("cursorForwardNull");
        }
        if(cursor.getNext() == null){
            throw new EndOfListException("The cursor is at the tail of the list");
        }
        cursor = cursor.getNext();
    }

    /**
     * Moves cursor backwards in the list if possible.
     * @custom.preconditions: The cursor and its previous node must be initialized.
     * @custom.postconditions: Sets the cursor to the previous node in the list.
     * @throws EndOfListException
     */

    public void cursorBackward() throws EndOfListException{
        if(cursor == null){
            throw new EndOfListException("The cursor is at the tail of the list!");
        }
        if(cursor.getPrev() == null){
            throw new EndOfListException("The cursor is at the tail of the list!");
        }
        cursor = cursor.getPrev();
    }

    /**
     * Inserts a new car in the CarList before the cursor
     * 
     * @param newCar
     * @custom.preconditions: newCar cannot be null
     * @custom.postconditions: newCar is added to the list and references the cursor and the preivous car. The cursor remains unchanged.
     */
    public void insertBeforeCursor(Car newCar){

        if(newCar == null){
            throw new IllegalArgumentException();
        }

        CarListNode newCarNode = new CarListNode(newCar);
        if(cursor==null){
            head = newCarNode;
            tail = newCarNode;
            cursor = newCarNode;
        } else {
            if(cursor.getPrev() == null){
                newCarNode.setNext(head);
                head.setPrev(newCarNode);
                head = newCarNode;

            } else {
                cursor.getPrev().setNext(newCarNode);
                newCarNode.setPrev(cursor.getPrev());
                cursor.setPrev(newCarNode);
                newCarNode.setNext(cursor);
            }
        }
        carCount++;
    }

    /**
     * Appends a new car to the tail of the list.
     * 
     * @param newCar
     * @custom.preconditions: newCar cannot be null.
     * @custom.postconditions: The tail of the list is updated to the new car, and the previous tail points to newCar.
     */
    public void appendToTail(Car newCar){
        if(newCar == null){
            throw new IllegalArgumentException();
        }
        CarListNode newCarNode = new CarListNode(newCar);
        if(tail==null){
            head = newCarNode;
            tail = newCarNode;
            cursor = newCarNode;
        } else {
            newCarNode.setPrev(tail);
            tail.setNext(newCarNode);
            tail = newCarNode;
        }
        carCount++;
    }

    /**
     * Removes the CarListNode at the cursor from the list, having the adjacent nodes point to each other.
     * @return Returns the Car that was removed from the list.
     * @throws EndOfListException
     * @custom.preconditions: Cursor cannot be null
     * @custom.postconditions: cursor now points at the previous element in the list.
     */
    public Car removeCursor() throws EndOfListException{
        if(cursor == null){
            throw new EndOfListException("The cursor is at the tail of the list!");
        }
        Car removedCar = cursor.getData();
        if(cursor.getPrev() == null && cursor.getNext() == null){
            head = null;
            tail = null; 
            cursor = null;
            carCount--;
            return removedCar;
        } else if(cursor.getNext() == null){
            cursor.getPrev().setNext(null);
            tail = cursor.getPrev();
        } else if(cursor.getPrev() == null){
            cursor.getNext().setPrev(null);
            head = cursor.getNext();
        } else {
            cursor.getPrev().setNext(cursor.getNext());
            cursor.getNext().setPrev(cursor.getPrev());
        }
        if(cursor.getPrev() == null){
            cursor = head;
        } else {
            cursor = cursor.getPrev();
        }
        carCount--;
        return removedCar;
    }

    public String toString(){
        if(head == null){
            return "[empty]\n";
        }
        CarListNode current = head;
        String output = "";
        while(current!=null){
            if(current == cursor && this.getName().toLowerCase().equals("finished") == false){
                output += "->";
            }
            output += current.getData().toString();
            current = current.getNext();
        }
        return output;
    }

}
