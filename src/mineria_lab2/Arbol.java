/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mineria_lab2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author Kay
 */
public class Arbol {

    LinkedList<String> nombres;
    int cantidad_nombres;
    LinkedList<LinkedList> datos;
    int posicion_clase;
    double soporte_min;
    int cantidad_datos;
    double permutaciones = 0;

    public Arbol(LinkedList<LinkedList> arreglo, int num_columnas, int indice_clase, double sop_min) {
        // datos, numero de columnas, indice_clase, soporte minimo
        //// Necesito la cantidad de nombres para saber como generar la combinación
        cantidad_nombres = num_columnas;
        cantidad_datos = arreglo.size();
        posicion_clase = indice_clase;
        soporte_min = sop_min;
        datos = arreglo;

    }

    public LinkedList<Regla> generarCombinacion() {

        //LinkedList<Integer> arreglo = new LinkedList();
        boolean aux = true;

        Combinacion combinacion = new Combinacion();
        
        LinkedList<Regla> lista_reglas = new LinkedList();
        LinkedList<LinkedList> combinaciones = combinacion.generar(cantidad_nombres, new LinkedList());// Aquí se genera una lista de combinaciones
        while (aux) {
            LinkedList<LinkedList> aceptados = new LinkedList();
            for (LinkedList<Integer> arreglo : combinaciones) {
                if (soporte(arreglo) >= soporte_min) {
                    aceptados.add(arreglo);
                    int cont_antec = 0;
                    int cont_consec = 0;
                    for (int indice : arreglo) {
                        
                        if (indice < posicion_clase) {
                            cont_antec++;
                        } else {
                            cont_consec++;
                        }
                    }
                    
                    if ((cont_antec > 0) && (cont_consec > 0)) {
                        lista_reglas.add(agregarRegla(obtenerSoporte(arreglo), arreglo));
                    }
                }
            }
            if(aceptados.isEmpty()){
                aux=false;
            }
            //aux++;
            combinaciones = combinacion.generar(cantidad_nombres, aceptados);// Aquí se genera una lista de combinaciones

        }
        return lista_reglas;
    }

    public LinkedList<Integer> generarFila(LinkedList<Integer> arreglo, int tamaño) {

        LinkedList<Integer> fila = new LinkedList();


        for (int i = 0; i < tamaño; i++) {

            fila.add(0);

        }

        for (int indice : arreglo) {

            fila.set(indice, 1);
        }

        return fila;
    }

    public double soporte(LinkedList<Integer> arreglo) {

        int ocurrencias = 0;
        for (LinkedList<Integer> fila : datos) {

            int contador_aux = 0;

            for (int indice : arreglo) {

                if (fila.get(indice) == 1) {
                    contador_aux++;
                }
            }

            if (contador_aux == arreglo.size()) {

                ocurrencias++;
            }
        }

        return ocurrencias;
    }

    public double obtenerSoporte(LinkedList<Integer> arreglo) {

        int ocurrencias = 0;
        for (LinkedList<Integer> fila : datos) {

            int contador_aux = 0;

            for (int indice : arreglo) {

                if (fila.get(indice) == 1) {
                    contador_aux++;
                }
            }

            if (contador_aux == arreglo.size()) {

                ocurrencias++;
            }
        }

        return ((double) ocurrencias / (double) cantidad_datos);
        //return ocurrencias;
    }

    public Regla agregarRegla(double soporte, LinkedList<Integer> arreglo) {

        Regla regla = new Regla();

        regla.setSoporte(soporte);

        regla.setArreglo(generarFila(arreglo, cantidad_nombres));

        LinkedList<Integer> arreglo_antecedente = new LinkedList();

        //for(int i =0;i<posicion_clase;i++){

        //    arreglo_antecedente.push(arreglo.get(i));
        //}

        for (Integer indice : arreglo) {

            if (indice < posicion_clase) {
                arreglo_antecedente.add(indice);
            }
        }

        double soporte_antecedente = obtenerSoporte(arreglo_antecedente);


        regla.setConfianza((double) soporte / (double) soporte_antecedente);
        //System.out.println(soporte);
        //System.out.println("El antecedente es " + arreglo_antecedente + " y su soporte es " + soporte_antecedente);
        //System.out.println("La confianza de la regla es " + regla.getConfianza() + "....." + soporte/soporte_antecedente);

        return regla;
    }
}
