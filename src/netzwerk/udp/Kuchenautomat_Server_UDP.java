package netzwerk.udp;
/*
import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchenautomat;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import jbp.ObjektSpeicherungJBP;
import jos.ObjektLadenJOS;
import jos.ObjektSpeicherungJOS;
import vertrag.Allergene;
import java.io.*;
import java.net.*;
import java.time.Duration;

public class Kuchenautomat_Server_UDP {
    private final byte[] inBuffer=new byte[70];
    Verwaltung model = new Verwaltung();

    private DatagramSocket socket;

    public Kuchenautomat_Server_UDP(DatagramSocket socket, int kapazitaet){
        this.model.setKapazitaet(kapazitaet);
        this.socket = socket;
    }

    public void startServer(){
        System.out.println("Der UDP-Server wurde gestartet..");
        while (true){
            DatagramPacket packetIn = new DatagramPacket(this.inBuffer, this.inBuffer.length);
            try(DataInputStream dis=new DataInputStream(new ByteArrayInputStream(packetIn.getData()))) {
                this.processMessage(dis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


//TODO Der Server nach jedem Befehl in keiner While-Schleife sein. Nur die Befehle sollen ausgeführt werden
    public void processMessage(DataInputStream dis) throws IOException {
        System.out.println("Funktion aufgerufen.");
        this.socket.receive(packetIn);
        String option = dis.readUTF();
        String dataString = "";
        switch (option) {
            case ":c":
                //this.einfuegemodus();
                System.out.println("Befehl " + option);
                //TODO jeweiliger Befehl wird nach Versandt vom CLient nicht sofort erkannt

                // - Neuen Hersteller einfügen
                this.socket.receive(packetIn);
                String herstellername = dis.readUTF();
                String result = herstellerEinfuegen(herstellername);
                System.out.println(result);
                //- Neuen Kuchen einfügen
                dataString = dis.readUTF();
                String result2 = getKuchenObejtData(dataString);
                System.out.println(result2);
                    //dis.readUTF() blockiert angeblich?!
                break;
            case ":u":
                //this.aenderungsmodus();
                System.out.println("Befehl u");
               // String nummer = dis.readUTF();
               // int kuchenIDInspizierung = Integer.parseInt(nummer);
                //String result3 = kuchenInspizieren(kuchenIDInspizierung);
                //System.out.println(result3);
                break;
            case ":d":
                //this.loeschmodus();
                System.out.println("Befehl d");
                //this.socket.receive(packetIn);
               String inputFachnummerFueLoeschung = dis.readUTF();
                int fachnummerKuchenLoeschung = Integer.parseInt(inputFachnummerFueLoeschung);
                String result4 = kuchenloeschen(fachnummerKuchenLoeschung);

                System.out.println(result4);

                break;
            case ":p":
                this.persistenzmodus();
                break;
            case ":r":
                this.datenlesen();
                break;
            default:
        }
    }


    private void einfuegemodus(){

    }

    private void aenderungsmodus(){
        System.out.println("Änderung.");
    }

    private void anzeigemodus(){
        System.out.println("Anzeige.");
    }

    private void loeschmodus(){
        System.out.println("Löschen.");
    }

    //Quelle: https://stackoverflow.com/questions/66786965/how-can-i-turn-an-int-minutes-into-a-duration-in-java
    protected String getKuchenObejtData(String kuchendaten){
        String[] dataArray = kuchendaten.split(" ");
        // - Aus dem String am Index 0 werden die ersten zwei Zeichen entfernt
        //String typString = dataArray[0].substring(2);
        Kuchentyp typ = Kuchentyp.valueOf(dataArray[0]);
        String hersteller = dataArray[1];
        Hersteller newhersteller = new Hersteller(hersteller);
        double preis = Double.parseDouble(dataArray[2]);
        int naehrwert = Integer.parseInt(dataArray[3]);
        int haltbarkeitInt = Integer.parseInt(dataArray[4]);
        Duration haltbarkeitD = Duration.ofMinutes(haltbarkeitInt);
        //Duration haltbarkeit = Duration.parse(dataArray[4]);
        Allergene allergene = Allergene.valueOf(dataArray[5]);
        String sorte = dataArray[6];
        boolean isdeleted = this.model.insertKuchen(typ, newhersteller, preis,naehrwert,
                haltbarkeitD, allergene, sorte);
        if (isdeleted) {
            return "Kuchen eingefügt.";
        } else {
            return "Kuchen nicht eingefügt.";
        }
    }

    protected String herstellerEinfuegen(String herstellername) {
        Hersteller hersteller = new Hersteller(herstellername);
        boolean isdeleted = this.model.insertHersteller(hersteller);
        if (isdeleted) {
            return "Hersteller eingefügt.";
        } else {
            return "Hersteller nicht eingefügt..";
        }
    }

    protected String kuchenInspizieren(int input) {
        boolean isput = this.model.editKuchen(input);
        if (isput) {
            return "Habe den Kuchen inspiziert.";
        } else {
            return "Habe den Kuchen nicht inspiziert.";
        }
    }

    protected String kuchenloeschen(int input) {
        boolean isput = this.model.deleteKuchen(input);
        if (isput) {
            return "Habe den Kuchen geloescht.";
        } else {
            return "Habe den Kuchen nicht geloescht.";
        }
    }

    protected void datenlesen() throws IOException {
       /* ByteArrayInputStream bais = new ByteArrayInputStream(packetIn.getData());
        //DataInputStream dis = new DataInputStream(bais);
        //String input = dis.readUTF();
        //socket.receive(packetIn);
        //String input = new String(packetIn.getData(), packetIn.getLength());
        switch (input){
            case "kuchen":
                kuchendaten();
            case "hersteller":
                herstellerdaten();
            case "allergene":
                allergenendaten();
        }


    }

    protected void persistenzmodus() throws IOException {

        ByteArrayInputStream bais = new ByteArrayInputStream(packetIn.getData());
        DataInputStream dis = new DataInputStream(bais);
        String inputPersistierung = dis.readUTF();
        //socket.receive(packetIn);
        //String input = new String(packetIn.getData(), packetIn.getLength());
        switch (inputPersistierung){
            case "saveJOS":
                automatenspeichern();
            case "loadJOS":
                automatenladen();
            case "saveJBP":
            case "loadJBP":
        }


    }

    protected void automatenspeichern() {
        ObjektSpeicherungJOS.persistiereAutomaten(this.model);
    }

    protected void automatenladen() throws IOException {
        this.model = ObjektLadenJOS.reloadAutomt();
        if(this.model!=null){
            for(Kuchen kuchen: this.model.readKuchen()){
                String preis = String.valueOf(kuchen.getPreis());
                String naehrwert = String.valueOf(kuchen.getNaehrwert());
                String haltbarkeit = String.valueOf(kuchen.getHaltbarkeit());
                String allergene = String.valueOf(kuchen.getAllergene());
                String sorte  = kuchen.getKremsorte();
                ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
                DataOutputStream dos = new DataOutputStream(baos);
                dos.writeUTF(preis);
                dos.writeUTF(naehrwert);
                dos.writeUTF(haltbarkeit);
                dos.writeUTF(allergene);
                dos.writeUTF(sorte);
                baos.close();
                dos.close();
            }


            for(Hersteller hersteller: this.model.readHersteller()){
                String namehersteller = hersteller.getName();
                ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
                DataOutputStream dos = new DataOutputStream(baos);
                dos.writeUTF(namehersteller);
                baos.close();
                dos.close();
            }


            for(Allergene allergene: this.model.readAllergener()){
                String nameAllergen = allergene.toString();
                ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
                DataOutputStream dos = new DataOutputStream(baos);
                dos.writeUTF(nameAllergen);
                baos.close();
                dos.close();
            }
        }
    }


    protected void kuchendaten() throws IOException {
        for(Kuchen kuchen: this.model.readKuchen()){
            String naehrwert = String.valueOf(kuchen.getNaehrwert());
            String haltbarkeit = String.valueOf(kuchen.getHaltbarkeit());
            String allergene = String.valueOf(kuchen.getAllergene());
            String sorte  = kuchen.getKremsorte();
            ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeUTF(sorte);
            dos.writeInt(kuchen.getFachnummer());
            dos.writeUTF(naehrwert);
            dos.writeUTF(haltbarkeit);
            dos.writeUTF(allergene);
            baos.close();
            dos.close();
        }
    }


    protected void herstellerdaten() throws IOException {
        for(Hersteller hersteller: this.model.readHersteller()){
            String herstellerName = hersteller.getName();
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1096);
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeUTF(herstellerName);
            baos.close();
            dos.close();
        }
    }


    protected void allergenendaten() throws IOException {
        for(Allergene allergene: this.model.readAllergener()){
            String nameAllergen = String.valueOf(allergene);
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1096);
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeUTF(nameAllergen);
            baos.close();
            dos.close();
        }
    }
}

 */