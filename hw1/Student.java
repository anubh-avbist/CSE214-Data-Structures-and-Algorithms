/**
* This class implements a new Student with a name and balance of money.
* Students can be cloned, compared for equality, and updated.
*    
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/

public class Student {

    private String name;
    private double money;


    /**
     * Method instantiates a new student with the specified name and money amount
     * 
     * @param name
     * @param money
     */
    public Student(String name, double money){
        this.name = name;
        money *= 100;
        money = Math.round(money);
        money /= 100;
        this.money = money;
    }

    public Student(){               
        this.name = "";
        this.money = 0;
    }

    /**
     * Getters and setters for name and money fields.
     */
    
    public String getName(){
        return name;
    }

    public double getMoney(){
        return money;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setMoney(double money){
        this.money = money;
    }


    public String toString(){
        String studentInfo = "Name: " + name + "\nMoney: "+ money;
        return studentInfo;
    }
    

    /**
     * Compare this Student to another object for equality.
     * @param obj - any object to be compared to Student
     * @custom.precondition - ensure if obj is of Student class
     */
    public boolean equals(Object obj){
        if(obj instanceof Student){
            return ((Student) obj).toString().equals(this.toString());
        }
        return false;
    }

    
    public Student clone(){
        return new Student(this.name, this.money);
    }

}
