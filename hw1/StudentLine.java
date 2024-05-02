/**
* This class implements Student Line.
* It stores a list of students in an array and allows their values to be manipulated.
* 
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/

public class StudentLine {
    public static final int CAPACITY = 20;
    public String realityName;
    private  Student[] students;
    private int studentCount;


    // Constructor for reality, including reality name
    StudentLine(String name){
        this.realityName = name;
        students = new Student[CAPACITY];
        studentCount = 0;
    }

    StudentLine(){
        this.realityName = "";
        students = new Student[CAPACITY];
        studentCount = 0;
    }

    // Returns number of students in line
    public int numStudents(){
        return studentCount;
    }

    /**
     * Finds student at index in line
     * @param index                             desired index of student
     * @return                                  returns student at index
     * @throws ArrayIndexOutOfBoundsException   if index isn't within the number of students in line
     * @throws EmptyLineException               if line is empty
     * @custom.precondition                     index must be valid within the student line bounds
     */
    public Student getStudent(int index) throws ArrayIndexOutOfBoundsException, EmptyLineException{
        if(studentCount == 0) {
            throw new EmptyLineException();
        }
        if(index < 0 || index >= studentCount){
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return students[index];
    }


    /**
     * Adds a new student to an index in line.
     * @param index                             index to add student at
     * @param student                           student object to be added to StudentLine
     * @throws ArrayIndexOutOfBoundsException   if index isn't within the number of students in line
     * @throws DeanException                    if the line is full
     * @throws InvalidArgumentException         if the index where the student is added would create a hole in the line
     * @custom.preconditions        Index must be valid in the number of students in the array, and within the maximum. Student should be instantiated.
     * @custom.postconditions       Number of students counter should be incremented, new Student object should be added to StudentLine object.
     */
    public void addStudent(int index, Student student) throws ArrayIndexOutOfBoundsException, DeanException,InvalidArgumentException {
        if(studentCount == 20){
            throw new DeanException (student.getName());
        }
        if(index < 0 || index > 20){
            throw new ArrayIndexOutOfBoundsException(index);
        }
        if(index > studentCount){
            throw new InvalidArgumentException();
        }
        Student temporaryStudent;
        for(int i = index; i < studentCount+1; i++){
            temporaryStudent = students[i];
            students[i] = student;
            student = temporaryStudent;
        }
        
        studentCount ++;
    
    }

    /**
     * Returns a print-able string containing all the information of the Student Line
     */
    public String toString(){
        String s = "";
        try{
            for(int i = 0; i < this.numStudents(); i++){
                s = s + "\n" + ((i + 1) + "." + 
                  this.getStudent(i).getName() + 
                  " $" + String.format("%.2f", this.getStudent(i).getMoney()) +
                  "");
            }
        } catch (EmptyLineException e){
            System.out.println("There are no students in line!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("That's an invalid index. The line was not updated.");
        }
        return s;
    }

    /**
     * determines whether the parameter o is equivalent to this StudentLine
     * 
     * @param o is the object compared to this Student Line
     * @return boolean indicating whether the two realities are equal
     */

    public boolean equals(Object o){
        if(((StudentLine) o) instanceof StudentLine){
            if(this.toString().equals(((StudentLine) o).toString())){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Removes student from the line without creating holes
     * 
     * @param index index which is removed from list
     * @return the removed student
     * @throws ArrayIndexOutOfBoundsException   if index isn't within the number of students in line
     * @throws EmptyLineException if the line is empty
     * @custom.preconditions ensure that index is within the number of students in the list
     * @custom.postconditions indicated student is removed and studentcounter decreases by one
     */
    public Student removeStudent(int index) throws ArrayIndexOutOfBoundsException, EmptyLineException{
        if(studentCount == 0){
            throw new EmptyLineException();
        }
        if(index < 0 || index >= studentCount){
            throw new ArrayIndexOutOfBoundsException(index);
        }
        Student removedStudent = students[index];
        for(int i = index; i < studentCount; i++){
            if(i == 19){
                students[19] = null;
            } else {
                students[i] = students[i+1];
            }
        }
        studentCount--;
        return removedStudent;
    }

    /**
     * Swaps the indices of 2 students in the list.
     * 
     * @param index1 - The first index to be swapped
     * @param index2 - The second index to be swapped
     * @throws EmptyLineException if the line is empty
     * @throws ArrayIndexOutOfBoundsException - If either of the indices are outside the scope of the number of students in line
     */
    public void swapStudents(int index1, int index2) throws EmptyLineException, ArrayIndexOutOfBoundsException{
        if(studentCount == 0) {
            throw new EmptyLineException();
        }
       
        if(index1 < 0 || index1 >= studentCount){
            throw new ArrayIndexOutOfBoundsException(index1);
        } 
        if( index2 < 0 || index2 >= studentCount){
            throw new ArrayIndexOutOfBoundsException(index2);
        }
        Student temporaryStudent = students[index1];
        students[index1] = students[index2];
        students[index2] = temporaryStudent;
    }

    /**
     * Deep clones the current StudentLine object and returns a new object with the same content values for all fields.
     * 
     * @return StudentLine class with a clone of the current lunch line.
     */
    public StudentLine clone(){
        StudentLine alternateReality;
        if(this.realityName == "Reality A"){
            alternateReality = new StudentLine("Reality B");
        } else {
            alternateReality = new StudentLine("Reality A");
        }
        for(int i = 0; i < 20; i++){
            if(i<this.numStudents()){
                try {
                    alternateReality.addStudent(i, ((Student)this.getStudent(i)).clone());
                } catch (InvalidArgumentException e) {
                    System.out.println("There are only " + this.numStudents() + " students in the line!");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("There is not student on index " + e.index + " of the line. Make sure you enter a number between 1 and the number of students!");
                } catch(DeanException e){
                    System.out.println("Uh oh! There are too many students... Dean Mean took " + e.getName() + " to detention!");
                } catch (EmptyLineException e){
                    System.out.println("There are no students in line!");
                }
            }
        }
        return alternateReality;
    }
}
