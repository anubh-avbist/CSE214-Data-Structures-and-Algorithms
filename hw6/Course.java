/**
* Defines a serializable class for courses.
* Each course has a name, number, and semester defined by a season (S/F) and year.
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/

import java.io.Serializable;

public class Course implements Serializable{
    private String department;
    private int number;
    private String semester;

    /**
     * Standard default constructors to initialize the department, number, and semester of the course.
     */
    public Course(){
        department = null;
        semester = null;
    }
    
    public Course(String d, int n, String s){
        department = d;
        number = n;
        semester = s;
    }


    // Standard getters and setters
    public String getDepartment() {
        return department;
    }
    public int getNumber() {
        return number;
    }
    public String getSemester() {
        return semester;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String toString(){
        return String.format("%-7s %-7s %-7s" ,getDepartment(), getNumber(), getSemester());
    }

}
