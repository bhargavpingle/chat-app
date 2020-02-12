import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server");

            // Thread to listen for incoming messages from the server
            Thread listenerThread = new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        System.out.println("Message from server: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            listenerThread.start();

            // Reading user input and sending it to the server
            String messageToSend;
            while ((messageToSend = consoleReader.readLine()) != null) {
                out.println(messageToSend);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

