package com.py.compiladores.windows;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.py.compiladores.algoritmos.Subconjuntos;
import com.py.compiladores.analisis.Alfabeto;
import com.py.compiladores.analisis.AnalizadorSintactico;
import com.py.compiladores.estructuras.AFD;
import com.py.compiladores.estructuras.AFN;
import com.py.compiladores.estructuras.Log;
import com.py.compiladores.estructuras.TablaTransicion;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class Salidas extends JFrame {

	private JPanel contentPane;


	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public Salidas(String alfabeto, String ExpReg) throws Exception {
		setTitle("Salidas");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 500, 439);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*Aqui hacemos la construccion del alfabeto y el analisis sintactico de la expresion*/
		Alfabeto alfa = new Alfabeto(alfabeto);
		AnalizadorSintactico as = new AnalizadorSintactico(alfa, ExpReg);
		/*
         *  CONVERSION REGEX -> AFN
         *  ALGORITMO DE THOMPSON
         */ 
		AFN salida = as.analizar();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 464, 366);
		/*Aqui el primer tab-panel correspondiente al AFN*/
		JPanel panelAFN = new JPanel();
		tabbedPane.addTab("AFN", panelAFN);
		panelAFN.setLayout(null);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 459, 338);
		scrollPane.setAutoscrolls(true);
		panelAFN.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setText(salida.toString());
		scrollPane.setViewportView(textArea);
		/*Hasta aqui  el primer tab-panel correspondiente al AFN*/
		
		/*Aqui el segundo tab-panel correspondiente a la tabla de transicion AFN*/
		JPanel panelTransicionAFN = new JPanel();
		tabbedPane.addTab("Tabla Transicion AFN", panelTransicionAFN);
		panelTransicionAFN.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 459, 338);
		scrollPane_1.setAutoscrolls(true);
		panelTransicionAFN.add(scrollPane_1);
		
		/*Ahora formatearemos la salida para la impresion de la tabla de transicion AFN*/
		Log output = new Log();
		TablaTransicion tabla = salida.getTablaTransicion();
		
        for (int i=0; i < tabla.getColumnCount(); i++)
        	output.agregar(tabla.getColumnName(i) + "\t");
       
        output.nuevaLinea();
        for (int i=0; i < tabla.getRowCount(); i++) {
            for (int j=0; j < tabla.getColumnCount(); j++)
                output.agregar(tabla.getValueAt(i, j) + "\t");
           
            output.nuevaLinea();
        }
		/*Hasta aqui el formateo para la impresion de la tabla de transicion*/
        
        
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		textArea_1.setText(output.toString());
		scrollPane_1.setViewportView(textArea_1);
		/*Hasta Aqui el segundo tab-panel correspondiente a la tabla de transicion AFN*/
		
		/*Aqui el tercer tab-panel correspondiente a las derivaciones*/
		JPanel panelDerivaciones = new JPanel();
		tabbedPane.addTab("Derivaciones", panelDerivaciones);
		panelDerivaciones.setLayout(null);
		
		
		JScrollPane scrollPaneDerivaciones = new JScrollPane();
		scrollPaneDerivaciones.setBounds(0, 0, 459, 338);
		scrollPaneDerivaciones.setAutoscrolls(true);
		panelDerivaciones.add(scrollPaneDerivaciones);
		
		JTextArea textAreaDerivaciones = new JTextArea();
		textAreaDerivaciones.setEditable(false);
		textAreaDerivaciones.setText("Derivaciones: \n"+as.getLog());
		scrollPaneDerivaciones.setViewportView(textAreaDerivaciones);
		/*Hasta aqui  el tercer tab-panel correspondiente a las derivaciones*/
		
		/*Aqui el cuarto tab-panel correspondiente al AFD*/
		JPanel panelAFD = new JPanel();
		tabbedPane.addTab("AFD", panelAFD);
		panelAFD.setLayout(null);
		
		
		JScrollPane scrollPaneAFD = new JScrollPane();
		scrollPaneAFD.setBounds(0, 0, 459, 338);
		scrollPaneAFD.setAutoscrolls(true);
		panelAFD.add(scrollPaneAFD);
		
		AFD afd = Subconjuntos.getAFD(salida);
		
		JTextArea textAreaAFD = new JTextArea();
		textAreaAFD.setEditable(false);
		textAreaAFD.setText(afd.toString());
		scrollPaneAFD.setViewportView(textAreaAFD);
		/*Hasta aqui  el cuarto tab-panel correspondiente al AFD*/
		
		
		contentPane.add(tabbedPane);
		
		
	}
}
