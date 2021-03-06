/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mineria_lab2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kay
 */
public class Mushroom {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            // Argumentos: [*.data,  *.names,  tamaño de la  lista  de  las mejores reglas ,soporte mínimo]
            if (args.length == 4) {
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
                int mejores_reglas = Integer.parseInt(args[2]);
                double soporte_minimo = Double.parseDouble(args[3]);


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
                System.out.println("Se han eliminado: " + contador_eliminados + " filas por datos perdidos.");
                System.out.println("");

                //Leer nombres de clases y valores que pueden tomar
                fila_temp = scanner_names.nextLine();
                if (fila_temp.lastIndexOf(".") == fila_temp.length() - 1) {
                    fila_temp = fila_temp.substring(0, fila_temp.length() - 1);

                }
                String[] clase_temp = fila_temp.split(",");
                int i, j;
                clases[0] = new LinkedList();
                clases[1] = new LinkedList();
                for (i = 0; i < clase_temp.length; i++) {
                    clases[0].add("clase_" + clase_temp[i]); //Agrego los nombres clase_xxx
                    clases[1].add(clase_temp[i]); //le doy el valor en el que será considerada 1, todos los otros será 0
                }

                //Parte de lectura de los atributos y valores de las variables

                variables[0] = new LinkedList();
                variables[1] = new LinkedList();
                variables[2] = new LinkedList();

                while (scanner_names.hasNext()) {
                    fila_temp = scanner_names.nextLine().replace(":", ",");
                    fila_temp = fila_temp.replaceAll("\\s", ""); //quitar todos los espacios
                    if (!fila_temp.isEmpty()) {
                        if (fila_temp.lastIndexOf(".") == fila_temp.length() - 1) {
                            fila_temp = fila_temp.substring(0, fila_temp.length() - 1);

                        } //Le quito el punto al final
                        String[] atributo_temp = fila_temp.split(",");
                        for (i = 1; i < atributo_temp.length; i++) {
                            variables[0].add(atributo_temp[0] + "_" + atributo_temp[i]); //nombre_valorNominal
                            variables[1].add(atributo_temp[i]); //Valor variable
                            variables[2].add(indice_clase); //posición en la matriz de datos anterior
                            ++indice_clase_procesados;

                        }
                        ++indice_clase;
                    }
                }

                System.out.println("Binarizando los datos de entrada. . .");
                System.out.println("");
                //Parte que crea la matriz con los datos binarizados
                for (i = 0; i < datos.size(); i++) {
                    LinkedList temp = new LinkedList();
                    for (j = 0; j < variables[0].size(); j++) { //por toda la cantidad de variables
                        //Si el dato que está en la tabla enorme, es el mismo del atributo analizado pongo un 1
                        if (variables[1].get(j).equals(datos.get(i).get((int) variables[2].get(j)))) {
                            temp.add(1);
                        } else {
                            temp.add(0);
                        }
                    }
                    for (j = 0; j < clases[0].size(); j++) { //por toda la cantidad de clases the same
                        if (clases[1].get(j).equals(datos.get(i).get(indice_clase))) {
                            temp.add(1);
                        } else {
                            temp.add(0);
                        }
                    }
                    datos_procesados.add(temp);
                }

                //Función para borrar variables que no posean valores verdaderos.
                int contadorDeCeros, columnasBorradas = 0;
                for (j = 0; j < variables[0].size(); j++) {
                    contadorDeCeros = 0;
                    for (i = 0; i < datos_procesados.size(); i++) {
                        if (datos_procesados.get(i).get(j).equals(0)) {
                            ++contadorDeCeros;
                        }
                    }
                    if (contadorDeCeros == datos_procesados.size()) {
                        ++columnasBorradas;
                        for (i = 0; i < datos_procesados.size(); i++) {
                            datos_procesados.get(i).remove(j);
                        }
                        variables[0].remove(j);
                        variables[1].remove(j);
                        variables[2].remove(j);
                        --indice_clase_procesados;
                    }
                }
                System.out.println("Binarización realizada. Se borraron " + columnasBorradas + " atributos que no tenían valores verdaderos.");
                System.out.println("");
                //Definición de variables
                Arbol arbol = new Arbol(datos_procesados, datos_procesados.get(0).size(), indice_clase_procesados, soporte_minimo); // datos, numero de columnas, indice_clase, soporte minimo
                LinkedList<Regla> lista_reglas_soporte = arbol.generarReglas();
                LinkedList<Regla> lista_reglas_confianza = (LinkedList<Regla>) lista_reglas_soporte.clone();
                Collections.sort(lista_reglas_soporte, new OrdenarPorSoporte());
                Collections.sort(lista_reglas_confianza, new OrdenarPorConfianza());
                LinkedList<String> lista_nombres = (LinkedList<String>) variables[0].clone();
                lista_nombres.addAll(clases[0]);
                //Se generan reglas por confianza y soporte
                for (Regla regla : lista_reglas_soporte) {
                    regla.generarRegla(lista_nombres, indice_clase_procesados);
                }
                for (Regla regla : lista_reglas_confianza) {
                    regla.generarRegla(lista_nombres, indice_clase_procesados);
                }
                Scanner sopOConf = new Scanner(System.in);
                System.out.println("¿Desea las reglas ordenadas por 1) soporte o 2) confianza?");
                System.out.println("Ingrese 1 o 2: ");
                boolean aux = true;
                while (aux) {
                    int decision = sopOConf.nextInt();
                    if (decision == 1) {
                        aux = false;
                        System.out.println("Las " + mejores_reglas + " mejores reglas según soporte son:");
                        System.out.println("");
                        if (lista_reglas_soporte.size() == 0) {
                            System.out.println("No existen reglas que cumplan con el soporte minimo indicado");
                        } else {
                            if (mejores_reglas < lista_reglas_soporte.size()) {
                                for (i = 0; i < mejores_reglas; i++) {
                                    System.out.println("Regla: " + lista_reglas_soporte.get(i).regla);
                                    System.out.println("Soporte: " + lista_reglas_soporte.get(i).soporte);
                                    System.out.println("Confianza: " + lista_reglas_soporte.get(i).confianza);
                                    System.out.println("");
                                }
                                System.out.println("");
                                System.out.println("");
                            } else {
                                for (i = 0; i < lista_reglas_soporte.size(); i++) {
                                    System.out.println("Regla: " + lista_reglas_soporte.get(i).regla);
                                    System.out.println("Soporte: " + lista_reglas_soporte.get(i).soporte);
                                    System.out.println("Confianza: " + lista_reglas_soporte.get(i).confianza);
                                    System.out.println("");
                                }
                                System.out.println("");
                                System.out.println("");
                            }
                        }
                    } else if (decision == 2) {
                        aux = false;
                        if (lista_reglas_confianza.size() == 0) {
                            System.out.println("No existen reglas que cumplan con el soporte minimo indicado");
                        } else {
                            System.out.println("Las " + mejores_reglas + " mejores reglas según confianza son:");
                            System.out.println("");
                            if (mejores_reglas < lista_reglas_confianza.size()) {
                                for (i = 0; i < mejores_reglas; i++) {
                                    System.out.println("Regla: " + lista_reglas_confianza.get(i).regla);
                                    System.out.println("Soporte: " + lista_reglas_confianza.get(i).soporte);
                                    System.out.println("Confianza: " + lista_reglas_confianza.get(i).confianza);

                                    System.out.println("");
                                }
                                System.out.println("");
                                System.out.println("");
                            } else {
                                for (i = 0; i < lista_reglas_confianza.size(); i++) {
                                    System.out.println("Regla: " + lista_reglas_confianza.get(i).regla);
                                    System.out.println("Soporte: " + lista_reglas_confianza.get(i).soporte);
                                    System.out.println("Confianza: " + lista_reglas_confianza.get(i).confianza);

                                    System.out.println("");
                                }
                                System.out.println("");
                                System.out.println("");
                            }
                        }
                    } else {
                        System.out.println("Ingrese 1 o 2: ");
                    }
                }
            }
            else{
                System.out.println("Error en la cantidad ingresada de parametros.");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Mushroom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}