package IA.ProbIA5;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bejar on 17/01/17.
 */
public class ProbIA5SuccesorFunction implements SuccessorFunction{

    public List getSuccessors(Object state){
        ArrayList retval = new ArrayList();
        ProbIA5Board board = (ProbIA5Board) state;

        // Some code here
        // (flip all the consecutive pairs of coins and generate new states
        for(int i= 0; i<5;i++){
            ProbIA5Board pepe = board.getCopy();
            pepe.flip_it(i);
            retval.add(new Successor(new String ("flip "+ Integer.valueOf(i).toString()) ,pepe));
        }
        
        // Add the states to retval as Succesor("flip i j, new_state)
        // new_state has to be a copy of state

        return retval;

    }

}
