
import IA.Red.Sensor;
import IA.Red.Sensores;
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
    
    public int calclength (int n1,int n2, boolean stos){
        if(stos) {
            int x= Sensores.get(n1)  
        }
        
    }
    
}
