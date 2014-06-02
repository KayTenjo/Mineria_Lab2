/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mineria_lab2;

import java.util.ArrayList;
import java.util.LinkedList;
/**
 *
 * @author Kay
 */
public class Arbol {
    
    LinkedList<String> nombres ; 
    int cantidad_nombres; 
    LinkedList<LinkedList> datos;
    int posicion_clase;
    double soporte_min;
    int cantidad_datos;
    double permutaciones = 0;
    
    
    
    public Arbol(int ctd_nombres, LinkedList<LinkedList> arreglo){
    
        //// Necesito la cantidad de nombres para saber como generar la combinación
        cantidad_nombres = ctd_nombres;
        cantidad_datos = arreglo.size();
        
       datos = arreglo;
       
    }
    
    
    public void generarCombinacion(){
    
        int cantidad_aux = 3;
        int[] datos_aux= {0,1,2};
        int indice_clase = 2;
        soporte_min =0.2;
        posicion_clase=2;
        ArrayList<Integer> iter = new ArrayList();
        iter.add(0);
        iter.add(1);
        iter.add(2);
        
       
    
        //LinkedList<Integer> arreglo = new LinkedList();
        int aux =1;
        int tamano = datos_aux.length;
         Combinacion combinacion = new Combinacion();
         LinkedList<LinkedList> ignorados = new LinkedList();
         
        
        while(tamano >=aux){
            
            LinkedList<LinkedList> combinaciones = combinacion.generar(tamano, aux); // Aquí se genera una lista de combinaciones
            for (LinkedList<Integer> arreglo : combinaciones){
               boolean ignorar= false;                
                
                for (LinkedList<Integer> elem_ignorado : ignorados){
                    int largoElemIgn =elem_ignorado.size();
                    int contador=0;
                    for (int i=0; i<largoElemIgn;i++){
                    
                        if(elem_ignorado.get(i) == arreglo.get(i)){
                            contador++;
                        }
                    
                    }
                    
                    if (contador == largoElemIgn){
                        ignorar = true;
                        System.out.println("Ignore a la combinación" + arreglo);
                    }
                }
                
                if (!ignorar){
                System.out.println("El soporte de:" + arreglo+ " es " +obtenerSoporte(arreglo));
            
                if(obtenerSoporte(arreglo) >= soporte_min){
                    
                    int cont_antec=0;
                    int cont_consec=0;
                    
                    for (int indice : arreglo){
                        
                        if(indice < posicion_clase){
                            cont_antec++;
                        } 
                        else{
                            cont_consec++;
                        }
                    }
                    
                    if ((cont_antec >0) && (cont_consec >0)){
                        agregarRegla(obtenerSoporte(arreglo), arreglo);
                    }
                
                }     
                
                else{
                    ignorados.add(arreglo);
                    
                }
                
                }
                
                else{
                    
                    ignorados.add(arreglo);
                }
            }
         System.out.println("Los ignorados de esta iteracion son ");
                    System.out.println(ignorados);   
         aux++;
         
        
        }
        
    }

                
    
    
    public LinkedList<Integer> generarFila(LinkedList<Integer> arreglo, int tamaño){
    
        LinkedList<Integer> fila = new LinkedList();
       
        
        for(int i =0; i<tamaño; i++){
            
            fila.add(0);
            
        }
        
        for (int indice: arreglo){
        
        fila.set(indice, 1);
        }
        
        return fila;
    }
    
    public double obtenerSoporte(LinkedList<Integer> arreglo){
        
        int ocurrencias =0;
        for(LinkedList<Integer> fila : datos){
            
            int contador_aux=0;
            
            for(int indice: arreglo){
                
                if(fila.get(indice)== 1){
                    contador_aux++;
                }
            }
            
            if (contador_aux == arreglo.size()){
            
                ocurrencias++;
            }
        }
    
        return ((double)ocurrencias/(double)cantidad_datos);
        //return ocurrencias;
    }
    
    public void agregarRegla(double soporte, LinkedList<Integer> arreglo){
    
        Regla regla = new Regla();
        
        regla.setSoporte(soporte);
        
        regla.setArreglo(generarFila(arreglo, cantidad_nombres));
        
        LinkedList<Integer> arreglo_antecedente = new LinkedList();
        
        //for(int i =0;i<posicion_clase;i++){
        
        //    arreglo_antecedente.push(arreglo.get(i));
        //}
        
        for (Integer indice: arreglo){
        
            if (indice < posicion_clase){
                arreglo_antecedente.add(indice);
            }
        }
        
        double soporte_antecedente = obtenerSoporte(arreglo_antecedente);
        
        
        regla.setConfianza((double)soporte/(double)soporte_antecedente);
        //System.out.println(soporte);
        System.out.println("El antecedente es " + arreglo_antecedente + " y su soporte es " + soporte_antecedente);
        System.out.println("La confianza de la regla es " + regla.getConfianza() + "....." + soporte/soporte_antecedente);
        
    
    }
    
    
    
    
    
}
