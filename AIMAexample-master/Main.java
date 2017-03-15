import IA.Red.CentrosDatos;
import IA.Red.Sensores;

public class Main {
    
    private int nsensores;
    private int ncentros;

    public static void main(String[] args) throws Exception{
        CentrosDatos C = new CentrosDatos(3,1234);
        Sensores S = new Sensores(20,1234);
        Wires W = new Wires()
        for (int i =0; i< 3;++i){ 
        System.out.println(C.get(i).getCoordX());
        System.out.println(C.get(i).getCoordY());
        }
        
        for (int i =0; i< 20;++i){ 
        System.out.println(S.get(i).getCoordX());
        System.out.println(S.get(i).getCoordY());
        System.out.println(S.get(i).getCapacidad());
        }
        
        
        
        

    }
}
