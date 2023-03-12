package netzwerk.tcp;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Kuchenautomat_Client_TCP {
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Kuchenautomat_Client_TCP clientTCP = new Kuchenautomat_Client_TCP();
        clientTCP.startClient();
    }


    public void startClient() throws IOException, ClassNotFoundException {
        try (Socket socket = new Socket("localhost", 5001);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             DataInputStream in=new DataInputStream(socket.getInputStream());
             DataOutputStream out=new DataOutputStream(socket.getOutputStream())) {
            String option = scanner.nextLine();
            out.writeUTF(option);
            switch (option){
                case ":c":
                    einfuegeprozessClient(in, out);
                    break;
                case ":u":
                    aenderungsmodusClient(in, out);
                    break;
                case ":d":
                    loeschmodusClient(in, out);
                    break;
                case ":r":
                    anzeigemodusClient(out, in);
                    break;
                case ":p":
                    persistenzmodusClient(out, in);
                    break;
            }
        }
    }

    private void einfuegeprozessClient(DataInputStream in, DataOutputStream out) throws IOException, ClassNotFoundException {
        String herstellername = scanner.nextLine();
        out.writeUTF(herstellername);
        String kuchendaten = scanner.nextLine();
        while (!(kuchendaten.equals(":u") || kuchendaten.equals(":r") ||
                kuchendaten.equals(":d") || kuchendaten.equals(":p"))) {
            out.writeUTF(kuchendaten);
            kuchendaten = scanner.nextLine();
            switch (kuchendaten) {
                case ":d" -> {
                    out.writeUTF(kuchendaten);
                    loeschmodusClient(in, out);
                }
                case ":r" -> {
                    out.writeUTF(kuchendaten);
                    anzeigemodusClient(out, in);
                }
                case ":u" -> {
                    out.writeUTF(kuchendaten);
                    aenderungsmodusClient(in, out);
                }
                case ":p" -> {
                    out.writeUTF(kuchendaten);
                    persistenzmodusClient(out, in);
                }
            }
        }
    }

    private void aenderungsmodusClient(DataInputStream in, DataOutputStream out) throws IOException, ClassNotFoundException {
        String kuchenID = scanner.nextLine();
        while (!(kuchenID.equals(":c") || kuchenID.equals(":r") ||
                kuchenID.equals(":d") || kuchenID.equals(":p"))){
            out.writeUTF(kuchenID);
            kuchenID = scanner.nextLine();
            switch (kuchenID) {
                case ":c" -> {
                    out.writeUTF(kuchenID);
                    einfuegeprozessClient(in, out);
                }
                case ":r" -> {
                    out.writeUTF(kuchenID);
                    anzeigemodusClient(out, in);
                }
                case ":d" -> {
                    out.writeUTF(kuchenID);
                    loeschmodusClient(in, out);
                }
                case ":p" -> {
                    out.writeUTF(kuchenID);
                    persistenzmodusClient(out, in);
                }
            }
        }
    }


    private void loeschmodusClient(DataInputStream in, DataOutputStream out) throws IOException, ClassNotFoundException {
        String herstellername = scanner.nextLine();
        while (!(herstellername.equals(":c") || herstellername.equals(":r") ||
                herstellername.equals(":u") || herstellername.equals(":p"))) {
            out.writeUTF(herstellername);
            String kuchendaten = scanner.nextLine();
            out.writeUTF(kuchendaten);
            herstellername = scanner.nextLine();
            switch (herstellername) {
                case ":c" -> {
                    out.writeUTF(herstellername);
                    einfuegeprozessClient(in, out);
                }
                case ":r" -> {
                    out.writeUTF(herstellername);
                    anzeigemodusClient(out, in);
                }
                case ":u" -> {
                    out.writeUTF(herstellername);
                    aenderungsmodusClient(in, out);
                }
                case ":p" -> {
                    out.writeUTF(herstellername);
                    persistenzmodusClient(out, in);
                }
            }
        }
    }


    private void anzeigemodusClient(DataOutputStream out, DataInputStream in) throws IOException, ClassNotFoundException {
        String daten = scanner.nextLine();
        while (!(daten.equals(":c") || daten.equals(":d") ||
                daten.equals(":u") || daten.equals(":p"))) {
            out.writeUTF(daten);
            System.out.println("Verschickt: " + daten);
            String objektdaten = in.readLine();
            while (in.available()>0) {
                System.out.println(in.readLine());
            }
            daten = scanner.nextLine();
            switch (daten) {
                case ":c" -> {
                    out.writeUTF(daten);
                    einfuegeprozessClient(in, out);
                }
                case ":u" -> {
                    out.writeUTF(daten);
                    aenderungsmodusClient(in, out);
                }
                case ":d" -> {
                    out.writeUTF(daten);
                    loeschmodusClient(in, out);
                }
                case ":p" -> {
                    out.writeUTF(daten);
                    persistenzmodusClient(out, in);
                }
            }
        }
    }


    private void persistenzmodusClient(DataOutputStream out, DataInputStream in) throws IOException, ClassNotFoundException {
        String daten = scanner.nextLine();
        while (!(daten.equals(":c") || daten.equals(":r") ||
                daten.equals(":u") || daten.equals(":d") ||
                daten.equals("loadJOS") || daten.equals("loadJBP"))){
            out.writeUTF(daten);
            daten = scanner.nextLine();
            switch (daten) {
                case ":c" -> {
                    out.writeUTF(daten);
                    einfuegeprozessClient(in, out);
                }
                case ":r" -> {
                    out.writeUTF(daten);
                    anzeigemodusClient(out, in);
                }
                case ":d" -> {
                    out.writeUTF(daten);
                    loeschmodusClient(in, out);
                }
                case ":u" -> {
                    out.writeUTF(daten);
                    aenderungsmodusClient(in, out);
                }
                case "loadJOS"-> {
                    out.writeUTF(daten);
                    getObjektData(out, in);
                }
            }
        }
    }

    private void getObjektData(DataOutputStream out, DataInputStream in) throws IOException, ClassNotFoundException {
        String data = in.readLine();
        while (in.available()>0){
            System.out.println(in.readLine());
        }
        System.out.println("test");
        data = scanner.nextLine();
        switch (data) {
            case ":c" -> {
                out.writeUTF(data);
                einfuegeprozessClient(in, out);
            }
            case ":r" -> {
                out.writeUTF(data);
                anzeigemodusClient(out, in);
            }
            case ":u" -> {
                out.writeUTF(data);
                aenderungsmodusClient(in, out);
            }
            case ":d" -> {
                out.writeUTF(data);
                loeschmodusClient(in, out);
            }
            case "loadJOS"-> {
                out.writeUTF(data);
                getObjektData(out, in);
            }
            case "saveJOS"-> out.writeUTF(data);
        }
    }
}
