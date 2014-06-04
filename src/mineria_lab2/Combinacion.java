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
public class Combinacion {
    int n;
    LinkedList<LinkedList> aceptados;
    public LinkedList<LinkedList> generar(int n, LinkedList<LinkedList> aceptados) {
        this.n = n;
        this.aceptados = aceptados;
        return combinaciones(n);
    }

    //Genera las combinaciones, a partir de una lista ya creada 
    //añadiendo solo una variable a las combinaciones anteriores.
    public LinkedList<LinkedList> combinaciones(int n) {
        LinkedList<LinkedList> combinaciones = new LinkedList();
        int i = 0;
        if (aceptados.isEmpty()) { //caso borde, cuando inicia genero todas las de 1
            for (i = 0; i < n; i++) {
                LinkedList<Integer> resultado = new LinkedList();
                resultado.add(i);
                combinaciones.add(resultado);
            }
        } else {
            for (LinkedList<Integer> combinacion : aceptados) {
                for (i = combinacion.getLast(); i < n; i++) {
                    LinkedList<Integer> resultado = new LinkedList();
                    resultado.addAll(combinacion);
                    if (!resultado.contains(i)) {
                        resultado.add(i);
                        combinaciones.add(resultado);
                    }
                }
            }
        }
        return combinaciones;
    }
}
