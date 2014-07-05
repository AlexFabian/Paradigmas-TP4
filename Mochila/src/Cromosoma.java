
import java.util.ArrayList;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public class Cromosoma {
    private String cromosoma = "";
    private double fitness = 0;
    private double pesoMochila = 0;
    private double pesoTotal = 0;
    private double probaPaternidad = 0;
    private ArrayList<Articulo> articulos;
    
    public Cromosoma(ArrayList<Articulo> articulos, double pesoMochila){
        this.articulos = articulos;
        this.pesoMochila = pesoMochila;
        for(int i=0;i<articulos.size();++i){
            Random r = new Random();
            cromosoma += r.nextInt(2); //Se inicializan al azar
        }
    }
    
    public Cromosoma(){   }
    
    public double getPesoTotal(){
        return pesoTotal;
    }
    
    public double getFitness(){
        return fitness;
    }
    
    public String getCromosoma(){
        return cromosoma;
    }
    
    public void setCromosoma(String cromosoma){
        this.cromosoma = cromosoma;
    }
    
    public void setProbaPaternidad(double probaPaternidad){
        this.probaPaternidad = probaPaternidad;
    }
    
    public double calcularFitnessUtilidad(){
        for(int i=0;i<cromosoma.length();++i){
            if(cromosoma.charAt(i)=='1'){
                pesoTotal += articulos.get(i).getPeso();
                fitness += articulos.get(i).getUtilidad();
            }
        }
        return (pesoTotal<=pesoMochila) ? fitness : -1;
    }
    
    public double calcularFitnessCantidad(){
        for(int i=0;i<cromosoma.length();++i){
            if(cromosoma.charAt(i)=='1'){
                pesoTotal += articulos.get(i).getPeso();
                ++fitness;
            }
        }
        return (pesoTotal<=pesoMochila) ? fitness : -1;
    }
    
}
