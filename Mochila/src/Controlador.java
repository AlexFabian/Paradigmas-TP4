
import java.util.ArrayList;

/**
 * @author Mario, Alex
 * @version 07.08.14
 */
public class Controlador {
    
    ArrayList<Articulo> articulos;
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
    }
    
    public void run(){
        poblacion.generarPoblacionInicial(articulos); //Paso 1
        System.out.println("Poblacion incial");
        for(int i=0;i<poblacion.getCantidadIndividuos();++i){
            System.out.print(poblacion.getIndividuo(i).getCromosoma()+"\t");
        }
        System.out.println();
        
        Poblacion posiblesPadres = poblacion.evaluarAptitud(tipoOptimizacion); // Paso 2
        System.out.println("\nPosibles padres");
        for(int i=0;i<posiblesPadres.getCantidadIndividuos();++i){
            System.out.print(posiblesPadres.getIndividuo(i).getCromosoma()+"\t");
        }
        System.out.println();
        int generaciones = 0;
        //while(generaciones < 100){ //Paso 3
            /*Selección*/
            Poblacion padres = posiblesPadres.seleccionarPadres();
            System.out.println("\nPadres");
            for(int i=0;i<padres.getCantidadIndividuos();++i){
                System.out.print(padres.getIndividuo(i).getCromosoma()+"\t");
            }
            System.out.println();
            for(int i=0;i<padres.getCantidadIndividuos();++i){
                System.out.print(padres.getIndividuo(i).getFitness()+"\t");
            }
            System.out.println();
            
            /*Reproducción*/
            Poblacion hijos = padres.cruzar(probabilidadCruce);
            System.out.println("\nHijos");
            for(int i=0;i<hijos.getCantidadIndividuos();++i){
                System.out.print(hijos.getIndividuo(i).getCromosoma()+"\t");
            }
            System.out.println();
            
            /*Mutación*/
            System.out.println("\nHijos mutados");
            Poblacion hijosMutados = hijos.mutar(probabilidadMutacion);
            for(int i=0;i<hijosMutados.getCantidadIndividuos();++i){
                System.out.print(hijosMutados.getIndividuo(i).getCromosoma()+"\t");
            }
            System.out.println();
            
            /*Reemplazo*/
            posiblesPadres = hijosMutados;
            for(int i=0;i<padres.getCantidadIndividuos();++i){
                posiblesPadres.agregarIndividuo(padres.getIndividuo(i));
            }
            System.out.println();
            System.out.println("\n nueva generacion invalida");
            for(int i=0;i<posiblesPadres.getCantidadIndividuos();++i){
                System.out.print(posiblesPadres.getIndividuo(i).getCromosoma()+"\t");
            }
            posiblesPadres = posiblesPadres.evaluarAptitud(tipoOptimizacion);
            System.out.println("\nNueva generación");
            for(int i=0;i<posiblesPadres.getCantidadIndividuos();++i){
                System.out.print(posiblesPadres.getIndividuo(i).getCromosoma()+"\t");
            }
            
            
            ++generaciones;
        //}
        /*System.out.println("Finales");
        for(int i=0;i<posiblesPadres.getCantidadIndividuos();++i){
            System.out.print(posiblesPadres.getIndividuo(i).getCromosoma()+"\t");
        }
        for(int i=0;i<posiblesPadres.getCantidadIndividuos();++i){
            System.out.print(posiblesPadres.getIndividuo(i).getFitness()+"\t");
        }*/
    }
}
