package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import utilities.Crypto;
import utilities.FileHandler;

public class EncryptionWindow {

	JFrame frmEncryptMessage;
	private JPasswordField passwordField;
	private JLabel lblRepeatPassword;
	private JPasswordField repeatPasswordField;
	private JButton btnEncrypt;
	private JButton btnCancel;
	private JTextArea textArea;
	private FileHandler fileHandler;

	/**
	 * Create the application.
	 */
	public EncryptionWindow(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEncryptMessage = new JFrame();
		frmEncryptMessage.setTitle("Encrypt message");
		frmEncryptMessage.setResizable(false);
		frmEncryptMessage.setBounds(100, 100, 300, 390);
		frmEncryptMessage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmEncryptMessage.getContentPane().setLayout(springLayout);
		
		JLabel lblPassword = new JLabel("Enter the password:");
		springLayout.putConstraint(SpringLayout.NORTH, lblPassword, 10, SpringLayout.NORTH, frmEncryptMessage.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblPassword, 10, SpringLayout.WEST, frmEncryptMessage.getContentPane());
		frmEncryptMessage.getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		springLayout.putConstraint(SpringLayout.NORTH, passwordField, 6, SpringLayout.SOUTH, lblPassword);
		springLayout.putConstraint(SpringLayout.WEST, passwordField, 10, SpringLayout.WEST, frmEncryptMessage.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, passwordField, -12, SpringLayout.EAST, frmEncryptMessage.getContentPane());
		frmEncryptMessage.getContentPane().add(passwordField);
		passwordField.setColumns(10);
		
		lblRepeatPassword = new JLabel("Repeat the password:");
		springLayout.putConstraint(SpringLayout.NORTH, lblRepeatPassword, 6, SpringLayout.SOUTH, passwordField);
		springLayout.putConstraint(SpringLayout.WEST, lblRepeatPassword, 0, SpringLayout.WEST, lblPassword);
		frmEncryptMessage.getContentPane().add(lblRepeatPassword);
		
		repeatPasswordField = new JPasswordField();
		springLayout.putConstraint(SpringLayout.NORTH, repeatPasswordField, 6, SpringLayout.SOUTH, lblRepeatPassword);
		springLayout.putConstraint(SpringLayout.WEST, repeatPasswordField, 10, SpringLayout.WEST, frmEncryptMessage.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, repeatPasswordField, 0, SpringLayout.EAST, passwordField);
		frmEncryptMessage.getContentPane().add(repeatPasswordField);
		
		JLabel lblNewLabel = new JLabel("Write the message:");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 6, SpringLayout.SOUTH, repeatPasswordField);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, lblPassword);
		frmEncryptMessage.getContentPane().add(lblNewLabel);
		
		btnEncrypt = new JButton("Encrypt");
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertEncryptedMessage();
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, btnEncrypt, -10, SpringLayout.SOUTH, frmEncryptMessage.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnEncrypt, -151, SpringLayout.EAST, frmEncryptMessage.getContentPane());
		frmEncryptMessage.getContentPane().add(btnEncrypt);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmEncryptMessage.dispose();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnCancel, 0, SpringLayout.NORTH, btnEncrypt);
		springLayout.putConstraint(SpringLayout.WEST, btnCancel, 6, SpringLayout.EAST, btnEncrypt);
		frmEncryptMessage.getContentPane().add(btnCancel);
		
		textArea = new JTextArea();
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 6, SpringLayout.SOUTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, textArea, 0, SpringLayout.WEST, lblPassword);
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, -6, SpringLayout.NORTH, btnEncrypt);
		springLayout.putConstraint(SpringLayout.EAST, textArea, 284, SpringLayout.WEST, frmEncryptMessage.getContentPane());
		frmEncryptMessage.getContentPane().add(textArea);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 6, SpringLayout.SOUTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, lblPassword);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -6, SpringLayout.NORTH, btnEncrypt);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, passwordField);
		frmEncryptMessage.getContentPane().add(scrollPane);
		
		centerFrame();
	}
	
	private void centerFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension window = frmEncryptMessage.getSize();    
        frmEncryptMessage.setLocation((screen.width - window.width) / 2, (screen.height - window.height) / 2);
	}
	
	private void insertEncryptedMessage() {
		String passwordOne = new String(passwordField.getPassword());
		String passwordTwo = new String(repeatPasswordField.getPassword());
		String message = textArea.getText();
		
		if (passwordOne.equals("") || passwordTwo.equals("") || message.equals("")) {
			JOptionPane.showMessageDialog(null,
				    "Fill all the fields",
				    "Warning",
				    JOptionPane.WARNING_MESSAGE);
		} else if (fileHandler == null) {
			JOptionPane.showMessageDialog(null,
				    "NullPointerException",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		} else if(!passwordOne.equals(passwordTwo)){
			JOptionPane.showMessageDialog(null,
				    "Passwords do not match",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		} else {
			byte [] byteMessage = message.getBytes();
			byte [] encryptedMessage = Crypto.encrypt(passwordOne, byteMessage);
			fileHandler.insertMessage(encryptedMessage);
			JOptionPane.showMessageDialog(null,
				    "Message has been inserted",
				    "Done",
				    JOptionPane.INFORMATION_MESSAGE);
			frmEncryptMessage.dispose();
		}
	}
}
