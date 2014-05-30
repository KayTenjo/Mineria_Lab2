/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mineria_lab2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kay
 */
public class Mineria_Lab2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Argumentos: [*.data,  *.names,  tamaño de la  lista  de  las mejores reglas ,soporte mínimo]

            Scanner scanner_data = new Scanner(new FileReader(args[0]));
            Scanner scanner_names = new Scanner(new FileReader(args[1]));
            String fila_temp;
            LinkedList<String>[] clases = new LinkedList[2];

            LinkedList[] variables = new LinkedList[3];

            LinkedList<LinkedList> datos = new LinkedList();
            LinkedList<LinkedList> datos_procesados = new LinkedList();

            int indice_clase = 0;
            int indice_clase_procesados = 0;
            int contador_eliminados = 0;



            //Leer todos los datos y eliminar los que tienen datos perdidos
            while (scanner_data.hasNext()) {

                fila_temp = scanner_data.nextLine();
                if (!fila_temp.contains("?")) { //Si tiene no dato eliminado la cuento
                    String[] array_temp = fila_temp.split(",");

                    datos.add(new LinkedList(Arrays.asList(array_temp))); //Agregar a la lista enorme de datos
                } else {

                    contador_eliminados++;

                }

            }
            //Leer nombres de clases y valores que pueden tomar
            fila_temp = scanner_names.nextLine();

            fila_temp = fila_temp.substring(0, fila_temp.length() - 1);

            String[] clase_temp = fila_temp.split(",");
            //System.out.println(clase_temp[0]);
            int i, j;
            clases[0] = new LinkedList();
            clases[1] = new LinkedList();

            for (i = 0; i < clase_temp.length; i++) {
                clases[0].add("clase_" + clase_temp[i]); //Agrego los nombres clase_xxx
                clases[1].add(clase_temp[i]); //le doy el valor en el que será considerada 1, todos los otros será 0
            }

            variables[0] = new LinkedList();
            variables[1] = new LinkedList();
            variables[2] = new LinkedList();

            while (scanner_names.hasNext()) {
                fila_temp = scanner_names.nextLine().replace(":", ",");
                fila_temp = fila_temp.replaceAll("\\s", ""); //quitar todos los espacios
                if (!fila_temp.isEmpty()) {
                    fila_temp = fila_temp.substring(0, fila_temp.length() - 1); //Le quito el punto al final
                    String[] atributo_temp = fila_temp.split(",");
                    for (i = 1; i < atributo_temp.length; i++) {
                        variables[0].add(atributo_temp[0] + "_" + atributo_temp[i]); //nombre_valorNominal
                        variables[1].add(atributo_temp[i]);
                        variables[2].add(indice_clase);
                        ++indice_clase_procesados;
                    }
                    ++indice_clase;
                }
            }
            for (i = 0; i < datos.size(); i++) {
                LinkedList temp = new LinkedList();
                for (j = 0; j < variables[0].size(); j++) { //por toda la cantidad de variables

                    //Si el dato que está en la tabla enorme, es el mismo del atributo analizado
                    if (variables[1].get(j).equals(datos.get(i).get((int) variables[2].get(j)))) {
                        temp.add(1);
                    } else {
                        temp.add(0);
                    }

                }
                for (j = 0; j < clases[0].size(); j++) { //por toda la cantidad de clases
                    if (clases[1].get(j).equals(datos.get(i).get(indice_clase))) {
                        temp.add(1);
                    } else {
                        temp.add(0);
                    }

                }
                datos_procesados.add(temp);
            }


            //TODO: Entregarle el primer índice desde donde hay clase
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Mineria_Lab2.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
}
