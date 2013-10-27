package com.py.compiladores.windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;

import java.awt.GridLayout;

import javax.swing.JEditorPane;

import java.awt.Canvas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Principal {

	private JFrame frmTrabajoDeCompiladores;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frmTrabajoDeCompiladores.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTrabajoDeCompiladores = new JFrame();
		frmTrabajoDeCompiladores.setTitle("Trabajo de Compiladores 2013");
		frmTrabajoDeCompiladores.setBounds(100, 100, 487, 369);
		frmTrabajoDeCompiladores.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTrabajoDeCompiladores.getContentPane().setLayout(null);
		
		JTextArea txtrTrabajoCompiladores = new JTextArea();
		txtrTrabajoCompiladores.setFont(new Font("Monospaced", Font.BOLD, 16));
		txtrTrabajoCompiladores.setEditable(false);
		txtrTrabajoCompiladores.setBounds(118, 0, 269, 32);
		txtrTrabajoCompiladores.setTabSize(10);
		txtrTrabajoCompiladores.setText("TRABAJO COMPILADORES 2013");
		frmTrabajoDeCompiladores.getContentPane().add(txtrTrabajoCompiladores);
		
		JButton btnPersonalizado = new JButton("Personalizado");
		btnPersonalizado.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				AlfabetoPersonalizado frame = new AlfabetoPersonalizado();
				frame.setVisible(true);
			}
		});
		btnPersonalizado.setBounds(148, 102, 207, 65);
		btnPersonalizado.setIcon(new ImageIcon(Principal.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		frmTrabajoDeCompiladores.getContentPane().add(btnPersonalizado);
		
		JButton btnDefecto = new JButton("Predeterminado");
		btnDefecto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnDefecto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AlfabetoPorDefecto frameDefecto = new AlfabetoPorDefecto();
				frameDefecto.setVisible(true);
			}
		});
		buttonGroup.add(btnDefecto);
		btnDefecto.setBounds(148, 189, 207, 65);
		btnDefecto.setIcon(new ImageIcon(Principal.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		frmTrabajoDeCompiladores.getContentPane().add(btnDefecto);
		
		JEditorPane dtrpnSeleccionDelAlfabeto = new JEditorPane();
		dtrpnSeleccionDelAlfabeto.setFont(new Font("Tahoma", Font.BOLD, 12));
		dtrpnSeleccionDelAlfabeto.setText("SELECCION DEL ALFABETO");
		dtrpnSeleccionDelAlfabeto.setBounds(163, 71, 174, 20);
		frmTrabajoDeCompiladores.getContentPane().add(dtrpnSeleccionDelAlfabeto);
		
		JEditorPane dtrpnCristianAceval = new JEditorPane();
		dtrpnCristianAceval.setText("Cristian Aceval - Victor Franco - Todos los derechos reservados");
		dtrpnCristianAceval.setBounds(36, 299, 397, 20);
		frmTrabajoDeCompiladores.getContentPane().add(dtrpnCristianAceval);
	}
}
