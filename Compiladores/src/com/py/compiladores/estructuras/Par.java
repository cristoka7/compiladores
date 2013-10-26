package com.py.compiladores.estructuras;


/**
 * Esta clase representa un par de objetos de cualquier clase.
 * @param <A> Clase del primer elemento del par.
 * @param <B> Clase del segundo elemento del par.
 * @author Cristian Aceval
 * @author Victor Franco
 */
public class Par<A extends Comparable<A>, B extends Comparable<B>>
                        implements Comparable<Par<A, B>> {

    /**
     * Primer elemento.
     */
    private A primero;
   
    /**
     * Segundo elemento.
     */
    private B segundo;
   
    /**
     * Construye un <code>Par</code> con el primer
     * elemento especificado.
     * @param primero Primer elemento del <code>Par</code>.
     */
    public Par(A primero) {
        this(primero, null);
    }

    /**
     * Construye un <code>Par</code> con ambos elementos
     * especificados.
     * @param primero Primer elemento del <code>Par</code>.
     * @param segundo Segundo elemento del <code>Par</code>.
     */
    public Par(A primero, B segundo) {
        setPrimero(primero);
        setSegundo(segundo);
    }

    /**
     * Obtiene el primer elemento de este <code>Par</code>.
     * @return El primer elemento de este <code>Par</code>.
     */
    public A getPrimero() {
        return primero;
    }

    /**
     * Establece el primer elemento de este <code>Par</code>.
     * @param primero El nuevo primer elemento de este <code>Par</code>.
     */
    public void setPrimero(A primero) {
        this.primero = primero;
    }

    /**
     * Obtiene el segundo elemento de este <code>Par</code>.
     * @return El segundo elemento de este <code>Par</code>.
     */
    public B getSegundo() {
        return segundo;
    }

    /**
     * Establece el segundo elemento de este <code>Par</code>.
     * @param segundo El nuevo segundo elemento de este <code>Par</code>.
     */
    public void setSegundo(B segundo) {
        this.segundo = segundo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        final Par<A, B> other = (Par<A, B>) obj;
        if (this.primero.equals(other.primero) && this.segundo.equals(other.segundo))
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.primero != null ? this.primero.hashCode() : 0);
        hash = 79 * hash + (this.segundo != null ? this.segundo.hashCode() : 0);
        return hash;
    }
   
    @Override
    public String toString() {
        if (this.primero != null && this.segundo != null)
            return "(" + this.primero + ", " + this.segundo + ")";
        else if (this.primero != null)
            return "(" + this.primero + ")";
        else if (this.segundo != null)
            return "(null, " + this.segundo + ")";
        else
            return "()";
    }

    public int compareTo(Par<A, B> obj) {
        int diferencia = this.primero.compareTo(obj.primero);
       
        if (diferencia == 0)
            return this.segundo.compareTo(obj.segundo);
        else
            return diferencia;
    }
}

