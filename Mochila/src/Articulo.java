
/**
 * @author Mario, Alex
 * @version 07.08.14
 */
public class Articulo {
    private String nombre;
    private double peso;
    private double utilidad;
    
    /**
     * Constructor de artículos
     * @param nombre Nombre del artículo
     * @param peso  Peso del artículo
     * @param utilidad  Utilidad del artículo
     */
    public Articulo(String nombre, double peso, double utilidad){
        this.nombre = nombre;
        this.peso = peso;
        this.utilidad = utilidad;
    }
    /**
     * Retorna el nombre del artículo
     * @return nombre del artículo
     */
    public String getNombre(){
        return nombre;
    }
    /**
     * Retorna el peso del artículo
     * @return pero del artículo
     */
    public double getPeso(){
        return peso;
    }
    /**
     * Retorna utilidad del artículo
     * @return utilidad del artículo
     */
    public double getUtilidad(){
        return utilidad;
    }
}
