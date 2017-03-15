/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javier.lopez.segui
 */
public class Wire {
    
    private int length;///ESTO DEBERIA DE SER UN DOUBLE
  //guillem: Yo diría que esto lo calculemos en cuanto estemos computando 
    //una solución en si, me parece que a ojo no podemos determinar mucho de 
    //la largada de dos wires
    private int node1; //sensor
    private int node2; //sensor o centro 
    private boolean stos; //true sensor -> sensor false sensor -> centro 
    
    public Wire (int n1, int n2, int l, boolean stos){
        this.length=l;
        this.node1=n1;
        this.node2=n2;
        this.stos=stos;
    }
    
    public int getlength(){
    return this.length;
    }
    
    public int getnode1(){
    return this.node1;
    }
    
    public int getnode2(){
    return this.node2;
    }
    
    public boolean is_stos(){
    return stos;
    }
}
