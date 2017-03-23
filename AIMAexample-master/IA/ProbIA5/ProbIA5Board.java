package IA.ProbIA5;

import IA.Red.Centro;
import IA.Red.CentrosDatos;
import IA.Red.Sensor;
import IA.Red.Sensores;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
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
    private ArrayList<Tree> sol;
    //ArrayList  conex;
    static  Integer numCentros=4;
    static Integer numSensores=100;
    //CODIFICACION : si el Integer a >= numcentros es un sensor.
    //se busca en sensores a-numcentros 
    //else se busca en centros
    static private ArrayList<ArrayList<Float> > distances;
    
    public void preparedistances(){
        //INSTANCIAMOS
        distances= new ArrayList<ArrayList<Float>>(numCentros+numSensores);
        for(int i=0;i<numCentros+numSensores;i++){
            ArrayList a= new ArrayList(numCentros+numSensores);
            for(int j=0;j<numCentros+numSensores;j++) a.add(0);
            distances.add(a);
        }
        //CALCULAMOS
        for(int i=0;i<numCentros+numSensores;i++){
            for(int j=0;j<numCentros+numSensores;j++)
                if(j>=numCentros){
                    (distances.get(i)).set(j, new Float(distance(i,j)));
                    (distances.get(j)).set(i,new Float(distance(i,j)));
                }
        }
    }
    
    public ArrayList<Integer> get30perc(int node){
        //WARNING ALGORITMO POCO EFICIENTE
        Integer size= new Integer(30*(numCentros+numSensores)/100);
        ArrayList<Float> closestd=new ArrayList(size);
        ArrayList<Integer> closest= new ArrayList(size);
        int i;
        //Cogemos los 30 primeros para tener alguno
        for(i=0;i<size;i++){
            closest.set(i, i);
            closestd.set(i,new Float(distance(i, node)) );
        }
        for(;i<numCentros+numSensores;i++){
            //y luego para cada uno miramos si podemo
            for(int j=0;j<size;j++){
                if(closestd.get(j)>distances.get(i).get(j)){//tenemos uno más cerca
                   closestd.set(j,distances.get(i).get(j));
                   closest.set(j,i);
                   break;//ya hemos cambiado algo por i
                }
            }
        }
        return closest;
    }
    
    public int numNodos(){
        return numCentros+numSensores;
    }
    public int numCentros(){
        return numCentros;
    }
    public boolean is_goal(){
         // en principio no planteamos salirnos del 
         //espacio de soluciones en este problema 
         return true;
     }
 
    public ProbIA5Board(Integer ncenters,Integer nsensores){
        numCentros=ncenters;
        distances=new ArrayList<ArrayList<Float> >(ncenters+nsensores);
        this.centrosDatos= new CentrosDatos(4,1234);
        this.sensores= new Sensores(100, 1234);
       // this.conex=new ArrayList();
        for(int i= 0; i<numCentros ;i++) this.sol.add(new Tree(0));
        //for(int i=0;i<104; i++)conex.add(0);
    }
     // Some functions will be needed for creating a copy of the state
 
    public void setStart(){
        // solucion inicial
        //Inte
        //for(int i= c)
    }
    
    public void setSol(ArrayList<Tree  > a){
        this.sol= a;
    }
    
    public double distance(int a, int sensor){
        if(numCentros < a){//a es un sensor
            Sensor s1=sensores.get(a-numCentros);
            Sensor s2=sensores.get(sensor-numCentros);
            return sqrt(pow(s1.getCoordX()-s2.getCoordX(),2) +pow(s1.getCoordY()-s2.getCoordY(),2));
        }else {// a es centro
            Centro s1= centrosDatos.get(a);
            Sensor s2=sensores.get(sensor-numCentros);
            return sqrt(pow(s1.getCoordX()-s2.getCoordX(),2) +pow(s1.getCoordY()-s2.getCoordY(),2));
        }
    }
    
    public double heuristic(){
        // compute the number of coins out of place respect to solution
        double sum =0;
        //falta implementar cada uno de los heuristicos, uno que funcione
        //con suma, otro con una división, y quizás uno que los mezcle
        return sum;
    }
    
    public ProbIA5Board copyestat(){
        ProbIA5Board a = new ProbIA5Board(numCentros,sensores.size());
        ArrayList<Tree> dif =new ArrayList<Tree  > ();
       for(Tree  t : sol )dif.add(t.copy());
        a.setSol(dif);
        return a;
    }
    
    public Integer father(Integer x){
        Integer a=-1;
        for(Tree t:sol){
             a=t.father(x);
             if(!! a.equals(-1))break;
        }
        return a;
    }
    
    public boolean change(Integer pare,Integer noupare, Integer fill){
        Tree father= new Tree(-1);
        Tree newfather = new Tree(-1);
        int i =0;
        while(newfather.getId().equals(-1)){
            newfather=sol.get(i).find(noupare);
        }
        i=0;
        if(newfather.children().equals(2)){
            return false;
        }
        while(father.getId().equals(-1)){
            father=sol.get(i).find(pare);
            i++;
        }
        newfather.add(father.quickfind(fill));//ho trobara a al primera iteracio 
        //aixi que es prou eficient
        father.remove(fill);//aixo tambe es directe
       return true;
    }
}