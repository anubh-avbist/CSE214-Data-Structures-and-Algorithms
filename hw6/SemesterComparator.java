/**
* Comparator class that is used to compare two classes by semester.
* Finds the semester of two given classes and compares them by year first, and then season (F/S), outputting a number indicating which value is "smaller" or "larger".
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/
import java.util.Comparator;

/**
 * Comparator class implements the Comparator interface which generally compares 2 objects.
 */
public class SemesterComparator implements Comparator{
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
        String leftSeason = leftCourse.getSemester().charAt(0) + "";
        String rightSeason = leftCourse.getSemester().charAt(0) + "";
        leftSeason = leftSeason.toLowerCase();
        rightSeason = rightSeason.toLowerCase();
        int leftYear = Integer.valueOf(leftCourse.getSemester().substring(1));
        int rightYear = Integer.valueOf(rightCourse.getSemester().substring(1));


        if(Integer.compare(leftYear,rightYear) == 0 ){
            if(leftSeason.equals('f')){
                return -1;
            } else if(rightSeason.equals('s')){
                return 1;
            } else {
                return 0;
            }
        } else {
            return Integer.compare(leftYear,rightYear);
        }



    }

}