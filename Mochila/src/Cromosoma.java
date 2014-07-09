
import java.util.ArrayList;
import java.util.Comparator;
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
    private double probaPaternidad = 0;
    private ArrayList<Articulo> articulos;
    
    /*
    * Constructor de cromosomas para la población inicial
    * Crea cromosomas de manera aleatoria
    */
    public Cromosoma(ArrayList<Articulo> articulos, double pesoMochila){
        this.articulos = articulos;
        this.pesoMochila = pesoMochila;
        for(int i=0;i<articulos.size();++i){
            Random r = new Random();
            cromosoma += r.nextInt(2); //Se inicializan al azar
        }
    }
    
    /**
     * Constructor de cromosomas para poblaciones siguientes
     */
    public Cromosoma(ArrayList<Articulo> articulos, double pesoMochila, int dummy){
        this.articulos = articulos;
        this.pesoMochila = pesoMochila;
    }
    
    public double getPesoTotal(){
        return pesoTotal;
    }
    
    public double getFitness(){
        return fitness;
    }
    
    public void setFitness(double fitness){
        this.fitness = fitness;
    }
    
    public String getCromosoma(){
        return cromosoma;
    }
    
    public void setCromosoma(String cromosoma){
        this.cromosoma = cromosoma;
    }
    
    public Articulo getArticulo(int i){
        return articulos.get(i);
    }
    
    public void setProbaPaternidad(double probaPaternidad){
        this.probaPaternidad = probaPaternidad;
    }
    
    public double calcularFitnessUtilidad(){
        fitness = 0;
        for(int i=0;i<cromosoma.length();++i){
            if(cromosoma.charAt(i)=='1'){
                pesoTotal += articulos.get(i).getPeso();
                fitness += articulos.get(i).getUtilidad();
            }
        }
        return (pesoTotal<=pesoMochila) ? fitness : -1;
    }
    
    public double calcularFitnessCantidad(){
        fitness = 0;
        for(int i=0;i<cromosoma.length();++i){
            if(cromosoma.charAt(i)=='1'){
                pesoTotal += articulos.get(i).getPeso();
                ++fitness;
            }
        }
        return (pesoTotal<=pesoMochila) ? fitness : -1;
    }
    
    public int compareTo(Cromosoma cromosoma) {
        int compareFitness = (int)((Cromosoma) cromosoma).getFitness(); 
        return compareFitness - (int)this.fitness;
    }
    
}
