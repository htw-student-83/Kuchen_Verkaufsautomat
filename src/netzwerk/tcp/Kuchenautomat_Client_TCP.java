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
                    einfuegeprozessClient(in, out, br);
                    break;
                case ":u":
                    aenderungsmodusClient(in, out, br);
                    break;
                case ":d":
                    loeschmodusClient(in, out, br);
                    break;
                case ":r":
                    anzeigemodusClient(out, in, br);
                    break;
                case ":p":
                    persistenzmodusClient(out, in, br);
                    break;
            }
        }
    }

    private void einfuegeprozessClient(DataInputStream in, DataOutputStream out, BufferedReader br)
            throws IOException, ClassNotFoundException {
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
                    loeschmodusClient(in, out, br);
                }
                case ":r" -> {
                    out.writeUTF(kuchendaten);
                    anzeigemodusClient(out, in, br);
                }
                case ":u" -> {
                    out.writeUTF(kuchendaten);
                    aenderungsmodusClient(in, out, br);
                }
                case ":p" -> {
                    out.writeUTF(kuchendaten);
                    persistenzmodusClient(out, in, br);
                }
            }
        }
    }

    private void aenderungsmodusClient(DataInputStream in, DataOutputStream out, BufferedReader br)
            throws IOException, ClassNotFoundException {
        String kuchenID = scanner.nextLine();
        while (!(kuchenID.equals(":c") || kuchenID.equals(":r") ||
                kuchenID.equals(":d") || kuchenID.equals(":p"))){
            out.writeUTF(kuchenID);
            kuchenID = scanner.nextLine();
            switch (kuchenID) {
                case ":c" -> {
                    out.writeUTF(kuchenID);
                    einfuegeprozessClient(in, out, br);
                }
                case ":r" -> {
                    out.writeUTF(kuchenID);
                    anzeigemodusClient(out, in, br);
                }
                case ":d" -> {
                    out.writeUTF(kuchenID);
                    loeschmodusClient(in, out, br);
                }
                case ":p" -> {
                    out.writeUTF(kuchenID);
                    persistenzmodusClient(out, in, br);
                }
            }
        }
    }


    private void loeschmodusClient(DataInputStream in, DataOutputStream out, BufferedReader br)
            throws IOException, ClassNotFoundException {
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
                    einfuegeprozessClient(in, out, br);
                }
                case ":r" -> {
                    out.writeUTF(herstellername);
                    anzeigemodusClient(out, in, br);
                }
                case ":u" -> {
                    out.writeUTF(herstellername);
                    aenderungsmodusClient(in, out, br);
                }
                case ":p" -> {
                    out.writeUTF(herstellername);
                    persistenzmodusClient(out,in, br);
                }
            }
        }
    }


    private void anzeigemodusClient(DataOutputStream out, DataInputStream in, BufferedReader br)
            throws IOException, ClassNotFoundException {
        String daten = scanner.nextLine();
        while (!(daten.equals(":c") || daten.equals(":d") ||
                daten.equals(":u") || daten.equals(":p"))) {
            out.writeUTF(daten);
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if(line.equals(" ")){
                    break;
                }
            }
            daten = scanner.nextLine();
            switch (daten) {
                case ":c" -> {
                    out.writeUTF(daten);
                    einfuegeprozessClient(in, out, br);
                }
                case ":u" -> {
                    out.writeUTF(daten);
                    aenderungsmodusClient(in, out, br);
                }
                case ":d" -> {
                    out.writeUTF(daten);
                    loeschmodusClient(in, out, br);
                }
                case ":p" -> {
                    out.writeUTF(daten);
                    persistenzmodusClient(out, in, br);
                }
            }
        }
    }


    private void persistenzmodusClient(DataOutputStream out, DataInputStream in, BufferedReader br)
            throws IOException, ClassNotFoundException {
        String daten = scanner.nextLine();
        while (!(daten.equals(":c") || daten.equals(":r") ||
                daten.equals(":u") || daten.equals(":d") ||
                daten.equals("loadJOS") || daten.equals("loadJBP"))){
            out.writeUTF(daten);
            daten = scanner.nextLine();
            switch (daten) {
                case ":c" -> {
                    out.writeUTF(daten);
                    einfuegeprozessClient(in, out, br);
                }
                case ":r" -> {
                    out.writeUTF(daten);
                    anzeigemodusClient(out, in, br);
                }
                case ":d" -> {
                    out.writeUTF(daten);
                    loeschmodusClient(in, out, br);
                }
                case ":u" -> {
                    out.writeUTF(daten);
                    aenderungsmodusClient(in, out, br);
                }
                case "loadJOS"-> {
                    out.writeUTF(daten);
                    getObjektData(out, in, br);
                }
            }
        }
    }

    private void getObjektData(DataOutputStream out, DataInputStream in, BufferedReader br)
            throws IOException, ClassNotFoundException {
        String line = "";
        while ((line = br.readLine())!=null){
            System.out.println(line);
            if(line.equals(" ")){
                break;
            }
        }
        String data = scanner.nextLine();
        switch (data) {
            case ":c" -> {
                out.writeUTF(data);
                einfuegeprozessClient(in, out, br);
            }
            case ":r" -> {
                out.writeUTF(data);
                anzeigemodusClient(out, in, br);
            }
            case ":u" -> {
                out.writeUTF(data);
                aenderungsmodusClient(in, out, br);
            }
            case ":d" -> {
                out.writeUTF(data);
                loeschmodusClient(in, out, br);
            }
            case "loadJOS"-> {
                out.writeUTF(data);
                getObjektData(out, in, br);
            }
            case "saveJOS"-> out.writeUTF(data);
        }
    }
}
