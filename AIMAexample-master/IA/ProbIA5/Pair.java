package IA.ProbIA5;
 
/**
 *
 * @author xarax
 */
public class Pair {
    Integer vol;Float cost;
    public Pair (){this.vol=new Integer(0);this.cost=new Float(0);}
    public Integer getVol(){ return this.vol;}
    public Float getCost(){ return this.cost;}
    public void setVol(Integer x){this.vol=x ;}
    public void setCost(Float x){this.cost=x ;}
    public void addVol(Integer x){this.vol=this.vol+x;}
    public  void addCost(Float x){this.cost=this.cost+x;}
}