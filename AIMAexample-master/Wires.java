
import java.util.ArrayList;

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
    
    private ArrayList<Wire> W;
    
    public Wires(){
        W = new ArrayList<Wire>();
    }
    
    public int CreateWire(int n1, int n2){
        W.add(new Wire(n1,n2,calclength(n1,n2)));
        return W.size() - 1;    
    }
    
    public void DeleteWire(int index){
        W.remove(index);
    }
        
    public Wire GetWire(int index){
        return W.get(index);
    
    }
    
    public void SwapWire(int index1, int index2){
        Wire aux = W.get(index1);
        W.set(index1, W.get(index2));
        W.set(index2, aux);
    }
    
}
