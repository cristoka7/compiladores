package com.py.compiladores.test;

import org.junit.Test;

import com.py.compiladores.analisis.Alfabeto;
import com.py.compiladores.analisis.AnalizadorSintactico;
import com.py.compiladores.estructuras.AFN;
import com.py.compiladores.estructuras.TablaTransicion;

/**
 * Clase de prueba para la clase <code>AnalizadorSintactico</code>
 * @author Cristian Aceval
 * @author Victor Franco
 */
public class AnalizadorSintacticoTest {

    /**
     * Test of analizar method, of class AnalizadorSintactico.
     * @throws Exception
     */
    @Test
    public void testAnalizar() throws Exception {
        Alfabeto alfa = new Alfabeto("ab");
        String er = "a*b?(ab|ba)*b?a*";
        AnalizadorSintactico as = new AnalizadorSintactico(alfa, er);
       
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
    }
}
