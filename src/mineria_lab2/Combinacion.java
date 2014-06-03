/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mineria_lab2;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Kay
 */
public class Combinacion {
    
    int n;
    int r;
    LinkedList<LinkedList> ignorados ;
    

    
    public LinkedList<LinkedList> generar(int n, int r, LinkedList<LinkedList> ignorados) {
        
        this.n = n;
        this.r = r;
        this.ignorados = ignorados;
            
        if ((n - r) > r) {
            permutaciones = factorial(n, n - r) / factorial(r, 1);
        } else {
            permutaciones = factorial(n, r) / factorial(n - r, 1);
        }
        
        
        return combinaciones(n, r);
        
        
    }


    public static double permutaciones = 0;

    //Esta parte no serÃ¡ necesaria, es solo para probar
    public static LinkedList<Integer> escribe(int indices[], int r) {
        
        LinkedList<Integer> resultado = new LinkedList();
        
        
        for (int i = 0; i < r; i++) {
            //System.out.format("%3d", indices[i]); //acÃ¡ dentro estÃ¡n los Ã­ndices y aquÃ­ debe hacerse el cÃ¡lculo de la cosa
            resultado.add(indices[i]);
        }
        //System.out.println("");
        return resultado;
    }

    public  LinkedList<LinkedList> combinaciones(int n, int r) {
        
        LinkedList<LinkedList> combinaciones = new LinkedList();
        int i = 0, j = 0, comb_hechas = 0;
        int indices[] = new int[n];

        for (i = 0; i < n; i++) {
            indices[i] = i;
        }

        combinaciones.add(escribe(indices, r)); //aquÃ­ tengo una combinatoria
        comb_hechas++;
        while (comb_hechas < permutaciones) {
            i = r - 1;
            while (indices[i] == n - r + i) {
                i--;
            }

            indices[i] = indices[i] + 1;
            for (j = i + 1; j < r; j++) {
                indices[j] = indices[i] + j - i;
            }
            combinaciones.add(escribe(indices, r)); //acÃ¡ tengo otra :c
            comb_hechas++;
        }
        
        return combinaciones;
    }

    public  double factorial(int n, int m) {
        //Multiplica desde n hasta m-1. n>=m
        double f = 1;
        while (n > m) {
            f = f * n;
            n--;
        }
        return f;
    }

}

