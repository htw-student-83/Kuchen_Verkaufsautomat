package netzwerk.udp;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Kuchenautomat_Client_UDP {
    Scanner scanner = new Scanner(System.in);
    private DatagramPacket packetOut;
    byte[] buffer = new byte[1096];
    private DatagramSocket socket;
    private InetAddress address;
    private int port;


    public static void main(String[] args) throws IOException {
        while (true) {
            try (DatagramSocket datagramSocket = new DatagramSocket(5000);
                 DataOutputStream dos = new DataOutputStream(new ByteArrayOutputStream(70))) {
                InetAddress address = InetAddress.getByName("localhost");
                Kuchenautomat_Client_UDP client = new Kuchenautomat_Client_UDP(datagramSocket, address, 5001);
                client.clientStart();
            } catch (SocketException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Kuchenautomat_Client_UDP(DatagramSocket socket, InetAddress serveradress, int serverport) {
        this.socket = socket;
        this.address = serveradress;
        this.port = serverport;
    }

    public void clientStart() throws IOException, ClassNotFoundException {
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

    private void einfuegemodus() throws IOException, ClassNotFoundException {
        String herstellername = scanner.nextLine();
        byte[] herstellernameInByte = herstellername.getBytes();
        sendMessage(herstellernameInByte);
        String kuchendaten = scanner.nextLine();
        while (!(kuchendaten.equals(":u") || kuchendaten.equals(":d") ||
                kuchendaten.equals(":r") || kuchendaten.equals(":p"))) {
            byte[] kuchendatenInByte = kuchendaten.getBytes();
            sendMessage(kuchendatenInByte);
            kuchendaten = scanner.nextLine();
            byte[] befehl = kuchendaten.getBytes();
            switch (kuchendaten) {
                case ":d" -> {
                    sendMessage(befehl);
                    loeschmodus();
                }
                case ":p" -> {
                    sendMessage(befehl);
                    persistieren();
                }
                case ":u" -> {
                    sendMessage(befehl);
                    aenderungsmodus();
                }
                case ":r" -> {
                    sendMessage(befehl);
                    anzeigemodus();
                }
            }
        }
    }


    private void aenderungsmodus() throws IOException, ClassNotFoundException {
        String userInput = scanner.nextLine();
        while (!(userInput.equals(":c") || userInput.equals(":d") ||
                userInput.equals(":p") || userInput.equals(":r"))) {
            byte[] userinputInByte = userInput.getBytes();
            sendMessage(userinputInByte);
            userInput = scanner.nextLine();
            byte[] befehl = userInput.getBytes();
            switch (userInput) {
                case ":c" -> {
                    sendMessage(befehl);
                    einfuegemodus();
                }
                case ":d" -> {
                    sendMessage(befehl);
                    loeschmodus();
                }
                case ":p" -> {
                    sendMessage(befehl);
                    persistieren();
                }
                case ":r" -> {
                    sendMessage(befehl);
                    anzeigemodus();
                }
            }
        }
    }



    private void loeschmodus() throws IOException, ClassNotFoundException {
        String loeschdaten = scanner.nextLine();
        while (!(loeschdaten.equals(":u") || loeschdaten.equals(":c") ||
                loeschdaten.equals(":p") || loeschdaten.equals(":r"))) {
            byte[] herstellername_bekanntInBytes = loeschdaten.getBytes();
            sendMessage(herstellername_bekanntInBytes);
            loeschdaten = scanner.nextLine();
            byte[] zuloeschenderKuchen = loeschdaten.getBytes();
            sendMessage(zuloeschenderKuchen);
            loeschdaten = scanner.nextLine();
            byte[] userbefehlInBytes = loeschdaten.getBytes();
            switch (loeschdaten) {
                case ":c":
                    sendMessage(userbefehlInBytes);
                    einfuegemodus();
                case ":d":
                    sendMessage(userbefehlInBytes);
                    loeschmodus();
                case ":p":
                    sendMessage(userbefehlInBytes);
                    persistieren();
                case ":u":
                    sendMessage(userbefehlInBytes);
                    aenderungsmodus();
                case ":r":
                    sendMessage(userbefehlInBytes);
                    anzeigemodus();
            }
        }
    }


    private void persistieren() throws IOException, ClassNotFoundException {
        String pBefehl = scanner.nextLine();
        while (!(pBefehl.equals(":u") || pBefehl.equals(":c") ||
                pBefehl.equals(":d") || pBefehl.equals(":r"))) {
            byte[] userbefehlInBytes = pBefehl.getBytes();
            sendMessage(userbefehlInBytes);
            pBefehl = scanner.nextLine();
            byte[] userbefehlPersistierenInBytes = pBefehl.getBytes();
            switch (pBefehl) {
                case ":c":
                    sendMessage(userbefehlPersistierenInBytes);
                    einfuegemodus();
                case ":d":
                    sendMessage(userbefehlPersistierenInBytes);
                    loeschmodus();
                case ":p":
                    sendMessage(userbefehlPersistierenInBytes);
                    persistieren();
                case ":u":
                    sendMessage(userbefehlPersistierenInBytes);
                    aenderungsmodus();
                case "loadJOS":
                    sendMessage(userbefehlPersistierenInBytes);
                    loadStatusAutomatenWithJOS(pBefehl);
            }
        }
    }



    private void anzeigemodus() throws IOException, ClassNotFoundException {
        String userbefehl = scanner.nextLine();
        while (!(userbefehl.equals(":c") || userbefehl.equals(":u") ||
                userbefehl.equals(":d") || userbefehl.equals(":p"))){
            byte[] userbefelInBytes = userbefehl.getBytes();
            sendMessage(userbefelInBytes);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            //Quelle chatGPT;
            //this.socket.receive(packet);
            //byte[] objectData = packet.getData();
            //ByteArrayInputStream objectStream = new ByteArrayInputStream(objectData);
            //ObjectInputStream objectInput = new ObjectInputStream(objectStream);
            //Object receivedObject = objectInput.readObject();
            while (true){
                this.socket.receive(packet);
                //ByteArrayInputStream objectStream = new ByteArrayInputStream(packet.getData());
                //ObjectInputStream objectInput = new ObjectInputStream(objectStream);
                //Object receivedObject = objectInput.readObject();
                String data = new String(packet.getData());
                if (data.contains("Ende")) {
                    break;
                }
                System.out.println(data);
            }
            System.out.println("Test");
            userbefehl = scanner.nextLine();
            byte[] userBefehlDatenAuslesen = userbefehl.getBytes();
            switch (userbefehl){
                case ":u":
                    sendMessage(userBefehlDatenAuslesen);
                    aenderungsmodus();
                    break;
                case ":c":
                    sendMessage(userBefehlDatenAuslesen);
                    einfuegemodus();
                    break;
                case ":d":
                    sendMessage(userBefehlDatenAuslesen);
                    loeschmodus();
                    break;
                case ":p":
                    sendMessage(userBefehlDatenAuslesen);
                    persistieren();
                    break;
            }
        }
    }


    private void loadStatusAutomatenWithJOS(String pBefehl) throws IOException, ClassNotFoundException {
        String userbefehl = pBefehl;
        while (!(userbefehl.equals(":c") || userbefehl.equals(":u") ||
                userbefehl.equals(":d") || userbefehl.equals(":p"))){
            byte[] userbefelInBytes = userbefehl.getBytes();
            packetOut = new DatagramPacket(userbefelInBytes, userbefelInBytes.length, address, port);
            this.socket.send(packetOut);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            //Quelle chatGPT;
            //this.socket.receive(packet);
            //byte[] objectData = packet.getData();
            //ByteArrayInputStream objectStream = new ByteArrayInputStream(objectData);
            //ObjectInputStream objectInput = new ObjectInputStream(objectStream);
            //Object receivedObject = objectInput.readObject();
            while (true){
                try {
                    this.socket.receive(packet);
                    //ByteArrayInputStream objectStream = new ByteArrayInputStream(packet.getData());
                    //ObjectInputStream objectInput = new ObjectInputStream(objectStream);
                    //Object receivedObject = objectInput.readObject();
                    String data = new String(packet.getData());
                    System.out.println(data);
                    //objectData = packet.getData();
                    //objectStream = new ByteArrayInputStream(objectData);
                    //objectInput = new ObjectInputStream(objectStream);
                    //receivedObject = objectInput.readObject();
                    if (data.contains("Ende")) {
                        break;
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
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
        DatagramPacket packetOut = new DatagramPacket(message,message.length, this.address, this.port);
        try {
            this.socket.send(packetOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}