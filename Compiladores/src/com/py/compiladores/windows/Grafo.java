/**
 * 
 */
package com.py.compiladores.windows;

import com.py.compiladores.analisis.Alfabeto;
import com.py.compiladores.estructuras.Automata;
import com.py.compiladores.estructuras.Configuracion;
import com.py.compiladores.estructuras.Conjunto;
import com.py.compiladores.estructuras.Estado;
import com.py.compiladores.estructuras.Transicion;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * Clase donde se dibujan los grafos
 */
public class Grafo {

    /* Automata a dibujar */
    private Automata AF;
    
    String graphicVitzPath = new String("C:\\release\\bin");
    String tempPath = new String("C:" + "\\" + "release");
    
    String nombreGrap = new String("graph");
    
    

    /**
     * Constructor Principal
     * @param AF
     */
    public Grafo(Automata AF) {
        this.AF = AF;
    }
    
    /*
     * Función para Generar y cargar la Imagen del Automata correspondiente.
     * @param e Estado que se debe pintar de rojo.
     */
    public ImageIcon ManejarImagen(Estado e) {
        
        /* Paso 1. Crear Archivo de Salida  */
        String marca = "" + System.currentTimeMillis();
        String salidaDot = tempPath + File.separator + nombreGrap.replace(" ", "");
        salidaDot +=  "-" + marca;
        salidaDot += ".dot";
        
        GrafoToDot(salidaDot, e);

        /* Paso 2. Crear la Imagen */        
        String dibujo = tempPath+ File.separator  + nombreGrap.replace(" ", "");
        dibujo += "-" + marca;
        dibujo += ".png";
        
        try {
            String exeFile = graphicVitzPath + File.separator + "dot";
            ejecutarDotExe(exeFile, salidaDot, dibujo);
        } catch (IOException ex) {
        	JFrame frameDeError = new JFrame();
			frameDeError.setBounds(100, 100, 487, 369);
			frameDeError.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			frameDeError.setTitle("ERROR");
			JTextArea textArea_1 = new JTextArea();
			textArea_1.setEditable(false);
			textArea_1.setText(ex.getMessage());
			frameDeError.getContentPane().add(textArea_1);
			frameDeError.setLocationRelativeTo(null);
			frameDeError.setVisible(true);
        }

        /* Paso 3. Cargar la imagen en la Ventana */
        return new ImageIcon(dibujo);
    }
    
    /**
     * Paso 1 para el Manejo de Imagenes (Crear Archivo de Salida).
     * @param salidaDot
     */
    private void GrafoToDot(String salidaDot, Estado actual){
        PrintWriter dotWriter = null;
        try {
            dotWriter = new PrintWriter(new File(salidaDot), new String("ISO-8859-1"));
        } catch (IOException ex) {
        	JFrame frameDeError = new JFrame();
			frameDeError.setBounds(100, 100, 487, 369);
			frameDeError.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			frameDeError.setTitle("ERROR");
			JTextArea textArea_1 = new JTextArea();
			textArea_1.setEditable(false);
			textArea_1.setText(ex.getMessage());
			frameDeError.getContentPane().add(textArea_1);
			frameDeError.setLocationRelativeTo(null);
			frameDeError.setVisible(true);
        }
        
        /* Paso 1.1. Escribir Cabecera  */
        String nombre = "\"".concat(this.nombreGrap+"\"");
        int longitud = AF.cantidadEstados();
        if (longitud < 5)
            longitud = 5;
        
        dotWriter.print("digraph " + nombre + "{\n");
        dotWriter.print("\trankdir=LR;\n");
        dotWriter.print("\tsize=\""+longitud+",5\"\n");
        dotWriter.print("\toverlap=\"scale\";\n");

        String linea;
        for (Estado e : AF.getEstados()) {
            if (e.getEsFinal())
                linea = "\"" + e + "\"" + "[shape=doublecircle, color=red, style=filled ";
            else
                linea = "\"" + e + "\"" + "[shape=circle, color=yellow, style=filled";
            
            if (e.equals(actual))
                linea += ", style=filled, fillcolor=red, color=red];\n";
            else
                linea += "];\n";
                
            dotWriter.print(linea);
        }
        
        /* Agregamos las transiciones */
        for (Estado e : AF.getEstados()) {
            String origen = e.toString();
            Conjunto<Transicion> transiciones = e.getTransiciones();
            for (Transicion t : transiciones) {
                String etiqueta = t.getSimbolo();
                
                if (etiqueta.equals(Alfabeto.VACIO))
                    etiqueta = "".concat("&epsilon;");
                
                dotWriter.print("\t\"" + origen + "\" -> \"" + t.getEstado() + "\" [ label = \"" +
                        etiqueta + "\" ];\n");
            }
        }
        dotWriter.println("}");
        dotWriter.close();        
    }

    /*
     * Paso 2 para el Manejo de Imagenes (Crear Imagen).
     */
    private void ejecutarDotExe(String exeFile, String dotFileName, String outFileName) throws IOException {

        final String exeCmd = exeFile + " -Tpng " + dotFileName;

        Process p = Runtime.getRuntime().exec(exeCmd);

        PrintStream ps = new PrintStream(outFileName);
        InputStream is = p.getInputStream();				
	byte[] buf = new byte[32 * 1024];
        int len;
        while (true) {
            len = is.read(buf);
            if (len <= 0){
                break;
            }
            ps.write(buf, 0, len);
        }        
        is.close();
        ps.close();
        p.destroy();
    }

}
