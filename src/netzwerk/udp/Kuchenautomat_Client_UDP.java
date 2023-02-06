package netzwerk.udp;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class Kuchenautomat_Client_UDP {
    Scanner scanner = new Scanner(System.in);
    private DatagramPacket packetOut;
    byte[] buffer = new byte[4096];
    private DatagramSocket socket;
    private InetAddress address;
    private int port;


    public static void main(String[] args) throws IOException {
        while (true) {
            try (DatagramSocket datagramSocket = new DatagramSocket(5000);
                 DataOutputStream dos = new DataOutputStream(new ByteArrayOutputStream(70))) {
                InetAddress address = InetAddress.getByName("localhost");
                Kuchenautomat_Client_UDP client = new Kuchenautomat_Client_UDP(datagramSocket, address, 5001);
                client.clientStart(dos);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }

    public Kuchenautomat_Client_UDP(DatagramSocket socket, InetAddress serveradress, int serverport) {
        this.socket = socket;
        this.address = serveradress;
        this.port = serverport;
    }

    public void clientStart(DataOutputStream dos) throws IOException {
        String option = scanner.nextLine();
        byte[] optionInByte = option.getBytes();
        packetOut = new DatagramPacket(optionInByte, optionInByte.length, address, port);
        this.socket.send(packetOut);
        switch (option) {
            case ":c":
                einfuegemodus();
                break;
            case ":u":
                aenderungsmodus();
                break;
            case ":d":
                loeschmodus();
                break;
            case ":r":
                anzeigemodus();
                break;
            case ":p":
                persistieren();
                break;
        }
    }

    private void einfuegemodus() throws IOException {
        String herstellername = scanner.nextLine();
        byte[] herstellernameInByte = herstellername.getBytes();
        packetOut = new DatagramPacket(herstellernameInByte, herstellernameInByte.length, address, port);
        this.socket.send(packetOut);
        //packetOut = new DatagramPacket(herstellernameInByte, herstellername.length(), address, port);
        //this.socket.send(packetOut);
        String kuchendaten = scanner.nextLine();
        while (!(kuchendaten.equals(":u") || kuchendaten.equals(":d") ||
                kuchendaten.equals(":r") || kuchendaten.equals(":p"))) {
            byte[] kuchendatenInByte = kuchendaten.getBytes();
            packetOut = new DatagramPacket(kuchendatenInByte, kuchendatenInByte.length, address, port);
            this.socket.send(packetOut);
            kuchendaten = scanner.nextLine();
            byte[] befehl = kuchendaten.getBytes();
            switch (kuchendaten) {
                case ":d" -> {
                    packetOut = new DatagramPacket(befehl, befehl.length, address, port);
                    this.socket.send(packetOut);
                    loeschmodus();
                }
                case ":p" -> {
                    packetOut = new DatagramPacket(befehl, befehl.length, address, port);
                    this.socket.send(packetOut);
                    persistieren();
                }
                case ":u" -> {
                    packetOut = new DatagramPacket(befehl, befehl.length, address, port);
                    this.socket.send(packetOut);
                    aenderungsmodus();
                }
                case ":r" -> {
                    packetOut = new DatagramPacket(befehl, befehl.length, address, port);
                    this.socket.send(packetOut);
                    anzeigemodus();
                }
            }
        }
    }


    private void aenderungsmodus() throws IOException {
        System.out.println("Änderungsmodus:");
        String userInput = scanner.nextLine();
        while (!(userInput.equals(":c") || userInput.equals(":d") ||
                userInput.equals(":p") || userInput.equals(":r"))) {
            byte[] userinputInByte = userInput.getBytes();
            packetOut = new DatagramPacket(userinputInByte, userinputInByte.length, address, port);
            this.socket.send(packetOut);
            userInput = scanner.nextLine();
            byte[] befehl = userInput.getBytes();
            switch (userInput) {
                case ":c" -> {
                    packetOut = new DatagramPacket(befehl, befehl.length, address, port);
                    this.socket.send(packetOut);
                    einfuegemodus();
                }
                case ":d" -> {
                    packetOut = new DatagramPacket(befehl, befehl.length, address, port);
                    this.socket.send(packetOut);
                    loeschmodus();
                }
                case ":p" -> {
                    packetOut = new DatagramPacket(befehl, befehl.length, address, port);
                    this.socket.send(packetOut);
                    persistieren();
                }
                case ":r" -> {
                    packetOut = new DatagramPacket(befehl, befehl.length, address, port);
                    this.socket.send(packetOut);
                    anzeigemodus();
                }
            }
        }
    }



    private void loeschmodus() throws IOException {
        System.out.println("Löschmodus:");
        String loeschdaten = scanner.nextLine();
        while (!(loeschdaten.equals(":u") || loeschdaten.equals(":c") ||
                loeschdaten.equals(":p") || loeschdaten.equals(":r"))) {
            byte[] herstellername_bekanntInBytes = loeschdaten.getBytes();
            packetOut = new DatagramPacket(herstellername_bekanntInBytes, herstellername_bekanntInBytes.length, address, port);
            this.socket.send(packetOut);
            loeschdaten = scanner.nextLine();
            byte[] zuloeschenderKuchen = loeschdaten.getBytes();
            packetOut = new DatagramPacket(zuloeschenderKuchen, zuloeschenderKuchen.length, address, port);
            this.socket.send(packetOut);
            loeschdaten = scanner.nextLine();
            byte[] userbefehlInBytes = loeschdaten.getBytes();
            switch (loeschdaten) {
                case ":c":
                    packetOut = new DatagramPacket(userbefehlInBytes, userbefehlInBytes.length, address, port);
                    this.socket.send(packetOut);
                    einfuegemodus();
                case ":d":
                    packetOut = new DatagramPacket(userbefehlInBytes, userbefehlInBytes.length, address, port);
                    this.socket.send(packetOut);
                    loeschmodus();
                case ":p":
                    packetOut = new DatagramPacket(userbefehlInBytes, userbefehlInBytes.length, address, port);
                    this.socket.send(packetOut);
                    persistieren();
                case ":u":
                    packetOut = new DatagramPacket(userbefehlInBytes, userbefehlInBytes.length, address, port);
                    this.socket.send(packetOut);
                    aenderungsmodus();
            }
        }
    }


    private void persistieren() throws IOException {
        String pBefehl = scanner.nextLine();
        while (!(pBefehl.equals(":u") || pBefehl.equals(":c") ||
                pBefehl.equals(":d") || pBefehl.equals(":r"))) {
            byte[] userbefehlInBytes = pBefehl.getBytes();
            packetOut = new DatagramPacket(userbefehlInBytes, userbefehlInBytes.length, address, port);
            this.socket.send(packetOut);
            pBefehl = scanner.nextLine();
            switch (pBefehl) {
                case ":c":
                    packetOut = new DatagramPacket(userbefehlInBytes, userbefehlInBytes.length, address, port);
                    this.socket.send(packetOut);
                    einfuegemodus();
                case ":d":
                    packetOut = new DatagramPacket(userbefehlInBytes, userbefehlInBytes.length, address, port);
                    this.socket.send(packetOut);
                    loeschmodus();
                case ":p":
                    packetOut = new DatagramPacket(userbefehlInBytes, userbefehlInBytes.length, address, port);
                    this.socket.send(packetOut);
                    persistieren();
                case ":u":
                    packetOut = new DatagramPacket(userbefehlInBytes, userbefehlInBytes.length, address, port);
                    this.socket.send(packetOut);
                    aenderungsmodus();
            }
        }
    }



    private void anzeigemodus() throws IOException {
        System.out.println("Anzeigemodus:");
        int cnt = 0;
        String userbefehl = scanner.nextLine();
        while (!(userbefehl.equals(":c") || userbefehl.equals(":u") ||
                userbefehl.equals(":d") || userbefehl.equals(":p"))){
            byte[] userbefelInBytes = userbefehl.getBytes();
            packetOut = new DatagramPacket(userbefelInBytes, userbefelInBytes.length, address, port);
            this.socket.send(packetOut);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            //Quelle chatGPT;
            //TODO Welche Abbruchbedingung kann hier verwendet werden?
            while (packet.getData().length!=0){
                this.socket.receive(packet);
                String cakeData = new String(packet.getData(), 0, packet.getLength());
                System.out.println(cakeData);
                /*
                if(cnt == 6){
                 break;
                }else {
                    cnt++;
                }
                 */
            }
            System.out.println("Test");
            userbefehl = scanner.nextLine();
            System.out.println("Eingegeben: " + userbefehl);
            switch (userbefehl){
                case ":u":
                    aenderungsmodus();
                    break;
                case ":c":
                    einfuegemodus();
                    break;
                case ":d":
                    loeschmodus();
                    break;
                case ":p":
                    persistieren();
                    break;
            }
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