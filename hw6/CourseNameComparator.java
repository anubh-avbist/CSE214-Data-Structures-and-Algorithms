/**
* Comparator class that is used to compare two classes by name .
* Finds the name of two given classes and compares them by department, and then number, outputting a number indicating which value is "smaller" or "larger".
* Prioritizes name alphabetically, and the number is compared normally/numerically.
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/
import java.util.Comparator;

public class CourseNameComparator implements Comparator{

    /**
     * Comparator class implements the Comparator interface which generally compares 2 objects.
     */
    @Override
    public int compare(Object left, Object right){
        if(!(left instanceof Course) || !(right instanceof Course)){
            try {
                throw new Exception();
            } catch (Exception e) {
                return -2; // Exception!
            }
        }
        
        Course leftCourse = (Course) left;
        Course rightCourse = (Course) right;
        String leftName = ((Course) left).getDepartment().toLowerCase();
        String rightName = ((Course) right).getDepartment().toLowerCase();
        int leftNumber = leftCourse.getNumber();
        int rightNumber = rightCourse.getNumber();

        if(leftName.compareTo(rightName) == 0){
            if(leftNumber == rightNumber){
                SemesterComparator semesterComparator = new SemesterComparator();
                return semesterComparator.compare(leftName, rightName);
            } else {
                return Integer.compare(leftNumber, rightNumber);
            }
        } else {
            return leftName.compareTo(rightName);
        }


    }

}