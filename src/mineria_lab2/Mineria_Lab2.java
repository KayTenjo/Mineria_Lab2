/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mineria_lab2;

import java.util.Arrays;
import java.util.LinkedList;
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
        
        Scanner scanner_data = new Scanner(args[0]);
        Scanner scanner_names = new Scanner(args[1]);
        LinkedList<LinkedList> datos = new LinkedList();
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
        
        
        
        
        
        
    }
    
}
