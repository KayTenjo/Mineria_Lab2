/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mineria_lab2;

import java.util.LinkedList;

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

    public Arbol(LinkedList<LinkedList> arreglo, int num_columnas, int indice_clase, double sop_min) {
        // datos, numero de columnas, indice_clase, soporte minimo
        cantidad_nombres = num_columnas;
        cantidad_datos = arreglo.size();
        posicion_clase = indice_clase;
        soporte_min = sop_min;
        datos = arreglo;
    }

    //Revisa las combinaciones y genera la lista de reglas con soporte mayor o igual al mínimo
    public LinkedList<Regla> generarReglas() {
        boolean aux = true;
        Combinacion combinacion = new Combinacion();
        LinkedList<Regla> lista_reglas = new LinkedList();
        LinkedList<LinkedList> combinaciones = combinacion.generar(cantidad_nombres, new LinkedList());// Aquí se genera una lista de combinaciones
        while (aux) {
            LinkedList<LinkedList> aceptados = new LinkedList();
            for (LinkedList<Integer> arreglo : combinaciones) {
                if (obtenerSoporte(arreglo) >= soporte_min) {
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
            combinaciones = combinacion.generar(cantidad_nombres, aceptados);// Aquí se genera una lista de combinaciones
        }
        return lista_reglas;
    }

    //Transforma el arreglo de las posiciones en un arreglo con todas variables de la fila.
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

    //Obtiene el soporte normalizado de la regla
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
    }
    
    //Genera una regla
    public Regla agregarRegla(double soporte, LinkedList<Integer> arreglo) {
        Regla regla = new Regla();
        regla.setSoporte(soporte);
        regla.setArreglo(generarFila(arreglo, cantidad_nombres));
        LinkedList<Integer> arreglo_antecedente = new LinkedList();
        for (Integer indice : arreglo) {
            if (indice < posicion_clase) {
                arreglo_antecedente.add(indice);
            }
        }
        double soporte_antecedente = obtenerSoporte(arreglo_antecedente);
        regla.setConfianza((double) soporte / (double) soporte_antecedente);
        return regla;
    }
}
