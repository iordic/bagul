package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import utilities.FileHandler;

public class MainWindow {

	public JFrame frmBagul;
	private JTextField pathField;
	private FileHandler selectedFile;
	private ImageIcon imageSelectedIcon;
	private JRadioButton rdbtnEncrypt;
	private JRadioButton rdbtnDecrypt;
	private JLabel lblContainsMessage;

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBagul = new JFrame();
		frmBagul.setTitle("Bagul");
		frmBagul.setResizable(false);
		frmBagul.setBounds(100, 100, 340, 380);
		frmBagul.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmBagul.getContentPane().setLayout(springLayout);
		
		JLabel lblNewLabel = new JLabel("Select an image to load:");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 10, SpringLayout.NORTH, frmBagul.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 10, SpringLayout.WEST, frmBagul.getContentPane());
		frmBagul.getContentPane().add(lblNewLabel);
		
		pathField = new JTextField();
		pathField.setEnabled(false);
		springLayout.putConstraint(SpringLayout.NORTH, pathField, 6, SpringLayout.SOUTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, pathField, 10, SpringLayout.WEST, frmBagul.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, pathField, 28, SpringLayout.SOUTH, lblNewLabel);
		frmBagul.getContentPane().add(pathField);
		pathField.setColumns(10);
		
		JLabel lblNoImageSelected = new JLabel("No image selected");
		springLayout.putConstraint(SpringLayout.NORTH, lblNoImageSelected, 6, SpringLayout.SOUTH, pathField);
		lblNoImageSelected.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		springLayout.putConstraint(SpringLayout.EAST, lblNoImageSelected, 327, SpringLayout.WEST, frmBagul.getContentPane());
		lblNoImageSelected.setHorizontalAlignment(SwingConstants.CENTER);
		springLayout.putConstraint(SpringLayout.WEST, lblNoImageSelected, 10, SpringLayout.WEST, frmBagul.getContentPane());
		frmBagul.getContentPane().add(lblNoImageSelected);
		
		ButtonGroup radioGroup = new ButtonGroup();

		JButton btnDoIt = new JButton("Do it!");
		btnDoIt.setEnabled(false);
		springLayout.putConstraint(SpringLayout.NORTH, btnDoIt, 36, SpringLayout.SOUTH, lblNoImageSelected);
		springLayout.putConstraint(SpringLayout.SOUTH, btnDoIt, -27, SpringLayout.SOUTH, frmBagul.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnDoIt, -34, SpringLayout.EAST, frmBagul.getContentPane());
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.WEST, btnDoIt, 53, SpringLayout.EAST, panel);
		springLayout.putConstraint(SpringLayout.NORTH, panel, 266, SpringLayout.NORTH, frmBagul.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblNoImageSelected, -6, SpringLayout.NORTH, panel);
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -10, SpringLayout.SOUTH, frmBagul.getContentPane());
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Message option", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, lblNewLabel);
		springLayout.putConstraint(SpringLayout.EAST, panel, 172, SpringLayout.WEST, frmBagul.getContentPane());
		frmBagul.getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		rdbtnEncrypt = new JRadioButton("Encrypt");
		rdbtnEncrypt.setEnabled(false);
		panel.add(rdbtnEncrypt);
		rdbtnEncrypt.setSelected(true);
		springLayout.putConstraint(SpringLayout.SOUTH, rdbtnEncrypt, -41, SpringLayout.SOUTH, frmBagul.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, rdbtnEncrypt, 0, SpringLayout.WEST, lblNewLabel);
		radioGroup.add(rdbtnEncrypt);
		
		rdbtnDecrypt = new JRadioButton("Decrypt");
		rdbtnDecrypt.setEnabled(false);
		panel.add(rdbtnDecrypt);
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnDecrypt, 50, SpringLayout.SOUTH, lblNoImageSelected);
		springLayout.putConstraint(SpringLayout.WEST, rdbtnDecrypt, 0, SpringLayout.WEST, lblNewLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, rdbtnDecrypt, -10, SpringLayout.SOUTH, frmBagul.getContentPane());
		radioGroup.add(rdbtnDecrypt);
		
		lblContainsMessage = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, lblContainsMessage, 6, SpringLayout.SOUTH, lblNoImageSelected);
		springLayout.putConstraint(SpringLayout.WEST, lblContainsMessage, 6, SpringLayout.EAST, panel);
		frmBagul.getContentPane().add(lblContainsMessage);
		
		JButton openFileButton = new JButton();
		springLayout.putConstraint(SpringLayout.WEST, openFileButton, 306, SpringLayout.WEST, frmBagul.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, pathField, -6, SpringLayout.WEST, openFileButton);
		springLayout.putConstraint(SpringLayout.NORTH, openFileButton, 0, SpringLayout.NORTH, pathField);
		springLayout.putConstraint(SpringLayout.SOUTH, openFileButton, -1, SpringLayout.SOUTH, pathField);
		springLayout.putConstraint(SpringLayout.EAST, openFileButton, -10, SpringLayout.EAST, frmBagul.getContentPane());
		
		// Action listeners
		openFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG Images", "jpg", "jpeg");
				fileChooser.setFileFilter(filter);
			    int returnVal = fileChooser.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	String path = fileChooser.getSelectedFile().getAbsolutePath();
			    	selectedFile = new FileHandler(path);
			    	pathField.setText(path);
			    	Image image;
					try {	
						image = getScaledImage(ImageIO.read(new File(path)), lblNoImageSelected.getWidth(), lblNoImageSelected.getHeight());
					} catch (IOException e1) {
						e1.printStackTrace();
						image = null;
					}		    	
			    	imageSelectedIcon = new ImageIcon(image);
			    	lblNoImageSelected.setIcon(imageSelectedIcon);
			    	rdbtnEncrypt.setEnabled(true);
			    	btnDoIt.setEnabled(true);
			    	try {
						if (selectedFile.containsMessage()) {
							lblContainsMessage.setText("Available message");
							rdbtnDecrypt.setEnabled(true);
						}
						else {
							lblContainsMessage.setText("No message");
							rdbtnDecrypt.setEnabled(false);
							rdbtnDecrypt.setEnabled(false);
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			    	
			    }
			    else {
			    	selectedFile = null;
			    	imageSelectedIcon = null;
			    	lblNoImageSelected.setIcon(null);
			    	pathField.setText(null);
			    	lblContainsMessage.setText(null);
			    	rdbtnEncrypt.setEnabled(false);
			    	rdbtnDecrypt.setEnabled(false);
			    	btnDoIt.setEnabled(false);
			    }
			}
		});
		openFileButton.setIcon(new ImageIcon(MainWindow.class.getResource("/images/open_icon.png")));
		frmBagul.getContentPane().add(openFileButton);
		
		btnDoIt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							if (rdbtnEncrypt.isSelected()) {
								EncryptionWindow window = new EncryptionWindow(selectedFile);
								window.frmEncryptMessage.addWindowListener(new WindowAdapter() {
							           @Override
							            public void windowClosed(WindowEvent e) {
							                reloadComponents();
							            }
								});
								window.frmEncryptMessage.setVisible(true);
							} else if (rdbtnDecrypt.isSelected()) {
								String password = askPassword();								
								DecryptionWindow window = new DecryptionWindow(password, selectedFile);
								if (password != null && selectedFile != null) {
									window.frmDecryptMessage.addWindowListener(new WindowAdapter() {
								           @Override
								            public void windowClosed(WindowEvent e) {
								                reloadComponents();
								            }
									});
									window.frmDecryptMessage.setVisible(true);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		frmBagul.getContentPane().add(btnDoIt);
		centerFrame();
	}
	
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
	
	private String askPassword() {
		JPanel dialogPanel = new JPanel();
		dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
		JPasswordField passwordField = new JPasswordField(20);
		dialogPanel.add(new JLabel("Please Enter Password: "));
	    dialogPanel.add(passwordField);
		int result = JOptionPane.showConfirmDialog(null, dialogPanel, "Insert password to decrypt", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
	    	return new String(passwordField.getPassword());
	    }
		return null;
	}
	
	private void centerFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension window = frmBagul.getSize();    
        frmBagul.setLocation((screen.width - window.width) / 2, (screen.height - window.height) / 2);
	}
	
	private void reloadComponents() {
		try {
			if (selectedFile.containsMessage()) {
				rdbtnDecrypt.setEnabled(true);
				lblContainsMessage.setText("Available message");
			} else {
				rdbtnDecrypt.setEnabled(false);
				lblContainsMessage.setText("No message");
				rdbtnEncrypt.setSelected(true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
