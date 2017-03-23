package IA.ProbIA5;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ProbIA5SuccesorFunction implements SuccessorFunction{

    public List getSuccessors(Object state){
        ArrayList retval = new ArrayList();
        //aqui debemos crear nuevos estados y añadirlos a retval
        //basicamente uno para cada operación y cada posible cambio
        //quizás querremos descartar unos cuantos
        ProbIA5Board board= (ProbIA5Board)state;
        ProbIA5Board newboard = board.copyestat();
        
        //provarem a "reenganxar" cada sensor a un altre
        for(int i=board.numCentros();i<board.numNodos();i++){
            //obtenem el 30 percent als que estem disposats a reenganxar aquest
            //sensor, els mes propers
            ArrayList<Integer> viables =board.get30perc(i);
            //provem a crear una solucio per cadascun dels intercanvis
            for (Integer x: viables) {
                //Instanciem
                newboard=board.copyestat();
                //fem el canvi de pare corresponent, només si es pot
                if(newboard.change(board.father(i), x, i)) retval.add(newboard);
            }
        }
        return retval;
    }

}
