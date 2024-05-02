/**
* This class is used for randomly picking a Ride from an array of rides.
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/


public class RandomGenerator{

    public static Ride[] rides = {new Ride("One", 0, 3,0), new Ride("Four", 5, 3,3), new Ride("Three", 5, 3,2), new Ride("Two", 5, 3,1)};

    /**
     * Default ride selection that returns a ride with equal probability for any ride.
     * @param rides - Input array of rides
     * @return One ride from a uniform probability.
     */
    public static Ride selectRide(Ride[] rides){
        return rides [(int) Math.floor(Math.random()*rides.length)];
    }

    /**
     * Allows to input a probability distribution that returns a ride based on weighted probabilities.
     * @param rides
     * @param probabilities The probability distribution (which must sum to 1 and have no negative probabilities)
     */
    public static Ride selectRide(Ride[] rides, double[] probabilities){

        // 0 -> prob[0] : ride 1
        // prob[0] -> prob[0] + prob[1] : ride 2
        // prob[0] + prob[1] -> prob[0] + prob[1] + prob[2] : ride 2
        // prob[0] + prob[1] + prob[2] -> prob[0] + prob[1] + prob[2] + prob[3] : ride 3

        double randomNum = Math.random();
        double cumulativeProbability = 0;
        int i = 0;
        for(i = 0; i < probabilities.length; i++){
            if(cumulativeProbability<=randomNum && randomNum <= cumulativeProbability+probabilities[i]){
                break;
            }
            cumulativeProbability+=probabilities[i];
        }
        return rides[i];
    }
}