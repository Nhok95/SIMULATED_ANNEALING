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
    
    private int length;
    private int node1;
    private int node2;
    
    public Wire (int n1, int n2, int l){
        this.length=l;
        this.node1=n1;
        this.node2=n2;
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
}
