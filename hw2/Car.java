/**
* This class is used to create a Car object.
* Cars have only two attributes: a Make (defined by enum) and an owner (String). 
* There are getters/setters to access these attributes
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/

public class Car {
    private Make make;
    private String owner;

    public Car(){
        this.owner = null;
        this.make = null;
    }

    public Car(String owner, Make make){
        this.make = make;
        this.owner = owner;
    }

    public Make getMake(){
        return make;
    }

    public String getOwner(){
        return owner;
    }

    public void setOwner(String owner){
        this.owner = owner;
    }

    public void setMake(Make make){
        this.make = make;
    }

    public String toString(){
        String s = String.format("%-12s %-12s %n", make, owner);
        return s;
    }
}
