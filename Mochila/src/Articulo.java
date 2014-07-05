/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public class Articulo {
    private String nombre;
    private double peso;
    private double utilidad;
    
    public Articulo(String nombre, double peso, double utilidad){
        this.nombre = nombre;
        this.peso = peso;
        this.utilidad = utilidad;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public double getPeso(){
        return peso;
    }
    
    public double getUtilidad(){
        return utilidad;
    }
}
