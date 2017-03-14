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
    public ProbIA5Board(int []init, int[] goal) {

        board = new int[init.length];
        solution = new int[init.length];

        for (int i = 0; i< init.length; i++) {
            board[i] = init[i];
            solution[i] = goal[i];
        }

    }

    /* vvvvv TO COMPLETE vvvvv */
    public void flip_it(int i){
        if ( i == board.length-1)
        {
            if (board[i] == 0) {
                board[i] = 1;
            }
            else board[i] = 0;
            if(board[0]==0)board[0]=1;
            else board[0]=0;
        } else 
        {
            if (board[i] == 0) board[i] = 1;
            else board[i] = 0;
            if (board[i+1] == 0) board[i+1] = 1;
            else board[i+1] = 0;
        }
        
        // flip the coins i and i + 1
        //modulo
    }

    /* Heuristic function */
    public double heuristic(){
        // compute the number of coins out of place respect to solution
        double sum =0;
        for( int i : board){
            if(board[i]!= solution[i])sum++;
        }
        return sum;
    }

     /* Goal test */
     public boolean is_goal(){
         // compute if board = solution
        for(int i=0; i<5;i++){
            if(board[i]!= solution[i])return false;
        }
         return true;
     }

     /* auxiliary functions */

     // Some functions will be needed for creating a copy of the state
     public ProbIA5Board getCopy(){
         int[] nuevo;
        nuevo = new int[5];
         for(int i=0;i<5;i++)nuevo[i]= board[i];
         ProbIA5Board n = new ProbIA5Board(nuevo, solution);
       return n;
     }
     

    /* ^^^^^ TO COMPLETE ^^^^^ */

}
