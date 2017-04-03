package IA.ProbIA5;

import IA.Red.Centro;
import IA.Red.CentrosDatos;
import IA.Red.Sensor;
import IA.Red.Sensores;
//import pruebas.prueba.PairIndexDist;
//import pruebas.prueba.pairComparator;

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
    static  Integer numCentros = 4;
    static Integer numSensores = 100;
    //CODIFICACION : si el Integer a >= numcentros es un sensor.
    //se busca en sensores a-numcentros 
    //else se busca en centros
    //static public ArrayList<ArrayList<Float> > distances;
    public double sumh = 50;
    Float cost;
    Integer vtotal;
    static public ArrayList<ArrayList<Integer> > closest;
    static public ArrayList<ArrayList<Float> > m_dist; //0-3 -> centros || 4-103 -> sensores
    
    static public ArrayList<Double> capacidades;//0-3 0(me va bien para calcular el volumen
    //lose demas son 1,2,5 en funcion de lo que sea

    
    public void insertsort(Integer i,Integer j, ArrayList<Integer> place){
        if(place.size()==0) place.add(j);
        else{
            int k=0;
            while (k < place.size() && m_dist.get(i).get(j) > m_dist.get(i).get(place.get(k))){
                k++;
            }
            place.add(k,j);
        }
    }
    
    public void calc_cercanos(){
        //Rellena closest con los nodos más cercanos para cada nodo
        closest=new ArrayList< ArrayList<Integer> >();
        for(int i=0;i<numCentros+numSensores;i++){
            closest.add(new ArrayList<Integer>());
            for(Integer j=0; j <numCentros+numSensores;j++){
                insertsort(i,j,closest.get(i));
            }
        }
        
        //comprobamos
        /*
        for (int i = 0; i < closest.size(); i++)
        {
            for (int j = 0; j < closest.get(0).size(); j++)
            {
                if (i <numCentros && j < numCentros) System.out.println ("closest de c"+i+": "+ closest.get(i).get(j));
                    else if (i < numCentros && j >= numCentros) System.out.println ("closest de c"+i+": "+closest.get(i).get(j));
                    else if (i >= numCentros && j < numCentros) System.out.println ("closest de s"+(i-numCentros)+": "+ closest.get(i).get(j));
                    else System.out.println ("closest de s"+(i-numCentros)+": "+ closest.get(i).get(j));
            }
            System.out.println();
        }
        */
        
    }
    
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
	private ArrayList<ArrayList<PairIndexDist>> distanciesOrdenades = new ArrayList<ArrayList<PairIndexDist>>(); //2a matriz ordenada
   

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
        public Float square(Float x){return x*x;}
        public void squaringdist(){
            for(ArrayList<Float> x : m_dist){
                for(Float f: x){
                    f=square(f);
                }
            }
        }

    public ArrayList<Integer> get30perc(int node){
        //WARNING ALGORITMO POCO EFICIENTE
        Integer size= new Integer(50*(numCentros+numSensores)/100);
        ArrayList<Integer> closest30= new ArrayList();
        //Cogemos el 30 % de primeros descartando el 1o(será el mismo)
        for(int i=1;i<size+1;i++){
            //closest30.add(new Integer(this.closest.get(i).get(node)));
            closest30.add(new Integer(this.closest.get(node).get(i)));
        }
        return closest30;
    }
    
    public ArrayList<Integer> getclosest(int node){
        return this.closest.get(node);
    }
    

    
    public int numNodos(){
        return numCentros+numSensores;
    }
    public int numCentros(){
        return numCentros;
    }
    

    
    
    public boolean is_goal(){
         // en principio no planteamos salirnos del 
         //espacio de soluciones en este problema 
         return true;
     }
 
    public ProbIA5Board(Integer ncenters,Integer nsensores){
        numCentros=ncenters;
        numSensores=nsensores;
       // distances=new ArrayList<ArrayList<Float> >(ncenters+nsensores);
        this.centrosDatos= new CentrosDatos(ncenters,1234);
        this.sensores= new Sensores(nsensores,4321);
       // this.conex=new ArrayList();
       //for(int i= 0; i<numCentros ;i++) this.sol.add(new Tree(0));
        this.capacidades= new ArrayList();
       int i = 0;
       for(; i<numCentros;i++)capacidades.add(0.0);
       for(;i<numCentros+numSensores;i++){
           capacidades.add(sensores.get(i-numCentros).getCapacidad());
       }
       
       /*for (int j= 0; j < numCentros+numSensores; j++)
       {
           System.out.println("cap("+j+"): " +capacidades.get(j));
       }*/
       
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
		for (int i = 0; i< numSensores+numCentros; ++i) {
			ArrayList<PairIndexDist> aux = new ArrayList<PairIndexDist>(numSensores+numCentros);
                        ArrayList<PairIndexDist> aux2 = new ArrayList<PairIndexDist>(numSensores+numCentros);//crea una fila de la matriz
			for (int j = 0; j < numCentros; ++j){
                                PairIndexDist P = new PairIndexDist();
				P.index = j;					  //la rellena con el indice original y la distancia de forma que los valores relevantes para definir un cable
				P.dist = distances.get(i).get(j); //son el indice de la fila y el valor contenido en el pair, llamado index.
                                aux.add(P);	
                        }
                   
                        for (int j = numCentros; j < numCentros+numSensores; ++j){
                                PairIndexDist P = new PairIndexDist();
				P.index = j;					  //la rellena con el indice original y la distancia de forma que los valores relevantes para definir un cable
				P.dist = distances.get(i).get(j); //son el indice de la fila y el valor contenido en el pair, llamado index.
				aux2.add(P);				
			}
                       
        
			Collections.sort(aux, new pairComparator()); 
                        Collections.sort(aux2, new pairComparator());//ordena cada fila segun el comparator d
                        aux.addAll(aux2);
			distanciesOrdenades.add(aux);  //finalmente a�ade cada fila a la matriz
		}
                
		return distanciesOrdenades;
	}
	
	private int NearFreeS2(int i1, int ns, int nc, ArrayList<Boolean> used, ArrayList<Integer> hijos) {
		for (int i= nc; i<nc+ns; ++i){
    		int i2 = distanciesOrdenades.get(i1).get(i).index;  // el for va de nc+1 (mas cercano) a nc+ns (mas lejano) comparando si estan libres
                //System.out.println(i2+" used " + used.get(i2)+ " hijos " + hijos.get(i2));
    		if (used.get(i2) == false && hijos.get(i2)<2) return i2; // used == true implica que solo se unira a un nodo que ya se haya tratado y este formando parte de un arbol
		}
		return -1;
        }
        private int NearFreeS3(int i1, int ns, int nc, ArrayList<Boolean> used, ArrayList<Integer> hijos) {
		for (int i= nc; i<nc+ns; ++i){
    		int i2 = distanciesOrdenades.get(i1).get(i).index;  // el for va de nc+1 (mas cercano) a nc+ns (mas lejano) comparando si estan libres
                //System.out.println(i2+" used " + used.get(i2)+ " hijos " + hijos.get(i2));
    		if (used.get(i2) == true && hijos.get(i2)<2) return i2; // used == true implica que solo se unira a un nodo que ya se haya tratado y este formando parte de un arbol
		}
		return -1;
	}
    
    private int NearFreeS(int i1, int ns, int nc, ArrayList<Integer> hijos) {
    	for (int i= nc; i<nc+ns; ++i){
    		int i2 = distanciesOrdenades.get(i1).get(i).index; // el for va de nc+1 (mas cercano) a nc+ns (mas lejano) comparando si estan libres
			if (i2 < i1 && hijos.get(i2)<2) return i2; //si esta por debajo (ya esta unido al arbol) y libre se le une
		}
		return -1;
	}

	private int NearFreeC(int i1, int nc, ArrayList<Integer> hijos) {
		for (int i= 0; i < nc; ++i){ 	// el for va de 0 (mas cercano) a nc (mas lejano) comparando si estan libres
			int i2 = distanciesOrdenades.get(i1).get(i).index;
			if (hijos.get(i2)<25) return i2; //si esta libre se le une
		}
		return -1;
	}
	
	
    public void init1(){
    	int nc = numCentros;
    	int ns = numSensores;
        sol = new ArrayList<Tree> (); //creamos un array 
    	for (int i =0; i< nc; ++i) sol.add(new Tree(i));
    	distanciesOrdenades = Ordenar(m_dist);  


	for (int i = 0; i < nc+ns;++i) hijos.add(0);
       	boolean saturados = false;
    	for(int i=nc;i<nc+ns;i++){
    		int n = 0;
    		if (! saturados) {
                    	n = NearFreeC(i,nc,hijos);  //-1 si no encuentra centros libres: centros saturados
    			saturados = (n == -1);
    		}
            if (saturados) n = NearFreeS(i,ns,nc,hijos); //solo debe buscar sensores por debajo de i
            //System.out.println(i+ " es hijo de: "+n);
            if(!saturados) sol.get(n).add(new Tree(i));
            else for (int j = 0; j< sol.size();++j){
               //int m = 0;
                if(i==195){
                 //   System.out.println("nodo que inserto "+  i+  "nodo donde inserto" + n);
                  //  System.out.println("dde busco"+ j);
                }
                
                Tree t = sol.get(j).find(n,null);
                //System.out.println("b = "+ m);
                if (t!=null){
                    t.add(new Tree(i));
                 //   System.out.println("HOLA" + t.getId());
                    break;
                }	
            }
            hijos.set(n, hijos.get(n)+1);
        }
        //printsol();
    }
    
    //COlocamos sensores x orden en el centro hasta rellenarlo, el resto de nodos se enganchan a los otros sensores
    public void init2(){
        
    	int nc = numCentros;
    	int ns = numSensores;
        
        /*System.out.println ("CAPACIDADES:");
        for (int x = 0; x <nc+ns; x++)
            System.out.println("cap"+x+": "+capacidades.get(x));*/
        
        sol = new ArrayList<Tree> (); //creamos un array 
        int i = 0;
    	for (; i< nc; ++i) sol.add(new Tree(i)); //añadimos los centros


        //Añadimos 25 sensores a cada centro
        int var = ns;
        for (int k = 0; k < nc; k++)
        {
            int tope = var;
            if (tope > 25) tope = 25;
            //System.out.println("tope: "+tope);
            for (int q = 0; q <tope; q++)
            {
                sol.get(k).add(new Tree(i));
                //System.out.println("ns: " +ns);
                //System.out.println("añadiendo sensor "+(i-nc)+ " al centro "+ k);
                i++;
            }
            var -= 25;
        }
        while (i < nc+ns) 
        {
            //System.out.println("This isn't even my final form");
           
            for (int j = 0; j <nc ;j++){  //centros
                boolean b = false;
                boolean centro = true;
                
                //System.out.println("j: "+j);
                //System.out.println("nc+ns: "+(nc+ns));
                Tree search = sol.get(0);
                for (int l = 0; l < sol.get(j).children() && !b; l++){ //hijos
                    //System.out.println("--i: "+i+ "; search: "+search.getId());
                    b = (sol.get(j).putSensor(search,i,centro));
                    if (b){
                        //System.out.println("--i: "+i+ "; b:"+b);
                        i++;
                        search = sol.get(j);
                        //System.out.println("--next i: "+i);
                    }
                    else {
                        //System.out.println("ELSEEEEEEEEEEEEEEEEEEEEEEEE");
                        search = sol.get(j).child(l);
                        centro = true;
                    }
                }
                //if (!b) System.out.println("****************************ESTOO SE VA A DESCONTROLAAAAAAR!!!!!");
                //printsol();
            }
            
            
        }
        printsol();
    }
    
    public void init3(){ //roundrobin de centros para escoger su mas cercano + sensor mas cercano cuando se saturan

	distanciesOrdenades = Ordenar(m_dist);
        int nc = numCentros;
    	int ns = numSensores;
        sol = new ArrayList<Tree> (); 
    	for (int i =0; i< nc; ++i) sol.add(new Tree(i));
        for (int i = 0; i < nc+ns;++i) hijos.add(0);
     	ArrayList<Boolean> used = new ArrayList<Boolean>();
        for (int i = 0; i < nc;++i)used.add(true);
        for (int i = 0; i < ns;++i)used.add(false);
    	Boolean done = false;
    	for(int i=0;i<25;++i){
    		for (int j = 0; j < nc; ++j){
    			int n = NearFreeS2(i,ns,nc,used,hijos);
    			done = (n == -1); 
    			if (done) break;
    			used.set(n,true);
    			sol.get(j).add(new Tree(n));
    			hijos.set(j, hijos.get(j)+1);
    		}
    		if (done) break;
    	}
    	int i = used.indexOf(false);
    	while ( i != -1 ){
    		int n = NearFreeS3(i,ns, nc,used,hijos);
                for (int j = 0; j < sol.size(); ++j){
                    Tree t = sol.get(j).find(n,null);
                    if (t!=null){
                        t.add(new Tree(i));
                        used.set(i, true);
                    }
                }
                //System.out.println(n);
                hijos.set(n, hijos.get(n)+1);
                i = used.indexOf(false);
    	}
    	//printsol();
    }
    
    
    
    public void printsol(){
        for(Tree t :sol)t.print();
    }
   
    


	/*public Map<Integer,Integer> Init2(int nc, int ns){

		distanciesOrdenades = Ordenar(m_dist);

		for (int i = 0; i < hijos.size();++i) hijos.set(i,0);
    	Map<Integer,Integer> firstSol = new TreeMap<Integer,Integer>();
    	ArrayList<Boolean> used = new ArrayList<Boolean>(ns);
    	Boolean done = false;
    	for(int i=ns;i<ns+nc;i++){
    		for (int j = 0; j < 25; ++j){
    			int n = NearFreeS2(i,ns,nc,used,hijos);
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
    		int n = NearFreeS2(i,ns, nc,used,hijos);
    		firstSol.put(i, n);
    		hijos.set(n, hijos.get(n)+1);
    		i = used.indexOf(false);
    	}
    	return firstSol;
    }*/

    
    public void setSol(ArrayList<Tree  > a){
        this.sol= a;
    }
    
    public double distance(int a, int sensor){
        if(numCentros > a && numCentros > sensor){ return Double.MAX_VALUE; }//dos centros
        else if(numCentros <= a && numCentros > sensor){//a es sensor,b es centro
            Centro s1= centrosDatos.get(sensor);
            Sensor s2=sensores.get(a-numCentros);
            return sqrt(pow(s1.getCoordX()-s2.getCoordX(),2) +pow(s1.getCoordY()-s2.getCoordY(),2));
        }
        else if(numCentros <= a && numCentros <= sensor){//a es un sensor b es sensor
            Sensor s1=sensores.get(a-numCentros);
            Sensor s2=sensores.get(sensor-numCentros);
            return sqrt(pow(s1.getCoordX()-s2.getCoordX(),2) +pow(s1.getCoordY()-s2.getCoordY(),2));
        }else {// a es centro   b es sensor
            Centro s1= centrosDatos.get(a);
            Sensor s2=sensores.get(sensor-numCentros);
            return sqrt(pow(s1.getCoordX()-s2.getCoordX(),2) +pow(s1.getCoordY()-s2.getCoordY(),2));
        }
    }
    
 
    public double heuristic(){
        // compute the number of coins out of place respect to solution
        sumh =0;
        
        //System.out.println("------heuristic-------");
        //falta implementar cada uno de los heuristicos, uno que funcione
        //con suma, otro con una división, y quizás uno que los mezcle
        cost = new Float(0);
        Pair p= new Pair();
        Integer v_util = new Integer(0);
        vtotal= new Integer(0);
        for (int i = 0; i< sol.size(); i++)
        {
            //System.out.println("preecost");
            //sol.get(0).print();
            
            p=sol.get(i).volumeandcostp();
            
            //System.out.println("----------------> arbol "+i+ ": *volumen: " +p.getVol()+", *coste: "+ p.getCost());
            //sol.get(0).print();
          
            
            vtotal=vtotal+p.getVol();
            cost=cost+p.getCost();
        }
        
        sumh = heuristic2(vtotal,cost); 
        
        
        //sum = heuristic3(vtotal,cost); 
        //System.out.println("suma total:" +sumh);
        //System.out.println("*********************************");
        //printsol();
        //System.out.println("*********************************");

        return sumh;
    }
    public int num_c_u(){
        int res = 0;
        for (int i = 0; i < sol.size(); i++){
            if (sol.get(i).children() >0) res++;
        }
        return res;
    }
    
    double geth(){
        return sumh;
    }
    int getvol(){
        return vtotal;
    }
    float getcost(){
        return cost;
    }
    
    public double heuristic2(Integer v_util, Float cost) { //coste
        Float k = new Float(100); // constante experimental
        //System.out.println("\n heuristic value: c->" +cost+"/ v->"+v_util);
        Double h2 = cost /(k* pow(v_util,3) ) ;
        return h2;
    }
   
   
    //
    public double heuristic3(Integer v_util, Float cost){
        Integer k = 100; //constante experimental
        double h4 = cost- k*pow(v_util,3) ;
        return h4;
    }
    
    public double heuristic4(Integer v_util, Float cost){
        int k = 100;
         double h3= pow(maxvol(),3)-pow(v_util,3) + (cost/k);
         return h3;
    }
    public int maxvol(){
        int x=numCentros*25*15;
        return x;
    }
    
    public ProbIA5Board copyestat(){
        ProbIA5Board a = new ProbIA5Board(numCentros,sensores.size());
        ArrayList<Tree> dif =new ArrayList();
       for(Tree  t : sol )dif.add(t.copy());
        a.setSol(dif);
        return a;
    }
    
    public Integer father(Integer fill){
        //System.out.println("--------------FATHER-GOD----------: " + fill);
        Integer a=null;
        for(Tree t:sol){
            //System.out.println("bucle for-> soy "+t.getId());
             a=t.father(t,fill);
             //System.out.println("fii father: "+ a);
             if(a!=null)break;
        }
        return a;
    }
    
    public boolean change(Integer pare,Integer noupare, Integer fill){
        
        //System.out.println ("----------> change ("+fill+" -> "+ pare+" to "+fill+" -> "+ noupare+").");
        if(fill.equals(noupare) || noupare.equals(pare)) {
            //System.out.println("SPARTAAAA!!!!!!!");
            return false;
        }
        
        /*System.out.println ("------> print antes1");
        sol.get(0).print();
        System.out.println ("------> print antes2");
        
        System.out.println ("noupare: "+noupare);
        System.out.println ("pare: "+ pare);*/
        Tree father= null;
        Tree newfather = null;
        int i =0;
        //System.out.println ( "noupare " +noupare + " fill " + fill);
        while(newfather==null && i <sol.size()){
            //System.out.println ("chivato1");
           // System.out.println("i: "+i);
            newfather=sol.get(i).find(noupare, fill);
            i++;
        }
        if (newfather != null) {
            //System.out.println("newfather: "+ newfather.getId());
            i=0;
            if(fill==noupare || fill == pare) System.out.println("ERROR FILL == PARES");
            if((noupare >= numCentros && newfather.children().equals(2)) ||
                    (noupare < numCentros && newfather.children().equals(25))){
                //System.out.println("childrens: "+newfather.children());
                //System.out.println("DOBLEEEEE IFFFFFFFFF");
                return false;
            }
            //System.out.println("GOD FATHER: " + father);
            while(newfather != null && father==null){
                //System.out.println ("chivato2");
                //System.out.println ("i " + i + " pare " +pare);
                /*if (i ==2) {
                    System.out.println("***********");
                    sol.get(2).print();
                }*/
                father=sol.get(i).find(pare, fill);

                i++;
            }
        
            //if (father.getId() == fill || newfather.getId() == fill) System.out.println("ERROR!!!!!!!!!!");
            //System.out.println("GOD FATHER2: " + father.getId());
            newfather.add(father.quickfind(fill));//ho trobara a al primera iteracio 
            //aixi que es prou eficient
            father.remove(fill);//aixo tambe es directe
            /*System.out.println ("------> print despues true");
            sol.get(0).print();
            System.out.println ("------> print despues true2");*/
            return true;
        }
        else {
            /*System.out.println ("------> print despues false");
            sol.get(0).print();
            System.out.println ("------> print despues false2");*/
            return false;
        }
    }
    
     

public void calc_dist()
    {
        int n_c = centrosDatos.size();
        //System.out.println("n_c¨: " + n_c);
        int n = n_c + sensores.size();
        //System.out.println("n¨: " + n);
        m_dist= new ArrayList<ArrayList<Float>>(n);
        float x_var = 0.0f;
        float y_var = 0.0f;
        for (int i = 0; i < n; i++)
        {
            m_dist.add(new ArrayList<Float>());
            for (int j = 0; j < n; j++)
            {
                //System.out.println("i¨: " + i + "; j : " + j );
                if (i < n_c && j < n_c){
                    //System.out.println("if");
                    x_var = (float)centrosDatos.get(i).getCoordX() - (float)centrosDatos.get(j).getCoordX();
                    y_var = (float)centrosDatos.get(i).getCoordY() - (float)centrosDatos.get(j).getCoordY();
                    
                }
                else if (i < n_c && j >= n_c){
                    //System.out.println("else if1");
                    x_var = (float)centrosDatos.get(i).getCoordX() - (float)sensores.get(j-n_c).getCoordX();
                    y_var = (float)centrosDatos.get(i).getCoordY() - (float)sensores.get(j-n_c).getCoordY();
                    
                }
                else if (i >= n_c && j < n_c){
                    //System.out.println("else if2");
                    x_var = (float)sensores.get(i-n_c).getCoordX() - (float)centrosDatos.get(j).getCoordX();
                    y_var = (float)sensores.get(i-n_c).getCoordY() - (float)centrosDatos.get(j).getCoordY();
                }
                else{
                    //System.out.println("else");
                    x_var = (float)sensores.get(i-n_c).getCoordX() - (float)sensores.get(j-n_c).getCoordX();
                    y_var = (float)sensores.get(i-n_c).getCoordY() - (float)sensores.get(j-n_c).getCoordY();
                }
                x_var = (float) x_var*x_var;
                y_var = (float) y_var*y_var;
                
                Float dist = new Float(sqrt(x_var + y_var));
                
                m_dist.get(i).add(dist*dist);
                //m_dist.get(i).add(dist);
            }
        }
        //Comprobamos
        /*for(int i=0;i<numCentros+numSensores;i++){
            for(int j=0;j<numCentros+numSensores;j++)
                {
                    if (i <n_c && j < n_c) System.out.println ("distancia de c"+i+" a c"+j+" : "+ m_dist.get(i).get(j));
                    else if (i < n_c && j >= n_c) System.out.println ("distancia de c"+i+" a s"+(j-n_c)+" : "+ m_dist.get(i).get(j));
                    else if (i >= n_c && j < n_c) System.out.println ("distancia de s"+(i-n_c)+" a c"+j+" : "+ m_dist.get(i).get(j));
                    else System.out.println ("distancia de s"+(i-n_c)+" a s"+(j-n_c)+" : "+ m_dist.get(i).get(j));
                }
            System.out.println();
        }*/
    }
}
    
    /**
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
    
    
    
    
    
    **/
