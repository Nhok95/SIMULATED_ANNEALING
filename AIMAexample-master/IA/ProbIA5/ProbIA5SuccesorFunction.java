package IA.ProbIA5;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import java.util.ArrayList;
import java.util.List;


public class ProbIA5SuccesorFunction implements SuccessorFunction{

    public List getSuccessors(Object state){
        ArrayList retval = new ArrayList();
        //aqui debemos crear nuevos estados y añadirlos a retval
        //basicamente uno para cada operación y cada posible cambio
        //quizás querremos descartar unos cuantos
        ProbIA5Board board= (ProbIA5Board)state;
        ProbIA5Board board2=board.copyestat();
        retval.add(board2);
        return retval;
    }

}
