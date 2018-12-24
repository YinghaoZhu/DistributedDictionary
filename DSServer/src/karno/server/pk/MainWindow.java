package karno.server.pk;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

/** 
* @author 887402 Yinghao Zhu E-mail: zhu156323225@icloud.com
* @version time：5Sep.,2018 3:00:20 pm 
* 
*/
public class MainWindow {

	private JFrame frame;
    private Server server;
	/**
	 * Launch the application.
	 */
	 
	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		
	}
    public void showView (int port, String path) {
		
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				frame.setVisible(true);
			}
		});
		server = new Server(port, path);
		server.init();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JTextArea txtrThisServerIs = new JTextArea();
		txtrThisServerIs.setBounds(44, 29, 331, 84);
		txtrThisServerIs.setText("This server is designed for Distributed Dictionary, \nclicking this start button, then the dictionary can \nbe sccessed!");
		frame.getContentPane().add(txtrThisServerIs);
		JButton btnNewButton = new JButton("Stop Server");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.closeServer();
			}
		});
		btnNewButton.setBounds(255, 125, 97, 29);
		frame.getContentPane().add(btnNewButton);

		
	}
}
