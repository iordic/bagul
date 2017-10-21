package main;

import java.awt.EventQueue;

import gui.MainWindow;

/**
 * Controller class
 * @author Jordi Castelló
 *
 */
public class Bagul {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmBagul.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
