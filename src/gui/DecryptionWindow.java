package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import utilities.Crypto;
import utilities.FileHandler;

public class DecryptionWindow {

	JFrame frmDecryptMessage;
	private String password;
	private FileHandler fileHandler;

	/**
	 * Create the application.
	 */
	public DecryptionWindow(String password, FileHandler fileHandler) {
		this.password = password;
		this.fileHandler = fileHandler;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDecryptMessage = new JFrame();
		frmDecryptMessage.setResizable(false);
		frmDecryptMessage.setTitle("Hidden Message");
		frmDecryptMessage.setBounds(100, 100, 330, 290);
		frmDecryptMessage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmDecryptMessage.getContentPane().setLayout(springLayout);
		
		JLabel lblMessage = new JLabel("Message:");
		springLayout.putConstraint(SpringLayout.NORTH, lblMessage, 10, SpringLayout.NORTH, frmDecryptMessage.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblMessage, 10, SpringLayout.WEST, frmDecryptMessage.getContentPane());
		frmDecryptMessage.getContentPane().add(lblMessage);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 6, SpringLayout.SOUTH, lblMessage);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, frmDecryptMessage.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 126, SpringLayout.SOUTH, lblMessage);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, frmDecryptMessage.getContentPane());
		frmDecryptMessage.getContentPane().add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JButton btnOk = new JButton("OK");
		frmDecryptMessage.getContentPane().add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		springLayout.putConstraint(SpringLayout.WEST, btnCancel, 152, SpringLayout.WEST, frmDecryptMessage.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnOk, 0, SpringLayout.NORTH, btnCancel);
		springLayout.putConstraint(SpringLayout.EAST, btnOk, -6, SpringLayout.WEST, btnCancel);
		frmDecryptMessage.getContentPane().add(btnCancel);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		springLayout.putConstraint(SpringLayout.NORTH, btnCancel, 6, SpringLayout.SOUTH, comboBox);
		springLayout.putConstraint(SpringLayout.WEST, comboBox, 10, SpringLayout.WEST, frmDecryptMessage.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, comboBox, 0, SpringLayout.EAST, scrollPane);
		comboBox.setToolTipText("Select operation");
		frmDecryptMessage.getContentPane().add(comboBox);
		comboBox.addItem("Save message to file");
		comboBox.addItem("Remove message from file");
		
		JLabel lblOperation = new JLabel("What do you want to do with message:");
		springLayout.putConstraint(SpringLayout.NORTH, comboBox, 6, SpringLayout.SOUTH, lblOperation);
		springLayout.putConstraint(SpringLayout.NORTH, lblOperation, 6, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, lblOperation, 0, SpringLayout.WEST, lblMessage);
		frmDecryptMessage.getContentPane().add(lblOperation);
		
		byte[] encryptedData;
		String message = "";
		try {
			encryptedData = fileHandler.extractMessage();
			message = new String(Crypto.decrypt(password, encryptedData));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
				    "IOException has ocurred",
				    "FAIL",
				    JOptionPane.WARNING_MESSAGE);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null,
				    "Did you insert the correct password?",
				    "FAIL",
				    JOptionPane.WARNING_MESSAGE);
		}
		textArea.setText(message);
		
		//Action listeners
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selection = comboBox.getSelectedIndex();
				switch (selection) {
					case 0:		// Save message to file
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setDialogTitle("Specify a file to save");   					 
						int userSelection = fileChooser.showSaveDialog(null);						 
						if (userSelection == JFileChooser.APPROVE_OPTION) {
							try {
						    File fileToSave = fileChooser.getSelectedFile();
						    FileWriter fileWritter = new FileWriter(fileToSave.getAbsolutePath());
						    BufferedWriter bufferedWritter = new BufferedWriter(fileWritter);
						    bufferedWritter.write(textArea.getText());
						    bufferedWritter.close();
							JOptionPane.showMessageDialog(null,
								    "File saved.",
								    "Done",
								    JOptionPane.INFORMATION_MESSAGE);
							frmDecryptMessage.dispose();
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
						break;
					case 1:		// Remove message from file
					try {
						fileHandler.removeMessage();
						JOptionPane.showMessageDialog(null,
							    "Removed message from image.",
							    "Done",
							    JOptionPane.INFORMATION_MESSAGE);
						frmDecryptMessage.dispose();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
						break;
					default:
						break;
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmDecryptMessage.dispose();
			}
		});
		centerFrame();
	}
	
	private void centerFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension window = frmDecryptMessage.getSize();    
        frmDecryptMessage.setLocation((screen.width - window.width) / 2, (screen.height - window.height) / 2);
	}
}
