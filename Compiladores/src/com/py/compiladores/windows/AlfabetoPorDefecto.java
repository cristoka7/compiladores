package com.py.compiladores.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class AlfabetoPorDefecto extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AlfabetoPorDefecto() {
		setResizable(false);
		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						AlfabetoPorDefecto.class
								.getResource("/com/sun/java/swing/plaf/motif/icons/DesktopIcon.gif")));
		setTitle("Alfabeto Predeterminado");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 480, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("ALFABETOS PREDETERMINADOS");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(98, 51, 290, 24);
		contentPane.add(lblNewLabel);

		JLabel lblDigitos = new JLabel("DIGITOS={0,1,2,3,4,5,6,7,8,9}");
		lblDigitos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDigitos.setBounds(24, 86, 312, 24);
		contentPane.add(lblDigitos);

		JLabel lblLetrasabcdefghijklmnopqrstuvwxyz = new JLabel(
				"LETRAS={a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z}");
		lblLetrasabcdefghijklmnopqrstuvwxyz.setFont(new Font("Tahoma",
				Font.BOLD, 14));
		lblLetrasabcdefghijklmnopqrstuvwxyz.setBounds(24, 121, 394, 25);
		contentPane.add(lblLetrasabcdefghijklmnopqrstuvwxyz);

		final TextField textField = new TextField();
		textField.setBounds(154, 168, 300, 22);
		contentPane.add(textField);

		Label label = new Label("Expresion Regular: ");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		label.setBounds(24, 168, 126, 22);
		contentPane.add(label);

		JButton btnProcesarEntrada = new JButton("Procesar Entrada");
		btnProcesarEntrada.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnProcesarEntrada.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String alfabeto = new String(
						"abcdefghijklmnopqrstuvwxyz1234567890");
				String expReg = textField.getText();
				Salidas frame;
				if (expReg.equals("")) {
					JFrame frameDeError = new JFrame();
					frameDeError.setBounds(100, 100, 487, 369);
					frameDeError.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					frameDeError.setTitle("WARNING");
					JTextArea textArea_1 = new JTextArea();
					textArea_1.setEditable(false);
					textArea_1.setText("Se debe ingresar la Expresión Regular");
					frameDeError.getContentPane().add(textArea_1);
					frameDeError.setLocationRelativeTo(null);
					frameDeError.setVisible(true);
				} else {
					try {
						frame = new Salidas(alfabeto, expReg);
						frame.setLocationRelativeTo(null);
						frame.setVisible(true);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						JFrame frameDeError = new JFrame();
						frameDeError.setBounds(100, 100, 487, 369);
						frameDeError
								.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						frameDeError.setTitle("ERROR");
						JTextArea textArea_1 = new JTextArea();
						textArea_1.setEditable(false);
						textArea_1.setText(e.getMessage());
						frameDeError.getContentPane().add(textArea_1);
						frameDeError.setLocationRelativeTo(null);
						frameDeError.setVisible(true);
						// e.printStackTrace();
					}
				}
			}
		});
		btnProcesarEntrada.setBounds(154, 232, 182, 37);
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
		editorPane
				.setText("Cristian Aceval - Victor Franco - Todos los derechos reservados");
		editorPane.setFont(new Font("Lucida Sans", Font.ITALIC, 12));
		editorPane.setBounds(39, 290, 415, 20);
		contentPane.add(editorPane);

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textField.setText("");
			}
		});
		btnLimpiar.setBounds(24, 246, 89, 23);
		contentPane.add(btnLimpiar);
	}
}
