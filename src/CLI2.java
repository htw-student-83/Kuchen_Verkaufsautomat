import eventsystem.controller.events.*;
import eventsystem.handler.Handler;
import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import jbp.ObjektLadenJBP;
import jbp.ObjektSpeicherungJBP;
import jos.ObjektLadenJOS;
import jos.ObjektSpeicherungJOS;
import vertrag.Allergene;

import javax.sound.midi.Soundbank;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class CLI2 {

    //TODO
    // 2) Ist das Anzeigen, das Speichern und das Laden von Daten jeweils als ein Event anzusehen?

    Scanner scanner = new Scanner(System.in);
    Verwaltung model;
    Set<Allergene> newallergeneSet = new HashSet<>();
    private Handler insertHerstellerHandler, insertKuchenHandler, editKuchenHandler, deleteHerstellerHandler,
            deleteKuchenHandler, anzeigeKuchenHandler, anzeigeHerstellerHandler, anzeigeAllergeneHandler,
            speichernAutomatenHandler, ladenAutomatenHandler;

    private static final String EINFUEGEN = ":c";
    private static final String LOESCHEN = ":d";
    private static final String AENDERN = ":u";
    private static final String ANZEIGEN = ":r";
    private static final String PERSISTIEREN = ":p";


    public CLI2(Verwaltung model) {
        this.model = model;
    }


    public void setInsertHerstellerHandler(Handler handler) { this.insertHerstellerHandler = handler; }
    public void setInsertKuchenHandler(Handler handler) {
        this.insertKuchenHandler = handler;
    }
    public void setEditKuchenHandler(Handler handler) {
        this.editKuchenHandler = handler;
    }
    public void setDeleteHerstellerHandler(Handler handler) {
        this.deleteHerstellerHandler = handler;
    }
    public void setDeleteKuchenHandler(Handler handler) {
        this.deleteKuchenHandler = handler;
    }
    public void setAnzeigeKuchenHandler(Handler handler) {
        this.anzeigeKuchenHandler = handler;
    }
    public void setAnzeigeHerstellerHandler(Handler handler) {
        this.anzeigeHerstellerHandler = handler;
    }
    public void setAnzeigeAllergeneHandler(Handler handler) {
        this.anzeigeAllergeneHandler = handler;
    }
    public void setSpeichernAutomatenHandler(Handler handler) {
        this.speichernAutomatenHandler = handler;
    }
    public void setLadenAutomatenHandler(Handler handler) {
        this.ladenAutomatenHandler = handler;
    }


    public void startCLI() throws FileNotFoundException {
        String options = scanner.nextLine();
        switch (options) {
            case EINFUEGEN:
                checkHerstellerdatenForInserting();
                break;
            case AENDERN:
                kuchendatenForInspizierung();
                break;
            case ANZEIGEN:
                anzeigemodus();
                break;
            case LOESCHEN:
                kuchendatenForDeleting();
                break;
            case PERSISTIEREN:
                persistieren();
                break;
            default:
                startCLI();
        }
    }

    private void checkHerstellerdatenForInserting() throws FileNotFoundException {
        String nameHersteller = scanner.nextLine();
        if (insertHerstellerHandler != null) insertHerstellerHandler.distribute(einfuegenHersteller(nameHersteller));
        checkKuchendatenForInserting();
    }


    private void checkKuchendatenForInserting() throws FileNotFoundException {
        String kuchendaten = scanner.nextLine();
        while(!(kuchendaten.equals(":u") || kuchendaten.equals(":d") ||
                kuchendaten.equals(":r") ||  kuchendaten.equals(":p"))){
            if(insertKuchenHandler!=null)insertKuchenHandler.distribute(einfuegenKuchen(kuchendaten));
            kuchendaten = scanner.nextLine();
            switch (kuchendaten) {
                case ":u":
                    kuchendatenForInspizierung();
                    break;
                case ":d":
                    kuchendatenForDeleting();
                    break;
                case ":r":
                    anzeigemodus();
                    break;
                case ":p":
                    persistieren();
                    break;
            }
        }
    }

    //TODO einen besseren Namen für die Methode wählen
    private void kuchendatenForInspizierung() throws FileNotFoundException {
        String kuchenId = scanner.nextLine();
        while (!(kuchenId.equals(":c") || kuchenId.equals(":d") ||
                kuchenId.equals(":r") || kuchenId.equals(":p"))) {
            if (editKuchenHandler != null) editKuchenHandler.distribute(aenderungsmodus(kuchenId));
            kuchenId = scanner.nextLine();
            switch (kuchenId) {
                case ":c":
                    checkHerstellerdatenForInserting();
                    break;
                case ":d":
                    kuchendatenForDeleting();
                    break;
                case ":r":
                    anzeigemodus();
                    break;
                case ":p":
                    persistieren();
                    break;
            }
        }
    }

    private void kuchendatenForDeleting() throws FileNotFoundException {
        String herstellername = scanner.nextLine();
        while (!(herstellername.equals(":c") || herstellername.equals(":u") ||
                herstellername.equals(":r") || herstellername.equals(":p"))) {
            if (deleteHerstellerHandler != null) deleteHerstellerHandler.distribute(loeschmodus(herstellername));
            String kuchenID = scanner.nextLine();
            if (deleteKuchenHandler != null) deleteKuchenHandler.distribute(kuchenloeschen(kuchenID));
            herstellername = scanner.nextLine();
            switch (herstellername) {
                case ":c":
                    checkHerstellerdatenForInserting();
                    break;
                case ":u":
                    kuchendatenForInspizierung();
                    break;
                case ":r":
                    anzeigemodus();
                    break;
                case ":p":
                    persistieren();
                    break;
            }
        }
    }



    public EventHerstellerEinfuegen einfuegenHersteller(String herstellerName) {
        Hersteller hersteller = new Hersteller(herstellerName);
        return new EventHerstellerEinfuegen(this, hersteller);
    }


    public EventKuchenEinfuegen einfuegenKuchen(String kuchendaten) {
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
                    // - es wird ein leeren Allergenenset übergeben
                    // - wenn eine Varibale keine Daten beinhaltet, so ist diese mit "" zu initialisieren
                    return new EventKuchenEinfuegen(this, kuchentyp, hersteller, preisd, naehrwertint,
                            haltbarkeit, this.newallergeneSet, obstsorte, ksorte);
                }else{
                    obstsorte = array[6];
                    //TODO es wird ein leeres Allergenenset übergeben
                    // - wenn eine Varibale keine Daten beinhaltet, so ist diese mit "" zu initialisieren!
                    return new EventKuchenEinfuegen(this, kuchentyp, hersteller, preisd, naehrwertint,
                            haltbarkeit, this.newallergeneSet, obstsorte, ksorte);
                }
            case 8:
                allergens = new HashSet<>(Arrays.asList(array[5].split(",")));
                for(String allergenElement: allergens){
                    Allergene allergen = Allergene.valueOf(allergenElement);
                    this.newallergeneSet.add(allergen);
                }
                obstsorte = array[6];
                ksorte = array[7];
                return new EventKuchenEinfuegen(this, kuchentyp, hersteller, preisd, naehrwertint,
                            haltbarkeit, this.newallergeneSet, obstsorte, ksorte);
                }
        return null;
    }

    public void anzeigemodus() throws FileNotFoundException {
        final String KUCHEN = "kuchen";
        //TODO Wie soll die Eingabe vom User genau aussehen?
        final String KUCHENFILTER_KREMKUCHEN = "kuchen [Kremkuchen]";
        final String KUCHENFILTER_OBSTKUCHEN = "kuchen [Obstkuchen]";
        final String KUCHENFILTER_OBSTTORTE = "kuchen [Obsttorte]";
        final String HERSTELLER = "hersteller";
        final String ALLERGENE_PROCESSED = "allergene i";
        final String ALLERGENE_NICHT_IN_KUCHEN = "allergene e";

        Scanner userInput = new Scanner(System.in);
        String eingabeUser = userInput.nextLine();
        while (!(eingabeUser.equals(":c") || eingabeUser.equals(":d") ||
                eingabeUser.equals(":u") || eingabeUser.equals(":p"))) {
            switch (eingabeUser) {
                case KUCHEN:
                    readKuchen();
                    break;
                case KUCHENFILTER_KREMKUCHEN:
                    //TODO Wie wird die Eingabe vom Kunden erfolgen:
                    readKuchen("Kremkuchen");
                    break;
                case KUCHENFILTER_OBSTKUCHEN:
                    //TODO Wie wird die Eingabe vom Kunden erfolgen:
                    readKuchen("Obstkuchen");
                    break;
                case KUCHENFILTER_OBSTTORTE:
                    //TODO Wie wird die Eingabe vom Kunden erfolgen:
                    readKuchen("Obsttorte");
                    break;
                case HERSTELLER:
                    readHersteller();
                    break;
                case ALLERGENE_PROCESSED:
                    readAllergene();
                    break;
                case ALLERGENE_NICHT_IN_KUCHEN:
                    readNotProcessAllergene();
                    break;
            }
            eingabeUser = userInput.nextLine();
            switch (eingabeUser) {
                case ":c":
                    checkHerstellerdatenForInserting();
                    break;
                case ":d":
                    kuchendatenForDeleting();
                    break;
                case ":u":
                    kuchendatenForInspizierung();
                    break;
                case ":p":
                    persistieren();
                    break;
            }
        }
    }


    public void readKuchen() {
        for (Kuchen kuchen : this.model.readKuchen()) {
            System.out.println("Fachnummer: " + kuchen.getFachnummer() + "\n" +
                    "Einfügedatum: " + kuchen.getEinfuegedatum() + "\n" +
                    "Inspektionsdatum: " + kuchen.getInspektionsdatum() + "\n" +
                    "Preis: " + kuchen.getPreis() + "\n" +
                    "Sorte: " + kuchen.getKremsorte() + "\n" +
                    "Nährwert: " + kuchen.getNaehrwert() + "\n" +
                    "Haltbarkeit: " + kuchen.getHaltbarkeit());
            System.out.println("---------------------------");
        }
    }

    public void readAllergene() {
        for (Allergene allergen : this.model.readAllergener()) {
            System.out.println(allergen.name());
        }
    }

    public void readNotProcessAllergene() {
        for (Allergene allergen : this.model.readAllergeneNotinCakes()) {
            System.out.println(allergen.name());
        }
    }

    public void readHersteller() {
        int cnt  = 0;
        for (Hersteller hersteller : this.model.readHersteller()) {
            //TODO prüfen, ob der Hersteller in der Kuchenliste vorhanden ist
           /* for(int i = 0; i<this.model.getKuchenlisteSize(); i++){
                if(hersteller.getName().contains(this.model.)){
                   cnt++;
                }
            }

            */
            System.out.println(hersteller.getName() + cnt + " Kuchen");
        }
    }

    public void readKuchen(String typ){
        for(Kuchen kuchen: this.model.readKuchen(this.model.readKuchen(), typ)){
            System.out.println( "Fachnummer: " + kuchen.getFachnummer() + "\n" +
                    "Inspektionsdatum: " + kuchen.getInspektionsdatum() + "\n" +
                    "verbleibender Haltbarkeit: " + kuchen.getHaltbarkeit());
        }
   }

    public EventHerstellerLoeschen loeschmodus(String herstellername) {
        return new EventHerstellerLoeschen(this, herstellername);
    }

    public EventKuchenLoeschen kuchenloeschen(String loeschdaten) {
        int kuchenID = Integer.parseInt(loeschdaten);
        return new EventKuchenLoeschen(this, kuchenID);
    }


    public EventKuchenInspizierung aenderungsmodus(String kuchenID) {
        int fachnummer = Integer.parseInt(kuchenID);
        return new EventKuchenInspizierung(this, fachnummer);
    }

    private void persistieren() throws FileNotFoundException {
        final String SAVEJOS = "saveJOS";
        final String LOADJOS = "loadJOS";
        final String SAVEJBP = "saveJBP";
        final String LOADJBP = "loadJBP";

        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        while (!(userInput.equals(":c") || userInput.equals(":r") ||
                userInput.equals(":u") || userInput.equals(":d"))) {
            switch (userInput) {
                case SAVEJOS:
                    automatenzustandmitJOSspeichern();
                    break;
                case LOADJOS:
                    ladeAutomaten();
                    break;
                case SAVEJBP:
                    automatenzustandmitJBPSpeichern();
                    break;
                case LOADJBP:
                    automatenzustandmitJBPLaden();
                    break;
            }
            userInput = scanner.nextLine();
            switch (userInput) {
                case ":c" -> checkHerstellerdatenForInserting();
                case ":u" -> kuchendatenForInspizierung();
                case ":d" -> kuchendatenForDeleting();
                case ":r" -> anzeigemodus();
            }
        }
    }

    public void ladeAutomaten() {
        this.model = ObjektLadenJOS.reloadAutomt("automaten.txt");
        System.out.println("Hersteller:");
        for (Hersteller hersteller : this.model.readHersteller()) {
            System.out.println(hersteller.getName());
        }
        System.out.println();
        System.out.println("Kuchen:");
        if(this.model!=null){
            for (Kuchen kuchen : this.model.readKuchen()) {
                System.out.println("Fachnummer: " + kuchen.getFachnummer() + "\n" +
                                    "Einfuegedatum: " + kuchen.getEinfuegedatum() + "\n" +
                                    "Inspektionsdatum: " + kuchen.getInspektionsdatum() + "\n" +
                                    "Preis: " + kuchen.getPreis() + "\n" +
                                    "Naehrwert: " + kuchen.getNaehrwert() + "\n" +
                                    "Haltbarkeit: " + kuchen.getHaltbarkeit());
            }
            System.out.println();
            System.out.println("Vorhandene Allergene:");
            for (Allergene allergen : this.model.readAllergener()) {
                System.out.println(allergen.name());
            }
            System.out.println();
            System.out.println("Nichtvorhandene Allergene:");
            for (Allergene allergen : this.model.readAllergeneNotinCakes()) {
                System.out.println(allergen.name());
            }
        }
    }

    private void automatenzustandmitJOSspeichern() throws FileNotFoundException {
        OutputStream os = new FileOutputStream("automaten.txt");
        ObjektSpeicherungJOS.persistiereAutomaten(this.model, os);
    }

    private void automatenzustandmitJBPSpeichern() throws FileNotFoundException {
        OutputStream os = new FileOutputStream("automaten.xml");
        System.out.println("Automatenzustand vor dem Speichervorgang:");
        ObjektSpeicherungJBP.persistiereAutomaten(this.model, os);
    }
    
    public void automatenzustandmitJBPLaden() {
        //TODO Wieso werden keine Daten geladen?
        ObjektLadenJBP.automatenzustandLaden("automaten.xml");
    }
}
