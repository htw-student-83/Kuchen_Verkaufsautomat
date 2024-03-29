package netzwerk.udp;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import jbp.ObjektLadenJBP;
import jbp.ObjektSpeicherungJBP;
import jos.ObjektLadenJOS;
import jos.ObjektSpeicherungJOS;
import vertrag.Allergene;

import java.io.*;
import java.net.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Kuchenautomat_Server_UDP {
    private final byte[] inBuffer=new byte[1024];
    Verwaltung model;
    private DatagramSocket socket;
    DatagramPacket packetIn = new DatagramPacket(this.inBuffer, this.inBuffer.length);
    DatagramPacket packetOut, packetOut2, packetOut3, packetOut4;

    private Set<Allergene> newallergeneSet = new HashSet<>();

    public Kuchenautomat_Server_UDP(DatagramSocket socket, Verwaltung model){
        this.model = model;
        this.socket = socket;
    }

    public void startServer() {
        System.out.println("Der UDP-Server wurde gestartet..");
        try(DataInputStream dis=new DataInputStream(new ByteArrayInputStream(packetIn.getData()));
            DataOutputStream dos=new DataOutputStream(new ByteArrayOutputStream(14))){
            this.processProtokoll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processProtokoll() throws IOException {
        packetIn = new DatagramPacket(this.inBuffer, this.inBuffer.length);
        this.socket.receive(packetIn);
        String received = new String(packetIn.getData(),0,packetIn.getLength());
        switch (received) {
            case ":c" -> this.kuchenEinfuegen();
            case ":u" -> this.inspizierung();
            case ":d" -> this.loeschung();
            case ":p" -> this.persistenzmodus();
            case ":r" -> this.datenlesen();
        }
    }

    //Quelle: https://stackoverflow.com/questions/66786965/how-can-i-turn-an-int-minutes-into-a-duration-in-java

    protected String getKuchenObejtData(String kuchendaten){
            Hersteller hersteller = null;
            String obstsorte = "";
            String ksorte = "";
            String typ;
            String herstellername;
            String preis;
            String naehrwert;
            String haltbarkeitS;

            String[] array = kuchendaten.split(" ");
            typ = array[0];
            Kuchentyp kuchentyp = Kuchentyp.valueOf(typ);
            herstellername = array[1];
            hersteller = new Hersteller(herstellername);
            preis = array[2];
            double preisd = Double.parseDouble(preis);
            naehrwert = array[3];
            int naehrwertint = Integer.parseInt(naehrwert);
            haltbarkeitS = array[4];
            Duration haltbarkeit = Duration.ofDays(Long.parseLong(haltbarkeitS));
            switch (array.length){
                case 7:
                    Set<String> allergens = new HashSet<>(Arrays.asList(array[5].split(",")));
                    if(allergens.size()>0){
                        for(String allergenElement: allergens){
                            Allergene allergen = Allergene.valueOf(allergenElement);
                            this.newallergeneSet.add(allergen);
                        }
                        ksorte = array[6];
                        boolean result = this.model.insertKuchen(kuchentyp, hersteller, preisd, naehrwertint, haltbarkeit,
                                this.newallergeneSet, obstsorte, ksorte);
                        return "Kuchen wurde eingefügt: " + result;
                    }else{
                        obstsorte = array[6];
                        boolean result = this.model.insertKuchen(kuchentyp, hersteller, preisd, naehrwertint, haltbarkeit,
                                this.newallergeneSet, obstsorte, ksorte);
                        return "Kuchen wurde eingefügt: " + result;
                    }
                case 8:
                    allergens = new HashSet<>(Arrays.asList(array[5].split(",")));
                    for(String allergenElement: allergens){
                        Allergene allergen = Allergene.valueOf(allergenElement);
                        this.newallergeneSet.add(allergen);
                    }
                    obstsorte = array[6];
                    ksorte = array[7];
                    boolean result = this.model.insertKuchen(kuchentyp, hersteller, preisd, naehrwertint, haltbarkeit,
                            this.newallergeneSet, obstsorte, ksorte);
                    return "Kuchen wurde eingefügt: " + result;
            }
            return null;
    }


    private void kuchenEinfuegen() throws IOException {
        System.out.println("Einfügeprozess gestartet...");
        // - Neuen Hersteller einfügen
        packetIn = new DatagramPacket(this.inBuffer, this.inBuffer.length);
        this.socket.receive(packetIn);
        String herstellername = new String(packetIn.getData(),0, packetIn.getLength());
        String result = herstellerEinfuegen(herstellername);
        System.out.println(result);
        //- Neuen Kuchen einfügen
        int lengthKuchendaten = 10;
        packetIn = new DatagramPacket(inBuffer, inBuffer.length);
        this.socket.receive(packetIn);
        String received = new String(packetIn.getData(),0,packetIn.getLength());
        while (received.length()>lengthKuchendaten){
            received = new String(packetIn.getData(),0,packetIn.getLength());
            String result2 = getKuchenObejtData(received);
            System.out.println(result2);
            this.socket.receive(packetIn);
            received = new String(packetIn.getData(),0,packetIn.getLength());
            switch (received) {
                case ":u" -> this.inspizierung();
                case ":d" -> this.loeschung();
                case ":p" -> this.persistenzmodus();
                case ":r" -> this.datenlesen();
            }
        }
    }

    private void inspizierung() throws IOException {
        System.out.println("Inspektionsprozess gestartet...");
        this.socket.receive(packetIn);
        String received = new String(packetIn.getData(),0,packetIn.getLength());
        while (!(received.equals(":c") || received.equals(":d") ||
                received.equals(":r")|| received.equals(":p"))){
            int kuchenid = Integer.parseInt(received);
            String isChecked = kuchenInspizieren(kuchenid);
            System.out.println(isChecked);
            this.socket.receive(packetIn);
            received = new String(packetIn.getData(),0,packetIn.getLength());
            System.out.println("Empfangener Befehl: " + received);
            switch (received) {
                case ":c" -> this.kuchenEinfuegen();
                case ":d" -> this.loeschung();
                case ":p" -> this.persistenzmodus();
                case ":r" -> this.datenlesen();
            }
        }
    }


    private void loeschung() throws IOException {
        System.out.println("Löschprozess gestartet...");
        this.socket.receive(packetIn);
        String received = new String(packetIn.getData(),0,packetIn.getLength());
        while (!(received.equals(":c") || received.equals(":u") ||
                received.equals(":r")|| received.equals(":p"))){
            String herstellerisDeleted = herstellerloeschen(received);
            System.out.println(herstellerisDeleted);
            this.socket.receive(packetIn);
            received = new String(packetIn.getData(),0,packetIn.getLength());
            int kuchenid = Integer.parseInt(received);
            String isdeleted = kuchenloeschen(kuchenid);
            System.out.println(isdeleted);
            this.socket.receive(packetIn);
            received = new String(packetIn.getData(),0,packetIn.getLength());
            switch (received) {
                case ":c" -> this.kuchenEinfuegen();
                case ":u" -> this.inspizierung();
                case ":p" -> this.persistenzmodus();
                case ":r" -> this.datenlesen();
            }
        }
    }

    protected void datenlesen() throws IOException {
        System.out.println("Daten lesen");
        this.socket.receive(packetIn);
        String received = new String(packetIn.getData(),0,packetIn.getLength());
        while (!(received.equals(":c") || received.equals(":u") ||
                received.equals(":d") || received.equals(":p"))){
            switch (received) {
                case "kuchen":
                    kuchendaten();
                    break;
                case "hersteller":
                    herstellerdaten();
                    break;
                case "allergene i":
                    allergenendaten();
                    break;
                case "allergene e":
                    notProccedallergene();
                    break;
            }
        }
    }

    protected void persistenzmodus() throws IOException {
        System.out.println("Persistieren..");
        //TODO in diesem Modus gibt es 4 Möglichkeiten
        packetIn = new DatagramPacket(inBuffer, inBuffer.length);
        this.socket.receive(packetIn);
        String received = new String(packetIn.getData(),0,packetIn.getLength());
        switch (received){
            case "saveJOS":
                automatenspeichernMitJOS();
                break;
            case "loadJOS":
                automatenladenMitJOS();
                break;
            case "saveJBP":
                automatenspeichernMitJBP();
                break;
            case "loadJBP":
                automatenzustandmitJBPLaden();
        }
    }

    protected void automatenspeichernMitJOS() throws IOException {
        System.out.println("Der aktuelle Zustand des Automaten wird gespeichert...");
        //TODO Wie soll die Angabe über den Speicherort erfolgen - Frage ins Forum!
        OutputStream outputStream = new FileOutputStream("automaten.txt");
        ObjektSpeicherungJOS.persistiereAutomaten(this.model, outputStream);
        packetIn = new DatagramPacket(inBuffer, inBuffer.length);
        this.socket.receive(packetIn);
        String received = new String(packetIn.getData(),0,packetIn.getLength());
        switch (received){
            case "loadJOS":
                automatenladenMitJOS();
                break;
            case "saveJBP":
                automatenspeichernMitJBP();
                break;
            case "loadJBP":
                automatenzustandmitJBPLaden();
                break;
            case ":c":
                kuchenEinfuegen();
                break;
            case ":u":
                inspizierung();
                break;
            case ":d":
                loeschung();
                break;
            case ":r":
                datenlesen();
                break;
        }
    }

    protected void automatenladenMitJOS() throws IOException {
        System.out.println("Der aktuelle Zustand des Automaten wird geladen...");
        this.model = ObjektLadenJOS.reloadAutomt("automaten.txt");
        if(this.model!=null){
            for(Kuchen kuchen: this.model.readKuchen()){
                String id = String.valueOf(kuchen.getFachnummer());
                String naehrwert = String.valueOf(kuchen.getNaehrwert());
                String preis = String.valueOf(kuchen.getPreis());
                String edatum = String.valueOf(kuchen.getEinfuegedatum());
                String idatum = String.valueOf(kuchen.getInspektionsdatum());
                String gespeicherteKuchendaten = "KuchenID: " + id + "\nSorte: " + kuchen.getKremsorte() +  "\nNährwert: " + naehrwert + "\nPreis: " + preis +
                        "\nEinfügedatum: " + edatum + "\nInpekstionsdatum: " + idatum;
                byte[] gespeicherteKuchendatenInBytes = gespeicherteKuchendaten.getBytes();
                packetOut = new DatagramPacket(gespeicherteKuchendatenInBytes, gespeicherteKuchendatenInBytes.length, packetIn.getAddress(), packetIn.getPort());
                this.socket.send(packetOut);
            }

            for(Hersteller hersteller: this.model.readHersteller()){
                String nameHersteller = hersteller.getName()+"\n";
                byte[] nameInByte = nameHersteller.getBytes();
                packetOut2 = new DatagramPacket(nameInByte, nameInByte.length, packetIn.getAddress(), packetIn.getPort());
                this.socket.send(packetOut2);
            }


            for(Allergene allergen: this.model.readAllergener()){
                byte[] nameInByte2 = allergen.name().getBytes();
                packetOut3 = new DatagramPacket(nameInByte2, nameInByte2.length, packetIn.getAddress(), packetIn.getPort());
                this.socket.send(packetOut3);
            }

            for(Allergene allergen: this.model.readAllergeneNotinCakes()){
                byte[] nameInByte3 = allergen.name().getBytes();
                packetOut4 = new DatagramPacket(nameInByte3, nameInByte3.length, packetIn.getAddress(), packetIn.getPort());
                this.socket.send(packetOut4);
            }
        }
        String endOFData = "\nEnde";
        byte[] endArray = endOFData.getBytes();
        DatagramPacket packetOut5 = new DatagramPacket(endArray, endArray.length, packetIn.getAddress(), packetIn.getPort());
        this.socket.send(packetOut5);

            packetIn = new DatagramPacket(inBuffer, inBuffer.length);
            this.socket.receive(packetIn);
            String received = new String(packetIn.getData(),0,packetIn.getLength());
            switch (received) {
                case "loadJOS":
                    automatenladenMitJOS();
                    break;
                case "saveJBP":
                    automatenspeichernMitJBP();
                    break;
                case "loadJBP":
                    automatenzustandmitJBPLaden();
                    break;
                case ":c":
                    kuchenEinfuegen();
                    break;
                case ":u":
                    inspizierung();
                    break;
                case ":d":
                    loeschung();
                    break;
                case ":r":
                    datenlesen();
                    break;
            }

    }


    protected void kuchendaten() throws IOException {
        System.out.println("Kuchendaten lesen");
            for(Kuchen kuchen: this.model.readKuchen()) {
                String id = String.valueOf(kuchen.getFachnummer());
                String naehrwert = String.valueOf(kuchen.getNaehrwert());
                String sorte = String.valueOf(kuchen.getKremsorte());
                String haltbarkeit = String.valueOf(kuchen.getHaltbarkeit());
                String preis = String.valueOf(kuchen.getPreis());
                String edatum = String.valueOf(kuchen.getEinfuegedatum());
                String idatum = String.valueOf(kuchen.getInspektionsdatum());
                String kuchendaten = "KuchenID: " + id + "\nNährwert: " + naehrwert + "\nPreis: " + preis +
                        "\nEinfügedatum: " + edatum + "\nInpekstionsdatum: " + idatum + "\nHaltbarkeit: " + haltbarkeit
                        +"\nSorte: " + sorte;
                byte[] objectData = convertObjectToByteArray(kuchendaten);
                packetOut = new DatagramPacket(objectData, objectData.length, packetIn.getAddress(),
                        packetIn.getPort());
                this.socket.send(packetOut);
            }
            String endOFData = "\nEnde";
            byte[] endArray = endOFData.getBytes();
            packetOut2 = new DatagramPacket(endArray, endArray.length, packetIn.getAddress(), packetIn.getPort());
            this.socket.send(packetOut2);
            DatagramPacket packetIn = new DatagramPacket(inBuffer, inBuffer.length);
            this.socket.receive(packetIn);
            String received = new String(packetIn.getData(),0,packetIn.getLength());
            System.out.println("Neuer Befehl: " + received);
            switch (received){
                case "hersteller":
                    herstellerdaten();
                    break;
                case "allergene i":
                    allergenendaten();
                    break;
                case "allergene e":
                    notProccedallergene();
                    break;
                case ":c":
                    kuchenEinfuegen();
                    break;
                case ":u":
                    System.out.println(":u");
                    inspizierung();
                    break;
                case ":d":
                    loeschung();
                    break;
                case ":p":
                    persistenzmodus();
                    break;
                case ":r":
                    datenlesen();
                    break;
            }
    }


    protected void herstellerdaten() throws IOException {
        for(Hersteller hersteller: this.model.readHersteller()){
            String herstellerName = hersteller.getName();
            System.out.println(herstellerName);
            byte[] nameherstellerInBytes = herstellerName.getBytes();
            packetOut2 = new DatagramPacket(nameherstellerInBytes, nameherstellerInBytes.length, packetIn.getAddress(), packetIn.getPort());
            this.socket.send(packetOut2);
            String endOFData = "\nEnde";
            byte[] endArray = endOFData.getBytes();
            packetOut2 = new DatagramPacket(endArray, endArray.length, packetIn.getAddress(), packetIn.getPort());
            this.socket.send(packetOut2);

            DatagramPacket packetIn = new DatagramPacket(inBuffer, inBuffer.length);
            this.socket.receive(packetIn);
            String received = new String(packetIn.getData(),0,packetIn.getLength());
            switch (received){
                case "kuchen":
                    kuchendaten();
                    break;
                case "allergene i":
                    allergenendaten();
                    break;
                case "allergene e":
                    notProccedallergene();
                    break;
                case ":c":
                    kuchenEinfuegen();
                    break;
                case ":u":
                    inspizierung();
                    break;
                case ":d":
                    loeschung();
                    break;
                case ":p":
                    persistenzmodus();
                    break;
                case ":r":
                    datenlesen();
                    break;
            }
        }
    }


    protected void allergenendaten() throws IOException {
        System.out.println("Proceed allergene read");
        for(Allergene allergene: this.model.readAllergener()){
            String nameAllergen = String.valueOf(allergene);
            byte[] nameAllergenInBytes = nameAllergen.getBytes();
            packetOut3 = new DatagramPacket(nameAllergenInBytes, nameAllergenInBytes.length, packetIn.getAddress(), packetIn.getPort());
            String endOFData = "\nEnde";
            byte[] endArray = endOFData.getBytes();
            packetOut2= new DatagramPacket(endArray, endArray.length, packetIn.getAddress(), packetIn.getPort());
            this.socket.send(packetOut3);
            this.socket.send(packetOut3);
            System.out.println("Ich stehe hier");
            this.socket.receive(packetIn);
            String received = new String(packetIn.getData(),0,packetIn.getLength());
            System.out.println("Empfangen: " + received);
            switch (received){
                case "kuchen":
                    kuchendaten();
                    break;
                case "hersteller":
                    herstellerdaten();
                    break;
                case "allergene e":
                    notProccedallergene();
                    break;
                case ":c":
                    kuchenEinfuegen();
                    break;
                case ":u":
                    inspizierung();
                    break;
                case ":d":
                    loeschung();
                    break;
                case ":p":
                    persistenzmodus();
                    break;
                case ":r":
                    datenlesen();
                    break;
            }
        }
    }

    protected void notProccedallergene() throws IOException {
        System.out.println("to read not proceed allergene");
        //TODO Wie kann man sich alle Allergene vom Dekoratorkuchen ausgeben lassen?
        for(Allergene allergene: this.model.readAllergeneNotinCakes()){
            String nameAllergen = String.valueOf(allergene);
            byte[] nameAllergenInBytes = nameAllergen.getBytes();
            packetOut4 = new DatagramPacket(nameAllergenInBytes, nameAllergenInBytes.length, packetIn.getAddress(), packetIn.getPort());
            this.socket.send(packetOut4);
            String endOFData = "\nEnde";
            byte[] endArray = endOFData.getBytes();
            DatagramPacket packetOut5 = new DatagramPacket(endArray, endArray.length, packetIn.getAddress(), packetIn.getPort());
            this.socket.send(packetOut5);
            packetIn = new DatagramPacket(inBuffer, inBuffer.length);
            this.socket.receive(packetIn);
            String received = new String(packetIn.getData(),0,packetIn.getLength());
            switch (received){
                case "kuchen":
                    kuchendaten();
                    break;
                case "hersteller":
                    herstellerdaten();
                    break;
                case "allergene i":
                    allergenendaten();
                    break;
                case ":c":
                    kuchenEinfuegen();
                    break;
                case ":u":
                    inspizierung();
                    break;
                case ":d":
                    loeschung();
                    break;
                case ":p":
                    persistenzmodus();
                    break;
                case ":r":
                    datenlesen();
                    break;
            }
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

    protected String kuchenInspizieren(int kuchenId){
        boolean isput = this.model.editKuchen(kuchenId);
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

    protected String herstellerloeschen(String name) {
        boolean isput = this.model.deleteHersteller(name);
        if (isput) {
            return "Habe den Hersteller geloescht.";
        } else {
            return "Habe den Hersteller nicht geloescht.";
        }
    }

    protected void automatenspeichernMitJBP() throws FileNotFoundException {
        OutputStream os = new FileOutputStream("automaten.xml");
        ObjektSpeicherungJBP.persistiereAutomaten(this.model, os);
    }

    public void automatenzustandmitJBPLaden() {
       ObjektLadenJBP.automatenzustandLaden("automaten.xml");
       /*
        if(model!=null){
            for(DekoKuchen kuchen: model.readKuchen()){
                System.out.println("KuchenID: " + kuchen.getFachnummer() + "\nEinfuegedatum: " + kuchen.getEinfuegedatum()
                        + "\nInspektionsdatum: " + kuchen.getInspektionsdatum() + "\nPreis: " + kuchen.getPreis());
            }
        }

        */
    }

    private static byte[] convertObjectToByteArray(Object object) throws IOException {
        // Konvertiere das Objekt in ein Byte-Array, um es über das Netzwerk zu übertragen
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutput = new ObjectOutputStream(outputStream);
        objectOutput.writeObject(object);
        return outputStream.toByteArray();
    }
}