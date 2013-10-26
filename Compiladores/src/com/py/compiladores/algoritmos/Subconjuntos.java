/*
 * Trabajo Practico de Compiladores 2013.
 * 10mo Semestre Ingenieria Infomatica.
 * Facultad Politecnica - UNA.
 */
package com.py.compiladores.algoritmos;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.py.compiladores.analisis.Alfabeto;
import com.py.compiladores.estructuras.AFD;
import com.py.compiladores.estructuras.Automata;
import com.py.compiladores.estructuras.Conjunto;
import com.py.compiladores.estructuras.Estado;
import com.py.compiladores.estructuras.Log;
import com.py.compiladores.estructuras.Transicion;

/**
 * Esta clase implementa los algoritmos para realizar
 * la conversion de un AFN a un AFD.
 * @see Automata
 * @author Cristian Aceval
 * @author Victor Franco
 */
public class Subconjuntos {
   
    /**
     * Log para el algoritmo de subconjuntos.
     */
    private static Log log = new Log();

    /**
     * Realiza la conversion de un AFN a un AFD, aplicando el
     * algoritmo 3.20 del libro de Compiladores de Aho (2a. ed).
     * @param afn El <code>AFN</code> a convertir.
     * @return El <code>AFD</code> equivalente a <code>afn</code>.
     */
    public static AFD getAFD(Automata afn) {
        Estado estadoOrigen, estadoDestino;
       
        // Logging
        log.vaciar();
        log.agregar("Calculo de conjuntos de estados".toUpperCase()).nuevaLinea();
        log.agregar("-------------------------------").nuevaLinea().nuevaLinea();
       
        /* AFD resultante */
        AFD afd = new AFD(afn.getAlfabeto(), afn.getExprReg());
       
        /* Conjunto de estados finales del AFD */
        Conjunto<Conjunto<Estado>> estadosD = new Conjunto<Conjunto<Estado>>();
       
        /*
         * En esta cola se iran almacenando temporalmente los conjuntos de
         * estados y la operacion de marcarlos consistira en quitarlos la misma
         * y guardarlos en estadosD.
         * La condicion de que no haya estados marcados se dara cuando la cola
         * este vacia.
         */
        Queue<Conjunto<Estado>> colaTemp = new LinkedList<Conjunto<Estado>>();
       
        /* Contador de estados procesados del AFD */
        int estadosProcesados = 0;
       
        /* Calculamos la Cerradura Epsilon del estado inicial */
        Conjunto<Estado> resultado = cerraduraEpsilon(afn.getEstadoInicial());
       
        // Logging
        log.agregar("cerradura(" + afn.getEstadoInicial() + ") = " + resultado).nuevaLinea().nuevaLinea();
       
         /*
          * Agregamos la Cerradura Epsilon del estado
          * inicial del AFN a estadosD sin marcar
          */
        estadosD.agregar(resultado);
        colaTemp.add(resultado);
       
        /*
         * Iniciamos el ciclo principal del algoritmo
         */
        while (!colaTemp.isEmpty()) {
            /* Marcar T */
            Conjunto<Estado> T = colaTemp.remove();
           
            /* Agregamos el correspondiente estado al AFD */
            if (afd.cantidadEstados() < estadosD.cantidad())
                afd.agregarEstado(new Estado(afd.cantidadEstados()));
           
            /* Estado del AFD a procesar */
            estadoOrigen = afd.getEstado(estadosProcesados++);
           
            /* Buscar transiciones por cada simbolo */
            for (String simbolo : afn.getAlfabeto()) {
                /* Aplicar cerraduraEpsilon(mueve(T, simbolo)) */
                Conjunto<Estado> M = mover(T, simbolo);
                Conjunto<Estado> U = cerraduraEpsilon(M);
               
                // Logging
                log.agregar("cerradura(mover(" + T + ", " + simbolo + ")) = ")
                   .agregar("cerradura(" + M + ") = " + U)
                   .nuevaLinea();
               
                if (estadosD.contiene(U)) {
                    int posicion  = estadosD.obtenerPosicion(U);
                    estadoDestino = afd.getEstado(posicion);
                }
                else if (!U.estaVacio()) {
                    estadoDestino = new Estado(afd.cantidadEstados());
                    afd.agregarEstado(estadoDestino);
                   
                    /* Agregar U a estadosD (sin marcar) si no esta aún */
                    estadosD.agregar(U);
                    colaTemp.add(U);
                }
                else {
                    /*
                     * Encontramos un conjunto vacio, por tanto,
                     * no debemos agregar ninguna transicion y
                     * debemos saltar directamente a evaluar el
                     * siguiente simbolo del alfabeto.
                     */
                    continue;
                }
               
                // Agregamos la transicion al AFD
                Transicion trans = new Transicion(estadoDestino, simbolo);
                estadoOrigen.getTransiciones().agregar(trans);
            }
           
            // Logging
            log.nuevaLinea();
        }
       
        /* Establecemos los estados finales del AFD */
        for (int i=0; i < estadosD.cantidad(); i++) {
            Estado estadoAFD = afd.getEstado(i);
           
            for (Estado e : estadosD.obtener(i)) {
                if (e.getEsFinal()) {
                    estadoAFD.setEsFinal(true);
                    break;
                }
            }
        }
       
        afd.setEstadosD(estadosD);
        return afd;
    }
   
    /**
     * Implementa la operacion Cerradura Epsilon sobre un <code>Estado</code>.
     * @param estado <code>Estado</code> sobre el cual aplicar la operacion.
     * @return El <code>Conjunto</code> de estados alcanzados.
     */
    public static Conjunto<Estado> cerraduraEpsilon(Estado estado) {
        Conjunto<Estado> resultado = new Conjunto<Estado>();
        recorrido(estado, resultado, Alfabeto.VACIO);
        resultado.ordenar();
        return resultado;
    }
   
    /**
     * Implementa la operacion Cerradura Epsilon sobre un <code>Conjunto</code>
     * de <code>Estado</code>s.
     * @param estados <code>Conjunto</code> de <code>Estado</code>s sobre el
     * cual aplicar operacion.
     * @return El <code>Conjunto</code> de estados alcanzados.
     */
    public static Conjunto<Estado> cerraduraEpsilon(Conjunto<Estado> estados) {
        Conjunto<Estado> resultado = new Conjunto<Estado>();
        recorrido(estados, resultado, Alfabeto.VACIO);
        resultado.ordenar();
        return resultado;
    }
   
    /**
     * Implementa la operacion Mueve.
     * @param estados <code>Estado</code>s sobre los cuales aplicar la operacion.
     * @param simbolo Simbolo que debe seguirse en las <code>Transicion</code>s.
     * @return El <code>Conjunto</code> de estados alcanzados.
     */
    public static Conjunto<Estado> mover(Conjunto<Estado> estados, String simbolo) {
        Conjunto<Estado> resultado = new Conjunto<Estado>();
        recorrido(estados, resultado, simbolo);
        resultado.ordenar();
        return resultado;
    }
   
    /**
     * Realiza un recorrido del automata a partir de un <code>Estado</code>
     * inicial y pasando por todas las <code>Transicion</code>s que coincidan
     * con determinado simbolo.<br>
     * Este algoritmo corresponde al una generalizacion del algoritmo de la
     * Figura 3.33 del libro Compiladores de Aho (2da. edicion), de manera a
     * que el mismo pueda utilizarse para las dos operaciones de Cerradura
     * Epsilon y tambien para la operacion Mueve.
     * @param actual El <code>Estado</code> a partir del cual se realiza el recorrido.
     * @param alcanzados <code>Conjunto</code> donde se guardan los <code>Estado</code>s alcanzados.
     * @param simboloBuscado Simbolo que debe seguirse en las <code>Transicion</code>s.
     */
    private static void recorrido(Estado actual, Conjunto<Estado> alcanzados, String simboloBuscado) {
        /* Pila para almacenar los estados pendientes */
        Stack<Estado> pila = new Stack<Estado>();
       
        /*
         * Cuando el simbolo buscado es igual al simbolo
         * vacio, el estado desde donde se empieza el
         * recorrido debe incluirse entre los estados
         * alcanzados.
         */
        if (simboloBuscado.equals(Alfabeto.VACIO))
            alcanzados.agregar(actual);
       
        /* Meter el estado actual como el estado inicial */
        pila.push(actual);
       
        while (!pila.isEmpty()) {
            actual = pila.pop();
            for (Transicion t : actual.getTransiciones()) {
                Estado e = t.getEstado();
                String s = t.getSimbolo();
               
                if (s.equals(simboloBuscado) && !alcanzados.contiene(e)) {
                    alcanzados.agregar(e);
                   
                    /*
                     * Debido a que solo cuando el simbolo buscado
                     * es igual a vacio se debe recorrer recursivamente
                     * los estados alcanzados, agregamos dichos estados
                     * a la pila solo si se da esa condicion.
                     */
                    if (simboloBuscado.equals(Alfabeto.VACIO))
                        pila.push(e);
                }
            }
        }
    }
   
    /**
     * Realiza un recorrido del automata a partir de un <code>Estado</code>
     * inicial y pasando por todas las <code>Transicion</code>s que coincidan
     * con determinado simbolo.
     * @param inicios El <code>Conjunto</code> <code>Estado</code>s a partir de los cuales
     * se realiza el recorrido.
     * @param alcanzados <code>Conjunto</code> donde se guardan los <code>Estado</code>s alcanzados.
     * @param simboloBuscado Simbolo que debe seguirse en las <code>Transicion</code>s.
     */
    private static void recorrido(Conjunto<Estado> inicios, Conjunto<Estado> alcanzados, String simboloBuscado) {
        for (Estado e : inicios)
            recorrido(e, alcanzados, simboloBuscado);
    }
   
    /**
     * Obtiene el <code>Log</code> de esta clase.
     * @return El <code>Log</code> correspondiente
     * al algoritmo de subconjuntos.
     */
    public static Log getLog() {
        return log;
    }
}

