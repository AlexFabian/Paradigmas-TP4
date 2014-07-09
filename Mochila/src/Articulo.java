
/**
 * @author Mario, Alex
 * @version 07.08.14
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
