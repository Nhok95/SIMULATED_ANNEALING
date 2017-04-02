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
    private ArrayList<Tree> children= new ArrayList();
    
    public Tree(Integer rootData) {
            this.root = rootData;
            this.children = new ArrayList();
    }

    public  Tree copy() {
        Tree b = new Tree(new Integer(this.root));
        for(Tree x :this.children){
            b.add(x.copy());
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
            System.out.println ("Nodo "+ this.root +"(size: " + children.size()+ ")  hijos:");
            for(Tree t : children) System.out.print (t.root+ " ");
            for(Tree t :children) t.print();
            
    }
    
    public Integer volume(){return this.root;}
    
    public Tree find(Integer a, Integer father){
        Tree x = null;
        if (this.root != null){
            //System.out.println ("+++++find: "+a);
            

            //System.out.println("mirando arbol " + this.getId() + " tamaño " + this.children());
            if(this.root.equals(a)) {
                //System.out.println ("mirando: "+this.root);
                x = this;
                return x;
            }//trobat
            else if (this.root.equals(father)){
                return null;
            }
            else {
                //System.out.println("children size: "+ this.children.size());
                //for(Tree t:this.children){
                if(this.children.size() > 0) {
                    x= this.children.get(0).find(a, father);
                    //System.out.println("x de "+this.children.get(0).getId()+" es:"+ x);
                    int i = 1;
                    while (x ==  null && i < this.children.size()) {
                        x= this.children.get(i).find(a, father);
                        i++;
                    }
                    //if (x == null) System.out.println("x es null");
                    //else System.out.println("mirando arbol" + x.getId());

                }
                //System.out.println("fi for");
            }
        }
        //System.out.println("+++++fin find");
        return x;
    }
    
        /*public Tree find(Integer a, int found){
            Tree t= find(a);
            
            if(t==null)found=0;
            else found = 1;
           // if(t != null && a.equals(37))System.out.println("querido booleano" +found);
            return t;
        }*/

    /*public Integer father(Integer a){
        for(Tree t: this.children)
            if(a.equals(t.getId())) return this.root;
        return null;//not found
    }*/
    
     public Integer father(Tree f, Integer fill){
         //System.out.println("soy "+fill+", BUSCO A MI FADEEER");
        Integer answer= null;
        for(Tree t: f.children){
            //System.out.println("SOY EL ARBOL: " + t.getId());
            
            if(fill.equals(t.getId())) {
               /* System.out.println("fill: "+fill+" equal "+t.getId());
                System.out.println("f.root: "+ f.getId());
                System.out.println();*/
                return f.getId();
            }
            else {
                //System.out.println("ELSABROSO");
                answer=father(t, fill);
                if (answer != null) return answer;
            }
        }
        //System.out.println("devuelvo: "+ answer);
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
        //System.out.println("--------------------");
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
                        ProbIA5Board.m_dist.get(this.root).get(this.children.get(i).getId());
            }
            //el volumen es como mucho 3 veces el suyo, o todo lo que recibe
            //System.out.println("mySum: " + mysum(myvol));
            volume=min(getCapacidad(root)*3,mysum(myvol)+getCapacidad(root));
        }
        /*System.out.println("nodo: " + this.root);
        System.out.println("volume: " + volume);
        System.out.println("cost: " + cost);*/
    }
    /**
    public void change(Integer pare,Integer noupare, Integer fill){
        Tree a= find(fill),b=find(noupare),c=find(pare);
        if(b.children.size()==2)return ;//no es pot fer
        //palante
        c.remove(fill);b.children.add(a);
    }**/
        public int mysump(ArrayList<Pair> a){
        Integer sum = 0;
        for(Pair x:a){
            sum= sum+ x.getVol();
            //System.out.println("sumando: "+ sum);
        }
        return sum;
    }
        
    //FUNCION PARA TREEE
    public Pair volumeandcostp(){
        //System.out.println("---------v&ctp-----------");
        //System.out.println("nodo: " + this.root);
        Pair p=new Pair();
        if (this == null) System.out.println ("ERROR FATAL!!");
        if(children().equals(0)){
            p.addVol(getCapacidad(this.root));
        }
        else{
            //System.out.println("---------else-----------");
            ArrayList<Pair> myvol= new ArrayList();
            for(int i=0;i<this.children();i++){
               /* System.out.println("children size: "+ children.size());
                System.out.println("iteration i: "+ i);
                System.out.println("children: " + children.get(i).root);*/
                
                myvol.add(children.get(i).volumeandcostp());                
                //System.out.println("valor despues de llamada recursiva del nodo"+ root+ " a " + i +" vol: " + myvol.get(i).getVol() + " cost: " + myvol.get(i).getCost());
                p.addCost(myvol.get(i).getCost()+ myvol.get(i).getVol()* //;cost=cost+mycost.get(i)+myvol.get(i)*
                        //square(ProbIA5Board.m_dist.get(this.root).get(this.children.get(i).getId())));
                        ProbIA5Board.m_dist.get(this.root).get(this.children.get(i).getId()));

            }
            //el volumen es como mucho 3 veces el suyo, o todo lo que recibe
            //System.out.println("mySum: " + mysump(myvol));
            if(this.root>=ProbIA5Board.numCentros)p.addVol(min(getCapacidad(root)*3,mysump(myvol)+getCapacidad(root)));//volume=;
            else p.addVol(mysump(myvol));
        }
        
        
        //System.out.println("volume("+ this.root+"): " + p.getVol());
        //System.out.println("cost("+this.root +"): " + p.getCost());
        return p;
    }
    
    Boolean putSensor(Tree loc, Integer nouSensor, boolean centro) {
        //System.out.println("Estoy en: "+loc.getId()+ "; centro= "+ centro);
        Boolean b = new Boolean(false);
        if (loc != null){
            //System.out.println("++++NO null++++");
            for (int k = 0; k < loc.children.size() && loc.children.size() >0 ; k++){
                //System.out.println("FILLO("+k+"): "+ loc.children.get(k).getId());
            }
            if (loc.children.isEmpty()) 
            {
                //System.out.println("LO PUTEO ");
                loc.add(new Tree(nouSensor));
                b = true;
            }
            else if (loc.children.size() == 1)
            {
                //System.out.println("Ya hay un hijo ");
                loc.add(new Tree(nouSensor));
                b = true;
            }
            else if (loc.children.size() == 2 && !centro) {
                //System.out.println("HIJITOS DE DIOS");
                b = false;
            }
            
            else { 
                for (int i = 0; i < loc.children() && !b ; i++){
                //System.out.println("i :" + i);
                b = putSensor(loc.children.get(i), nouSensor, false);
                //System.out.println("else "+ b);
                }
            }
            
          
        }
        //System.out.println("soy "+ b);
        return b;
    } 
    
}
