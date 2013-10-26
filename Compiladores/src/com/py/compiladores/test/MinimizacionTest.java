package com.py.compiladores.test;

import com.py.compiladores.estructuras.AFD;
import com.py.compiladores.estructuras.AFDMin;
import com.py.compiladores.estructuras.AFN;
import com.py.compiladores.algoritmos.Minimizacion;
import com.py.compiladores.algoritmos.Subconjuntos;
import com.py.compiladores.analisis.Alfabeto;
import com.py.compiladores.analisis.AnalizadorSintactico;
import org.junit.Test;

/**
 * Clase de prueba para la clase <code>Minimizacion</code>.
 * @author Germán Hüttemann
 * @author Marcelo Rodas
 */
public class MinimizacionTest {

    /**
     * Test of getAFDminimo method, of class Minimizacion.
     * @throws Exception
     */
    @Test
    public void testGetAFDminimo() throws Exception {
        Alfabeto alfa = new Alfabeto("ab");
        String er = "(a|b)*abb";
        AnalizadorSintactico as = new AnalizadorSintactico(alfa, er);
       
        AFN afn = as.analizar();
        AFD afd = Subconjuntos.getAFD(afn);
        AFDMin afdMin = Minimizacion.getAFDminimo(afd);
        System.out.printf("AFD Original:\n%s\n", afdMin.getAfdOriginal());
        System.out.printf("AFD Post Inalcanzables (%s):\n%s\n", afdMin.inalcanzablesEliminados() ? "<>" : "==", afdMin.getAfdPostInalcanzables());
        System.out.printf("AFD Post Minimización:\n%s\n", afdMin.getAfdPostMinimizacion());
        System.out.printf("AFD Post Identidades (%s):\n%s\n", afdMin.identidadesEliminados() ? "<>" : "==", afdMin.getAfdPostIdentidades());
       
        System.out.printf("\nParticiones:\n%s", Minimizacion.getLog());
    }
}

