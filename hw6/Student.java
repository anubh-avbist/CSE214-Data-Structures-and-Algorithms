/**
* Student class defines a student object, whose name is indicated by their webID.
* Additionally attaches a list of all the courses the student is assigned to.
* Implements the serializable interface so that the student's data can persist.
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/


import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable{
    private String webID;
    private ArrayList<Course> courses;

    // Default constructorsinitialize the list of courses to that it can be accessed and altered elsewhere.
    public Student(){
        this.webID = null;
        this.courses = new ArrayList<>();
    }
    
    public Student(String ID){
        this.webID = ID;
        this.courses = new ArrayList<>();
    }

    // Standard getters and setters
    public ArrayList<Course> getCourses() {
        return courses;
    }
    public String getWebID() {
        return webID;
    }
    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
    public void setWebID(String webID) {
        this.webID = webID;
    }
    
}