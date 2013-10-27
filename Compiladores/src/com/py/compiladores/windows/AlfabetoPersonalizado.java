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
import java.awt.Color;
import javax.swing.JEditorPane;
import java.awt.Toolkit;

public class AlfabetoPersonalizado extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AlfabetoPersonalizado() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AlfabetoPersonalizado.class.getResource("/com/sun/java/swing/plaf/motif/icons/DesktopIcon.gif")));
		setResizable(false);
		setTitle("Alfabeto Personalizado");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 479, 357);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final TextField textFieldAlfabeto = new TextField();
		textFieldAlfabeto.setBounds(175, 125, 278, 22);
		contentPane.add(textFieldAlfabeto);
		
		Label label = new Label("Alfabeto :");
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(10, 125, 117, 22);
		contentPane.add(label);
		
		Label label_1 = new Label("Expresion Regular :");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_1.setBounds(10, 176, 144, 22);
		contentPane.add(label_1);
		
		final TextField textFieldExpReg = new TextField();
		textFieldExpReg.setBounds(175, 176, 278, 22);
		contentPane.add(textFieldExpReg);
		
		Label label_2 = new Label("Ingrese los Datos");
		label_2.setAlignment(Label.CENTER);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_2.setBounds(180, 58, 147, 22);
		contentPane.add(label_2);
		
		JButton btnProcesarEntrada = new JButton("Procesar Entrada");
		btnProcesarEntrada.setFont(new Font("Tahoma", Font.BOLD, 16));
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
					frameDeError.getContentPane().add(textArea_1);
					frameDeError.setVisible(true);
					//e.printStackTrace();
				}
			}
		});
		btnProcesarEntrada.setBounds(175, 227, 185, 47);
		contentPane.add(btnProcesarEntrada);
		
		JTextArea textArea = new JTextArea();
		textArea.setText("                   COMPI-WIN");
		textArea.setTabSize(10);
		textArea.setFont(new Font("Monospaced", Font.BOLD, 16));
		textArea.setEditable(false);
		textArea.setBackground(new Color(60, 179, 113));
		textArea.setBounds(0, 0, 473, 32);
		contentPane.add(textArea);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setText("Cristian Aceval - Victor Franco - Todos los derechos reservados");
		editorPane.setFont(new Font("Lucida Sans", Font.ITALIC, 12));
		editorPane.setBounds(74, 297, 379, 20);
		contentPane.add(editorPane);
	}
}
