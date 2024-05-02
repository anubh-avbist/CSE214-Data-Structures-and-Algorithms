/**
* This class handles exceptions when the cursor reaches the end of a list, or is null.
* 
*    
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/


public class EndOfListException extends Exception{
    private String message;
    public EndOfListException(String e){
        message = e;
    }

    public String getMessage(){
        return message;
    }
}
