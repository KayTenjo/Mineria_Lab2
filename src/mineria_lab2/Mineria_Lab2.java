/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mineria_lab2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Kay
 */
public class Mineria_Lab2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Argumentos: [*.data,  *.names,  tamaño de la  lista  de  las mejores reglas ,soporte mínimo.  ]
        
        /*Scanner scanner_data = new Scanner(args[0]);
        Scanner scanner_names = new Scanner(args[1]);
        LinkedList<LinkedList> datos = new LinkedList();
        LinkedList<String> nombres = new LinkedList();
        LinkedList<Regla> reglas = new LinkedList();
        
        int contador_eliminados = 0;
        
        while (scanner_data.hasNext()){
            
            String fila_temp = scanner_data.nextLine();
            if(!fila_temp.contains("?")){
            String[] array_temp = fila_temp.split(",");
            datos.add(new LinkedList(Arrays.asList(array_temp)));
                    }
            
            else{
            
                contador_eliminados++;
            }
            
        }
        
        */
        
        
        
        LinkedList<Integer> fila1 = new LinkedList();
        LinkedList<Integer> fila2 = new LinkedList();
        LinkedList<Integer> fila3 = new LinkedList();
        LinkedList<LinkedList> arreglo = new LinkedList();
        
        for(int i =0; i<3; i++){
        
            LinkedList<Integer> fila = new LinkedList();
            
            for(int j =0; j<3; j++){
                
                Random rnd = new Random();
                fila.add(rnd.nextInt(2));
            }
            
            arreglo.add(fila);
            System.out.println(fila);
        }
        
        Arbol arbol = new Arbol(3,arreglo);
        arbol.generarCombinacion();
        
        
        
       //Vamos armando las combinaciones posibles en forma de un árbol
       //Por cada elemento que salga, calculamos su soporte. 
       //Si el soporte cumple el soporte mínimo, seguimos formando combinaciones.
       //Si no cumple, no seguimos formando, puesto que el resto tampoco cumplirá con el soporte minimo establecido.
        
       
        
        
    }
    
}
