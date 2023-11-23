package Uppgift1;

import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9999); // Anslut till servern på localhost och port 9999

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Meddelande från servern: " + message);
                if (message.equals("Avslutar anslutningen")) {
                    break; // Om servern skickar "Avslutar anslutningen", bryt loopen
                }
            }

            socket.close(); // Stäng klientanslutningen
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
