/**
 * 
 */
package com.py.compiladores.estructuras;

import com.py.compiladores.analisis.Alfabeto;
import com.py.compiladores.estructuras.Automata;
import com.py.compiladores.estructuras.TablaTransicion;


/**
 * Clase que representa la abstraccion para un Automata Finito
 * No deterministico (AFN). Un AFN es contruido a partir de una
 * expresion regular a traves de las construcciones de Thompson.
 * @author Cristian Aceval
 * @author Victor Franco
 */
public class AFN extends Automata {
       
    /**
     * Constructor por defecto.
     */
    public AFN() {
       super();
    }
   
    /**
     * Construye un <code>AFN</code> con un determinado <code>Alfabeto</code>
     * y una determinada expresion regular.
     * @param alfabeto El <code>Alfabeto</code> de este <code>AFN</code>.
     * @param exprReg La expresion regular para este <code>AFN</code>.
     */
    public AFN(Alfabeto alfabeto, String exprReg) {
        super(alfabeto, exprReg);
    }
   
    /**
     * Retorna la tabla de transicion de estados.
     * @return La tabla de transicion de estados.
     */
    public TablaTransicion getTablaTransicion() {
        int cantFil = getEstados().cantidad();
        int cantCol = getAlfabeto().getCantidad() + 2;
       
        return cargarTablaTransicion(cantFil, cantCol, 0);
    }
}

