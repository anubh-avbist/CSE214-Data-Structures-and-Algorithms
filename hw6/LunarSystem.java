
/**
* This class runs the main method and allows the user to write log into the LunarSystem for class registration management.
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class LunarSystem {
    private static HashMap<String, Student> database;
    public static Scanner scanner;
    private static FileInputStream file;
    /**
     * Main method that starts the program and allows the user to log into a specific account.
     * Loads previous information if it exists, allows the user to save data that persists in a local file.
     * @param args
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException{
        scanner = new Scanner(System.in);
        database = new HashMap<>();
        
        System.out.println("Welcome to the Lunar System, a second place course registration system for second rate courses at a second class school.");
        
        /**
         * Serialization loading previous object
         */
        try {
            file = new FileInputStream("Lunar.ser");
            ObjectInputStream inStream = new ObjectInputStream(file);
            database = (HashMap) inStream.readObject();
            inStream.close();
            
        } catch (FileNotFoundException e) {
            database = new HashMap<>();
        }

        System.out.println("Menu:");
        System.out.println("L) Login");
        System.out.println("X) Save state and quit");
        System.out.println("Q) Quit without saving state.");
        boolean registrarLogged = false;

        boolean running = true;
        while(running){
            System.out.print("Please select an option: ");
            String input = scanner.nextLine();
            switch(input.toLowerCase()){
                case "l":
                    System.out.print("Please enter webid:");
                    String webID = scanner.nextLine();
                    if(webID.toLowerCase().equals("registrar")){
                        registrar();
                    } else if(database.containsKey(webID)){
                        System.out.println("Welcome " + database.get(webID).getWebID() + ".");
                        studentLogin(database.get(webID));
                    } else {
                        System.out.println("Could not find User");
                    }
                    break;
                case "q":
                    System.out.println("Good bye, please pick the right SUNY next time!");

                    File fileDelete = new File("Lunar.ser");
                    fileDelete.delete();
                    running = false;
                    break;
                case "x": 
                    /**
                     * Outputs the hashmap into a serialized file.
                     */
                    FileOutputStream file = new FileOutputStream("Lunar.ser");
                    ObjectOutputStream outStream = new ObjectOutputStream(file);
                    outStream.writeObject(database);
                    outStream.close();
                    System.out.println("System state saved, system shut down for maintenance");
                    running = false;
                default:
                    System.out.println("Please enter a valid input!");
                    break;
            }
            
        }

    }

    /**
     * This method is responsible for the input when a student logs in.
     * @param s
     */
    public static void studentLogin(Student s){
        System.out.println("Options:");
        System.out.println("A) Add a class");
        System.out.println("D) Drop a class");
        System.out.println("C) View your classes sorted by course name/department");
        System.out.println("S) View your courses sorted by semester");
        boolean running = true;
        while(running){
            System.out.print("Please select an option:");
            String input = scanner.nextLine();
            switch(input.toLowerCase()){
                case "a":
                    System.out.print("Please enter course department:");
                    String courseDepartment = scanner.nextLine();
                    System.out.print("Please enter course number:");
                    int courseNumber = Integer.valueOf(scanner.nextLine());
                    System.out.print("Please select a semester:");
                    String semester = scanner.nextLine();
                    if(semesterParse(semester).equals("Invalid")){
                        System.out.println("Please enter a valid semester in the form \'X1234\'");
                        break;
                    }
                    s.getCourses().add(new Course(courseDepartment.toUpperCase(), courseNumber, semester.toUpperCase()));
                    System.out.println("Added course " + courseDepartment + " " + courseNumber + " for semester " + semesterParse(semester));
                    break;
                case "c":
                    System.out.println(String.format("%-7s %-7s %-7s", "Dept.", "Course", "Semester"));
                    System.out.println("----------------");
                    Collections.sort(s.getCourses(), new CourseNameComparator());
                    for(Course c: s.getCourses()){
                        System.out.println(c.toString());
                    }
                    break;
                case "s":
                    System.out.println(String.format("%-7s %-7s %-7s", "Dept.", "Course", "Semester"));
                    System.out.println("----------------");
                    Collections.sort(s.getCourses(), new SemesterComparator());
                    for(Course c: s.getCourses()){
                        System.out.println(c.toString());
                    }
                    break;
                case "d":
                    System.out.print("Please enter course department: ");
                    String removedDepartment = scanner.nextLine().toUpperCase();
                    System.out.print("Please enter course number: ");
                    int removedNumber = Integer.valueOf(scanner.nextLine());
                    int removeCount = 0;
                    ArrayList<Integer> indices = new ArrayList<>();
                    for(int i = s.getCourses().size()-1; i >= 0; i--){
                        Course c = s.getCourses().get(i);
                        if(c.getDepartment().equals(removedDepartment) && c.getNumber() == removedNumber){
                            indices.add(i);
                            removeCount++;
                        }
                    }
                    for(int i = 0; i < indices.size(); i++){
                        s.getCourses().remove(indices.get(i));
                    }
                    System.out.println("Removed " + removeCount + " course(s) from student " + s.getWebID());
                    break;
                case "l":
                    System.out.println(s.getWebID() + " logged out");
                    running = false;
                    break;
                default:
                    System.out.println("Please enter a valid input!");
                    break;
            }
        }
    }

    /**
     * This method parses the short string of the form 'X1234' to give a readable string
     * indicating the season and year indicated by the semester.
     * @param sem
     * @return
     */

    public static String semesterParse(String sem){
        sem = sem.toLowerCase();
        String season = "";
        int year;

        if(sem.length() <= 1){
            return "Invalid";
        }
        String output = "";
        switch(sem.charAt(0)){
            case 'f':
                season = "Fall";
                break;
            case 's':
                season = "Spring";
                break;
            default:
                return "Invalid";
        }
        try{
            year = Integer.valueOf(sem.substring(1));
        } catch(Exception e){
            return "Invalid";
        }

        output = season + " " + year;
        return output;
    }


    /**
     * This method is responsible for the input from the admin/registrar.
     */
    public static void registrar(){
        
        System.out.println("Welcome Registrar");
        System.out.println("Options:");
        System.out.println("R) Register a student");
        System.out.println("D) De-register a student");
        System.out.println("E) View course enrollment");
        System.out.println("L) Logout");

        boolean running = true;
        while(running){
            System.out.print("Please select an option:");
            String input = scanner.nextLine();
            switch(input.toLowerCase()){
                case "e":
                    System.out.print("Please enter course department:");
                    String chosenCourse = scanner.nextLine().toUpperCase();
                    System.out.print("Please enter course number:");
                    int chosenNumber = Integer.valueOf(scanner.nextLine());
                    System.out.println("Students registered in " + chosenCourse);
                    System.out.println("Student   Semester");
                    System.out.println("--------------------");
                    for(String key: database.keySet()){
                        Student currentStudent = database.get(key);
                        // If student has course
                        for(int i = 0; i < currentStudent.getCourses().size(); i++){
                            Course currentCourse = currentStudent.getCourses().get(i);
                            if(currentCourse.getDepartment().equals(chosenCourse) && currentCourse.getNumber() == chosenNumber){
                                System.out.println(String.format("%-10s %-10s", currentStudent.getWebID(), currentCourse.getSemester()));
                            }
                        }
                    }
                    break;
                case "r":
                    System.out.print("Please enter a webid for the new student:");
                    String newWebID = scanner.nextLine();
                    database.put(newWebID, new Student(newWebID));
                    System.out.println(newWebID + " registered.");
                    break;
                case "d":
                    System.out.print("Please enter a webid for the student to be deregistered:");
                    String webId = scanner.nextLine();
                    if(database.containsKey(webId)){
                        database.remove(webId);
                        System.out.println(webId + " deregistered");
                    } else {
                        System.out.println("Error: Could not find student in database");
                    }
                    break;
                case "l":
                    running = false;
                    break;
                default:
                    System.out.println("Please enter a valid input!");
                    break;
            }
        }
        System.out.println("Registrar logged out.");
        
    }

    
}
