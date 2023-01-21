package netzwerk.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Kuchenautomat_Client_TCP {

    public static void main(String[] args) throws IOException {
        startClient();
    }


    public static void startClient() throws IOException {
        try (Socket socket = new Socket("localhost", 5001);
             DataInputStream in=new DataInputStream(socket.getInputStream());
             DataOutputStream out=new DataOutputStream(socket.getOutputStream())) {
            Scanner scanner = new Scanner(System.in);
            String option = scanner.nextLine();
            out.writeUTF(option);
            switch (option){
                case ":c":
                    String herstellername = scanner.nextLine();
                    out.writeUTF(herstellername);
                    String kuchendaten = scanner.nextLine();
                    out.writeUTF(kuchendaten);
            }
        }
    }
}
