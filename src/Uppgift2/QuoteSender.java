package Uppgift2;

import java.io.*;
import java.net.*;

public class QuoteSender {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345); // Anslut till servern på localhost och port 12345

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String[] quotes = {
                    "Ingenting är omöjligt, det omöjliga tar bara längre tid.",
                    "Varje framgångsrik person har en historia om misslyckande bakom sig.",
                    "Ju mer du svettas i fred, desto mindre blöder du i krig.",
                    "Varje morgon har två handtag. Vi kan antingen hålla fast vid drömmen eller vakna upp och jaga dem.",
                    "Det är aldrig för sent att vara det du kunde ha varit."
            };

            for (String quote : quotes) {
                out.println(quote);
                System.out.println("Sänder citat: " + quote);

                // Vänta på kvitto från mottagaren
                String receipt = in.readLine();
                System.out.println("Kvitto mottaget: " + receipt);

                Thread.sleep(60000); // Vänta i 1 minut innan nästa citat sänds
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
