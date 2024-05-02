/**
* This class implements a Stack from java.util with a Package parameter.
* It allows packages to be pushed, popped, and peek'd at.
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/
import java.util.*;
public class PackageStack<Package> extends Stack<Package>{ 
    private final float CAPACITY = 7;
    private int numPackages = 0;
    private boolean isFloor = false;


    /**
     * The floor parameter bypasses the maximum capacity of the stack.
     * @param isFloor
     */
    public PackageStack(boolean isFloor){
        this.isFloor = isFloor;
    }

    public Package pop(){
        numPackages--;
        return super.pop();
    }

    public Package push(Package p){
        p = (Package) p;
        numPackages++;
        return super.push(p);
    }

    public Package peek(){
        return super.peek();
    }

    public boolean isEmpty(){
        return super.isEmpty();
    }

    public boolean isFull(){
        return numPackages == CAPACITY && !isFloor;
    }

    /**
     * The printable method uses the toString method from the Package class.
     * Uses a temporary method to iterate through the top of the stack and reverse the stack in the end.
     * 
     * @custom.postconditions: The stack remains unchanged after push/popping.
     */
    public String toString(){
        String s = "|";
        if(numPackages == 0){
            return "|empty.";
        } else {
            PackageStack<Package> tempStack = new PackageStack<>(true);
            int originalNumPackages = numPackages;
            
            for(int i = 0; i < originalNumPackages; i++){
                tempStack.push(this.pop());
            }
            for(int i = 0; i < originalNumPackages; i++){
                s+= "[" + tempStack.peek() + "]";
                this.push(tempStack.pop());
            }
        }
        return s;
    }

    /**
     * @returns the number of packages in the list in O(1).
     */
    public int getNumPackages(){
        return numPackages;
    }

}
