
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
public class Poblacion {
    private ArrayList<Cromosoma> individuos;
    private double probabilidadMutacion;
    private double pesoMochila;
    private int cantidadIndividuos;
    private int fitnessTotal = 0;
    
    public Poblacion(double probabilidadMutacion,double pesoMochila){
        this.probabilidadMutacion = probabilidadMutacion;
        this.pesoMochila = pesoMochila;
        individuos = new ArrayList<Cromosoma>();
        cantidadIndividuos = 30; //cambiar esto por fórmula
    }
    
    public void generarPoblacionInicial(ArrayList<Articulo> articulos){
        for(int i=0;i<cantidadIndividuos;++i){
            individuos.add( new Cromosoma(articulos, pesoMochila));
        }
    }
    
    public void agregarIndividuo(Cromosoma cromosoma){
        individuos.add(cromosoma);
    }
    
    public Cromosoma getIndividuo(int i){
        return individuos.get(i);
    }
    
    public int getCantidadIndividuos(){
        return individuos.size();
    }
    
    public Poblacion evaluarAptitud(String tipoOptimizacion){
        Poblacion posiblesPadres = new Poblacion(probabilidadMutacion, pesoMochila);
        if(tipoOptimizacion.equalsIgnoreCase("Por cantidad")){
            for(int i=0;i<cantidadIndividuos;++i){
                if(individuos.get(i).calcularFitnessCantidad()!=-1){
                    posiblesPadres.agregarIndividuo(individuos.get(i));
                }
            }
        }else{
            for(int i=0;i<cantidadIndividuos;++i){
                if(individuos.get(i).calcularFitnessUtilidad()!=-1){
                    posiblesPadres.agregarIndividuo(individuos.get(i));
                }
            }
        }
        return posiblesPadres;
    }
    
    public Poblacion seleccionarPadres(){
        Poblacion padres = new Poblacion( probabilidadMutacion, pesoMochila); //por ruleta
        int[] probas = new int[individuos.size()];
        for(int i=0;i<individuos.size();++i){
            fitnessTotal += individuos.get(i).getFitness();
        }
        double probaPaternidad=0;
        for(int i=0;i<individuos.size();++i){
            probaPaternidad  = individuos.get(i).getFitness()/fitnessTotal;
            individuos.get(i).setProbaPaternidad(probaPaternidad);
            if(i!=0){
                probas[i] = (int) ((probaPaternidad *100)+ probas[i-1]);
            }else{
                probas[i] = (int) (probaPaternidad*100);
            }
        }
        /*System.out.println("\n\nProbas");
        for(int j=0;j<probas.length;++j){
            System.out.print(probas[j]+"\t");
        }
        System.out.println();
        System.out.println("\n\nPadres");*/
        for(int i=0;i<individuos.size()/2;++i){
            Random r = new Random();
            int randomNum = r.nextInt(101);
            for(int j=0;j<probas.length;++j){
                if(probas[j]>randomNum || j == probas.length){
                    if(j!=0 && j != probas.length){
                        padres.agregarIndividuo(individuos.get(j-1));
                        //System.out.print(j-1+"\t");
                    }else{
                        padres.agregarIndividuo(individuos.get(j));
                        //System.out.print(j+"\t");
                    }
                    break;
                }
            }
        }
        
        return padres;
    }
    
    public Poblacion cruzar(double probabilidadCruce){
        Poblacion hijos = new Poblacion( probabilidadMutacion, pesoMochila); //por ruleta
        int hijosPorCruce = (int) (probabilidadCruce*individuos.size());
        int hijosClones = individuos.size() - hijosPorCruce;
        

        int j = 0;
        boolean paridad =(hijosPorCruce%2==0)? true:false;
        for(int i=0;i<hijosPorCruce/2;++i){
            //System.out.print(j+"y"+j+1+"\t");
            //System.out.print(j+1+"y"+j+"\t");
            Cromosoma papa = individuos.get(j);
            Cromosoma mama = individuos.get(j+1);
            Cromosoma hijo1 = cruce(papa,mama);
            Cromosoma hijo2 = cruce(mama,papa);
            j += 2;
            hijos.agregarIndividuo(hijo1);
            hijos.agregarIndividuo(hijo2);
        }
        if(!paridad){
            //System.out.print(j+"y"+j+1+"\t");
            Cromosoma papa = individuos.get(j);
            Cromosoma mama = individuos.get(j+1);
            Cromosoma hijo1 = cruce(papa,mama);
            j++;
            hijos.agregarIndividuo(hijo1);
        }
        for(int i=0;i<hijosClones;++i){
            //System.out.print("C\t");
            hijos.agregarIndividuo(individuos.get(j++));
        }
        return hijos;
    }
    
    public Cromosoma cruce(Cromosoma papa, Cromosoma mama){
        Cromosoma hijo = new Cromosoma();
        String cromosoma1 = papa.getCromosoma();
        String cromosoma2 = mama.getCromosoma();
        String cromosomaHijo = "";
        int puntoCruce = cromosoma1.length()/2;
        //System.out.println("Parte 1 "+cromosoma1.substring(0,puntoCruce));
        //System.out.println("Parte 2 "+cromosoma2.substring(puntoCruce,cromosoma2.length()));
        cromosomaHijo += cromosoma1.substring(0,puntoCruce)+cromosoma2.substring(puntoCruce,cromosoma2.length());
        hijo.setCromosoma(cromosomaHijo);
        return hijo;
    }
    
    public Poblacion mutar(double probabilidadMutacion){
        Poblacion hijosMutados = new Poblacion( probabilidadMutacion, pesoMochila);
        Random r = new Random();
        
        Random s = new Random();
        for(int i=0;i<individuos.size();++i){
            int randomNum = r.nextInt(101);
            if(randomNum<=(probabilidadMutacion*100)){
                int posicion = s.nextInt(individuos.get(0).getCromosoma().length());
                Cromosoma hijo = individuos.get(i);
                String cromosoma = hijo.getCromosoma();
                char gen = (cromosoma.charAt(posicion)=='1')?'0' : '1'; 
                //System.out.println("Parte 1: "+cromosoma.substring(0,posicion));
                //System.out.println("Parte 2: "+cromosoma.substring(posicion+1,cromosoma.length()));
                //System.out.println("Posición: "+posicion);
                //System.out.println("Gen: "+gen);
                hijo.setCromosoma(cromosoma.substring(0,posicion)+gen+cromosoma.substring(posicion+1,cromosoma.length()));
                hijosMutados.agregarIndividuo(hijo);
            }else{
                hijosMutados.agregarIndividuo(individuos.get(i));
            }
        }
        return hijosMutados;
    }
    
}
