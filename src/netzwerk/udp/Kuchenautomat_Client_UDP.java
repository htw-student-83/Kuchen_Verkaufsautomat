package netzwerk.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Kuchenautomat_Client_UDP {
    static String options = "";

    public static void main(String[]args){
        clientStart();
    }

    public static void clientStart(){
        Scanner userInput = new Scanner(System.in);
        try(DatagramSocket datagramSocket = new DatagramSocket(5001)) {
            InetAddress address = InetAddress.getByName("localhost");
            byte[] buffer = new byte[4096];
            System.out.println("Wählen Sie unter folgenden Optionen aus:");
            System.out.println("(i) - Hersteller einfügen");
            System.out.println("(i2) - Kuchen einfügen");
            System.out.println("(u) - Kuchen inspizieren");
            System.out.println("(d) - Kuchen löschen");
            options = userInput.next();
            String received = "";

            DatagramPacket packetIn = new DatagramPacket(buffer, buffer.length);

            switch (options){
                case "i":
                    byte[] bytesBefehl = options.getBytes();
                    DatagramPacket packetOut = new DatagramPacket(bytesBefehl, bytesBefehl.length, address, 5002);
                    datagramSocket.send(packetOut);
                    System.out.println("Habe die Messages an den Server weitergeleitet.");
                    System.out.println("Warte jetzt auf eine Reaktion vom Server");
                    datagramSocket.receive(packetIn);
                    Thread.sleep(3000);
                    System.out.println("Soeben sind Daten eingetroffen...");
                    received = new String(packetIn.getData(),0, packetIn.getLength());
                    System.out.println("[Server]: " + received);
                    String herstellername = userInput.next();
                    byte[] bytesherstellername = herstellername.getBytes();
                    packetOut = new DatagramPacket(bytesherstellername, bytesherstellername.length, address, 5002);
                    datagramSocket.send(packetOut);
                    datagramSocket.receive(packetIn);
                    received = new String(packetIn.getData(),0, packetIn.getLength());
                    System.out.println("[Server]: " + received);
                    break;
                case "i2":
                    byte[] bytesBefehl2 = options.getBytes();
                    packetOut = new DatagramPacket(bytesBefehl2, bytesBefehl2.length, address, 5002);
                    datagramSocket.send(packetOut);
                    System.out.println("Habe die Messages an den Server weitergeleitet.");
                    System.out.println("Warte jetzt auf eine Reaktion vom Server");
                    datagramSocket.receive(packetIn);
                    received = new String(packetIn.getData(),0, packetIn.getLength());
                    System.out.println("[Server]" + received);

                    // - Daten eingeben und diese an den Server senden
                    String kuchentyp = userInput.next();
                    byte[] typ = kuchentyp.getBytes();
                    packetOut = new DatagramPacket(typ, typ.length, address, 5002);
                    datagramSocket.send(packetOut);
                    datagramSocket.receive(packetIn);
                    received = new String(packetIn.getData(),0, packetIn.getLength());
                    System.out.println("[Server]" + received);

                    String hersteller = userInput.next();
                    byte[] inputHersteller = hersteller.getBytes();
                    packetOut = new DatagramPacket(inputHersteller, inputHersteller.length, address, 5002);
                    datagramSocket.send(packetOut);
                    datagramSocket.receive(packetIn);
                    received = new String(packetIn.getData(),0, packetIn.getLength());
                    System.out.println("[Server]" + received);

                    double preis = userInput.nextDouble();
                    String preisK = String.valueOf(preis);
                    byte[] preisinBytes = preisK.getBytes();
                    packetOut = new DatagramPacket(preisinBytes, preisinBytes.length, address, 5002);
                    datagramSocket.send(packetOut);
                    datagramSocket.receive(packetIn);
                    received = new String(packetIn.getData(),0, packetIn.getLength());
                    System.out.println("[Server]" + received);

                    String naehrwert = userInput.next();
                    byte[] naehrwertBytes = naehrwert.getBytes();
                    packetOut = new DatagramPacket(naehrwertBytes, naehrwertBytes.length, address, 5002);
                    datagramSocket.send(packetOut);
                    datagramSocket.receive(packetIn);
                    received = new String(packetIn.getData(),0, packetIn.getLength());
                    System.out.println("[Server]" + received);

                    String sorte = userInput.next();
                    byte[] sorteBytes = sorte.getBytes();
                    packetOut = new DatagramPacket(sorteBytes, sorteBytes.length, address, 5002);
                    datagramSocket.send(packetOut);
                    datagramSocket.receive(packetIn);
                    received = new String(packetIn.getData(),0, packetIn.getLength());
                    System.out.println("[Server]" + received);

                    String allergene = userInput.next();
                    byte[] allergeneBytes = allergene.getBytes();
                    packetOut = new DatagramPacket(allergeneBytes, allergeneBytes.length, address, 5002);
                    datagramSocket.send(packetOut);
                    datagramSocket.receive(packetIn);
                    received = new String(packetIn.getData(),0, packetIn.getLength());
                    System.out.println("[Server]" + received);
                    break;
                case "u":
                case "d":
                    byte[] bytesBefehl3 = options.getBytes();
                    packetOut = new DatagramPacket(bytesBefehl3, bytesBefehl3.length, address, 5002);
                    datagramSocket.send(packetOut);
                    System.out.println("Habe die Messages an den Server weitergeleitet.");
                    System.out.println("Warte jetzt auf eine Reaktion vom Server");
                    packetIn = new DatagramPacket(buffer, buffer.length);
                    datagramSocket.receive(packetIn);
                    Thread.sleep(3000);
                    System.out.println("Soeben sind Daten eingetroffen...");
                    received = new String(packetIn.getData(),0, packetIn.getLength());
                    System.out.println("[Server]: " + received);
                    String userFachnummmer = userInput.next();
                    byte[] fachnummerinBytes = userFachnummmer.getBytes();
                    packetOut = new DatagramPacket(fachnummerinBytes, fachnummerinBytes.length, address, 5002);
                    datagramSocket.send(packetOut);
                    System.out.println("Warte jetzt auf eine Reaktion vom Server");
                    datagramSocket.receive(packetIn);
                    System.out.println("Soeben sind Daten eingetroffen...");
                    received = new String(packetIn.getData(),0, packetIn.getLength());
                    System.out.println("[Server]: " + received);
                    break;
                default:
                    System.out.println("Ungueltiges Zeichen eingegeben.");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
