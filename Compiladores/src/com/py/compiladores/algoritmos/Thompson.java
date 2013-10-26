/**
 * 
 */
package com.py.compiladores.algoritmos;

import com.py.compiladores.estructuras.*;
import com.py.compiladores.analisis.Alfabeto;

/**
 * Esta clase implementa los algoritmos de Thompson para cada
 * uno de los operadores de una expresion regular.<br><br>
 * Cabe destacar que las operaciones implementadas en esta
 * clase tiene de efectos secundarios (side-effects) sobre
 * los AFN recibidos como argumentos ya que en ciertos casos
 * estos resultan alterados. Sin embargo, a efectos practicos
 * esto no es un problema, ya que en la traduccion de una
 * expresion regular a AFN siempre sera significativo solo el
 * último AFN construido.<br><br>
 * La solucion que podria aplicarse para corregir este efecto
 * es implementar una funcion de copia para los AFN.
 * @author Cristian Aceval
 * @author Victor Franco
 */
public class Thompson {

    /**
     * Construye un AFN a partir de un simbolo, que puede ser
     * un simbolo del alfabeto o el simbolo vacio.
     * @param simbolo El simbolo a partir del cual construir el AFN.
     * @return El AFN para <code>simbolo</code>.
     */
    public static AFN basico(String simbolo) {
        AFN afn = new AFN();
       
        /* Estados inicial y final */
        Estado ini = new Estado(0);
        Estado fin = new Estado(1, true);
       
        /* Transicion entre los estados inicial y final */
        Transicion tran = new Transicion(fin, simbolo);
        ini.getTransiciones().agregar(tran);
       
        /* Agregamos los estados al AFN */
        afn.agregarEstado(ini);
        afn.agregarEstado(fin);
       
        return afn;
    }
   
    /**
     * Aplica la cerradura de Kleene (*) a un AFN dado.
     * @param afn El AFN sobre el cual aplicar la cerradura de Kleene.
     * @return El AFN resultante de aplicar cerradura de Kleene a <code>afn</code>.
     */
    public static AFN cerraduraKleene(AFN afn) {
        AFN afn_salida = new AFN();
               
        /* Agregamos el estado inicial */
        Estado nuevoInicio = new Estado(afn_salida.cantidadEstados());
        afn_salida.agregarEstado(nuevoInicio);
       
        /*
         * Agregamos los demas estados, el incremento es igual
         * a la cantidad de estado en el AFN destino.
         */
        Automata.copiarEstados(afn, afn_salida, afn_salida.cantidadEstados());
       
        /* Agregamos el estado final */
        Estado nuevoFin = new Estado(afn_salida.cantidadEstados(), true);
        afn_salida.agregarEstado(nuevoFin);
       
        /* Agregamos transiciones adicionales desde el nuevo estado inicial */
        nuevoInicio.getTransiciones().agregar(new Transicion(afn_salida.getEstado(1), Alfabeto.VACIO));
        nuevoInicio.getTransiciones().agregar(new Transicion(nuevoFin, Alfabeto.VACIO));
       
        /* Estado anterior al final */
        Estado antesDeFinal = afn_salida.getEstado(afn_salida.cantidadEstados() - 2);
       
        /* Agregamos transiciones adicionales desde el anterior estado final */
        antesDeFinal.getTransiciones().agregar(new Transicion(afn_salida.getEstado(1), Alfabeto.VACIO));
        antesDeFinal.getTransiciones().agregar(new Transicion(nuevoFin, Alfabeto.VACIO));
       
        return afn_salida;
    }
   
    /**
     * Aplica la cerradura positiva (+) a un AFN dado.
     * @param afn El AFN sobre el cual aplicar la cerradura positiva.
     * @return El AFN resultante de aplicar cerradura positiva a <code>afn</code>.
     */
    public static AFN cerraduraPositiva(AFN afn) {
        return concatenacion(afn, cerraduraKleene(afn));
    }
   
    /**
     * Aplica el operador de opcion (?) a un AFN dado.
     * @param afn El AFN sobre el cual aplicar el operador de opcion.
     * @return El AFN resultante de aplicar el operador de opcion a <code>afn</code>.
     */
    public static AFN opcion(AFN afn) {
        return union(afn, basico(Alfabeto.VACIO));
    }
   
    /**
     * Aplica el operador de union a dos AFNs dados.
     * @param afn1 El primer operando de la union.
     * @param afn2 El segundo operando de la union.
     * @return El AFN resultante de la union de
     * <code>afn1</code> y <code>afn2</code>.
     */
    public static AFN union(AFN afn1, AFN afn2) {
        Transicion trans;
        AFN afn = new AFN();
       
        /* Agregamos el estado inicial */
        Estado nuevoInicio = new Estado(afn.cantidadEstados());
        afn.agregarEstado(nuevoInicio);
       
        /*
         * Agregamos los estados de afn1, el incremento es igual
         * a la cantidad de estado en el AFN destino.
         */
        Automata.copiarEstados(afn1, afn, afn.cantidadEstados());
       
        /*
         * Agregamos los estados de afn2, el incremento es igual
         * a la cantidad de estado en el AFN destino.
         */
        Automata.copiarEstados(afn2, afn, afn.cantidadEstados());
       
        /* Agregamos el estado final */
        Estado nuevoFin = new Estado(afn.cantidadEstados(), true);
        afn.agregarEstado(nuevoFin);
       
        /*
         * Creamos transicion vacia desde estado inicial
         * de afn hasta estado inicial de afn1 (ahora en afn).
         */
        trans = new Transicion();
        trans.setEstado(afn.getEstado(1));
        trans.setSimbolo(Alfabeto.VACIO);
        nuevoInicio.getTransiciones().agregar(trans);
       
        /*
         * Creamos transicion vacia desde estado inicial
         * de afn hasta estado inicial de afn2 (ahora en afn).
         */
        trans = new Transicion();
        trans.setEstado(afn.getEstado(afn1.cantidadEstados() + 1));
        trans.setSimbolo(Alfabeto.VACIO);
        nuevoInicio.getTransiciones().agregar(trans);
       
        /*
         * Creamos transicion vacia desde el estado final
         * de afn1 (ahora en afn) hasta el estado final de afn.
         */
        trans = new Transicion();
        trans.setEstado(afn.getEstado(afn.cantidadEstados() - 1));
        trans.setSimbolo(Alfabeto.VACIO);
        afn.getEstado(afn1.cantidadEstados()).getTransiciones().agregar(trans);
       
        /*
         * Creamos transicion vacia desde el estado final
         * de afn2 (ahora en afn) hasta el estado final de afn.
         */
        trans = new Transicion();
        trans.setEstado(afn.getEstado(afn.cantidadEstados() - 1));
        trans.setSimbolo(Alfabeto.VACIO);
        afn.getEstado(afn.cantidadEstados() - 2).getTransiciones().agregar(trans);
       
        return afn;
    }
   
    /**
     * Aplica el operador de concatenacion a dos AFNs dados.
     * @param afn1 El primer operando de la concatenacion.
     * @param afn2 El segundo operando de la concatenacion.
     * @return El AFN resultante de la concatenacion de
     * <code>afn1</code> y <code>afn2</code>.
     */
    public static AFN concatenacion(AFN afn1, AFN afn2) {
        AFN afn = new AFN();
       
        /*
         * Agregamos los estados de afn1, el incremento es igual
         * a la cantidad de estado en el AFN destino, que en este
         * caso es igual a cero.
         */
        Automata.copiarEstados(afn1, afn, afn.cantidadEstados());
       
        /* Último estado actual del automata que estamos generando */
        Estado ultimoActual = afn.getEstado(afn.cantidadEstados() - 1);
       
        /*
         * Agregamos los estados de afn2, excepto el primero.
         * En este caso, el incremento es igual al maximo
         * identificador de estados en el AFN destino y no a
         * la cantidad de estados ya que aqui se omite un estado.
         */
        Automata.copiarEstados(afn2, afn, afn.cantidadEstados() - 1, 1);
       
        /* Estado inicial de afn2 */
        Estado inicioAfn2 = afn2.getEstadoInicial();
       
        /*
         * Agregamos las transiciones del estado inicial de afn2
         * (ahora en afn) al estado final de afn1 (ahora en afn).
         */
        Automata.copiarTransiciones(afn, inicioAfn2.getTransiciones(),
                            ultimoActual, ultimoActual.getIdentificador());
       
        /* Establecer estado final */
        afn.getEstado(afn.cantidadEstados() - 1).setEsFinal(true);
       
        return afn;
    }
}

