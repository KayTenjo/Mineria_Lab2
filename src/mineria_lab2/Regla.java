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
public class Regla {

    double soporte;
    double confianza;
    int oli;
    LinkedList<Integer> arreglo;
    String regla;

    //Toma los índices de la combinación y genera la regla con los nombres de las variables para la impresión.
    public void generarRegla(LinkedList<String> lista_nombres, int indice_clase) {
        String regla = "";
        boolean marcador_clase = false;
        for (int i = 0; i < arreglo.size(); i++) {
            if (this.arreglo.get(i).equals(1)) {
                if (i >= indice_clase && !marcador_clase) {
                    regla = regla + "=> " + lista_nombres.get(i) + " ";
                    marcador_clase = true;
                } else {
                    regla = regla + lista_nombres.get(i) + " ";
                }
            }
        }
        this.regla = regla;
    }

    public double getSoporte() {
        return soporte;
    }

    public void setSoporte(double soporte) {
        this.soporte = soporte;
    }

    public double getConfianza() {
        return confianza;
    }

    public void setConfianza(double confianza) {
        this.confianza = confianza;
    }

    public LinkedList<Integer> getArreglo() {
        return arreglo;
    }

    public void setArreglo(LinkedList<Integer> arreglo) {
        this.arreglo = arreglo;
    }

    public String getRegla() {
        return regla;
    }

    public void setRegla(String regla) {
        this.regla = regla;
    }
}
