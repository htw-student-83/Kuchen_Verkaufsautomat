package netzwerk.udp;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Kuchenautomat_Client_UDP {
    private static final String EINFUEGEN = ":c";
    private static final String LOESCHEN = ":d";
    private static final String AENDERN = ":u";
    private static final String ANZEIGEN = ":r";
    private static final String PERSISTIEREN = ":p";

    byte[] buffer = new byte[4096];
    private DatagramSocket socket;
    private InetAddress address;
    private int port;


    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(5002); // Portnummer Client
        InetAddress address = InetAddress.getByName("localhost");
        Kuchenautomat_Client_UDP client = new Kuchenautomat_Client_UDP(datagramSocket, address, 5001);
        client.clientStart();
    }

    public Kuchenautomat_Client_UDP(DatagramSocket socket, InetAddress serveradress, int serverport) {
        this.socket = socket;
        this.address = serveradress;
        this.port = serverport;
    }

    public void clientStart() throws IOException {
              //DatagramPacket packetOut = new DatagramPacket(optioninbytes, optioninbytes.length, address, port);
        DatagramPacket packetIn = new DatagramPacket(buffer, buffer.length);
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packetIn.getData()));
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        DataOutputStream dos = new DataOutputStream(bos);
        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();
        dos.writeUTF(":c");
        this.sendMessage(bos.toByteArray());
        // - Daten werden über einen ByteArrayInputStream empfangen
        //DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packetIn.getData()));
        // - Daten werden über einen ByteArrayInputStream gesendet
        //DatagramPacket packetIn = new DatagramPacket(buffer, buffer.length);
        switch (option) {
            case ":c":
                String herstellername = scanner.nextLine();
                dos.writeUTF(herstellername);
                this.sendMessage(bos.toByteArray());
                String kuchendaten = scanner.nextLine();
                while (!(kuchendaten.equals(":u") || kuchendaten.equals(":d") || kuchendaten.equals(":r") ||
                        kuchendaten.equals(":p"))) {
                    //  dos.writeUTF(kuchendaten);
                    // this.sendMessage(bos.toByteArray());
                    kuchendaten = scanner.nextLine();
                }
                /*
                    scanner = new Scanner(System.in);
                    herstellername = scanner.nextLine();
                    dos.writeUTF(herstellername);
               //     System.out.println("Hersteller gespeichert: " + dis.readUTF());
                    String kuchendaten = scanner.nextLine();
                    while (!(kuchendaten.equals(":u") || kuchendaten.equals(":d") ||
                            kuchendaten.equals(":p") || kuchendaten.equals(":r"))) {
                        dos.writeUTF(kuchendaten);
                        kuchendaten = scanner.nextLine();
                        if (kuchendaten.equals(":c")) {
                      //      einfuegemodus();
                        } else if (kuchendaten.equals(":d")) {
                      //      loeschmodus();
                        } else if (kuchendaten.equals(":p")) {
                      //      persistieren();
                        } else if (kuchendaten.equals(":u")) {
                      //      aenderungsmodus();
                        }
                    }
                    //byte[] bytesBefehleinfuegen = userInput.getBytes();
                    //packetOut = new DatagramPacket(bytesBefehleinfuegen, bytesBefehleinfuegen.length, address, 5002);
                    //datagramSocket.send(packetOut);
                    //byte[] herstellername = herstellernameInput.getBytes();
                    //packetOut = new DatagramPacket(herstellername, herstellername.length, address, 5002);
                    //datagramSocket.send(packetOut);
                    //byte[] kuchendaten = kuchendatenInput.getBytes();
                    //packetOut = new DatagramPacket(kuchendaten, kuchendaten.length, address, 5002);
                    //datagramSocket.send(packetOut);
                    break;

                 */

            case ":u":
                dos.writeUTF(option);
                System.out.println("Änderungsmodus:");
                scanner = new Scanner(System.in);
                String userInput = scanner.nextLine();
                while (!(userInput.equals(":u") || userInput.equals(":d") ||
                        userInput.equals(":p") || userInput.equals(":r"))) {
                    int kuchenID = Integer.parseInt(userInput);
                    dos.writeInt(kuchenID);
                    //       System.out.println(dis.readUTF());
                    userInput = scanner.nextLine();

                    if (userInput.equals(":c")) {
                        //        einfuegemodus();
                    } else if (userInput.equals(":d")) {
                        //        loeschmodus();
                    } else if (userInput.equals(":p")) {
                        //        persistieren();
                    } else if (userInput.equals(":u")) {
                        //        aenderungsmodus();
                    }
                }
                break;

            case ":d":
                dos.writeUTF(option);
                System.out.println("Löschmodus:");
                scanner = new Scanner(System.in);
                userInput = scanner.nextLine();
                while (!(userInput.equals(":u") || userInput.equals(":d") ||
                        userInput.equals(":p") || userInput.equals(":r"))) {
                    String herstellername_bekannt = userInput;
                    dos.writeUTF(herstellername_bekannt);
                    //       System.out.println(dis.readUTF());
                    userInput = scanner.nextLine();
                    int kuchenID = Integer.parseInt(userInput);
                    dos.writeInt(kuchenID);
                    //       System.out.println(dis.readUTF());
                    userInput = scanner.nextLine();

                    if (userInput.equals(":c")) {
                        //                einfuegemodus();
                    } else if (userInput.equals(":d")) {
                        //              loeschmodus();
                    } else if (userInput.equals(":p")) {
                        //            persistieren();
                    } else if (userInput.equals(":u")) {
                        //          aenderungsmodus();
                    }
                }
                break;
            case ":p":
                dos.writeUTF(option);
                //byte[] bytesBefehlpersistieren = userInput.getBytes();
                //packetOut = new DatagramPacket(bytesBefehlpersistieren, bytesBefehlpersistieren.length, address, 5002);
                //datagramSocket.send(packetOut);
                String befehlpersistierung = scanner.nextLine();
                while (!(befehlpersistierung.equals(":u") || befehlpersistierung.equals(":d") ||
                        befehlpersistierung.equals(":p") || befehlpersistierung.equals(":r"))) {
                    dos.writeUTF(befehlpersistierung);
                    befehlpersistierung = scanner.nextLine();
                }
                break;
            //byte[] bytesbefehlautomatenspeichern = befehlautomatenspeichern.getBytes();
            //packetOut = new DatagramPacket(bytesbefehlautomatenspeichern, bytesbefehlautomatenspeichern.length, address, 5002);
            //datagramSocket.send(packetOut);
            //packetIn = new DatagramPacket(buffer, buffer.length);
            //datagramSocket.receive(packetIn);
            //TODO Zustand des Automaten wird empfangen

            default:
        }
    }
    private void sendMessage(byte[] message){
        DatagramPacket packetOut = new DatagramPacket(message,message.length,
                this.address,this.port);
        try {
            this.socket.send(packetOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}