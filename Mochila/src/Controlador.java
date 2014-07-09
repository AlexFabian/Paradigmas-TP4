
import java.util.ArrayList;

/**
 * @author Mario, Alex
 * @version 07.08.14
 */
public class Controlador {
    ArrayList<Articulo> articulos;
    ArrayList<Cromosoma> mejoresCromosomas;
    double pesoMochila;
    String tipoOptimizacion;
    double probabilidadCruce;
    double probabilidadMutacion;
    Poblacion poblacion;
    
    /**
     * Constructor del controlador
     * @param articulos
     * @param pesoMochila
     * @param tipoOptimizacion
     * @param probabilidadCruce
     * @param probabilidadMutacion 
     */
    public Controlador(ArrayList<Articulo> articulos,double pesoMochila,String tipoOptimizacion,double probabilidadCruce, double probabilidadMutacion){
        this.articulos = articulos;
        this.tipoOptimizacion = tipoOptimizacion;
        this.probabilidadCruce = probabilidadCruce;
        this.probabilidadMutacion = probabilidadMutacion;
        this.poblacion = new Poblacion(probabilidadMutacion, pesoMochila,articulos);
        this.mejoresCromosomas = new ArrayList<>();
    }
    /**
     * Retorna los mejores cromosomas de todas las generaciones
     * @return mejores cromosomas
     */
    public ArrayList<Cromosoma> getMejoresCromosomas(){
        return mejoresCromosomas;
    }
    /**
     * Método principal. Realiza los pasos de un algoritmo genético
     */
    public void run(){
        /*Paso 1: Se genera la población inicial*/
        poblacion.generarPoblacionInicial(articulos); 
        
        /*Se imprime la población inicial*/
        System.out.println("Poblacion incial");
        for(int i=0;i<poblacion.getCantidadIndividuos();++i){
            System.out.print(poblacion.getIndividuo(i).getCromosoma()+"\t");
        }
        System.out.println();
        
        /*Paso 2: Se evalua la aptitud*/
        Poblacion posiblesPadres = poblacion.evaluarAptitud(tipoOptimizacion); 
        
        /*Paso 3: se crean las generaciones*/
        int generaciones = 0;
        while(generaciones < 20 && (posiblesPadres.getCantidadIndividuos() > 5 || generaciones == 0) ){ 
            /*Selección de padres*/
            Poblacion padres = posiblesPadres.seleccionarPadres();
            
            /*Reproducción de los padres*/
            Poblacion hijos = padres.cruzar(probabilidadCruce);
            
            /*Mutación de los hijos*/
            Poblacion hijosMutados = hijos.mutar(probabilidadMutacion);
            
            /*Reemplazo de individuos*/
            /*Se reemplazan los no-padres, por eso a los nuevos hijos se les agregan los padres*/
            for(int i=0;i<padres.getCantidadIndividuos();++i){
                hijosMutados.agregarIndividuo(padres.getIndividuo(i)); 
            }
            
            /*Evaluación de la aptitud*/
            posiblesPadres = hijosMutados.evaluarAptitud(tipoOptimizacion);
            
            /*Se imprimen los resultados de la generación*/
            System.out.println("\nNueva generación");
            for(int i=0;i<posiblesPadres.getCantidadIndividuos();++i){
                System.out.print(posiblesPadres.getIndividuo(i).getCromosoma()+"\t");
            }
            System.out.println();
            
            /*Se obtiene el mejor cromosoma de la generación*/
            Cromosoma mejorCromosoma = posiblesPadres.getIndividuo(0);
            for(int i=0;i<posiblesPadres.getCantidadIndividuos();++i){
                if(mejorCromosoma.getFitness() < posiblesPadres.getIndividuo(i).getFitness()){
                    mejorCromosoma = posiblesPadres.getIndividuo(i);
                }
                System.out.print(posiblesPadres.getIndividuo(i).getFitness()+"\t");
            }
            mejoresCromosomas.add(mejorCromosoma);
            ++generaciones;
        }
        /*Se imprimen los mejores cromosomas de todas las generaciones*/
        System.out.println();
        for(int i=0;i<mejoresCromosomas.size();++i){
            System.out.println("\nGeneración "+i+", mejor cromosoma: "+mejoresCromosomas.get(i).getCromosoma());
        }
    }
}
