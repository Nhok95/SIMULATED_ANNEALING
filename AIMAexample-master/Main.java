import IA.ProbIA5.ProbIA5Board;
import IA.ProbIA5.ProbIA5GoalTest;
import IA.ProbIA5.ProbIA5HeuristicFunction;
import IA.ProbIA5.ProbIA5SuccesorFunction;
import IA.Red.CentrosDatos;
import IA.Red.Sensores;
import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

//import aima.search.informed.AStarSearch;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Main {

        private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
        
    }
    
    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }

    public static void main(String[] args) throws Exception{
        //CentrosDatos centrosDatos= new CentrosDatos(2,1234);
        //Sensores sensores= new Sensores(50, 4321);
        
        /*for (int i = 0; i < 1; i++)
        {
            System.out.println("c"+i+": x-> "+centrosDatos.get(i).getCoordX()+"; y->"+centrosDatos.get(i).getCoordY());
        }
        System.out.println();
        for (int i = 0; i < 4; i++)
        {
            System.out.println("s"+i+": x-> "+sensores.get(i).getCoordX()+"; y->"+sensores.get(i).getCoordY());
        }*/
        
        
         //la propia creadora te genera 4 centros y 100 sensores con seed 1234 
        ProbIA5Board board=new ProbIA5Board(4,100);
        
        //calculo de distancias (las dos funcionan bien)
        //board.preparedistances(); 
        board.calc_dist();
        
        board.calc_cercanos();
        
        //board.printdist();
        //board.printclose();
       
        //error en indice cuando accede a hijos
        //board.init1();
        //board.init2();
        board.init3();
        
        //board.squaringdist();
        //queda pendiente permitir usar SIM ANNEALING y HILL CLIMB Y ESCOGER
        Problem p = new  Problem(board,
                                new ProbIA5SuccesorFunction(),
                                new ProbIA5GoalTest(),
                                new ProbIA5HeuristicFunction());

                // Instantiate the search algorithm
	// SHA DE CANVIAR LA BÚSQUEDA PER HEURISTIC HILL CL
        Search alg = new SimulatedAnnealingSearch(4000,140,30,0.008);

        // Instantiate the SearchAgent object
        SearchAgent agent = new SearchAgent(p, alg);
        
        	// We print the results of the search
        System.out.println();
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());
        

        // You can access also to the goal state using the
	// method getGoalState of class Search


        

    }
}
