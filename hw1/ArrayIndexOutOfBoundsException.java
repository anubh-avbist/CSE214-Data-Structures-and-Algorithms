/**
* This class handles out of bounds exceptions.
* 
*    
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/

public class ArrayIndexOutOfBoundsException extends Exception{
    int index;
    public ArrayIndexOutOfBoundsException(int i){
        index = i;
    }
}
