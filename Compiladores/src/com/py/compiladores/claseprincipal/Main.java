package com.py.compiladores.claseprincipal;

import com.py.compiladores.algoritmos.Minimizacion;
import com.py.compiladores.algoritmos.Subconjuntos;
import com.py.compiladores.analisis.Alfabeto;
import com.py.compiladores.analisis.AnalizadorSintactico;
import com.py.compiladores.estructuras.AFD;
import com.py.compiladores.estructuras.AFDMin;
import com.py.compiladores.estructuras.AFN;
import com.py.compiladores.estructuras.TablaTransicion;

public class Main {
	
    public static void main(String args[]) throws Exception{

        Alfabeto alfa = new Alfabeto("abc");
        String er = "(a|b)*a+c?";
        AnalizadorSintactico as = new AnalizadorSintactico(alfa, er);
        
        /*
         *  CONVERSION REGEX -> AFN
         *  ALGORITMO DE THOMPSON
         */ 
       
        AFN salida = as.analizar();
        System.out.printf("AFN:\n%s", salida);
        
               
        /* Tabla transicion del AFN */
        System.out.println();
        TablaTransicion tabla = salida.getTablaTransicion();
        
               
        for (int i=0; i < tabla.getColumnCount(); i++)
            System.out.printf("%s\t", tabla.getColumnName(i));
       
        System.out.println();
        for (int i=0; i < tabla.getRowCount(); i++) {
            for (int j=0; j < tabla.getColumnCount(); j++)
                System.out.printf("%s\t", tabla.getValueAt(i, j));
           
            System.out.println();
        }
       
        System.out.printf("\nDerivaciones:\n%s", as.getLog());
        
        
        /*
         *  CONVERSION AFN -> AFD
         *  ALGORITMO DE SUBCONJUNTOS
         */
        
        System.out.println();
        AFD afd = Subconjuntos.getAFD(salida);
        System.out.printf("AFD:\n%s", afd);
        System.out.printf("\nEstadosD:\n%s", afd.estadosDtoString());
       
        /* Tabla transicion del AFD */
        System.out.println();
        TablaTransicion tabla2 = afd.getTablaTransicion();
       
        for (int i=0; i < tabla2.getColumnCount(); i++)
            System.out.printf("%s\t\t", tabla2.getColumnName(i));
       
        System.out.println();
        for (int i=0; i < tabla2.getRowCount(); i++) {
            for (int j=0; j < tabla2.getColumnCount(); j++)
                System.out.printf("%s\t\t", tabla2.getValueAt(i, j));
           
            System.out.println();
        }
        
        System.out.printf("\nConjuntos estados producidos:\n%s", Subconjuntos.getLog());
        
        
        /*
         *  CONVERSION AFD -> AFD MINIMO
         *  ALGORITMO DE MINIMIZACION 
         */
       
        
        AFDMin afdMin = Minimizacion.getAFDminimo(afd);
        System.out.printf("AFD Original:\n%s\n", afdMin.getAfdOriginal());
        System.out.printf("AFD Post Inalcanzables (%s):\n%s\n", afdMin.inalcanzablesEliminados() ? "<>" : "==", afdMin.getAfdPostInalcanzables());
        System.out.printf("AFD Post Minimizacion:\n%s\n", afdMin.getAfdPostMinimizacion());
        System.out.printf("AFD Post Identidades (%s):\n%s\n", afdMin.identidadesEliminados() ? "<>" : "==", afdMin.getAfdPostIdentidades());
       
        System.out.printf("\nParticiones:\n%s", Minimizacion.getLog());
    }

}



