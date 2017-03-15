package IA.ProbIA5;

/**
 * Created by bejar on 17/01/17.
 */
public class ProbIA5Board {
    /* Class independent from AIMA classes
       - It has to implement the state of the problem and its operators
     *

    /* State data structure
        vector with the parity of the coins (we can assume 0 = heads, 1 = tails
     */

    private int [] board;
    private static int [] solution;

    /* Constructor */
     static public CentrosDatos centrosDatos;
    static public Sensores sensores;
 
    public boolean is_goal(){
         // compute if board = solution
 
         return true;
     }
 
    public ProbIA5Board(){
        this.centrosDatos= new CentrosDatos(4,1234);
        this.sensores= new Sensores(100, 1234);
    }
     // Some functions will be needed for creating a copy of the state
 
         
 
    public double heuristic(){
        // compute the number of coins out of place respect to solution
        double sum =0;
        for( int i : board){
            if(board[i]!= solution[i])sum++;
        }
        return sum;
    }
}