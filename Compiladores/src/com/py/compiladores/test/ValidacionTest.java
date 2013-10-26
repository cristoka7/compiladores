/**
 * 
 */
package com.py.compiladores.test;

import com.py.compiladores.algoritmos.ResultadoValidacion;
import com.py.compiladores.algoritmos.Subconjuntos;
import com.py.compiladores.algoritmos.Validacion;
import com.py.compiladores.analisis.Alfabeto;
import com.py.compiladores.analisis.AnalizadorSintactico;
import com.py.compiladores.estructuras.AFD;
import com.py.compiladores.estructuras.AFN;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase de prueba para la clase <code>Validacion</code>.
 * @author Germán Hüttemann
 * @author Marcelo Rodas
 */
public class ValidacionTest {

    /**
     * Test of validarAFN method, of class Validacion.
     */
    @Test
    public void testValidarAFN() {
        assertTrue(true);
    }

    /**
     * Test of validarAFD method, of class Validacion.
     * @throws Exception En caso de fallar el análisis sintáctico.
     */
    @Test
    public void testValidarAFD() throws Exception {
        Alfabeto alfa = new Alfabeto("ab");
        String er = "(a|b)*abb";
        String entrada = "abb";
       
        AnalizadorSintactico as = new AnalizadorSintactico(alfa, er);
        AFN afn = as.analizar();
        AFD afd = Subconjuntos.getAFD(afn);
       
        System.out.printf("Entrada: %s\n", entrada);
        System.out.printf("AFD:\n%s", afd);
       
        ResultadoValidacion rv = Validacion.validarAFD(afd, entrada);      
        System.out.printf("\nCamino: ");
        System.out.printf("%s ", rv.getCamino());
       
        System.out.printf("\nFaltante: '%s'\n", rv.getEntradaFaltante());
               
        assertTrue(rv.esValido());
    }
}

