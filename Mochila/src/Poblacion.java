
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * @author Mario, Alex
 * @version 07.08.14
 */
public class Poblacion {
    private ArrayList<Cromosoma> individuos;
    private double probabilidadMutacion;
    private double pesoMochila;
    private ArrayList<Articulo> articulos;
    private int fitnessTotal = 0;
    
    /**
     * Constructor de la clase
     * @param probabilidadMutacion
     * @param pesoMochila
     * @param articulos 
     */
    public Poblacion(double probabilidadMutacion,double pesoMochila, ArrayList<Articulo> articulos){
        this.probabilidadMutacion = probabilidadMutacion;
        this.pesoMochila = pesoMochila;
        this.articulos = articulos;
        individuos = new ArrayList<>();
    }
    /**
     * Genera la poblacion inicial de cromosomas
     * @param articulos 
     */
    public void generarPoblacionInicial(ArrayList<Articulo> articulos){
        int cantidadIndividuos = (int) Math.pow(2,articulos.size()); //todas las posibles combinaciones
        for(int i=0;i<cantidadIndividuos;++i){
            individuos.add( new Cromosoma(articulos, pesoMochila));
        }
    }
    /**
     * Agrega un nuevo cromosoma a la poblacion de cromosomas
     * @param cromosoma 
     */
    public void agregarIndividuo(Cromosoma cromosoma){
        individuos.add(cromosoma);
    }
    /**
     * Obtiene el cromosoma que se encuentra en la posicion i 
     * @param i
     * @return 
     */
    public Cromosoma getIndividuo(int i){
        return individuos.get(i);
    }
    
    /**
     * Obtiene la cantidad total de cromosomas de la poblacion
     * @return 
     */
    public int getCantidadIndividuos(){
        return individuos.size();
    }
    
    /**
     * Funcion que permite evaluar el fitness para determinar los posibles padres
     * @param tipoOptimizacion
     * @return 
     */
    public Poblacion evaluarAptitud(String tipoOptimizacion){
        Poblacion posiblesPadres = new Poblacion(probabilidadMutacion, pesoMochila, articulos);
        if(tipoOptimizacion.equalsIgnoreCase("Por cantidad")){ //Fitness en optimización por cantidad
            for(int i=0;i<individuos.size();++i){
                if(individuos.get(i).calcularFitnessCantidad()!=-1){
                    posiblesPadres.agregarIndividuo(individuos.get(i));
                }
            }
        }else{ //Calculo del fitness cuando se desea optimizar la utilidad
            for(int i=0;i<individuos.size();++i){
                if(individuos.get(i).calcularFitnessUtilidad()!=-1){
                    posiblesPadres.agregarIndividuo(individuos.get(i));
                }
            }
        }
        return posiblesPadres;
    }
    /**
     * Selecciona los padres a partir de un conjunto de posibles padres.
     * Se utiliza el algoritmo de selección por elitismo
     * @return 
     */
    public Poblacion seleccionarPadres(){
        Poblacion padres = new Poblacion( probabilidadMutacion, pesoMochila, articulos); 
        Collections.sort(individuos); //Se ordenan descendentemente de acuerdo al fitness
        for(int i=0;i<individuos.size()/2;++i){ //Se escoge la mitad de la población para ser padres
            padres.agregarIndividuo(individuos.get(i));
        }
        
        return padres;
    }
    /**
     * Realiza el envio de los padres que se van a cruzar 
     * @param probabilidadCruce
     * @return 
     */
    public Poblacion cruzar(double probabilidadCruce){
        Poblacion hijos = new Poblacion( probabilidadMutacion, pesoMochila, articulos); //por ruleta
        int hijosPorCruce = (int) (probabilidadCruce*individuos.size());
        int hijosClones = individuos.size() - hijosPorCruce; //Individuos que pasarán integros a la siguiente generación
        

        int j = 0;
        boolean paridad =(hijosPorCruce%2==0)? true:false;
        for(int i=0;i<hijosPorCruce/2;++i){
            Cromosoma papa = individuos.get(j);
            Cromosoma mama = individuos.get(j+1);
            Cromosoma hijo1 = cruce(papa,mama); //Primer hijo
            Cromosoma hijo2 = cruce(mama,papa); //Segundo hijo
            
            hijos.agregarIndividuo(hijo1);
            hijos.agregarIndividuo(hijo2);
            j += 2;
        }
        if(!paridad){ //Si el numero de padres no es par
            Cromosoma papa = individuos.get(j);
            Cromosoma mama = individuos.get(j+1);
            Cromosoma hijo1 = cruce(papa,mama);
            j++;
            hijos.agregarIndividuo(hijo1);
        }
        for(int i=0;i<hijosClones;++i){
            hijos.agregarIndividuo(individuos.get(j++));
        }
        return hijos;
    }
    /**
     * Realiza el cruce de los individuos que recibe como parametros
     * Selecciona el punto de cruce
     * @param papa
     * @param mama
     * @return 
     */
    public Cromosoma cruce(Cromosoma papa, Cromosoma mama){
        Cromosoma hijo = new Cromosoma(articulos,pesoMochila,0);
        String cromosoma1 = papa.getCromosoma();
        String cromosoma2 = mama.getCromosoma();
        String cromosomaHijo = "";
        int puntoCruce = cromosoma1.length()/2; //punto de cruce en la mitad del cromosoma
        cromosomaHijo += cromosoma1.substring(0,puntoCruce)+cromosoma2.substring(puntoCruce,cromosoma2.length());
        hijo.setCromosoma(cromosomaHijo);
        return hijo;
    }
    
    /**
     * Escoge una posicion al azar dentro del cromosoma e invierte su valor
     * @param probabilidadMutacion
     * @return 
     */
    public Poblacion mutar(double probabilidadMutacion){
        Poblacion hijosMutados = new Poblacion( probabilidadMutacion, pesoMochila, articulos);
        Random r = new Random(); 
        Random s = new Random();
        for(int i=0;i<individuos.size();++i){
            int randomNum = r.nextInt(101);
            if(randomNum<=(probabilidadMutacion*100)){
                int posicion = s.nextInt(individuos.get(0).getCromosoma().length());
                Cromosoma hijo = individuos.get(i); //Cromosoma que va a tener la mutacion
                String cromosoma = hijo.getCromosoma();
                char gen = (cromosoma.charAt(posicion)=='1')?'0' : '1'; //Posicion en que se dara la mutacion
                hijo.setCromosoma(cromosoma.substring(0,posicion)+gen+cromosoma.substring(posicion+1,cromosoma.length()));
                hijosMutados.agregarIndividuo(hijo);
            }else{
                hijosMutados.agregarIndividuo(individuos.get(i));
            }
        }
        return hijosMutados;
    }
    
   
}

