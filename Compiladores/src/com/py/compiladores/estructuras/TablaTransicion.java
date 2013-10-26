package com.py.compiladores.estructuras;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Esta clase representa la tabla de transicion
 * de estados de un Automata, para ser mostrada
 * en un JTable.
 * @author Cristian Aceval
 * @author Victor Franco
 */
public class TablaTransicion implements TableModel {
   
    /**
     * Valores de la tabla de transiciones
     */
    private Object[][] datos;
   
    /**
     * Cabecera de la tabla de transiciones
     */
    private String[] cabecera;
   
    /**
     * Construye una <code>TablaTransicion</code> con
     * la cabecera y datos especificados.
     * @param cabecera Las cabeceras para cada columna de la tabla.
     * @param datos Los datos de la tabla.
     */
    public TablaTransicion(String[] cabecera, Object[][] datos) {
        this.cabecera = cabecera;
        this.datos    = datos;
    }

    public int getRowCount() {
        return datos.length;
    }

    public int getColumnCount() {
        return cabecera.length;
    }

    public String getColumnName(int columnIndex) {
        return cabecera[columnIndex];
    }

    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return datos[rowIndex][columnIndex];
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        datos[rowIndex][columnIndex] = aValue;
    }
   
    /**
     * Modifica el valor de la cabecera de una columna.
     * @param aValue El nuevo valor de la cabecera.
     * @param columnIndex La cabecera a modificar.
     */
    public void setHeaderAt(String aValue, int columnIndex) {
        cabecera[columnIndex] = aValue;
    }

    public void addTableModelListener(TableModelListener listener) {
        return;
    }

    public void removeTableModelListener(TableModelListener listener) {
        return;
    }
}


