/**
* This class handles exceptiosn if there are too many students.
* 20 students is the maximum in a line.
*    
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/
public class DeanException extends Exception{
    String studentName;
    public String getName(){
        return studentName;
    }
    public DeanException(String name){
        studentName = name;
    }
}
