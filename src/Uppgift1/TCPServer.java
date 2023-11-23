package Uppgift1;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class TCPServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999); // Lyssnar på port 9999

            System.out.println("Server started. Waiting for client...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Väntar på att en klient ska ansluta
                System.out.println("Client connected: " + clientSocket);

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Skicka ett meddelande varje sekund till klienten
                for (int i = 1; i <= 5; i++) {
                    out.println("Meddelande " + i + " från servern");
                    TimeUnit.SECONDS.sleep(3); // Vänta i 3 sekunder innan nästa meddelande skickas
                }

                out.println("Avslutar anslutningen");
                clientSocket.close(); // Stäng klientanslutningen
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
