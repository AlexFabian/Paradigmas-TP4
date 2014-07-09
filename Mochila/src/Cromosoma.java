
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Mario, Alex
 * @version 07.08.14
 */
public class Cromosoma implements Comparable<Cromosoma>{
    private String cromosoma = "";
    private double fitness = 0;
    private double pesoMochila = 0;
    private double pesoTotal = 0;
    private ArrayList<Articulo> articulos;
    
    /**
     * Constructor de cromosomas para la población inicial
     * Crea cromosomas con genes aletorios
     * @param articulos
     * @param pesoMochila 
     */
    public Cromosoma(ArrayList<Articulo> articulos, double pesoMochila){
        this.articulos = articulos;
        this.pesoMochila = pesoMochila;
        for(int i=0;i<articulos.size();++i){
            Random r = new Random();
            cromosoma += r.nextInt(2); //Se inicializan al azar, pueden ser 1 ó 0
        }
    }
    
    /**
     * Constructor de cromosomas para poblaciones siguientes
     * @param articulos
     * @param pesoMochila
     * @param dummy 
     */
    public Cromosoma(ArrayList<Articulo> articulos, double pesoMochila, int dummy){
        this.articulos = articulos;
        this.pesoMochila = pesoMochila;
    }
    /**
     * Retorna el peso total que ese cromosoma representa
     * @return peso total del cromosoma
     */
    public double getPesoTotal(){
        return pesoTotal;
    }
    /**
     * Retorna el fitness del cromosoma
     * @return fitness del cromosoma
     */
    public double getFitness(){
        return fitness;
    }
    /**
     * Establece el fitness del cromosoma
     * @param fitness 
     */
    public void setFitness(double fitness){
        this.fitness = fitness;
    }
    /**
     * Obtiene la combinación de genes del cromosoma
     * @return combinación de genes
     */
    public String getCromosoma(){
        return cromosoma;
    }
    /**
     * Establece la combinación de genes del cromosoma
     * @param cromosoma 
     */
    public void setCromosoma(String cromosoma){
        this.cromosoma = cromosoma;
    }
    /**
     * Retorna el artículo que representa un gen determinado
     * @param i
     * @return artículo
     */
    public Articulo getArticulo(int i){
        return articulos.get(i);
    }
    /**
     * Calcula el fitness con base en la utilidad de los artículos
     * @return fitness del cromosoma
     */
    public double calcularFitnessUtilidad(){
        fitness = 0;
        for(int i=0;i<cromosoma.length();++i){
            if(cromosoma.charAt(i)=='1'){
                pesoTotal += articulos.get(i).getPeso();
                fitness += articulos.get(i).getUtilidad(); //El fitness representa la suma de las utilidades
            }
        }
        return (pesoTotal<=pesoMochila) ? fitness : -1; //Si sobrepasa el peso, es un cromosoma no válido
    }
    
    /**
     * Calcula el fitness con base en el peso de los artículos
     * @return fitness del cromosoma
     */
    public double calcularFitnessCantidad(){
        fitness = 0;
        for(int i=0;i<cromosoma.length();++i){
            if(cromosoma.charAt(i)=='1'){
                pesoTotal += articulos.get(i).getPeso();
                ++fitness; //El fitness representa la cantidad de objetos
            }
        }
        return (pesoTotal<=pesoMochila) ? fitness : -1; //Si sobrepasa el peso, es un cromosoma no válido
    }
    /**
     * Compare dos cromosomas para saber cuál tiene mejor fitness
     * @param cromosoma
     * @return cierto si el primer cromosoma es mejor, falso de otro modo
     */
    public int compareTo(Cromosoma cromosoma) {
        int compareFitness = (int)((Cromosoma) cromosoma).getFitness(); 
        return compareFitness - (int)this.fitness;
    }
    
}
