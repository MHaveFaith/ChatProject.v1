package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Keno on 12/14/2016.
 */
public class ClientHandler extends Thread {

    // Attributes
    PrintWriter out;
    BufferedReader in;
    Socket socket;

    // FIXME: 12/14/2016  ATM THE CLIENT DOESNT END WHEN THE SEVER CONNECTION CLOSES.
    public ClientHandler(Socket socket, Client client) {
        this.socket = socket;

    }

    public void sendToServer(String text) {
        try {
            out.println(text);  // FIXME: 12/14/2016
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This is used for reading messages from the server..
     *
     */
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                while(!in.ready()) { // If the Buffered reader isn't active then sleep the thread....
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ee) {
                        ee.printStackTrace();
                    }
                }
                String response = null;
                response = in.readLine();
                System.out.println(response); // FIXME: 12/14/2016  NEEDS TO SEND TO USERS SCREEN
            }
        } catch (IOException e) {
            e.printStackTrace(); // FIXME: 12/14/2016
            closeConnection();
        }
    }


    /***
     * Call this whenever connection needs to be closed
     * or an exception is thrown.
     */
    public void closeConnection() {
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace(); // Fix me.
        }
    }

}
