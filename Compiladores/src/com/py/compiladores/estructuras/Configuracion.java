/**
 * 
 */
package com.py.compiladores.estructuras;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Clase para guardar las configuraciones
 * de la aplicacion.
 * @author Cristian Aceval
 * @author Victor Franco
 */
@Root
public class Configuracion {
    /**
     * Ruta para binarios de GraphViz.
     */
    @Element
    private String graphvizPath;
   
    /**
     * Ruta para Directorio Temporal.
     */
    @Element
    private String tempPath;
   
    /**
     * Constructor por defecto.
     */
    public Configuracion() {
       
    }

    /**
     * Obtiene la ruta al directorio
     * de los binarios de GraphViz.
     * @return La ruta a los binarios de GraphViz.
     */
    public String getGraphvizPath() {
        return graphvizPath;
    }

    /**
     * Establece la ruta al directorio de
     * los binarios de GraphViz.
     * @param graphvizPath La nueva ruta para los
     * binarios de GraphViz.
     */
    public void setGraphvizPath(String graphvizPath) {
        this.graphvizPath = graphvizPath;
    }

    /**
     * Obtiene la ruta para el directorio
     * de archivos temporales.
     * @return La ruta para archivos temporales.
     */
    public String getTempPath() {
        return tempPath;
    }

    /**
     * Establece la ruta para el directorio
     * de archivos temporales.
     * @param tempPath La ruta para archivos temporales.
     */
    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }
}

