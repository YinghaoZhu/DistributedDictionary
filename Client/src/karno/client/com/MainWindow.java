package karno.client.com;
/** 
* @author 887402 Yinghao Zhu E-mail: zhu156323225@icloud.com
* @version time：3Sep.,2018 8:00:30 pm 
* 
*/
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.json.JSONException;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MainWindow {
    
	private JFrame frmLaJiWan;

	private JButton addButton;
	private JTextField word;
	private JButton deleteButton;
	private JTextArea textArea;
	private static Client client;
	/**
	 * Launch the application.
	 */
	public void showView (String[] args) {
		
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				frmLaJiWan.setVisible(true);
				try {
					client = new Client(args[0], Integer.parseInt(args[1]));
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

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
	
		frmLaJiWan = new JFrame();
		frmLaJiWan.setTitle("You Dao ");
		frmLaJiWan.setBounds(100, 100, 500, 500);
		frmLaJiWan.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLaJiWan.getContentPane().setLayout(null);
		
		word = new JTextField();
		word.setBounds(6, 6, 219, 37);
		frmLaJiWan.getContentPane().add(word);
		word.setColumns(10);
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					textArea.setText(client.query(word.getText()));
				} catch (IOException | JSONException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		searchButton.setBounds(225, 6, 75, 37);
		frmLaJiWan.getContentPane().add(searchButton);
		
		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					textArea.setText(client.addWord(word.getText(), textArea.getText()));
				} catch (IOException | JSONException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		addButton.setBounds(312, 6, 75, 37);
		frmLaJiWan.getContentPane().add(addButton);
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					textArea.setText(client.remove(word.getText()));
				} catch (IOException | JSONException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		deleteButton.setBounds(399, 6, 75, 37);
		frmLaJiWan.getContentPane().add(deleteButton);
		
		textArea = new JTextArea();
		textArea.setBounds(6, 77, 488, 395);
		frmLaJiWan.getContentPane().add(textArea);
		/*int fraWidth = frmLaJiWan.getWidth();//frame的宽
		int fraHeight = frmLaJiWan.getHeight();//frame的高
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		frmLaJiWan.setSize(screenWidth, screenHeight);
		frmLaJiWan.setLocation(0, 0);
		float proportionW = screenWidth/fraWidth;
		float proportionH = screenHeight/fraHeight;
		
		GUIClient.modifyComponentSize(frmLaJiWan, proportionW, proportionH);
		frmLaJiWan.toFront();*/
			
	}
   
    
    public void addAction(Client client) {
    	try {
			client.addWord( word.getText(), textArea.getText());
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
		}
    }
}
