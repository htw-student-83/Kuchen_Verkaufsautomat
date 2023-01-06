package netzwerk.udp;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.Verwaltung;
import vertrag.Allergene;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.time.Duration;

public class Kuchenautomat_Server_UDP {
    Verwaltung model;

    public static void main(String[]args){
        Verwaltung model = new Verwaltung();
        Kuchenautomat_Server_UDP server = new Kuchenautomat_Server_UDP(model);
        server.startServer();
    }

    public Kuchenautomat_Server_UDP(Verwaltung model){
        this.model = model;
    }

    public void startServer() {
        while (true) {
            //String isWaiting = "Warte jetzt auf eine Anfrage vom Client";
            //byte[] isWaitinginBytes = isWaiting.getBytes();
            try (DatagramSocket datagramSocket = new DatagramSocket(5002)) {
                InetAddress address = InetAddress.getByName("localhost");
                byte[] buffer = new byte[4096];

                DatagramPacket packetIn = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(packetIn);
                String received = new String(packetIn.getData(), 0, packetIn.getLength());

                packetIn = new DatagramPacket(buffer, buffer.length);
                //DatagramPacket packetOut = new DatagramPacket(isWaitinginBytes, isWaitinginBytes.length, address, 5001);
                //datagramSocket.send(packetOut);
                switch (received){
                    case "i":
                        String herstellername = "Wie heißt der Hersteller?";
                        byte[] herstellernameBytes = herstellername.getBytes();
                        DatagramPacket packetOut = new DatagramPacket(herstellernameBytes, herstellernameBytes.length, address, 5001);
                        datagramSocket.send(packetOut);
                        datagramSocket.receive(packetIn);
                        String input = new String(packetIn.getData(), packetIn.getLength());
                        String result = herstellerEinfuegen(input);
                        byte[] bytes = result.getBytes();
                        packetOut = new DatagramPacket(bytes, bytes.length, address, 5001);
                        datagramSocket.send(packetOut);
                        break;
                    case "i2":
                        String kuchentyp = "Welcher Typ?";
                        byte[] kuchentypinBytes = kuchentyp.getBytes();
                        String kuchenhersteller = "Welcher Hersteller?";
                        byte[] kuchenherstellerinBytes = kuchenhersteller.getBytes();
                        String kuchenpreis = "Welcher Preis?";
                        byte[] kuchenpreisinBytes = kuchenpreis.getBytes();
                        String kuchensorte = "Welche Sorte?";
                        byte[] kuchensorteinBytes = kuchensorte.getBytes();
                        String kuchennaehrwert = "Welcher Naehrwert?";
                        byte[] kuchennaehrwertinBytes = kuchennaehrwert.getBytes();
                        String kuchenallergene = "Welche Allergene?";
                        byte[] kuchenallergeneinBytes = kuchenallergene.getBytes();

                        //TODO einzelne Daten convertieren

                        // - Aufforderung und Erwartung der Kuchendaten
                        packetOut = new DatagramPacket(kuchentypinBytes, kuchentypinBytes.length, address, 5001);
                        datagramSocket.send(packetOut);
                        datagramSocket.receive(packetIn);
                        byte[] typByte = packetIn.getData();
                        String typeS= new String(typByte, typByte.length);
                        System.out.println("Typ: " + typeS);

                        packetOut = new DatagramPacket(kuchenherstellerinBytes, kuchenherstellerinBytes.length, address, 5001);
                        datagramSocket.send(packetOut);
                        datagramSocket.receive(packetIn);
                        String inputherstellername = new String(packetIn.getData(), packetIn.getLength());

                        packetOut = new DatagramPacket(kuchenpreisinBytes, kuchenpreisinBytes.length, address, 5001);
                        datagramSocket.send(packetOut);
                        datagramSocket.receive(packetIn);
                        ByteBuffer buffer2 = ByteBuffer.allocate(Double.BYTES);
                        byte[] preisinBytes = packetIn.getData();
                        int preisInt = (preisinBytes[0] & 0xff);
                        System.out.println("Preis: " + Float.intBitsToFloat(preisInt));

                        //String preisSever = new String(packetIn.getData(), packetIn.getLength());

                        packetOut = new DatagramPacket(kuchennaehrwertinBytes, kuchennaehrwertinBytes.length, address, 5001);
                        datagramSocket.send(packetOut);
                        datagramSocket.receive(packetIn);
                        byte[] naehrwertinBytes = packetIn.getData();
                        int naehrwertZahl = (naehrwertinBytes[0] & 0xff);
                        System.out.println("Zahl: " + naehrwertZahl);

                        packetOut = new DatagramPacket(kuchensorteinBytes, kuchensorteinBytes.length, address, 5001);
                        datagramSocket.send(packetOut);
                        datagramSocket.receive(packetIn);
                        String sorte= new String(packetIn.getData(), packetIn.getLength());

                        packetOut = new DatagramPacket(kuchenallergeneinBytes, kuchenallergeneinBytes.length, address, 5001);
                        datagramSocket.send(packetOut);
                        datagramSocket.receive(packetIn);
                        String allergeneb= new String(packetIn.getData(), packetIn.getLength());
                        System.out.println("Allergene: " + allergeneb);

                        //result = kuchenEinfuegen(Kuchentyp.valueOf(typeS), inputherstellername, preis, naehrwertZahl, Allergene.valueOf(allergeneb), kuchensorte);

                        //byte[] resultinBytes = result.getBytes();
                        //packetOut = new DatagramPacket(resultinBytes, resultinBytes.length, address, 5001);
                        datagramSocket.send(packetOut);
                        break;
                    case "u":
                        //Quelle: https://stackoverflow.com/questions/22798345/how-to-get-integer-value-from-byte-array-returned-by-metamessage-getdata
                        String inspizierung = "Welche Fachnummer?";
                        byte[] inspizierenInBytes = inspizierung.getBytes();
                        packetOut = new DatagramPacket(inspizierenInBytes, inspizierenInBytes.length, address, 5001);
                        datagramSocket.send(packetOut);
                        datagramSocket.receive(packetIn);
                        byte[] data = packetIn.getData();
                        int fachnummer = (data[0] & 0xff);
                        result = kuchenInspizieren(fachnummer);
                        byte[] fachnummerinBytes = result.getBytes();
                        packetOut = new DatagramPacket(fachnummerinBytes, fachnummerinBytes.length, address, 5001);
                        datagramSocket.send(packetOut);
                        break;
                    case "d":
                        String loeschenderKuchen = "Um welche Fachnummer geht es?";
                        byte[] zuloeschenderKuchen = loeschenderKuchen.getBytes();
                        packetOut = new DatagramPacket(zuloeschenderKuchen, zuloeschenderKuchen.length, address, 5001);
                        datagramSocket.send(packetOut);
                        datagramSocket.receive(packetIn);
                        byte[] dataNr = packetIn.getData();
                        int fachnummerDeleteKuchen = (dataNr[0] & 0xff);
                        result = kuchenloeschen(fachnummerDeleteKuchen);
                        byte[] deletedKuchen = result.getBytes();
                        packetOut = new DatagramPacket(deletedKuchen, deletedKuchen.length, address, 5001);
                        datagramSocket.send(packetOut);
                        break;
                    default:
                        String failedInput = "Ungueltiges Zeichen verwendet.";
                        byte[] faildInputinBytes = failedInput.getBytes();
                        packetOut = new DatagramPacket(faildInputinBytes, faildInputinBytes.length, address, 5001);
                        datagramSocket.send(packetOut);
                        break;
                }
            } catch (
                    IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String herstellerEinfuegen(String herstellername) {
            Hersteller hersteller = new Hersteller(herstellername);
            boolean isdeleted = this.model.insertH(hersteller);
            if (isdeleted) {
                return "Hersteller eingefügt.";
            } else {
                return "Hersteller nicht eingefügt..";
            }
    }

    private String kuchenEinfuegen(Kuchentyp typ, String herstellername,
                                   Duration haltbarkeit, double preis,
                                   int naehrwert, Allergene allgene,
                                   String sorte) {
        Hersteller hersteller = new Hersteller(herstellername);
        boolean isdeleted = this.model.insert(typ, hersteller, haltbarkeit,preis, naehrwert, allgene, sorte);
        if (isdeleted) {
            return "Habe den Kuchen eingefuegt.";
        } else {
            return "Habe den Kuchen nicht eingefuegt.";
        }
    }


    private String kuchenInspizieren(int input) {
        boolean isput = this.model.edit(input);
        if (isput) {
            return "Habe den Kuchen inspiziert.";
        } else {
            return "Habe den Kuchen nicht inspiziert.";
        }
    }

    private String kuchenloeschen(int input) {
        boolean isput = this.model.delete(input);
        if (isput) {
            return "Habe den Kuchen geloescht.";
        } else {
            return "Habe den Kuchen nicht geloescht.";
        }
    }
}
