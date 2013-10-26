package com.py.compiladores.estructuras;

import com.py.compiladores.analisis.Alfabeto;

/**
 * Clase que representa la abstraccion para un Automata Finito
 * Deterministico (AFD). Un AFD es generado a partir de un AFN
 * a traves del algoritmo de Subconjuntos.
 * @author Cristian Aceval
 * @author Victor Franco
 */
public class AFD extends Automata {
   
    /**
     * Conjunto de estados del AFN contenidos
     * en cada uno de los estados de este AFD.
     */
    private Conjunto<Conjunto<Estado>> estadosD;
   
    /**
     * Constructor por defecto.
     */
    public AFD() {
       this(null, "");
    }
   
    /**
     * Construye un <code>AFD</code> con un determinado <code>Alfabeto</code>
     * y una determinada expresion regular.
     * @param alfabeto El <code>Alfabeto</code> de este <code>AFD</code>.
     * @param exprReg La expresion regular para este <code>AFD</code>.
     */
    public AFD(Alfabeto alfabeto, String exprReg) {
        super(alfabeto, exprReg);
        estadosD = null;
    }
   
    /**
     * Obtiene el <code>Conjunto</code> de <code>Estado</code>s
     * del <code>AFN</code> contenidos en cada uno de los
     * <code>Estado</code>s de este <code>AFD</code>.
     * @return El <code>Conjunto</code> de <code>Estado</code>s
     * contenidos en cada uno de los <code>Estado</code>s de
     * este <code>AFD</code>.
     */
    public Conjunto<Conjunto<Estado>> getEstadosD() {
        return estadosD;
    }

    /**
     * Establece el <code>Conjunto</code> de <code>Estado</code>s
     * del <code>AFN</code> contenidos en cada uno de los
     * <code>Estado</code>s de este <code>AFD</code>.
     * @param estadosD El nuevo <code>Conjunto</code> de
     * <code>Estado</code>s contenidos en cada uno de los
     * <code>Estado</code>s de este <code>AFD</code>.
     */
    public void setEstadosD(Conjunto<Conjunto<Estado>> estadosD) {
        this.estadosD = estadosD;
    }
   
    public String estadosDtoString() {
        String str = "";
       
        for (int i=0; i < estadosD.cantidad(); i++) {
            Conjunto<Estado> conj = estadosD.obtener(i);
            Estado actual = getEstado(i);
           
            str += actual + " --> " + conj + "\n";
        }
       
        return str;
    }
   
    /**
     * Retorna la tabla de transicion de estados.
     * @return La tabla de transicion de estados.
     */
    public TablaTransicion getTablaTransicion() {
        TablaTransicion tabla;
       
        if (getEstadosD() != null) {
            int cantFil = getEstados().cantidad();
            int cantCol = getAlfabeto().getCantidad() + 2;

            tabla = cargarTablaTransicion(cantFil, cantCol, 1);
            tabla.setHeaderAt("Estados del AFN", 0);

            for (int i=0; i < estadosD.cantidad(); i++)
                tabla.setValueAt(estadosD.obtener(i), i, 0);
        }
        else {
            int cantFil = getEstados().cantidad();
            int cantCol = getAlfabeto().getCantidad() + 1;

            tabla = cargarTablaTransicion(cantFil, cantCol, 0);
        }
       
        return tabla;
    }
}

