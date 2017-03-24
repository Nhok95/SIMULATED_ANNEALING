package IA.ProbIA5;

import IA.Red.Centro;
import IA.Red.CentrosDatos;
import IA.Red.Sensor;
import IA.Red.Sensores;
import pruebas.prueba.PairIndexDist;
import pruebas.prueba.pairComparator;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by bejar on 17/01/17.
 */
public class ProbIA5Board {
    /* Class independent from AIMA classes
       - It has to implement the state of the problem and its operators
     *

    /* State data structure
1 "tablero" para todo el problema, donde tenemos las posiciones de cada nodo
    */
     static public CentrosDatos centrosDatos;
    static public Sensores sensores;
    private ArrayList<Tree> sol;
    //ArrayList  conex;
    static  Integer numCentros=4;
    static Integer numSensores=100;
    //CODIFICACION : si el Integer a >= numcentros es un sensor.
    //se busca en sensores a-numcentros 
    //else se busca en centros
    static public ArrayList<ArrayList<Float> > distances;
    
    static private Float[][] m_dist; //0-3 -> centros || 4-103 -> sensores
    
	class PairIndexDist{  // struct de la que esta formada la 2a matriz
		public int index;
		public Float dist;
	}
	
	class pairComparator implements Comparator<PairIndexDist> //comparador para el sort de la 2a matriz
	   {            
	        public int compare(PairIndexDist p1, PairIndexDist p2)
	        {
	            Float a1 = p1.dist;
	            Float a2 = p2.dist;
	            return a1.compareTo(a2);
	        }
	   }
	


	private ArrayList<Integer> hijos = new ArrayList<Integer>(numSensores+numCentros); //lista de hijos de cada nodo
	private ArrayList<ArrayList<PairIndexDist>> distanciesOrdenades = new ArrayList<ArrayList<PairIndexDist>>; //2a matriz ordenada
    

public void calc_dist()
    {
        int n_c = centrosDatos.size();
        int n = n_c + sensores.size();
        float x_var = 0.0f;
        float y_var = 0.0f;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (i == j) m_dist[i][j] = 0.0f;
                else if (i < n_c && j < n_c){
                    x_var = (float)centrosDatos.get(i).getCoordX() - (float)centrosDatos.get(j).getCoordX();
                    y_var = (float)centrosDatos.get(i).getCoordY() - (float)centrosDatos.get(j).getCoordY();
                    
                }
                else if (i < n_c && j > n_c){
                    x_var = (float)centrosDatos.get(i).getCoordX() - (float)sensores.get(j-n_c).getCoordX();
                    y_var = (float)centrosDatos.get(i).getCoordY() - (float)sensores.get(j-n_c).getCoordY();
                }
                else if (i > n_c && j < n_c){
                    x_var = (float)sensores.get(i-n_c).getCoordX() - (float)centrosDatos.get(j).getCoordX();
                    y_var = (float)sensores.get(i-n_c).getCoordY() - (float)centrosDatos.get(j).getCoordY();
                }
                else{
                    x_var = (float)sensores.get(i-n_c).getCoordX() - (float)sensores.get(j-n_c).getCoordX();
                    y_var = (float)sensores.get(i-n_c).getCoordY() - (float)sensores.get(j-n_c).getCoordY();
                }
                x_var = (float) x_var*x_var;
                y_var = (float) y_var*y_var;
                
                m_dist[i][j] = (float) sqrt(x_var + y_var);
            }
        }
    }//

    //VOLUME Y COST DEBEN ESTAR INICIALIZADOS Y A 0!!!
    public void volumeandcost(Integer volume, Float cost){
        Integer nvol=new Integer(0);
        Float ncost=new Float(0);
        for(Tree t:sol ){
            t.volumeandcost(nvol, ncost);
            volume= volume+nvol;
            ncost=ncost+ ncost;
            nvol=0; ncost=(float)0;
        }

    }
    public void preparedistances(){
        //INSTANCIAMOS
        distances= new ArrayList<ArrayList<Float>>(numCentros+numSensores);
        for(int i=0;i<numCentros+numSensores;i++){
            ArrayList a= new ArrayList(numCentros+numSensores);
            for(int j=0;j<numCentros+numSensores;j++) a.add(0);
            distances.add(a);
        }
        //CALCULAMOS
        for(int i=0;i<numCentros+numSensores;i++){
            for(int j=0;j<numCentros+numSensores;j++)
                if(j>=numCentros){
                    (distances.get(i)).set(j, new Float(distance(i,j)));
                    (distances.get(j)).set(i,new Float(distance(i,j)));
                }
        }
    }
    
    public ArrayList<Integer> get30perc(int node){
        //WARNING ALGORITMO POCO EFICIENTE
        Integer size= new Integer(30*(numCentros+numSensores)/100);
        ArrayList<Float> closestd=new ArrayList(size);
        ArrayList<Integer> closest= new ArrayList(size);
        int i;
        //Cogemos los 30 primeros para tener alguno
        for(i=0;i<size;i++){
            closest.set(i, i);
            closestd.set(i,new Float(distance(i, node)) );
        }
        for(;i<numCentros+numSensores;i++){
            //y luego para cada uno miramos si podemo
            for(int j=0;j<size;j++){
                if(closestd.get(j)>distances.get(i).get(j)){//tenemos uno más cerca
                   closestd.set(j,distances.get(i).get(j));
                   closest.set(j,i);
                   break;//ya hemos cambiado algo por i
                }
            }
        }
        return closest;
    }
    
    public int numNodos(){
        return numCentros+numSensores;
    }
    public int numCentros(){
        return numCentros;
    }
    
    /////////////////////
    public double inorden_cost(Tree t)
    {
        double cost = 0;
        if (t == null)
            return 0;
        else
        {
            int id = t.getId();
            cost = get_cost(t.child(0),id);
            for (int i = 0; i < t.children(); i++)
                cost += get_cost(t.child(i),id);
        }
        return cost;
    }
    
    public double get_cost (Tree t, int id)
    {
        double cost = 0;
        if (t== null)
            return 0;
        else 
        {
            int n_id = t.getId();
            cost = get_cost(t.child(0),id);
            float d = m_dist[n_id][id]*m_dist[n_id][id];
            double v = sensores.get(n_id).getCapacidad();
            cost += d*v;
            for (int i = 0; i < t.children(); i++)
                cost += get_cost(t.child(i),id);
        }
        return cost;
    }
    
    public double inorden_v_tree(Tree t)
    {
        double v = 0;
        if (t == null)
            return 0;
        else 
        {
            //int id = t.getId();
            v = get_v(t.child(0), new Double (0.0));
            for (int i = 0; i < t.children(); i++)
                v += get_v(t.child(i),0.0);
                
        }
        return v;
        
    }
    public double get_v (Tree t, Double my_v)
    {
        double v = 0;
        
        if (t == null)
            return 0.0;
        else 
        {
            Double c_v =new Double (0.0);
            int n_id = t.getId();
            v = get_v (t.child(0),c_v);
            double v2 = sensores.get(n_id).getCapacidad();
            
            for (int i = 0; i <t.children(); i++)
            {
                v += get_v(t.child(i), 0.0);
            }
        
        }
        return v;
    }
    //////////////
    
    
    
    public boolean is_goal(){
         // en principio no planteamos salirnos del 
         //espacio de soluciones en este problema 
         return true;
     }
 
    public ProbIA5Board(Integer ncenters,Integer nsensores){
        numCentros=ncenters;
        distances=new ArrayList<ArrayList<Float> >(ncenters+nsensores);
        this.centrosDatos= new CentrosDatos(4,1234);
        this.sensores= new Sensores(100, 1234);
       // this.conex=new ArrayList();
        for(int i= 0; i<numCentros ;i++) this.sol.add(new Tree(0));
        //for(int i=0;i<104; i++)conex.add(0);
    }
     // Some functions will be needed for creating a copy of the state
 
    public void setStart(){
        // solucion inicial INCOMPLETA
        //engancho todos los sensores al centro mas cercano libre
        //o bien al sensor más cercano libre
        Integer cand=new Integer(-1);
        for(int i=0;i<numSensores;i++){
            for(int j=0; j<numCentros;j++){
                if(!! sol.get(j).children().equals(25)){
                //candidato
                    if(cand.equals(-1))cand=sol.get(j).getId();
                }
            }
        }
    }
    
	private ArrayList<ArrayList<PairIndexDist>> Ordenar(ArrayList<ArrayList<Float>> distances){
		ArrayList<ArrayList<PairIndexDist>> distanciesOrdenades = new ArrayList<ArrayList<PairIndexDist>>(numSensores);
		PairIndexDist P;
		for (int i = 0; i< numSensores+numCentros; ++i) {
			ArrayList<PairIndexDist> aux = new ArrayList<PairIndexDist>(numSensores+numCentros); //crea una fila de la matriz
			for (int j = 0; j < numSensores+numCentros; ++j){
				P.index = j;					  //la rellena con el indice original y la distancia de forma que los valores relevantes para definir un cable
				P.dist = distances.get(i).get(j); //son el indice de la fila y el valor contenido en el pair, llamado index.
				aux.add(P);				
			}
			Collections.sort(aux, new pairComparator()); //ordena cada fila segun el comparator d
			distanciesOrdenades.add(aux);  //finalmente a�ade cada fila a la matriz
		}
		return distanciesOrdenades;
	}
	
	private int NearFreeS2(int i1, int ns, ArrayList<Boolean> used, ArrayList<Integer> hijos) {
		for (int i= 0; i<ns; ++i){
    		int i2 = distanciesOrdenades.get(i1).get(i).index;  // el for va de 0(mas cercano) a ns (mas lejano) comparando si estan libres
    		if (used.get(i2) == true && hijos.get(i2)<2) return i2; // used == true implica que solo se unira a un nodo que ya se haya tratado y este formando parte de un arbol
		}
		return -1;
	}
    
    private int NearFreeS(int i1, int ns, ArrayList<Integer> hijos) {
    	for (int i= 0; i<ns; ++i){
    		int i2 = distanciesOrdenades.get(i1).get(i).index; // el for va de 0(mas cercano) a ns (mas lejano) comparando si estan libres
			if (i2 < i1 && hijos.get(i2)<2) return i2; //si esta por debajo (ya esta unido al arbol) y libre se le une
		}
		return -1;
	}

	private int NearFreeC(int i1, int ns, int nc, ArrayList<Integer> hijos) {
		for (int i= ns; i<ns+nc; ++i){ 	// el for va de ns+1 (mas cercano) a ns+nc (mas lejano) comparando si estan libres
			int i2 = distanciesOrdenades.get(i1).get(i).index; 
			if (hijos.get(i2)<25) return i2; //si esta libre se le une
		}
		return -1;
	}
	
	
    public Map<Integer,Integer> Init1(int nc, int ns){
    	distanciesOrdenades = Ordenar(distances);
		for (int i = 0; i < hijos.size();++i) hijos.set(i,0);
    	Map<Integer,Integer> firstSol = new TreeMap<Integer,Integer>();
    	boolean saturados = false;
    	for(int i=0;i<ns;i++){
    		int n = 0;
    		if (!! saturados) {
    			n = NearFreeC(i,ns,nc,hijos);  //-1 si no encuentra centros libres: centros saturados
    			saturados = (n == -1);
    		}
            if (saturados) n = NearFreeS(i,ns, hijos); //solo debe buscar sensores por debajo de i
            firstSol.put(i,n);							
            hijos.set(n, hijos.get(n)+1);
        }
    	return firstSol;
    }
    


	public Map<Integer,Integer> Init2(int nc, int ns){
		distanciesOrdenades = Ordenar(distances);
		for (int i = 0; i < hijos.size();++i) hijos.set(i,0);
    	Map<Integer,Integer> firstSol = new TreeMap<Integer,Integer>();
    	ArrayList<Boolean> used = new ArrayList<Boolean>(ns);
    	Boolean done = false;
    	for(int i=ns;i<ns+nc;i++){
    		for (int j = 0; j < 25; ++j){
    			int n = NearFreeS2(i,ns,used,hijos);
    			done = (n == -1); 
    			if (done) break;
    			used.set(n,true);
    			firstSol.put(n,i);
    			hijos.set(i, hijos.get(i)+1);
    		}
    		if (done) break;
    	}
    	int i = used.indexOf(false);
    	while ( i != -1 ){
    		int n = NearFreeS2(i,ns,used,hijos);
    		firstSol.put(i, n);
    		hijos.set(n, hijos.get(n)+1);
    		i = used.indexOf(false);
    	}
    	return firstSol;
    }

    
    public void setSol(ArrayList<Tree  > a){
        this.sol= a;
    }
    
    public double distance(int a, int sensor){
        if(numCentros < a){//a es un sensor
            Sensor s1=sensores.get(a-numCentros);
            Sensor s2=sensores.get(sensor-numCentros);
            return sqrt(pow(s1.getCoordX()-s2.getCoordX(),2) +pow(s1.getCoordY()-s2.getCoordY(),2));
        }else {// a es centro
            Centro s1= centrosDatos.get(a);
            Sensor s2=sensores.get(sensor-numCentros);
            return sqrt(pow(s1.getCoordX()-s2.getCoordX(),2) +pow(s1.getCoordY()-s2.getCoordY(),2));
        }
    }
    
    public double heuristic(){
        // compute the number of coins out of place respect to solution
        double sum =0;
        //falta implementar cada uno de los heuristicos, uno que funcione
        //con suma, otro con una división, y quizás uno que los mezcle
        return sum;
    }
    
    /*
    public double heuristic2() {
        
    }
    */
    
    //
    public double heuritic4( ArrayList<Tree> sol){
        double h4 = 0;
        double cost = 0;
        double v_util = 0;
        for (int i = 0; i< sol.size(); i++)
        {
            v_util += inorden_v_tree(sol.get(i));
            cost += inorden_cost(sol.get(i));
        }
        h4 = 2*v_util - cost; 
        return h4;
    }
    //
    
    public ProbIA5Board copyestat(){
        ProbIA5Board a = new ProbIA5Board(numCentros,sensores.size());
        ArrayList<Tree> dif =new ArrayList<Tree  > ();
       for(Tree  t : sol )dif.add(t.copy());
        a.setSol(dif);
        return a;
    }
    
    public Integer father(Integer x){
        Integer a=-1;
        for(Tree t:sol){
             a=t.father(x);
             if(!! a.equals(-1))break;
        }
        return a;
    }
    
    public boolean change(Integer pare,Integer noupare, Integer fill){
        Tree father= new Tree(-1);
        Tree newfather = new Tree(-1);
        int i =0;
        while(newfather.getId().equals(-1)){
            newfather=sol.get(i).find(noupare);
        }
        i=0;
        if((noupare >= numCentros && newfather.children().equals(2)) ||
                (noupare<numCentros && newfather.children().equals(25))){
            return false;
        }
        while(father.getId().equals(-1)){
            father=sol.get(i).find(pare);
            i++;
        }
        newfather.add(father.quickfind(fill));//ho trobara a al primera iteracio 
        //aixi que es prou eficient
        father.remove(fill);//aixo tambe es directe
       return true;
    }
}