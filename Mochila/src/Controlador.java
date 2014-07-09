
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
    
    public Controlador(ArrayList<Articulo> articulos,double pesoMochila,String tipoOptimizacion,double probabilidadCruce, double probabilidadMutacion){
        this.articulos = articulos;
        this.tipoOptimizacion = tipoOptimizacion;
        this.probabilidadCruce = probabilidadCruce;
        this.probabilidadMutacion = probabilidadMutacion;
        this.poblacion = new Poblacion(probabilidadMutacion, pesoMochila,articulos);
        this.mejoresCromosomas = new ArrayList<>();
    }
    
    public ArrayList<Cromosoma> getMejoresCromosomas(){
        return mejoresCromosomas;
    }
    
    public void run(){
        poblacion.generarPoblacionInicial(articulos); //Paso 1
        System.out.println("Poblacion incial");
        for(int i=0;i<poblacion.getCantidadIndividuos();++i){
            System.out.print(poblacion.getIndividuo(i).getCromosoma()+"\t");
        }
        System.out.println();
        
        Poblacion posiblesPadres = poblacion.evaluarAptitud(tipoOptimizacion); // Paso 2
        /*System.out.println("\nPosibles padres");
        for(int i=0;i<posiblesPadres.getCantidadIndividuos();++i){
            System.out.print(posiblesPadres.getIndividuo(i).getCromosoma()+"\t");
        }
        System.out.println();*/
        int generaciones = 0;
        while(generaciones < 20 && posiblesPadres.getCantidadIndividuos() > 5 ){ //Paso 3
            /*Selección*/
            Poblacion padres = posiblesPadres.seleccionarPadres();
            /*System.out.println("\nPadres");
            for(int i=0;i<padres.getCantidadIndividuos();++i){
                System.out.print(padres.getIndividuo(i).getCromosoma()+"\t");
            }
            System.out.println();
            for(int i=0;i<padres.getCantidadIndividuos();++i){
                System.out.print(padres.getIndividuo(i).getFitness()+"\t");
            }
            System.out.println();*/
            
            /*Reproducción*/
            Poblacion hijos = padres.cruzar(probabilidadCruce);
            /*System.out.println("\nHijos");
            for(int i=0;i<hijos.getCantidadIndividuos();++i){
                System.out.print(hijos.getIndividuo(i).getCromosoma()+"\t");
            }
            System.out.println();
            for(int i=0;i<hijos.getCantidadIndividuos();++i){
                System.out.print(hijos.getIndividuo(i).getFitness()+"\t");
            }
            System.out.println();*/
            
            /*Mutación*/
            Poblacion hijosMutados = hijos.mutar(probabilidadMutacion);
            /*System.out.println("\nHijos mutados");
            for(int i=0;i<hijosMutados.getCantidadIndividuos();++i){
                System.out.print(hijosMutados.getIndividuo(i).getCromosoma()+"\t");
            }
            System.out.println();
            for(int i=0;i<hijosMutados.getCantidadIndividuos();++i){
                System.out.print(hijosMutados.getIndividuo(i).getFitness()+"\t");
            }
            System.out.println();*/
            
            /*Reemplazo*/
            /*Se reemplazan los peores, por eso a los nuevos hijos se les agregan los padres*/
            for(int i=0;i<padres.getCantidadIndividuos();++i){
                hijosMutados.agregarIndividuo(padres.getIndividuo(i)); 
            }
            /*Evaluación*/
            posiblesPadres = hijosMutados.evaluarAptitud(tipoOptimizacion);
            System.out.println();
            System.out.println("\nNueva generación");
            for(int i=0;i<posiblesPadres.getCantidadIndividuos();++i){
                System.out.print(posiblesPadres.getIndividuo(i).getCromosoma()+"\t");
            }
            System.out.println();
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
        System.out.println();
        for(int i=0;i<mejoresCromosomas.size();++i){
            System.out.println("\nGeneración "+i+", mejor cromosoma: "+mejoresCromosomas.get(i).getCromosoma());
        }
    }
}
