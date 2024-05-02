/**
* This is the main class where the lunch lines are created.
* The main mathod displays the UI menu and allows the user to manipulate the lunch lines.
*    
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/

import java.util.InputMismatchException;
import java.util.Scanner;

public class LunchLineSimulator {
    private static StudentLine realityA;
    private static StudentLine realityB;
    public static Scanner scanner = new Scanner(System.in);


    /**
     * Main method that executes the start of the program. Allows for user input
     * @param args - default input for main
     */
    public static void main(String[] args){
        realityA = new StudentLine("Reality A");
        realityB = new StudentLine("Reality B");
        StudentLine currentReality = realityA;

        System.out.println(
            "Welcome to the Middle School "
          + "where you are the master of the lunch line,"
          + "and you may subject your angsty kids to whatever"
          + "form of culinary torture best fits your mood."
          + "You are in Reality A.\n\n");
        System.out.println(
          "Menu:\n" +
          "  A) Add a student to the line at the end\n\n" +
          "  C) Have a new student cut a friend\n\n" +
          "  T) Have two students trade places\n\n" +
          "  B) Have the bully remove a student\n\n" + 
          "  U) Update a student's money amount\n\n" +
          "  S) Serve a student\n\n" +
          "  P) Print the current reality's lunch line\n\n" +
          "  O) Switch to the other reality\n\n" +
          "  E) Check if the realities are equal\n\n" +
          "  D) Duplicate this reality into the other reality\n\n" +
          "  Q) Quit middle school and move on to real life."
        );  
        
        boolean running = true;;
        String input;

        while(running){
            System.out.print("Please select an option: ");
            input = scanner.nextLine();
            try{
                switch(input.toLowerCase() + ""){
                case "a":{
                    /**
                     * Add a new student to the front of the line
                     */
                    Student newStudent = createStudent();
                    if(newStudent.getMoney()>=0){
                        currentReality.addStudent(currentReality.numStudents(), newStudent);
                        System.out.println(newStudent.getName() + " has been added to the line in position " + 
                            currentReality.numStudents() + ". " + newStudent.getName() + " has $" + String.format("%.2f",newStudent.getMoney()) + ".");
                    }
                    break; 
                } 
                case "c":
                    /**
                     * Add a student to a specific index
                     */
                    if(currentReality.numStudents() == 0) {
                        System.out.println("There are no students to cut!");
                        break;
                    }
                    Student newStudent = createStudent();
                    System.out.print("Please enter position: ");
                    int position = scanner.nextInt();
                    if(newStudent.getMoney()>=0){
                        currentReality.addStudent(position-1, newStudent);

                        if(currentReality.getStudent(position) == null ){
                            System.out.println("Since there was nobody to cut, " +
                                newStudent.getName() + " has been added to the end " +
                                "of the line, with $" + String.format("%.2f",newStudent.getMoney()) + "."
                            );
                        } else {
                            System.out.println(newStudent.getName() + " has cut " +
                              currentReality.getStudent(position).getName() + " and is now in position " +
                              position + ". " + newStudent.getName() + " has $" + String.format("%.2f",newStudent.getMoney()) + "."
                            );
                        }
                    }
                    scanner.nextLine();
                    break;
                case "t":
                    /**
                     * Swap two students at an index
                     */
                    System.out.print("Please enter student1 index: ");
                    int index1 = scanner.nextInt();
                    System.out.print("Please enter student2 index: ");
                    int index2 = scanner.nextInt();
                    if(index1 == index2){
                        System.out.println("You cannot trade places with yourself. The lunch line was not updated");
                        scanner.nextLine();
                        break;
                    }
                    currentReality.swapStudents(index1-1, index2-1);
                    System.out.println(currentReality.getStudent(index2-1).getName() + 
                      " has traded places with " + 
                      currentReality.getStudent(index1-1).getName() + "."
                    );
                    scanner.nextLine();
                    break;
                case "b": {
                    /**
                     *  Remove a specific index from a list
                     */
                    if(currentReality.numStudents() == 0){
                        System.out.println("There was no one in line for the bully to bully");
                        break;
                    }
                    System.out.print("Please enter student index: ");
                    int index = scanner.nextInt();
                    System.out.println("The bully has stolen " + currentReality.getStudent(index-1).getName() + "'s lunch money, and " +
                      currentReality.getStudent(index-1).getName() + " has left, feeling hangry.");
                    scanner.nextLine(); 
                    currentReality.removeStudent(index-1);
                    break;
                }
                case "u":{
                    /**
                     * Update the money of a specific student from the list
                     */
                    System.out.print("Please enter student index: ");
                    int index = scanner.nextInt();
                    System.out.print("Please enter new money amount: ");
                    double money = scanner.nextDouble();
                    if(money < 0) {
                        System.out.println("You can't have debt in middle school. The lunch line was not updated.");
                        scanner.nextLine();
                        break;
                    }
                    if(money == 0) {
                        System.out.println("The student has no money, so they left the line.");
                        scanner.nextLine();
                        currentReality.removeStudent(index-1);
                        break;
                    }
                    currentReality.getStudent(index-1).setMoney(money);
                    System.out.println("As a result of a shady transaction with the floor, " + currentReality.getStudent(index-1).getName() +
                      " now has $" + String.format("%.2f",money));
                      scanner.nextLine();
                    break;

                }
                case "p":
                    /**
                     * Print the lunch line of the current reality
                     */
                    String s = "Lunch Line in " + currentReality.realityName + ":";
                    System.out.println(s + currentReality.toString());
                    break;
                case "s":
                    /**
                     * Serve and remove the student at the front of the line
                     */
                    if(currentReality.numStudents()==0){
                        System.out.println("There are no students to serve lunch to. Mystery Meat Martha is sad");
                        break;
                    }
                    Student removedStudent = currentReality.getStudent(0);
                    currentReality.removeStudent(0);
                    System.out.println(removedStudent.getName() + " has been served today's special: Bouncy \"Chicken?\" Nuggets. We hope he lives to see another day!");
                    break;
                case "o":
                    /**
                     * Switch realities
                     */
                    if(currentReality.realityName == "Reality A"){
                        currentReality = realityB;
                    } else {
                        currentReality = realityA;
                    }
                    System.out.println("You are in " + currentReality.realityName + ". I reject your reality and substitute my own.");
                    break;
                case "q":
                    // Quit the program
                    running = false;
                    scanner.close();
                    System.out.println("You are now leaving the Middle School Lunch Line Simulator."  +
                      " We congratulate you on your decision to do something more productive with your time.");
                    break;
                case "e":
                    // Check if realities are equal
                    if(realityA.equals(realityB)){
                        System.out.println("The realities are equal.");
                    } else {
                        System.out.println("The realities are not equal.");

                    }
                    break;
                case "d":
                    /**
                     * Duplicate this reality into the other one
                     */
                    if(currentReality.realityName == "Reality A"){
                        realityB = currentReality.clone();;
                    } else {
                        realityA = currentReality.clone();
                    }
                    System.out.println(currentReality.realityName + " has been copied into " + currentReality.clone().realityName +".");
                    break; 
                default:
                    System.out.println("Please enter a valid single character from the menu!");
                    break;
                }
            } catch (InputMismatchException e){
                System.out.println("Please enter a number!");
                scanner.nextLine();
            } catch (InvalidArgumentException e) {
                System.out.println("That's an invalid index. The line was not updated");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("That's an invalid index. The line was not updated.");
                scanner.nextLine();
            } catch(DeanException e){
                System.out.println("You tried to add a student to a full lunch line. Dean Mean has " +
                  "picked up the student and given them a healthy dose of detention. Therefore, they will not be added to the lunch line");
            } catch (EmptyLineException e){
                System.out.println("There are no students in line!");
            }
        }
    }
    

    /**
     * Method for any instance where a brand new student needs to be created from the user's input.
     * 
     * @return new Student created based on user's input
     * @throws InputMismatchException only if the input for the money amount is not a real number.
     */
    public static Student createStudent() throws InputMismatchException{
        System.out.print("Please enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Please enter money amount: ");
        double money;
        money = scanner.nextDouble();
        if(money < 0) {
            System.out.println("You can't have debt in middle school. The lunch line was not updated.");
        }
        scanner.nextLine();
        return new Student(name, money);
    }
}
