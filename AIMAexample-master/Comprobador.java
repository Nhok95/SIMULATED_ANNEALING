
import IA.Red.CentrosDatos;
import IA.Red.Sensores;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javier.lopez.segui
 */
public class Comprobador {
    
    private ArrayList<Integer> count;
    
    public Comprobador(int ns, int nc){ //numero de sensores + numero de centros
        count= new ArrayList<Integer>(ns+nc);
        for (Integer i:count) count.set(i,0);
    }
    
    public boolean checkwires(Wires W, CentrosDatos C, Sensores S){
        for (int i = 0; i < W.size(); ++i){
            Wire wir = W.GetWire(i);
            int id = wir.getnode1();
            count.set(id,count.get(id)+1);
            if (count.get(id) > 3) return nosolution();
            id = wir.getnode2();
            count.set(id,count.get(id)+1);
            if (wir.is_stos()){
                if (count.get(id) > 3) return nosolution();
            }
            else{
                if (count.get(id) > 25) return nosolution();
            }
        }
        return true;
    }
    
    private boolean nosolution(){
        for (Integer i:count) count.set(i,0);
        return false;
    }
    
}
