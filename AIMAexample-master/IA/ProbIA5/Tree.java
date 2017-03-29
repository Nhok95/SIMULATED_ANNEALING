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
        Tree a= null;
        for(Tree t:this.children) 
            if(t.root.equals(ded)){ a=t ;break;}
        this.children.remove(a);
    }
    public void add(Tree baby){
        this.children.add(baby);
    }
    public Integer children(){return this.children.size();} 
    
    public void print(){
            System.out.println ();
            System.out.println ("Nodo "+ this.root +"  hijos:");
            for(Tree t : children)         System.out.print (t.root+ " ");
             for (Tree t:children) t.print();
            
    }
    
    public Integer volume(){return this.root;}
    
    public Tree find(Integer a){
        if(this.root.equals(a)) return this;//trobat
                    //System.out.println("mirando arbol" + this.getId() + "tamaño" + this.children());
        Tree x= null;
        for(Tree t:this.children){
            x= t.find(a);
           // System.out.println("mirando arbol" + x.getId());
            if(x != null) {
             //System.out.println(x.getId());
            return x;}
        }
        
        
        return x;
    }
    
        public Tree find(Integer a, int found){
            Tree t= find(a);
            
            if(t==null)found=0;
            else found = 1;
           // if(t != null && a.equals(37))System.out.println("querido booleano" +found);
            return t;
        }

    /*public Integer father(Integer a){
        for(Tree t: this.children)
            if(a.equals(t.getId())) return this.root;
        return null;//not found
    }*/
    
     public Integer father(Integer a){
        Integer answer= null;
        for(Tree t: this.children){
            if(a.equals(t.getId())) return this.root;
            else {
                answer=father(t.getId());
            }
        }
         return answer;
    }
    
    public Tree quickfind(Integer a){
        for(Tree t:this.children){
             if(t.root.equals(a)) return t;
        }
        return null;
    }
    
    public Integer min(Integer a,Integer b){
        if(a>b) return b;
        else return a;
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
        Integer cap = new Integer(ProbIA5Board.capacidades.get(i).intValue());
        //System.out.println("cap: "+ cap);
        return cap;
        //return (ProbIA5Board.capacidades.get(i)).intValue();
    }
    
    public void volumeandcost(Integer volume,Float cost){
        volume = new Integer(0);cost=new Float(0);
        if(children().equals(0)){
            volume=volume+getCapacidad(this.root);//tenemos el volumen
                    //cost=cost+getCapacidad(this.root);//y el coste
        }
        else{//tenemos subproblemas a resolver
            //necesitamos calcular para cada hijo
            ArrayList<Integer> myvol= new ArrayList();
            ArrayList<Float> mycost=new ArrayList();
            //System.out.println ("children:" +this.children() );
            for(int i=0;i<this.children();i++){
                myvol.add(new Integer(0));
                mycost.add(new Float(0.0));
                children.get(i).volumeandcost(myvol.get(i), mycost.get(i));
                cost=cost+mycost.get(i)+myvol.get(i)*
                        square(ProbIA5Board.m_dist.get(this.root).get(this.children.get(i).getId()));
            }
            //el volumen es como mucho 3 veces el suyo, o todo lo que recibe
            System.out.println("mySum: " + mysum(myvol));
            volume=min(getCapacidad(root)*3,mysum(myvol)+getCapacidad(root));
        }
        System.out.println("--------------------");
        System.out.println("nodo: " + this.root);
        System.out.println("volume: " + volume);
        System.out.println("cost: " + cost);
    }
    /**
    public void change(Integer pare,Integer noupare, Integer fill){
        Tree a= find(fill),b=find(noupare),c=find(pare);
        if(b.children.size()==2)return ;//no es pot fer
        //palante
        c.remove(fill);b.children.add(a);
    }**/
    
}
