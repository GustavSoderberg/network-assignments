package Uppgift2;

import java.io.*;
import java.net.*;

public class QuoteReceiver {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Lyssna på port 12345
            Socket clientSocket = serverSocket.accept(); // Vänta på att en klient ansluter

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String quoteReceived;
            while ((quoteReceived = in.readLine()) != null) {
                System.out.println("Mottaget citat: " + quoteReceived);

                // Skicka kvitto till sändaren
                out.println("Kvitto för citat: " + quoteReceived);
            }

            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
