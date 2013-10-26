/*
 * Trabajo Practico de Compiladores 2013.
 * 10mo Semestre Ingenieria Infomatica.
 * Facultad Politecnica - UNA.
 */
package com.py.compiladores.estructuras;

/**
 * Clase que representa la abstraccion para un Automata Finito
 * Deterministico con estados minimos (AFDMin), en cuanto a
 * cantidad de estados se refiere.<br><br>
 * Un AFDMin es generado a partir de un AFD a traves del algoritmo
 * de Minimizacion de Estados.<br><br>
 * Antes del dicho algoritmo, deben eliminarse los estados inalcanzables
 * realizando un recorrido DFS o BFS a partir del estado inicial
 * del AFD. Luego de dicho algoritmo deben eliminarse aquellos estados
 * identidades que no sean estados finales.<br><br>
 * El AFDMin contiene tres instancias de AFD que representan a los
 * AFDs tras cada uno de estos algoritmos.
 * @author Cristian Aceval
 * @author Victor Franco
 */
public class AFDMin {
    /**
     * AFD original.
     */
    private AFD afdOriginal;
   
    /**
     * AFD resultante luego de aplicar la
     * eliminacion de estados inalcanzables.
     */
    private AFD afdPostInalcanzables;
   
    /**
     * AFD resultante luego de aplicar el
     * algoritmo de minimizacion.
     */
    private AFD afdPostMinimizacion;
   
    /**
     * AFD resultante luego de aplicar la
     * eliminacion de estados identidades
     * no finales.
     */
    private AFD afdPostIdentidades;
   
    /**
     * Construye un <code>AFDMin</code>.
     * @param afdOriginal El <code>AFD</code> a partir del cual fue construido este <code>AFDMin</code>.
     * @param afdPostInalcanzables El <code>AFD</code> resultante de la eliminacion de estados inalcanzables.
     * @param afdPostMinimizacion El <code>AFD</code> resultante del proceso de minimizacion.
     * @param afdPostIdentidades El <code>AFD</code> resultante de la eliminacion de estados identidades.
     */
    public AFDMin(AFD afdOriginal, AFD afdPostInalcanzables, AFD afdPostMinimizacion, AFD afdPostIdentidades) {
        this.afdOriginal          = afdOriginal;
        this.afdPostInalcanzables = afdPostInalcanzables;
        this.afdPostMinimizacion  = afdPostMinimizacion;
        this.afdPostIdentidades   = afdPostIdentidades;
    }
   
    /**
     * Obtiene el <code>AFD</code> a partir del cual fue construido este <code>AFDMin</code>.
     * @return El <code>AFD</code> a partir del cual fue construido este <code>AFDMin</code>.
     */
    public AFD getAfdOriginal() {
        return afdOriginal;
    }

    /**
     * Obtiene el <code>AFD</code> resultante de la eliminacion de estados inalcanzables.
     * @return El <code>AFD</code> resultante de la eliminacion de estados inalcanzables.
     */
    public AFD getAfdPostInalcanzables() {
        return afdPostInalcanzables;
    }

    /**
     * Obtiene el <code>AFD</code> resultante del proceso de minimizacion.
     * @return El <code>AFD</code> resultante del proceso de minimizacion.
     */
    public AFD getAfdPostMinimizacion() {
        return afdPostMinimizacion;
    }

    /**
     * Obtiene el <code>AFD</code> resultante de la eliminacion de estados identidades.
     * @return El <code>AFD</code> resultante de la eliminacion de estados identidades.
     */
    public AFD getAfdPostIdentidades() {
        return afdPostIdentidades;
    }
   
    /**
     * Verifica si la eliminacion de estados inalcanzables produjo algún
     * cambio sobre el <code>AFD</code> original.
     * @return <code>true</code> si la eliminacion de estados inalcanzables
     * produjo algún cambio sobre el <code>AFD</code> original, <code>false</code>
     * en caso contrario.
     */
    public boolean inalcanzablesEliminados() {
        if (afdOriginal.toString().equals(afdPostInalcanzables.toString()))
            return false;
        else
            return true;
    }
   
    /**
     * Verifica si la eliminacion de estados identidades produjo algún
     * cambio sobre el <code>AFD</code> resultante de la minimizacion.
     * @return <code>true</code> si la eliminacion de estados identidades
     * produjo algún cambio sobre el <code>AFD</code> resultante de la
     * minimizacion, <code>false</code> en caso contrario.
     */
    public boolean identidadesEliminados() {
        if (afdPostMinimizacion.toString().equals(afdPostIdentidades.toString()))
            return false;
        else
            return true;
    }
}
