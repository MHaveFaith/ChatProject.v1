package client;

/**
 * Created by Keno on 12/13/2016.
 */

import java.io.*;
import java.net.*;
import java.util.Properties;

public class Client {
    ClientHandler client_handler;
    Socket socket = null;

    public static void main(String[] args) {
        new Client();

    }

    /**
     * Loads the properties.prop file to get the host & port
     * Creates a socket with the info then uses the Handlerv2 object to read from the server .
     */

    // FIXME: 12/14/2016  ATM THE CLIENT DOESNT END WHEN THE SEVER CONNECTION CLOSES.
    public Client() {
        try {
            Properties prop = new Properties();
            FileInputStream fin = new FileInputStream("properties.prop");
            prop.load(fin);
            String DEFAULT_HOST = prop.getProperty("default_host"); // HOST
            int DEFAULT_PORT = Integer.parseInt(prop.getProperty("default_port"));  // PORT

            socket = new Socket(DEFAULT_HOST, DEFAULT_PORT);
            client_handler = new ClientHandler(socket, this); // Referencing this object - view constructor..
            client_handler.start();

            readUserInput(); // Method call.

        } catch (UnknownHostException e) {
            System.out.println(e);
            client_handler.closeConnection();
        } catch (IOException ex) {
            System.out.println(ex);
            client_handler.closeConnection();
        }
    }


    /**
     * This method is used for reading the user's input.
     */
    public void readUserInput() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); // FIXME: 12/14/2016  change to getText()
            String line = in.readLine(); // FIXME: 12/14/2016

            while (line != null && !line.equals("Bye.")) {
                client_handler.sendToServer(line); // Send Message to the server.
                line = in.readLine(); // FIXME: 12/14/2016
            }
            client_handler.closeConnection();
        } catch (IOException e) {
            e.printStackTrace(); // FIXME: 12/14/2016
            client_handler.closeConnection();
        }
    }
}
