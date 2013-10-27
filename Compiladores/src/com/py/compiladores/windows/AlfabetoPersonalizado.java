package com.py.compiladores.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JInternalFrame;

import java.awt.TextField;
import java.awt.Label;
import java.awt.Font;
import java.awt.Button;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlfabetoPersonalizado extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AlfabetoPersonalizado() {
		setTitle("Alfabeto Personalizado");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 479, 357);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final TextField textFieldAlfabeto = new TextField();
		textFieldAlfabeto.setBounds(133, 82, 243, 22);
		contentPane.add(textFieldAlfabeto);
		
		Label label = new Label("Alfabeto");
		label.setBounds(10, 82, 117, 22);
		contentPane.add(label);
		
		Label label_1 = new Label("Expresion Regular");
		label_1.setBounds(10, 146, 117, 22);
		contentPane.add(label_1);
		
		final TextField textFieldExpReg = new TextField();
		textFieldExpReg.setBounds(133, 146, 243, 22);
		contentPane.add(textFieldExpReg);
		
		Label label_2 = new Label("Ingrese los Datos");
		label_2.setFont(new Font("Dialog", Font.BOLD, 15));
		label_2.setBounds(165, 26, 147, 22);
		contentPane.add(label_2);
		
		JButton btnProcesarEntrada = new JButton("Procesar Entrada");
		btnProcesarEntrada.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String alfabeto = textFieldAlfabeto.getText();
				String expReg = textFieldExpReg.getText();
				Salidas frame;
				try {
					frame = new Salidas(alfabeto, expReg);
					frame.setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					JFrame frameDeError = new JFrame();
					frameDeError.setBounds(100, 100, 487, 369);
					frameDeError.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					frameDeError.setTitle("ERROR");
					JTextArea textArea_1 = new JTextArea();
					textArea_1.setEditable(false);
					textArea_1.setText(e.getMessage());
					frameDeError.add(textArea_1);
					frameDeError.setVisible(true);
					//e.printStackTrace();
				}
			}
		});
		btnProcesarEntrada.setBounds(165, 228, 147, 47);
		contentPane.add(btnProcesarEntrada);
	}
}
