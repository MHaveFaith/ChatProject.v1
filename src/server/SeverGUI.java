package server;

/**
 * Created by Muhammad on 24/12/2016.
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;



/*
 * The server as a GUI
 */

@SuppressWarnings("serial")
public class ServerGUI extends JFrame implements ActionListener, WindowListener {
	
	
	// the stop and start buttons
	private JButton stopStart;
	
	// JTextArea for the chat room and the events
	private JTextArea chatArea;
	private JTextArea eventArea;
	
	// The port number
	private JTextField tPortNumber;
	
	// my server
	private Server server;
	
	
	// server constructor that receive the port to listen to for connection as parameter
	ServerGUI(int DEFAULT_PORT) {
		super("Chat Server Window");
		server = null;
		
		// in the NorthPanel the PortNumber the Start and Stop buttons
		JPanel north = new JPanel();
		north.add(new JLabel("Port number: "));
		tPortNumber = new JTextField("  " + DEFAULT_PORT);
		north.add(tPortNumber);
		
		// to stop or start the server, we start with "Start"
		stopStart = new JButton(" Start ");
		stopStart.addActionListener(this);
		north.add(stopStart);
		add(north, BorderLayout.NORTH);
		
		// the event and chat room
		JPanel center = new JPanel(new GridLayout(2,1));
		
		chatArea = new JTextArea(70,80);
		chatArea.setEditable(false);
		appendRoom(" Chat room.\n ");
		center.add(new JScrollPane(chatArea));
		
		eventArea = new JTextArea(70,80);
		eventArea.setEditable(false);
		appendEvent("Events log.\n");
		center.add(new JScrollPane(eventArea));	
		add(center);
		
		// need to be informed when the user click the close button on the frame
		addWindowListener(this);
		setSize(500, 600);
		setVisible(true);
	}		

	// append message to the two JTextArea
	// position at the end
	void appendRoom(String str) {
		chatArea.append(str);
		chatArea.setCaretPosition(chatArea.getText().length() - 1);
	}
	
	void appendEvent(String str) {
		eventArea.append(str);
		eventArea.setCaretPosition(eventArea.getText().length() - 1);
		
	}
	
	// start or stop where clicked
	public void actionPerformed(ActionEvent e) {
		// if running we have to stop
		
      	// OK start the server	
		int DEFAULT_PORT;
		
		try {
			DEFAULT_PORT = Integer.parseInt(tPortNumber.getText().trim());
			
		}catch(Exception er) {
			appendEvent("Invalid port number");
			return;
		}
		
		// ceate a new Server
		//server = new Server(port, this);
		// and start it as a thread
		
		new ServerRunning().start();
		stopStart.setText(" Stop ");
		tPortNumber.setEditable(false);
	}
	
	// entry point to start the Server
	public static void main(String[] arg) {
		
		// start server default port 1337
		new ServerGUI(1337);
	}

	/*
	 * If the user click the X button to close the application
	 * I need to close the connection with the server to free the port
	 */
	
	public void windowClosing(WindowEvent e) {
		// if my Server exist
		if(server != null) {
			try {
				//server.stop();			// ask the server to close the conection
			}
			catch(Exception eClose) {
			}
			server = null;
		}
		
		// dispose the frame
		dispose();
		System.exit(0);
	}
	
	// I can ignore the other WindowListener method
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}

	/*
	 * A thread to run the Server
	 */
	class ServerRunning extends Thread {
		public void run() {
			//server.start();         // should execute until if fails
			// the server failed
			stopStart.setText(" Start ");
			tPortNumber.setEditable(true);
			appendEvent(" Server crashed\n ");
			server = null;
		}
	}

}
