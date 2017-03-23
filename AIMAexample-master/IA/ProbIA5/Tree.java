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
    /**
    public void change(Integer pare,Integer noupare, Integer fill){
        Tree a= find(fill),b=find(noupare),c=find(pare);
        if(b.children.size()==2)return ;//no es pot fer
        //palante
        c.remove(fill);b.children.add(a);
    }**/
    
}
