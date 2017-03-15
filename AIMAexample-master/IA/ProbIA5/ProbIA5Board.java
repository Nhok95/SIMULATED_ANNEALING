package IA.ProbIA5;

import IA.Red.CentrosDatos;
import IA.Red.Sensores;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bejar on 17/01/17.
 */
public class ProbIA5Board {
    /* Class independent from AIMA classes
       - It has to implement the state of the problem and its operators
     *

    /* State data structure
1 "tablero" para todo el problema, donde tenemos las posiciones de cada nodo
    */
     static public CentrosDatos centrosDatos;
    static public Sensores sensores;
    private Tree sol;

        
 
    public boolean is_goal(){
         // en principio no planteamos salirnos del 
         //espacio de soluciones en este problema 
         return true;
     }
 
    public ProbIA5Board(){
        this.centrosDatos= new CentrosDatos(4,1234);
        this.sensores= new Sensores(100, 1234);
    }
     // Some functions will be needed for creating a copy of the state
 
    public void setStart(){
        // solucion inicial
        this.sol= new Tree(-1);
    }
    
    public void setSol(Tree<Integer> a){
        this.sol= a;
    }
    
    public double heuristic(){
        // compute the number of coins out of place respect to solution
        double sum =0;
        //falta implementar cada uno de los heuristicos, uno que funcione
        //con suma, otro con una división, y quizás uno que los mezcle
        return sum;
    }
    
    public ProbIA5Board copyestat(){
        ProbIA5Board a = new ProbIA5Board();
        Tree<Integer> dif;
        dif = sol.copy();
        a.setSol(dif);
        return a;
    }
}