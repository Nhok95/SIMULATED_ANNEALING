package IA.ProbIA5;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class ProbIA5SuccesorFunction implements SuccessorFunction{

    public List getSuccessors(Object state){
        ArrayList retval = new ArrayList();
        //aqui debemos crear nuevos estados y añadirlos a retval
        //basicamente uno para cada operación y cada posible cambio
        //quizás querremos descartar unos cuantos
        ProbIA5Board board= (ProbIA5Board)state;
        ProbIA5Board newboard = board.copyestat();
                //System.out.println("bucle1");

        //provarem a "reenganxar" cada sensor a un altre
        //OPERADORES1
        
        /*for(int i=board.numCentros();i<board.numNodos();i++){
            //obtenem el 30 percent als que estem disposats a reenganxar aquest
            //sensor, els mes propers
                            //System.out.println("bucle2 "+ board.get30perc(i).size());
            ArrayList<Integer> viables =board.get30perc(i);
            //for (int l = 0; l < viables.size(); l++) System.out.println("viable "+l+" de "+i+" :"+viables.get(l));
            
            //provem a crear una solucio per cadascun dels intercanvis
            for (Integer x: viables) {
                //System.out.println("viables size: "+ viables.size());
                
                //System.out.println("bucle3");
                //Instanciem
                //newboard=board.copyestat();
                //fem el canvi de pare corresponent, només si es pot
                //System.out.println("SUCCESSOR FUNCTION: (MiFADER): "+board.father(i)+"; (x): "+x+"; (i): "+i);
                if(newboard.change(board.father(i), x, i)) {
                    //System.out.println("i:" + i);
                    //System.out.println("+-+-+-+-+-+-+-+-+-+expansion correcta+-+-+-+-+-+-+-+-+");
                    retval.add(new Successor((new String 
                         (i +" cambio de padre de " +board.father(i)+ " a " +x+ " (h:" +newboard.heuristic()+")")
                                    ), newboard));
                }
               
            }
        }*/
        /*
        //OPERADORES2
        for (int i= board.numCentros(); i < board.numNodos(); i++){
                ArrayList<Integer> proximos =board.getclosest(i);
                for (Integer x: proximos) {
                //System.out.println("viables size: "+ viables.size());
                
                //System.out.println("bucle3");
                //Instanciem
                //newboard=board.copyestat();
                //fem el canvi de pare corresponent, només si es pot
                if(newboard.change(board.father(i), x, i)) {
                    retval.add(new Successor((new String 
                        (i +" cambio de padre de " +board.father(i)+ " a " +x + " (h:" +newboard.heuristic()+")")
                        ), newboard));
                }
               
            }
        }*/
        //OPERADORES SA
                //Instanciem
        //newboard=board.copyestat();
        //fem el canvi de pare corresponent, només si es pot
        Random rand = new Random();
        int noupare = rand.nextInt(board.numCentros());
        int fill = rand.nextInt(board.numNodos());
        while (fill < board.numCentros()) fill = rand.nextInt(board.numNodos());
        
        System.out.println("fill: "+ fill);
        System.out.println("pare: "+ board.father(fill));
        System.out.println("noupare: "+ noupare);
        
        if(newboard.change(board.father(fill), noupare, fill)) {
            System.out.println("+-+-+-+-+-+-+-+-+-+expansion correcta+-+-+-+-+-+-+-+-+");
            retval.add(new Successor((new String 
               (fill +" cambio de padre de " +board.father(fill)+ " a " +noupare + " (h:" +newboard.heuristic()+")")
                        ), newboard));
                }
               
        return retval;
    }

}
