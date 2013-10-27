/**
 * 
 */
package com.py.compiladores.analisis;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

/**
 * Clase que representa el alfabeto sobre el cual se construye
 * una expresion regular.
 * @author Cristian Aceval
 * @author Victor Franco
 */
public class Alfabeto implements Iterable<String> {
   
    /**
     * Cadena que representa el simbolo vacio.
     */
    public static final String VACIO = "é";
   
    /**
     * Conjunto de simbolos del alfabeto.
     */
    private Vector<String> simbolos;
   
    /**
     * Contructor de la clase.
     * @param caracteres Cadena de caracteres con los simbolos del alfabeto.
     */
    public Alfabeto(String caracteres) {      
        String[] arregloTemp = new String[caracteres.length()];
        for (int i=0; i < caracteres.length(); i++)
            arregloTemp[i] = "" + caracteres.charAt(i);
       
        Arrays.sort(arregloTemp);
       
        simbolos = new Vector<String>(arregloTemp.length);
        for (int i=0; i < arregloTemp.length; i++) {
            String temp = arregloTemp[i];
            if (!simbolos.contains(temp))
                simbolos.add(temp);
        }  
    }
   
    /**
     * Retorna el tamaño de este alfabeto, es decir
     * la cantidad de simbolos que contiene.
     * @return Cantidad de simbolos del alfabeto.
     */
    public int getCantidad() {
        return simbolos.size();
    }
   
    /**
     * Retorna un determinado simbolo del alfabeto.
     * @param pos Posicion del simbolo dentro del alfabeto.
     * @return El simbolo del alfabeto ubicado en la posicion <code>pos</code>.
     */
    public String getSimbolo(int pos) {
        if (pos == getCantidad())
            return Alfabeto.VACIO;
        else
            return simbolos.get(pos);
    }
   
    /**
     * Metodo que permite conocer si un caracter dado pertenece
     * a este alfabeto.
     * @param caracter El caracter que se quiere saber si pertenece al alfabeto.
     * @return <code>true</code> si el caracter pertenece al alfabeto, <code>false</code> en caso contrario.
     */
    public boolean contiene(String caracter) {
        return simbolos.contains(caracter);
    }
   
    /**
     * Retorna la posicion de la primera ocurrencia
     * de un caracter en este <code>Alfabeto</code>.
     * @param caracter El caracter a buscar.
     * @return La posicion de la primera ocurrencia
     * de un caracter en este <code>Alfabeto</code>,
     * o -1 si el caracter no esta contenido.
     */
    public int obtenerPosicion(String caracter) {
        if (caracter.equals(Alfabeto.VACIO))
            return getCantidad();
        else
            return simbolos.indexOf(caracter);
    }
   
    @Override
    public String toString() {
        String salida = "{";
       
        for (int i=0; i < this.getCantidad(); i++) {
            salida += simbolos.get(i);
           
            if (i < this.getCantidad()-1)
                salida += ", ";
        }
       
        return salida;
    }
   
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
       
        if (getClass() != obj.getClass()) {
            return false;
        }
       
        final Alfabeto other = (Alfabeto) obj;
       
        // Si los tamaños son distintos, no pueden ser iguales.
        if (other.getCantidad() != this.getCantidad())
            return false;
       
        // Verificamos cada uno de los simbolos
        for (int i=0; i < this.getCantidad(); i++) {
            String tmp1 = this.getSimbolo(i);
            String tmp2 = other.getSimbolo(i);
           
            if (!tmp1.equals(tmp2))
                return false;
        }
       
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.simbolos != null ? this.simbolos.hashCode() : 0);
        return hash;
    }

    public Iterator<String> iterator() {
        return simbolos.iterator();
    }
}