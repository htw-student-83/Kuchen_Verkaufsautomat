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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.*;


public class CLI {

    //TODO
    // 1) Wie kann man von einem Modus zum nächsten kommen und dabei immer ein Event auslösen?
    // 2) Ist das Anzeigen, das Speichern und das Laden von Daten jeweils als ein Event anzusehen?


    Scanner scanner = new Scanner(System.in);
    Verwaltung model;
    Set<Allergene> newallergeneSet = new HashSet<>();
    //private Event_Hersteller_Einfuegen event = null;
    private Handler insertHerstellerHandler, insertKuchenHandler, editKuchenHandler, anzeigeKuchenHandler,
                     anzeigeHerstellerHandler, anzeigeAllergeneHandler, speichernAutomatenHandler,
                     ladenAutomatenHandler, deleteHerstellerHandler, deleteKuchenHandler;

    private static final String EINFUEGEN = ":c";
    private static final String LOESCHEN = ":d";
    private static final String AENDERN = ":u";
    private static final String ANZEIGEN = ":r";
    private static final String PERSISTIEREN = ":p";


    public CLI(Verwaltung model) {
        this.model = model;
    }


    public void setInsertHerstellerHandler(Handler handler) { this.insertHerstellerHandler = handler; }

    public void setInsertKuchenHandler(Handler handler) {
        this.insertKuchenHandler = handler;
    }

    public void setEditKuchenHandler(Handler handler) {
        this.editKuchenHandler = handler;
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

    public void setDeleteHerstellerHandler(Handler handler) {
        this.deleteHerstellerHandler = handler;
    }

    public void setDeleteKuchenHandler(Handler handler) {
        this.deleteKuchenHandler = handler;
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
                String nameHersteller = scanner.nextLine();
                if (insertHerstellerHandler != null) insertHerstellerHandler.distribute(einfuegenHersteller(nameHersteller));
                String kuchendaten = scanner.nextLine();
                while (!(kuchendaten.equals(":u") || kuchendaten.equals(":d") ||
                        kuchendaten.equals(":p") || kuchendaten.equals(":r"))) {
                    if (insertKuchenHandler != null) insertKuchenHandler.distribute(einfuegenKuchen(kuchendaten));
                    kuchendaten = scanner.nextLine();
                    if(kuchendaten.equals(":u")){
                        distribute(kuchendaten);
                    }
                    switch (kuchendaten) {
                        case ":u":
                            String kuchenID = scanner.nextLine();
                            while (!(kuchenID.equals(":p") || kuchenID.equals(":r") ||
                                    kuchenID.equals(":c") || kuchenID.equals(":d"))) {
                                if (editKuchenHandler != null) editKuchenHandler.distribute(aenderungsmodus(kuchenID));
                                kuchenID = scanner.nextLine();
                                switch (kuchenID){
                                    case ":r":
                                        anzeigemodus();
                                }
                            }
                            break;
                        case ":d":
                            String herstellername = scanner.nextLine();
                            while (!(herstellername.equals(":c") || herstellername.equals(":u") ||
                                    herstellername.equals(":r") || herstellername.equals(":p"))) {
                                if (deleteHerstellerHandler != null) deleteHerstellerHandler.distribute(loeschmodus(herstellername));
                                kuchendaten = scanner.nextLine();
                                if (deleteKuchenHandler != null) deleteKuchenHandler.distribute(kuchenloeschen(kuchendaten));
                                herstellername = scanner.nextLine();
                            }
                            break;
                        case ":r":
                            anzeigemodus();
                        case ":p":
                            persistieren();
                    }
                }
                break;
            case AENDERN:
                String kuchenID = scanner.nextLine();
                while (!(kuchenID.equals(":p") || kuchenID.equals(":r") ||
                        kuchenID.equals(":c") || kuchenID.equals(":d"))) {
                    if (editKuchenHandler != null) editKuchenHandler.distribute(aenderungsmodus(kuchenID));
                    kuchenID = scanner.nextLine();
                    switch (kuchenID){
                        case ":c":
                            nameHersteller = scanner.nextLine();
                            if (insertHerstellerHandler != null) insertHerstellerHandler.distribute(einfuegenHersteller(nameHersteller));
                            kuchendaten = scanner.nextLine();
                            while (!(kuchendaten.equals(":u") || kuchendaten.equals(":d") ||
                                    kuchendaten.equals(":p") || kuchendaten.equals(":r"))) {
                                if (insertKuchenHandler != null) insertKuchenHandler.distribute(einfuegenKuchen(kuchendaten));
                                kuchendaten = scanner.nextLine();
                            }
                            break;
                        case ":d":
                            String herstellername = scanner.next();
                            while (!(herstellername.equals(":c") || herstellername.equals(":u") ||
                                    herstellername.equals(":r") || herstellername.equals(":p"))) {
                                if (deleteHerstellerHandler != null) deleteHerstellerHandler.distribute(loeschmodus(herstellername));
                                kuchendaten = scanner.nextLine();
                                if (deleteKuchenHandler != null) deleteKuchenHandler.distribute(kuchenloeschen(kuchendaten));
                                herstellername = scanner.nextLine();
                            }
                            break;
                        case ":r":
                            //TODO Event erstellen?
                            anzeigemodus();
                        case ":p":
                            //TODO Event erstellen?
                            persistieren();
                    }
                }
                break;
            case ANZEIGEN:
                //TODO Event erstellen?
                String kuchentyp = scanner.nextLine();
                anzeigemodus();
                break;
            case LOESCHEN:
                String herstellername = scanner.next();
                while (!(herstellername.equals(":c") || herstellername.equals(":u") ||
                        herstellername.equals(":r") || herstellername.equals(":p"))) {
                    if (deleteHerstellerHandler != null) deleteHerstellerHandler.distribute(loeschmodus(herstellername));
                    kuchendaten = scanner.nextLine();
                    if (deleteKuchenHandler != null) deleteKuchenHandler.distribute(kuchenloeschen(kuchendaten));
                    herstellername = scanner.nextLine();
                }
                startCLI();
                break;
            case PERSISTIEREN:
                persistieren();
                break;
        }
    }


    private void distribute(String befehl) {
        switch (befehl) {
            case AENDERN:
                String kuchenID = "1";
                aenderungsmodus(kuchenID);
                break;
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
                    event = new KuchenEvent(this, kuchentyp, hersteller, preisd, naehrwertint,
                            haltbarkeit, this.newallergeneSet, obstsorte, ksorte);
                    return event;
                }else{
                    obstsorte = array[6];
                    //TODO es wird ein leeres Allergenenset übergeben
                    // - wenn eine Varibale keine Daten beinhaltet, so ist diese mit "" zu initialisieren!
                    return new EventKuchenEinfuegen(this, kuchentyp, hersteller, preisd, naehrwertint,
                            haltbarkeit, this.newallergeneSet, obstsorte, ksorte);
                    return event;
                }
            case 8:
                allergens = new HashSet<>(Arrays.asList(array[5].split(",")));
                for(String allergenElement: allergens){
                    Allergene allergen = Allergene.valueOf(allergenElement);
                    this.newallergeneSet.add(allergen);
                }
                obstsorte = array[6];
                ksorte = array[7];
                event = new KuchenEvent(this, kuchentyp, hersteller, preisd, naehrwertint,
                            haltbarkeit, this.newallergeneSet, obstsorte, ksorte);
                    return event;
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
                default:
            }

            eingabeUser = userInput.nextLine();
            switch (eingabeUser) {
                case ":c":
                    String herstellername = scanner.nextLine();
                    if (insertHerstellerHandler != null) insertHerstellerHandler.distribute(einfuegenHersteller(herstellername));
                    String kuchendaten = scanner.nextLine();
                    while (!(kuchendaten.equals(":u") || kuchendaten.equals(":d") ||
                            kuchendaten.equals(":p") || kuchendaten.equals(":r"))) {
                        if (insertKuchenHandler != null) insertKuchenHandler.distribute(einfuegenKuchen(kuchendaten));
                        kuchendaten = scanner.nextLine();
                    }
                    break;
                case ":d":
                    //TODO Hier wird erneut ein Event erstellt
                    herstellername = scanner.nextLine();
                    while (!(herstellername.equals(":c") || herstellername.equals(":u") ||
                            herstellername.equals(":r") || herstellername.equals(":p"))) {
                        if (deleteHerstellerHandler != null) deleteHerstellerHandler.distribute(loeschmodus(herstellername));
                        kuchendaten = scanner.nextLine();
                        if (deleteKuchenHandler != null) deleteKuchenHandler.distribute(kuchenloeschen(kuchendaten));
                        herstellername = scanner.nextLine();
                    }
                    break;
                case ":u":
                    //TODO Hier wird erneut ein Event erstellt
                    //TODO bevor die Methode aufgerufen wird, werden die Daten vom User eingeholt!
                    String kID = scanner.nextLine();
                    while (!(kID.equals(":p") || kID.equals(":r") ||
                            kID.equals(":c") || kID.equals(":d"))) {
                        if (editKuchenHandler != null) editKuchenHandler.distribute(aenderungsmodus(kID));
                        kID = scanner.nextLine();
                    }
                    break;
                case ":p":
                    persistieren();
                    break;
                default:
            }
        }
    }


    public void readKuchen() {
        for (Kuchen kuchen : this.model.readKuchen()) {
            System.out.println("Fachnummer: " + kuchen.getFachnummer() + "\n" +
                    "Einfügedatum: " + kuchen.getEinfuegedatum() + "\n" +
                    "Inspektionsdatum: " + kuchen.getInspektionsdatum() + "\n" +
                    "Preis: " + kuchen.getPreis() + "\n" +
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
        for (Hersteller hersteller : this.model.readHersteller()) {
            System.out.println(hersteller.getName());
        }
    }

    public void readKuchen(String typ){
        for(Kuchen kuchen: this.model.readKuchen(this.model.readKuchen(), typ)){
            System.out.println( "Fachnummer: " + kuchen.getFachnummer() + "\n" +
                    "Inspektionsdatum: " + kuchen.getInspektionsdatum() + "\n" +
                    "verbleibender Haltbarkeit: " + kuchen.getHaltbarkeit());
        }
   }

    public KuchenEvent loeschmodus(String herstellername) {
        event = new KuchenEvent(this, herstellername);
        return event;
    }

    public EventKuchenLoeschen kuchenloeschen(String loeschdaten) {
        int kuchenID = Integer.parseInt(loeschdaten);
        event = new KuchenEvent(this, kuchenID);
        return event;
    }


    public EventKuchenInspizierung aenderungsmodus(String kuchenID) {
        int fachnummer = Integer.parseInt(kuchenID);
        return new EventKuchenInspizierung(this, fachnummer);
         /*
             System.out.println(result);
            userInput = scanner.nextLine();
            switch (userInput) {
                case ":d" -> {
                    String herstellername = scanner.nextLine();
                    loeschmodus(herstellername);
                    String kuchendaten = scanner.nextLine();
                    loeschmodus(kuchendaten);
                    break;
                }
                case ":r" -> anzeigemodus();
                //case ":c" -> einfuegemodus();
                case ":p" -> persistieren();
            }
          */
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
                    ObjektSpeicherungJOS.persistiereAutomaten(this.model, "automaten.txt");
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
                //case ":c" -> einfuegemodus();
                case ":d" -> {
                    //TODO Hier wird erneut ein Event erstellt
                    String herstellername = scanner.nextLine();
                    loeschmodus(herstellername);
                    String kuchendaten = scanner.nextLine();
                    loeschmodus(kuchendaten);
                    break;
                }
                case ":r" -> anzeigemodus();
                case ":u" -> {
                    //TODO Hier wird erneut ein Event erstellt
                    String kuchenID = scanner.nextLine();
                    aenderungsmodus(kuchenID);
                }
            }
        }
    }

    public void ladeAutomaten() {
        this.model = ObjektLadenJOS.reloadAutomt("automaten.txt");
        System.out.println("Hersteller:");
        for (Hersteller hersteller : this.model.readHersteller()) {
            System.out.println(hersteller.getName());
        }

        System.out.println("Kuchen:");
        model = new Verwaltung(2);
        for (Kuchen kuchen : this.model.readKuchen()) {
            System.out.println("Fachnummer: " + kuchen.getFachnummer() + "\n" +
                    "Einfuegedatum: " + kuchen.getEinfuegedatum() + "\n" +
                    "Inspektionsdatum: " + kuchen.getInspektionsdatum() + "\n" +
                    "Preis: " + kuchen.getPreis() + "\n" +
                    "Naehrwert: " + kuchen.getNaehrwert() + "\n" +
                    "Allergene: " + kuchen.getAllergene());
        }
    }

    public void automatenzustandmitJBPSpeichern() throws FileNotFoundException {
        OutputStream os = new FileOutputStream("automaten.xml");
        System.out.println("Automatenzustand vor dem Speichervorgang:");
        System.out.println("Herstellerset: " + this.model.readHersteller().size());
        System.out.println("Kuchenliste: " + this.model.readKuchen().size());
        ObjektSpeicherungJBP.persistiereAutomaten(this.model, os);
    }
    public void automatenzustandmitJBPLaden() {
        //TODO Wieso werden keine Daten geladen?
        ObjektLadenJBP.automatenzustandLaden("automaten.xml");
    }
}
