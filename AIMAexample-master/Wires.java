
import IA.ProbIA5.ProbIA5Board;
import IA.Red.Sensor;
import IA.Red.Sensores;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javier.lopez.segui
 */
public class Wires { 
    //arboles y otras clases encontradas por el mundillo de internet
    public class Tree<Integer> {
        
        private Node<Integer> root;

        public Tree(Integer rootData) {
            root = new Node<Integer>();
            root.data = rootData;
            root.children = new ArrayList<Node<Integer>>();
        }
        public class Node<Integer> {
            private Integer data;
            private Node<Integer> parent;
            private List<Node<Integer>> children;
        }
    }
        
    
    //NUESTRA IMPLEMENTACION
    
    ProbIA5Board board;
    private ArrayList<Wire> W;
    private Tree<Integer> tree;
    public Wires(){
        W = new ArrayList<Wire>();
    }
    
    public Wires(ProbIA5Board boaard){
        W = new ArrayList<Wire>();
        this.board=boaard;//una util referencia al tablero
    }
    
    public int CreateWire(int n1, int n2, boolean stos){
        W.add(new Wire(n1,n2,calclength(n1,n2,stos),stos));
        return W.size() - 1;    
    }
    
    public void DeleteWire(int index){
        W.remove(index);
    }
        
    public Wire GetWire(int index){
        return W.get(index);
    
    }
        
    public int size() {
        return W.size();
    }
    
    //ESTO NO DEBERÍA SER UN DOUBLE?
    public int calclength (int n1,int n2, boolean stos){
        if(stos) {
            //int x= Sensores.get(n1)  
        }
        return 0;
        
    }
    
}
