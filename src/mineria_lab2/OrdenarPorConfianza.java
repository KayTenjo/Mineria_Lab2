package mineria_lab2;

import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kay
 */
public class OrdenarPorConfianza implements Comparator<Regla>{
    @Override
    public int compare(Regla o1, Regla o2) {
        if (o1.confianza > o2.confianza ){
            return -1;
        }
         if (o1.confianza < o2.confianza ){
            return 1;
        }
        return 0;
    }
}
