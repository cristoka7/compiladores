package com.py.compiladores.windows;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.py.compiladores.analisis.Alfabeto;
import com.py.compiladores.analisis.AnalizadorSintactico;
import com.py.compiladores.estructuras.AFN;
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
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 464, 366);
		
		JPanel panelAFN = new JPanel();
		tabbedPane.addTab("AFN", panelAFN);
		panelAFN.setLayout(null);
		
		Alfabeto alfa = new Alfabeto(alfabeto);
		AnalizadorSintactico as = new AnalizadorSintactico(alfa, ExpReg);
		AFN salida = as.analizar();
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 459, 338);
		scrollPane.setAutoscrolls(true);
		panelAFN.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setText(salida.toString());
		scrollPane.setViewportView(textArea);
		
		
		contentPane.add(tabbedPane);
		
		JPanel panelAFD = new JPanel();
		tabbedPane.addTab("AFD", panelAFD);
		panelAFN.setLayout(null);
		
		
		
		
	}
}
