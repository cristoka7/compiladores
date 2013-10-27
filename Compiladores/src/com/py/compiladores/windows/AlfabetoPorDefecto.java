package com.py.compiladores.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.TextField;
import java.awt.Label;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlfabetoPorDefecto extends JFrame {

	private JPanel contentPane;



	/**
	 * Create the frame.
	 */
	public AlfabetoPorDefecto() {
		setTitle("Alfabeto Predeterminado");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 443, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ALFABETOS PREDETERMINADOS");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(106, 11, 230, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblDigitos = new JLabel("DIGITOS={0,1,2,3,4,5,6,7,8,9}");
		lblDigitos.setBounds(24, 46, 174, 14);
		contentPane.add(lblDigitos);
		
		JLabel lblLetrasabcdefghijklmnopqrstuvwxyz = new JLabel("LETRAS={a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z}");
		lblLetrasabcdefghijklmnopqrstuvwxyz.setBounds(24, 82, 306, 14);
		contentPane.add(lblLetrasabcdefghijklmnopqrstuvwxyz);
		
		final TextField textField = new TextField();
		textField.setBounds(135, 122, 240, 22);
		contentPane.add(textField);
		
		Label label = new Label("Expresion Regular");
		label.setBounds(22, 122, 107, 22);
		contentPane.add(label);
		
		JButton btnProcesarEntrada = new JButton("Procesar Entrada");
		btnProcesarEntrada.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String alfabeto = new String("abcdefghijklmnopqrstuvwxyz1234567890");
				String expReg = textField.getText();
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
		btnProcesarEntrada.setBounds(137, 181, 152, 37);
		contentPane.add(btnProcesarEntrada);
	}
}
