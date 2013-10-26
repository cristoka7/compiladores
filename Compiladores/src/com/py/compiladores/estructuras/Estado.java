/**
 * 
 */
package com.py.compiladores.estructuras;

import com.py.compiladores.analisis.Alfabeto;
import java.util.HashMap;

/**
 * Esta clase representa un estado para un Automata Finito.
 * @author Cristian Aceval
 * @author Victor Franco
 */
public class Estado implements Comparable<Estado> {

    /**
     * Identificador de este Estado.
     */
    private int identificador;
   
    /**
     * Variable que indica si el Estado es final.
     */
    private boolean esFinal;
   
    /**
     * Etiqueta de este estado.
     */
    private String etiqueta;
   
    /**
     * Conjunto de transiciones del Estado.
     */
    private Conjunto<Transicion> transiciones;
   
    /**
     * Indica si este Estado ya fue visitado
     * durante algún recorrido realizado sobre
     * el Automata que contiene a este Estado.
     */
    private boolean visitado;
   
    /**
     * Crea un <code>Estado</code> no final con un identificador determinado.
     * @param identificador El identificador del nuevo estado.
     */
    public Estado(int identificador) {
        this(identificador, false);
    }
   
    /**
     * Crea un <code>Estado</code> con un identificador determinado,
     * que sera final o no de acuerdo al valor de <code>esFinal</code>.
     * @param identificador El identificador para este <code>Estado</code>.
     * @param esFinal Determina si el <code>Estado</code> es final o no.
     */
    public Estado(int identificador, boolean esFinal) {
        setIdentificador(identificador);
        setEsFinal(esFinal);
        setEtiqueta(String.valueOf(identificador));
        transiciones = new Conjunto<Transicion>();
    }
   
    /**
     * Establece un nuevo identificador para este <code>Estado</code>.
     * @param identificador El nuevo identificador para este <code>Estado</code>.
     */
    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }
   
    /**
     * Obtiene el identificador para este <code>Estado</code>.
     * @return El identificador del <code>Estado</code>.
     */
    public int getIdentificador() {
        return identificador;
    }
   
    /**
     * Obtiene la etiqueta de este <code>Estado</code>.
     * @return Un objeto <code>String</code> representando
     * la etiqueta de este <code>Estado</code>.
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Establece una nueva etiqueta para este <code>Estado</code>.
     * @param etiqueta La nueva etiqueta para este <code>Estado</code>.
     */
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
   
    /**
     * Obtiene el estado de aceptacion del <code>Estado</code>.
     * @return <code>true</code> si el <code>Estado</code> es final,
     * <code>false</code> en caso contrario.
     */
    public boolean getEsFinal() {
        return esFinal;
    }

    /**
     * Establece un nuevo estado de aceptacion para este <code>Estado</code>.
     * @param esFinal El nuevo estado de aceptacion para este <code>Estado</code>.
     * Si el valor <code>true</code> el <code>Estado</code> se convertira en
     * final, si es <code>false</code> el <code>Estado</code> se convertira
     * en no final.
     */
    public void setEsFinal(boolean esFinal) {
        this.esFinal = esFinal;
    }
   
    /**
     * Obtiene el estado de inicio del <code>Estado</code>.
     * @return <code>true</code> si el <code>Estado</code> es inicial,
     * <code>false</code> en caso contrario.
     */
    public boolean getEsInicial() {
        return identificador == 0;
    }
   
    /**
     * Obtiene el conjunto de transiciones de este <code>Estado</code>.
     * @return El conjunto de transiciones del <code>Estado</code>.
     */
    public Conjunto<Transicion> getTransiciones() {
        return transiciones;
    }
   
    /**
     * Obtiene una tabla hash con las transiciones de este <code>Estado</code>
     * a traves de los simbolos de un <code>Alfabeto</code> dado.
     * @param alfabeto El <code>Alfabeto</code> sobre el cual buscar transiciones
     * en el <code>Estado</code>.
     * @return Un <code>Hashtable</code> que contiene el <code>Estado</code>
     * alcanzado para cada simbolo de <code>alfabeto</code>. Si no existe transicion
     * para un simbolo dado, el valor sera igual a <code>null</code>.
     */
    public HashMap<String, Estado> getTransicionesSegunAlfabeto(Alfabeto alfabeto) {
        HashMap<String, Estado> trans = new HashMap<String, Estado>();
       
        /* Rellenamos todo con null */
        for (String s : alfabeto)
            trans.put(s, null);
       
        /* Reemplazamos las transiciones existentes */
        for (Transicion t : getTransiciones())
            trans.put(t.getSimbolo(), t.getEstado());
       
        return trans;
    }
   
    /**
     * Obtiene es estado de visitado de este <code>Estado</code>.
     * @return <code>true</code> si este <code>Estado</code> ya
     * ha sido visitado, <code>false</code> en caso contrario.
     */
    public boolean getVisitado() {
        return visitado;
    }
   
    /**
     * Establece el nuevo estado de visitado de este <code>Estado</code>.
     * @param visitado Nuevo estado de visitado de este <code>Estado</code>.
     */
    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }
   
    /**
     * Verifica si el <code>Estado</code> es un estado identidad.
     * @return <code>true</code> si este <code>Estado</code> es un
     * estado identidad, <code>false</code> en caso contrario.
     */
    public boolean getEsIdentidad() {
        for (Transicion trans : getTransiciones())
            if (!this.equals(trans.getEstado()))
                return false;
       
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
       
        if (getClass() != obj.getClass())
            return false;
       
        final Estado other = (Estado) obj;
        if (this.identificador == other.identificador)
            return true;
       
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.identificador;
        return hash;
    }

    public int compareTo(Estado obj) {
        return this.identificador - obj.identificador;
    }
   
    @Override
    public String toString() {
        String valor;
       
        if (getEtiqueta().equals(""))
            valor = String.valueOf(identificador);
        else
            valor = getEtiqueta();
       
        if (getEsInicial())
            valor += "i";
       
        if (getEsFinal())
            valor += "f";
               
        return valor;
    }
}

