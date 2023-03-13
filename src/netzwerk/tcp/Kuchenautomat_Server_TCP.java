package netzwerk.tcp;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import jos.ObjektLadenJOS;
import jos.ObjektSpeicherungJOS;
import vertrag.Allergene;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Kuchenautomat_Server_TCP implements Runnable{
    Verwaltung model;
    private Socket socket;

    Socket getData;
    private Set<Allergene> newallergeneSet = new HashSet<>();

    public Kuchenautomat_Server_TCP(Socket socket, Verwaltung model)  {
        this.model = model;
        this.socket = socket;
    }

    @Override public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                DataInputStream in = new DataInputStream(socket.getInputStream())) {
            System.out.println("clientTCP@"+socket.getInetAddress()+":"+socket.getPort()+" connected");
            this.manageKuchenObjekts(in, out);
        } catch (IOException e) {
                e.printStackTrace();
        }
    }


    public void manageKuchenObjekts(DataInputStream in, PrintWriter out) throws IOException {
        while (true){
            String command = in.readUTF();
            try {
                switch (command) {
                    case ":c":
                        einfuegeprozess(in, out);
                    case ":u":
                        aenderungsmodus(in, out);
                        break;
                    case ":d":
                        loeschmodus(in, out);
                        break;
                    case ":r":
                        anzeigemodus(in, out);
                        break;
                    case ":p":
                        persistenzmodus(in, out);
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void einfuegeprozess(DataInputStream in, PrintWriter out) throws IOException {
        System.out.println("Der Einfügeprozess wurde gestartet..");
        int maxLengthOfKuchendaten = 10;
        String herstellername = in.readUTF();
        Hersteller hersteller = new Hersteller(herstellername);
        boolean herstellerSaved = this.model.insertHersteller(hersteller);
        System.out.println("Hersteller eingefügt: " + herstellerSaved);
        String kuchendata = in.readUTF();
        while (kuchendata.length()>maxLengthOfKuchendaten) {
            String kucheninderliste = kuchendatenform(kuchendata);
            System.out.println(kucheninderliste);
            kuchendata = in.readUTF();
            switch (kuchendata) {
                case ":u" -> aenderungsmodus(in, out);
                case ":r" -> anzeigemodus(in, out);
                case ":d" -> loeschmodus(in, out);
                case ":p" -> persistenzmodus(in, out);
            }
        }
    }

    private void anzeigemodus(DataInputStream in, PrintWriter out) throws IOException {
        System.out.println("Anzeigemodus");
        String input = in.readUTF();
        switch (input) {
            case "kuchen":
                kuchendatenlsen(in, out, input);
                break;
            case "hersteller":
                getHerstellerDaten(in, out, input);
                break;
            case "allergene i":
                getKnownAllergene(in, out);
                break;
            case "allergene e":
                getNotProcessedAllergene(in, out);
                break;
        }
    }


    private void getHerstellerDaten(DataInputStream in, PrintWriter out, String input) throws IOException {
        System.out.println("Herstellerdaten auslesen");
        String userInput = input;
        while (!(userInput.equals(":c") || userInput.equals(":r") || userInput.equals(":d") || userInput.equals(":p") ||
                userInput.equals("allergene i") || userInput.equals("allergene e") || userInput.equals("kuchen"))) {
            for (Hersteller hersteller: this.model.readHersteller()) {
                out.println(hersteller);
            }
            System.out.println("Ich warte 002");
            input = in.readUTF();
        }
    }

    private void getKnownAllergene(DataInputStream in, PrintWriter out) throws IOException {
        System.out.println("Bekannte Allergene");
        String userInput = "";
        //PrintWriter outP = new PrintWriter(this.socket.getOutputStream(), true);
        while (!(userInput.equals(":c") || userInput.equals(":r") || userInput.equals(":d") ||
                userInput.equals(":p") || userInput.equals("allergene e") || userInput.equals("kuchen"))) {
            for (Allergene allergen : this.model.readAllergener()) {
                out.println(allergen.name());
            }
            userInput = in.readUTF();
            switch (userInput) {
                case ":c" -> einfuegeprozess(in, out);
                case ":u" -> aenderungsmodus(in, out);
                case ":d" -> loeschmodus(in, out);
                case ":p" -> persistenzmodus(in, out);
                case "allergene e" -> getNotProcessedAllergene(in, out);
                case "kuchen" -> kuchendatenlsen(in, out, userInput);
                default -> {
                }
            }
        }
    }

    private void getNotProcessedAllergene(DataInputStream in, PrintWriter out) throws IOException {
        String userInput = "";
        //PrintWriter outP = new PrintWriter(this.socket.getOutputStream(), true);
        while (!(userInput.equals(":c") || userInput.equals(":r") || userInput.equals(":d") ||
                userInput.equals(":p") || userInput.equals("kuchen") || userInput.equals("allergene i") ||
                userInput.equals("hersteller"))) {
            for (Allergene allergen : this.model.readAllergeneNotinCakes()) {
                out.println(allergen.name());
            }
            userInput = in.readUTF();
            switch (userInput) {
                case ":c" -> einfuegeprozess(in, out);
                case ":u" -> aenderungsmodus(in, out);
                case ":d" -> loeschmodus(in, out);
                case ":p" -> persistenzmodus(in, out);
                case "allergene i" -> getKnownAllergene(in, out);
                case "kuchen" -> kuchendatenlsen(in, out, userInput);
                case "hersteller" -> getHerstellerDaten(in, out, userInput);
            }
        }
    }

    private String kuchendatenform(String kuchendaten){
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
                    return "Kuchen eingefügt: " + result;
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


    private void aenderungsmodus(DataInputStream in, PrintWriter out) throws IOException {
        System.out.println("Der Inspizierungsprozess wurde gestartet..");
        String userInput = in.readUTF();
        while (!(userInput.equals(":c") || userInput.equals(":r") ||
                userInput.equals(":d") || userInput.equals(":p"))){
            int kuchenID = Integer.parseInt(userInput);
            boolean kuchenChecked = this.model.editKuchen(kuchenID);
            System.out.println("Kuchen inspiziert: " + kuchenChecked);
            userInput = in.readUTF();
            switch (userInput) {
                case ":c" -> einfuegeprozess(in, out);
                case ":r" -> anzeigemodus(in, out);
                case ":d" -> loeschmodus(in, out);
                case ":p" -> persistenzmodus(in, out);
            }
        }
    }

    private void loeschmodus(DataInputStream in, PrintWriter out) throws IOException {
        System.out.println("Der Löschprozess wurde gestartet..");
        String herstellername = in.readUTF();
        while (!(herstellername.equals(":c") || herstellername.equals(":r") ||
                herstellername.equals(":u") || herstellername.equals(":p"))){
            boolean herstellerDeleted = this.model.deleteHersteller(herstellername);
            System.out.println("Hersteller gelöscht: " + herstellerDeleted);
            String kuchennummer = in.readUTF();
            int kuchenID = Integer.parseInt(kuchennummer);
            boolean kuchenDeleted = this.model.deleteKuchen(kuchenID);
            System.out.println("Kuchen gelöscht: " + kuchenDeleted);
            herstellername = in.readUTF();
            switch (herstellername) {
                case ":c" -> einfuegeprozess(in, out);
                case ":r" -> anzeigemodus(in, out);
                case ":u" -> aenderungsmodus(in, out);
                case ":p" -> persistenzmodus(in, out);
            }
        }
    }

    private void kuchendatenlsen(DataInputStream in, PrintWriter out, String userinput) throws IOException {
        System.out.println("Kuchendaten auslesen");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss ");
        String input = userinput;
        while (!(input.equals(":c") || input.equals(":d") || input.equals(":u") ||
                input.equals(":r") || input.equals("hersteller") || input.equals("allergene e") ||
                input.equals("allergene i"))){
            for (Kuchen kuchen : this.model.readKuchen()) {
                Date eDate = kuchen.getEinfuegedatum();
                String edate = dateFormat.format(eDate);
                Date iDate = kuchen.getInspektionsdatum();
                if(iDate == null){
                    Date inspektion = null;
                    out.println("\nKuchenID: " + kuchen.getFachnummer() +
                            "\nSorte: " + kuchen.getKremsorte() +
                            "\nEinfuegedatum: " + edate +
                            "\nInspekionsdatum: " + inspektion +
                            "\nPreis:" + kuchen.getPreis() +
                            "\nNaehrwert: " + kuchen.getNaehrwert() +
                            "\nHaltbarkeit: " + kuchen.getHaltbarkeit());
                }else {
                    String idate = dateFormat.format(iDate);
                    out.println("\nKuchenID: " + kuchen.getFachnummer() +
                            "\nSorte: " + kuchen.getKremsorte() +
                            "\nEinfuegedatum: " + edate +
                            "\nInspekionsdatum: " + idate +
                            "\nPreis: " + kuchen.getPreis() +
                            "\nNaehrwert: " + kuchen.getNaehrwert()  +
                            "\nHaltbarkeit: " + kuchen.getHaltbarkeit());
                }
            }
            //TODO ermöglichen, dass bei einem Befehl wie "hersteller" die Namen aller bekannten Hersteller angezeigt werden
            // wenn die Befehle "hersteller" , "allergene i" oder "allergene e" folgen, gehören diese in
            // separate Funktionen
            System.out.println("Ich warte 001");
            input = in.readUTF();
            System.out.println("Empfangen: " + input);
            switch (input) {
                case ":c":
                    einfuegeprozess(in, out);
                    break;
                case ":u":
                    aenderungsmodus(in,out);
                    break;
                case ":d":
                    loeschmodus(in, out);
                    break;
                case ":p":
                    persistenzmodus(in, out);
                    break;
                case "hersteller":
                    getHerstellerDaten(in, out, input);
                    break;
                case "allergene i":
                    getKnownAllergene(in, out);
                    break;
                case "allergene e":
                    getNotProcessedAllergene(in, out);
                    break;
            }
        }
    }

    private void persistenzmodus(DataInputStream in, PrintWriter out) throws IOException {
        System.out.println("Der Persistenzprozess wurde gestartet..");
        String pBefehl = in.readUTF();
        while (!(pBefehl.equals(":c") || pBefehl.equals(":d") || pBefehl.equals(":u") ||
                pBefehl.equals(":r"))) {
            switch (pBefehl) {
                case "saveJOS":
                    saveStatusOfSystem(in, out);
                    break;
                case "loadJOS":
                    loadStatusOfSystem(in, out);
                    break;
            }
        }
    }


    private void saveStatusOfSystem(DataInputStream in, PrintWriter out) throws IOException {
        OutputStream outputStream = new FileOutputStream("automaten.txt");
        ObjektSpeicherungJOS.persistiereAutomaten(this.model, outputStream);
        String pBefehl = in.readUTF();
        switch (pBefehl) {
            case ":c" -> einfuegeprozess(in, out);
            case ":r" -> anzeigemodus(in, out);
            case ":u" -> aenderungsmodus(in, out);
            case ":d" -> loeschmodus(in, out);
            case "loadJOS"-> loadStatusOfSystem(in, out);
        }
    }


    private void loadStatusOfSystem(DataInputStream in, PrintWriter out) throws IOException {
        System.out.println("Gespeicherte Daten ausgeben.");
        String userInput = "";
           this.model = ObjektLadenJOS.reloadAutomt("automaten.txt");
           if (this.model != null) {
               for (Kuchen kuchen : this.model.readKuchen()) {
                   SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                   String fachnummer = String.valueOf(kuchen.getFachnummer());
                   Date eDate = kuchen.getEinfuegedatum();
                   String date = dateFormat.format(eDate);
                   Date iDate = kuchen.getInspektionsdatum();
                   if(iDate == null){
                       Date inspektion = null;
                       out.println("\nKuchenID: " + fachnummer+
                               "\nEinfuegedatum: " + date +
                               "\nInspekionsdatum: " + inspektion +
                               "\nPreis:" + kuchen.getPreis() +
                               "\nNaehrwert: " + kuchen.getNaehrwert() +
                               "\nHaltbarkeit: " + kuchen.getHaltbarkeit());
                   }else {
                       String idate = dateFormat.format(iDate);
                       out.println("\nKuchenID: " + fachnummer +
                               "\nEinfuegedatum: " + date +
                               "\nInspekionsdatum: " + idate +
                               "\nPreis: " + kuchen.getPreis() +
                               "\nNaehrwert: " + kuchen.getNaehrwert()  +
                               "\nHaltbarkeit: " + kuchen.getHaltbarkeit());
                   }
               }
               out.println("");
               out.println("Hersteller");
               for (Hersteller hersteller : this.model.readHersteller()) {
                   String herstellername = hersteller.getName();
                   out.println(herstellername);
               }
               out.println("");
               out.println("Allergene i");
               for (Allergene allergen : this.model.readAllergener()) {
                   out.println(allergen);
               }
               out.println("");
               out.println("Allergene e");
               for (Allergene allergen : this.model.readAllergeneNotinCakes()) {
                   out.println(allergen);
               }

               userInput = in.readUTF();
               switch (userInput) {
                   case ":c" -> einfuegeprozess(in, out);
                   case ":r" -> anzeigemodus(in, out);
                   case ":u" -> aenderungsmodus(in, out);
                   case ":d" -> loeschmodus(in, out);
                   case "saveJOS" -> saveStatusOfSystem(in, out);
               }
       }
    }
}

