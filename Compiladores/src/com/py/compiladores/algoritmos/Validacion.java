/*
 * Trabajo Practico de Compiladores 2013.
 * 10mo Semestre Ingenieria Infomatica.
 * Facultad Politecnica - UNA.
 */
package com.py.compiladores.algoritmos;

import java.util.LinkedList;
import java.util.Queue;

import com.py.compiladores.estructuras.AFD;
import com.py.compiladores.estructuras.AFN;
import com.py.compiladores.estructuras.Conjunto;
import com.py.compiladores.estructuras.Estado;
import com.py.compiladores.estructuras.Par;
import com.py.compiladores.estructuras.Transicion;

/**
 * Esta clase implementa los algoritmos de validacion
 * para AFNs y AFDs.
 * @author Cristian Aceval
 * @author Victor Franco
 */
public class Validacion {

    /**
     * Realiza la validacion de una cadena de entrada contra
     * un AFD dado.
     * @param afn <code>AFN</code> contra el cual validar la
     * cadena de entrada.
     * @param entrada Cadena de entrada a validar.
     * @return Un objeto <code>ResultadoValidacion</code> con
     * los resultados de la validacion.
     */
    public static ResultadoValidacion validarAFN(AFN afn, String entrada) {
        /* Buffer para manipular la entrada */
        Queue<String> buffer = new LinkedList<String>();
       
        /*
         * Agregamos los caracteres de la entrada en
         * forma FIFO. Cuando se consuman todos los
         * caracteres, el siguiente sera null, que
         * representara al caracter EOF.
         */
        for (int i=0; i < entrada.length(); i++)
            buffer.add("" + entrada.charAt(i));
       
        /* Secuencia de conjuntos de estados recorridos */
        Conjunto<Par<Conjunto<Estado>, String>> camino = new Conjunto<Par<Conjunto<Estado>, String>>();
       
        /* Comenzamos por la cerradura del estado inicial del AFN */
        Conjunto<Estado> estadosActuales = Subconjuntos.cerraduraEpsilon(afn.getEstadoInicial());
        camino.agregar(new Par<Conjunto<Estado>, String>(estadosActuales, ""));
       
        /* Cada simbolo de entrada */
        String simbolo;
       
        /* Recorremos mientras hayan simbolos en la entrada */
        while ((simbolo = buffer.poll()) != null) {
            /* Nos movemos al siguiente estado */
            estadosActuales = Subconjuntos.cerraduraEpsilon(Subconjuntos.mover(estadosActuales, simbolo));
           
            if (estadosActuales.estaVacio()) {
                /* Si no se alcanza ningún estado, terminamos */
                break;
            }
            else {
                /* Agregar el estado nuevo al camino */
                camino.agregar(new Par<Conjunto<Estado>, String>(estadosActuales, simbolo));
            }
        }
       
        /*
         * Debemos comprobar cual fue la condicion que
         * provoco la finalizacion del ciclo while y
         * reaccionar de acuerdo a ello.
         */
        if (estadosActuales.estaVacio()) {
            /*
             * Llegamos a un estado desde el cual
             * no se pudo avanzar con el simbolo
             * actual.
             * Recuperamos todos los simbolos no
             * consumidos.
             */
            String entradaFaltante = simbolo;
            for (String s : buffer)
                entradaFaltante += s;
           
            /*
             * Retornamos el camino construido y la
             * subcadena que falto consumir de la
             * entrada.
             */
            return new ResultadoValidacionAFN(afn, entrada, camino, entradaFaltante);
        }
        else {
            /*
             * Hemos consumido toda la entrada, por lo
             * que retornamos el camino construido y
             * la cadena vacia como entrada faltante.
             */
            return new ResultadoValidacionAFN(afn, entrada, camino, "");
        }
    }

    /**
     * Realiza la validacion de una cadena de entrada contra
     * un AFD dado.
     * @param afd <code>AFD</code> contra el cual validar la
     * cadena de entrada.
     * @param entrada Cadena de entrada a validar.
     * @return Un objeto <code>ResultadoValidacion</code> con
     * los resultados de la validacion.
     */
    public static ResultadoValidacion validarAFD(AFD afd, String entrada) {
        /* Buffer para manipular la entrada */
        Queue<String> buffer = new LinkedList<String>();
       
        /*
         * Agregamos los caracteres de la entrada en
         * forma FIFO. Cuando se consuman todos los
         * caracteres, el siguiente sera null, que
         * representara al caracter EOF.
         */
        for (int i=0; i < entrada.length(); i++)
            buffer.add("" + entrada.charAt(i));
       
        /* Secuencia de estados recorridos */
        Conjunto<Par<Estado, String>> camino = new Conjunto<Par<Estado, String>>();
       
        /* Comenzamos por el estado inicial del AFD */
        Estado estadoActual = afd.getEstadoInicial();
        camino.agregar(new Par<Estado, String>(estadoActual, ""));
       
        /* Cada simbolo de entrada */
        String simbolo;
       
        /* Recorremos mientras hayan simbolos en la entrada */
        while ((simbolo = buffer.poll()) != null) {
            /* Nos movemos al siguiente estado */
            estadoActual = mover(estadoActual, simbolo);
           
            if (estadoActual == null) {
                /* Si no se alcanza ningún estado, terminamos */
                break;
            }
            else {
                /* Agregar el estado nuevo al camino */
                camino.agregar(new Par<Estado, String>(estadoActual, simbolo));
            }
        }
       
        /*
         * Debemos comprobar cual fue la condicion que
         * provoco la finalizacion del ciclo while y
         * reaccionar de acuerdo a ello.
         */
        if (estadoActual == null) {
            /*
             * Llegamos a un estado desde el cual
             * no se pudo avanzar con el simbolo
             * actual.
             * Recuperamos todos los simbolos no
             * consumidos.
             */
            String entradaFaltante = simbolo;
            for (String s : buffer)
                entradaFaltante += s;
           
            /*
             * Retornamos el camino construido y la
             * subcadena que falto consumir de la
             * entrada.
             */
            return new ResultadoValidacionAFD(afd, entrada, camino, entradaFaltante);
        }
        else {
            /*
             * Hemos consumido toda la entrada, por lo
             * que retornamos el camino construido y
             * la cadena vacia como entrada faltante.
             */
            return new ResultadoValidacionAFD(afd, entrada, camino, "");
        }
    }

    /**
     * Dado un Estado, recupera el Estado destino de una
     * transicion por un simbolo dado.
     * @param origen El Estado origen.
     * @param simbolo Simbolo por el cual buscar una transicion.
     * @return El Estado alcanzado por una transicion por el
     * simbolo dado o null en caso de no existir transicion
     * para dicho simbolo.
     */
    private static Estado mover(Estado origen, String simbolo) {
        for (Transicion t : origen.getTransiciones())
            if (t.getSimbolo().equals(simbolo))
                return t.getEstado();
       
        return null;
    }
}

