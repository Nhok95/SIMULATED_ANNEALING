/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IA.ProbIA5;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xarax
 */   
public class Tree {
        
    private Integer root;
    private ArrayList<Tree> children;
    
    public Tree(Integer rootData) {
            this.root = rootData;
            this.children = new ArrayList<Tree>();
    }

    public  Tree copy() {
        Tree b = new Tree(new Integer(this.root));
        for(Tree x :this.children){
            b.children.add(x.copy());
        }
        return b;
    }
 
    public Integer getId(){
        return this.root;
    }
    
    /////mia
    public Tree child(int c)
    {
        return this.children.get(c);
    }
    /////
    
    public void setId(Integer a){
        this.root= a;
    }
    public void remove(Integer ded){
        for(Tree t:this.children) 
            if(t.root.equals(ded))this.children.remove(t);
    }
    public void add(Tree baby){
        this.children.add(baby);
    }
    public Integer children(){return this.children.size();} 
    
    
    public Integer volume(){return this.root;}
    public Tree find(Integer a){
        if(this.root.equals(a)) return this;//trobat
        for(Tree t:this.children){
            Tree x= t.find(a);
            if(!! x.equals(-1)) return x;
        }
        
        return new Tree(new Integer(-1));
    }
    
    public Integer father(Integer a){
        for(Tree t: this.children)
            if(a.equals(t.getId())) return this.root;
        return -1;//not found
    }
    
    public Tree quickfind(Integer a){
        for(Tree t:this.children){
             if(t.root.equals(a)) return t;
        }
        return new Tree(new Integer(-1));
    }
    
    public Integer max(Integer a,Integer b){
        if(a>b) return b;
        else return a;
    }
    
    public Float max(Float a,Float b){
        if(a>b) return b;
        else return a;
    }
    
    public Integer mysum(ArrayList<Integer> a){
        Integer sum = new Integer(0);
        for(Integer x:a)sum=sum+x;
        return sum;
    }
    public Float square(Float x){return x*x;}
    
    public Integer getCapacidad(Integer i){
        return (ProbIA5Board.capacidades.get(i)).intValue();
    }
    
    public void volumeandcost(Integer volume,Float cost){
        if(this.children.equals(0)){
            volume=volume+getCapacidad(this.root);//tenemos el volumen
                    //cost=cost+getCapacidad(this.root);//y el coste
        }
        else{//tenemos subproblemas a resolver
            //necesitamos calcular para cada hijo
            ArrayList<Integer> mysol= new ArrayList(this.children());
            ArrayList<Float> mycost=new ArrayList(this.children());
            for(int i=0;i<this.children();i++){
                mysol.set(i, 0);
                mycost.set(i, new Float(0.0));
                this.children.get(i).volumeandcost(mysol.get(i), mycost.get(i));
                cost=cost+mycost.get(i)+mysol.get(i)*
                        square(ProbIA5Board.distances.get(this.root).get(this.children.get(i).getId()));
            }
            //el volumen es como mucho 3 veces el suyo, o todo lo que recibe
            volume=max(this.root*3,mysum(mysol)+this.root);
        }
    }
    /**
    public void change(Integer pare,Integer noupare, Integer fill){
        Tree a= find(fill),b=find(noupare),c=find(pare);
        if(b.children.size()==2)return ;//no es pot fer
        //palante
        c.remove(fill);b.children.add(a);
    }**/
    
}
