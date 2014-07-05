
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
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
        this.pesoMochila = pesoMochila;
        this.tipoOptimizacion = tipoOptimizacion;
        this.probabilidadCruce = probabilidadCruce;
        this.probabilidadMutacion = probabilidadMutacion;
        this.poblacion = new Poblacion(probabilidadMutacion, pesoMochila);
    }
    
    public void run(){
        poblacion.generarPoblacionInicial(articulos); //Paso 1
        Poblacion posiblesPadres = poblacion.evaluarAptitud(tipoOptimizacion); // Paso 2
        int generaciones = 0;
        /*System.out.println("Posibles padres");
        for(int i=0;i<posiblesPadres.getCantidadIndividuos();++i){
            System.out.print(i+"\t");
        }
        System.out.println();
        for(int i=0;i<posiblesPadres.getCantidadIndividuos();++i){
            System.out.print(posiblesPadres.getIndividuo(i).getCromosoma()+"\t");
        }*/
        while(generaciones < 100){ //Paso 3
            
            Poblacion padres = posiblesPadres.seleccionarPadres();
            
            /*System.out.println();
            for(int i=0;i<padres.getCantidadIndividuos();++i){
                System.out.print(padres.getIndividuo(i).getCromosoma()+"\t");
            }
            System.out.println();*/
            
            //System.out.println("\nHijos");
            Poblacion hijos = padres.cruzar(probabilidadCruce);
            
            /*System.out.println();
            for(int i=0;i<hijos.getCantidadIndividuos();++i){
                System.out.print(hijos.getIndividuo(i).getCromosoma()+"\t");
            }*/
            System.out.println("\nHijos mutados");
            Poblacion hijosMutados = hijos.mutar(probabilidadMutacion);
            for(int i=0;i<hijosMutados.getCantidadIndividuos();++i){
                System.out.print(hijosMutados.getIndividuo(i).getCromosoma()+"\t");
            }
            ++generaciones;
            posiblesPadres = hijosMutados;
        }
        /*System.out.println("Finales");
        for(int i=0;i<posiblesPadres.getCantidadIndividuos();++i){
            System.out.print(posiblesPadres.getIndividuo(i).getCromosoma()+"\t");
        }
        for(int i=0;i<posiblesPadres.getCantidadIndividuos();++i){
            System.out.print(posiblesPadres.getIndividuo(i).getFitness()+"\t");
        }*/
    }
}
