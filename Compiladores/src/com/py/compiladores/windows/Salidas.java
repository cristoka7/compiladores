package com.py.compiladores.windows;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;



import com.py.compiladores.algoritmos.Minimizacion;
import com.py.compiladores.algoritmos.Subconjuntos;
import com.py.compiladores.analisis.Alfabeto;
import com.py.compiladores.analisis.AnalizadorSintactico;
import com.py.compiladores.estructuras.AFD;
import com.py.compiladores.estructuras.AFDMin;
import com.py.compiladores.estructuras.AFN;
import com.py.compiladores.estructuras.Automata;
import com.py.compiladores.estructuras.Log;
import com.py.compiladores.estructuras.TablaTransicion;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;

import java.awt.TextField;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Salidas extends JFrame {

	private JPanel contentPane;
	private javax.swing.JLabel Imagen;


	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public Salidas(String alfabeto, String ExpReg) throws Exception {
		setTitle("Salidas");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 900, 700);
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
		final AFN salida = as.analizar();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 0, 850, 650);
		/*Aqui el primer tab-panel correspondiente al AFN*/
		JPanel panelAFN = new JPanel();
		tabbedPane.addTab("AFN", panelAFN);
		panelAFN.setLayout(null);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 850, 610);
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
		scrollPane_1.setBounds(0, 0, 850, 610);
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
		scrollPaneDerivaciones.setBounds(0, 0, 850, 610);
		scrollPaneDerivaciones.setAutoscrolls(true);
		panelDerivaciones.add(scrollPaneDerivaciones);
		
		JTextArea textAreaDerivaciones = new JTextArea();
		textAreaDerivaciones.setEditable(false);
		textAreaDerivaciones.setText("Derivaciones: \n"+as.getLog());
		scrollPaneDerivaciones.setViewportView(textAreaDerivaciones);
		/*Hasta aqui  el tercer tab-panel correspondiente a las derivaciones*/
		
		/*Aqui el cuarto tab-panel correspondiente al AFD*/
		JPanel panelAFD = new JPanel();
		tabbedPane.addTab("AFD Equivalente", panelAFD);
		panelAFD.setLayout(null);
		
		
		JScrollPane scrollPaneAFD = new JScrollPane();
		scrollPaneAFD.setBounds(0, 0, 850, 610);
		scrollPaneAFD.setAutoscrolls(true);
		panelAFD.add(scrollPaneAFD);
		
		final AFD afd = Subconjuntos.getAFD(salida);
		
		JTextArea textAreaAFD = new JTextArea();
		textAreaAFD.setEditable(false);
		textAreaAFD.setText(afd.toString());
		scrollPaneAFD.setViewportView(textAreaAFD);
		/*Hasta aqui  el cuarto tab-panel correspondiente al AFD*/
		
		/*Aqui el quinto tab-panel correspondiente a la tabla de Transicion AFD*/
		JPanel panelTransicionAFD = new JPanel();
		tabbedPane.addTab("Tabla Transicion AFD", panelTransicionAFD);
		panelTransicionAFD.setLayout(null);
		
		
		JScrollPane scrollPaneTransAFD = new JScrollPane();
		scrollPaneTransAFD.setBounds(0, 0, 850, 610);
		scrollPaneTransAFD.setAutoscrolls(true);
		panelTransicionAFD.add(scrollPaneTransAFD);
		
		 /* Imprimir la Tabla transicion del AFD */
		Log output1 = new Log();
        TablaTransicion tabla2 = afd.getTablaTransicion();
       
        for (int i=0; i < tabla2.getColumnCount(); i++)
        	output1.agregar(tabla2.getColumnName(i) + "\t\t" );
       
        output1.nuevaLinea();
        for (int i=0; i < tabla2.getRowCount(); i++) {
            for (int j=0; j < tabla2.getColumnCount(); j++)
            	output1.agregar(tabla2.getValueAt(i, j)+ "\t\t");
           
            output1.nuevaLinea();
        }
        
		
		JTextArea textAreaTransAFD = new JTextArea();
		textAreaTransAFD.setEditable(false);
		textAreaTransAFD.setText(output1.toString());
		scrollPaneTransAFD.setViewportView(textAreaTransAFD);
		/*Hasta aqui  el quinto tab-panel correspondiente a la tabla de Transicion AFD*/
		
		/*Aqui el sexto tab-panel correspondiente al proceso de minimizacion*/
		JPanel panelAFDMinim = new JPanel();
		tabbedPane.addTab("AFD Minimo Equiv.", panelAFDMinim);
		panelAFDMinim.setLayout(null);
		
		
		JScrollPane scrollPaneAFDMinim = new JScrollPane();
		scrollPaneAFDMinim.setBounds(0, 0, 850, 610);
		scrollPaneAFDMinim.setAutoscrolls(true);
		panelAFDMinim.add(scrollPaneAFDMinim);
		
		AFDMin afdMin = Minimizacion.getAFDminimo(afd);
		Log outputMinimo = new Log();
		outputMinimo.agregar("AFD Post Inalcanzables ( ):\n \n");
		outputMinimo.agregar(afdMin.inalcanzablesEliminados() ? "<>" : "== "+ afdMin.getAfdPostInalcanzables());
		outputMinimo.nuevaLinea();
        outputMinimo.agregar("AFD Post Minimizacion:\n \n" + afdMin.getAfdPostMinimizacion());
        outputMinimo.nuevaLinea();
        outputMinimo.agregar("AFD Post Identidades ( ):\n \n");
        outputMinimo.agregar(afdMin.identidadesEliminados() ? "<>" : "== "+ afdMin.getAfdPostIdentidades());
		
		JTextArea textAreaAFDMinim = new JTextArea();
		textAreaAFDMinim.setEditable(false);
		textAreaAFDMinim.setText(outputMinimo.toString());
		scrollPaneAFDMinim.setViewportView(textAreaAFDMinim);
		/*Hasta aqui  el sexto tab-panel correspondiente al proceso de minimizacion*/
		
		/*Aqui el septimo tab-panel correspondiente a la impresion del proceso de minimizacion*/
		JPanel panelAFDProcMin = new JPanel();
		tabbedPane.addTab("Proceso de Minimizacion", panelAFDProcMin);
		panelAFDProcMin.setLayout(null);
		
		
		JScrollPane scrollPaneAFDProcMin = new JScrollPane();
		scrollPaneAFDProcMin.setBounds(0, 0, 850, 610);
		scrollPaneAFDProcMin.setAutoscrolls(true);
		panelAFDProcMin.add(scrollPaneAFDProcMin);
		
		
		JTextArea textAreaAFDProcMin = new JTextArea();
		textAreaAFDProcMin.setEditable(false);
		textAreaAFDProcMin.setText(Minimizacion.getLog().toString());
		scrollPaneAFDProcMin.setViewportView(textAreaAFDProcMin);
		/*Hasta aqui  el septimo tab-panel correspondiente a la impresion del proceso de minimizacion*/
		
		/*Aqui el octavo tab-panel correspondiente a la validacion de cadenas*/
		JPanel panelValid = new JPanel();
		tabbedPane.addTab("Validacion de AFN", panelValid);
		panelValid.setLayout(null);
		
		JLabel lblCadenaAValidar = new JLabel("Cadena a Validar");
		lblCadenaAValidar.setBounds(0, 23, 108, 22);
		panelValid.add(lblCadenaAValidar);
		
		final TextField textField = new TextField();
		textField.setBounds(112, 23, 175, 22);
		panelValid.add(textField);
		
		JScrollPane scrollPaneValid = new JScrollPane();
		scrollPaneValid.setBounds(293, 0, 510, 300);
		scrollPaneValid.setAutoscrolls(true);
		panelValid.add(scrollPaneValid);
		
		final JTextArea textArea_2 = new JTextArea();
		textArea_2.setBounds(293, 23, 498, 272);
		scrollPaneValid.setViewportView(textArea_2);
		
		/*Aqui se realiza el proceso de Validacion de AFN*/
		JButton btnNewButton = new JButton("Validar");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				String valor = textField.getText();
		        if (valor == null)
		            valor = "";

		        // Verificar cadena con el Automata correspondiente
		        com.py.compiladores.algoritmos.ResultadoValidacion Result = null;
		        
		        Result = com.py.compiladores.algoritmos.Validacion.validarAFN((AFN) salida, valor);

		        String mensaje = null;
		        if (!Result.esValido()) {
		            mensaje = "ERROR: La cadena ingresada NO ES ACEPTADA.\n\n";
		            String Resto = Result.getEntradaFaltante();
		            if (!Resto.isEmpty()) {
		                mensaje += "Se ha recorrido " + Result.getCamino() + " y no se " +
		                        "ha podido seguir avanzando. FALTA CONSUMIR: \"" + Resto + "\".";
		            } else {
		                mensaje += "Se ha recorrido " + Result.getCamino() + " y se ha consumido" +
		                        " la cadena de entrada sin llegar a un ESTADO FINAL. \n";
		            }
		        } else {
		            mensaje = "OK. La cadena es ACEPTADA.\nSe ha recorrido " + Result.getCamino();
		        }
		        textArea_2.setText("" + mensaje);
			}
		});
		btnNewButton.setBounds(85, 106, 114, 53);
		panelValid.add(btnNewButton);
		/*Hasta aqui el noveno tab-panel correspondiente a la validacion de cadenas*/
		
		/*Aqui el octavo tab-panel correspondiente a la validacion de cadenas AFD*/
		JPanel panelValidAFD = new JPanel();
		tabbedPane.addTab("Validacion de AFD", panelValidAFD);
		panelValidAFD.setLayout(null);
		
		JLabel lblCadenaAValidarAFD = new JLabel("Cadena a Validar");
		lblCadenaAValidarAFD.setBounds(0, 23, 108, 22);
		panelValidAFD.add(lblCadenaAValidarAFD);
		
		final TextField textFieldAFD = new TextField();
		textFieldAFD.setBounds(112, 23, 175, 22);
		panelValidAFD.add(textFieldAFD);
		
		JScrollPane scrollPaneValidAFD = new JScrollPane();
		scrollPaneValidAFD.setBounds(293, 0, 510, 300);
		scrollPaneValidAFD.setAutoscrolls(true);
		panelValidAFD.add(scrollPaneValidAFD);
		
		final JTextArea textArea_AFD = new JTextArea();
		textArea_AFD.setBounds(293, 23, 498, 272);
		scrollPaneValidAFD.setViewportView(textArea_AFD);
		
		/*Aqui se realiza el proceso de Validacion de AFN*/
		JButton btnNewButtonAFD = new JButton("Validar AFD");
		btnNewButtonAFD.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				String valor = textFieldAFD.getText();
		        if (valor == null)
		            valor = "";

		        // Verificar cadena con el Automata AFD correspondiente
		        com.py.compiladores.algoritmos.ResultadoValidacion Result = null;
		        
		        Result = com.py.compiladores.algoritmos.Validacion.validarAFD((AFD) afd, valor);

		        String mensaje = null;
		        if (!Result.esValido()) {
		            mensaje = "ERROR: La cadena ingresada NO ES ACEPTADA.\n\n";
		            String Resto = Result.getEntradaFaltante();
		            if (!Resto.isEmpty()) {
		                mensaje += "Se ha recorrido " + Result.getCamino() + " y no se " +
		                        "ha podido seguir avanzando. FALTA CONSUMIR: \"" + Resto + "\".";
		            } else {
		                mensaje += "Se ha recorrido " + Result.getCamino() + " y se ha consumido" +
		                        " la cadena de entrada sin llegar a un ESTADO FINAL. \n";
		            }
		        } else {
		            mensaje = "OK. La cadena es ACEPTADA.\nSe ha recorrido " + Result.getCamino();
		        }
		        textArea_AFD.setText("" + mensaje);
			}
		});
		btnNewButtonAFD.setBounds(85, 106, 114, 53);
		panelValidAFD.add(btnNewButtonAFD);
		/*Hasta aqui el noveno tab-panel correspondiente a la validacion de cadenas AFD*/
		
		
		/*Aqui la insercion de la imagen AFN*/
		JPanel panelImagen = new JPanel();
		tabbedPane.addTab("Grafo AFN", panelImagen);
		panelImagen.setLayout(null);
		
		
		JScrollPane scrollPaneImagen = new JScrollPane();
		scrollPaneImagen.setBounds(0, 0, 850, 610);
		scrollPaneImagen.setAutoscrolls(true);
		panelImagen.add(scrollPaneImagen);

		Imagen = new javax.swing.JLabel();
		Imagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        //Imagen.setIcon(resourceMap.getIcon("Imagen.icon")); // NOI18N
        //Imagen.setText(resourceMap.getString("Imagen.text")); // NOI18N
        Imagen.setName("Imagen"); // NOI18N
        scrollPaneImagen.setViewportView(Imagen);
        
        Grafo brocha = new Grafo((Automata) salida);
        this.Imagen.setIcon(brocha.ManejarImagen(null));
		
		contentPane.add(tabbedPane);
		
		
	}
}

