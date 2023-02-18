import eventsystem.controller.KuchenEvent;
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

    Scanner scanner = new Scanner(System.in);
    Verwaltung model;
    Set<Allergene> newallergeneSet = new HashSet<>();
    private KuchenEvent event = null;
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

    public void setInsertHerstellerHandler(Handler handler) {
        this.insertHerstellerHandler = handler;
    }

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
        System.out.println("Anfang");
        String options = scanner.nextLine();
        switch (options) {
            case EINFUEGEN:
                if (insertHerstellerHandler != null) insertHerstellerHandler.distribute(einfuegenHersteller());
                String kuchendaten = scanner.nextLine();
                while (!(kuchendaten.equals(":u") || kuchendaten.equals(":d") ||
                        kuchendaten.equals(":p") || kuchendaten.equals(":r"))) {
                    if (insertKuchenHandler != null) insertKuchenHandler.distribute(einfuegenKuchen(kuchendaten));
                    kuchendaten = scanner.nextLine();
                    switch (kuchendaten) {
                        case ":u":
                            while (!(kuchendaten.equals(":p") || kuchendaten.equals(":r") ||
                                    kuchendaten.equals(":c") || kuchendaten.equals(":d"))) {
                                if (editKuchenHandler != null) editKuchenHandler.distribute(aenderungsmodus());
                                kuchendaten = scanner.nextLine();
                                switch (kuchendaten){
                                    case ":r":
                                        anzeigemodus();
                                }
                            }
                            break;
                        case ":d":
                            String herstellername = scanner.nextLine();
                            while (!(herstellername.equals(":c") || herstellername.equals(":u") ||
                                    herstellername.equals(":r") || herstellername.equals(":p"))) {
                                if (deleteHerstellerHandler != null)
                                    deleteHerstellerHandler.distribute(loeschmodus(herstellername));
                                kuchendaten = scanner.nextLine();
                                if (deleteKuchenHandler != null)
                                    deleteKuchenHandler.distribute(kuchenloeschen(kuchendaten));
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
                aenderungsmodus();
                break;
            case ANZEIGEN:
                scanner = new Scanner(System.in);
                String kuchentyp = scanner.nextLine();
                anzeigemodus();
                break;
            case LOESCHEN:
                String userinput = scanner.next();
                while (!(userinput.equals(":c") || userinput.equals(":u") ||
                        userinput.equals(":r") || userinput.equals(":p"))) {
                    if (deleteHerstellerHandler != null) deleteHerstellerHandler.distribute(loeschmodus(userinput));
                    userinput = scanner.nextLine();
                    if (deleteKuchenHandler != null) deleteKuchenHandler.distribute(kuchenloeschen(userinput));
                    userinput = scanner.nextLine();
                }
                startCLI();
                break;
            case PERSISTIEREN:
                persistieren();
                break;
        }
    }


    public KuchenEvent einfuegenHersteller() {
        String herstellername = scanner.nextLine();
        Hersteller hersteller = new Hersteller(herstellername);
        event = new KuchenEvent(this, hersteller);
        return event;
    }


    public KuchenEvent einfuegenKuchen(String kuchendaten) {
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
                    //TODO es wird ein leeren Allergenenset übergeben
                    //TODO wenn eine Varibale keine Daten beinhaltet, so ist ein diese mit "" zu initialisieren!
                    event = new KuchenEvent(this, kuchentyp, hersteller, preisd, naehrwertint,
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
        final String KUCHENFILTER = "kuchen [[Kremkuchen]]";
        final String HERSTELLER = "hersteller";
        final String ALLERGENE = "allergene i";

        Scanner userInput = new Scanner(System.in);
        String eingabeUser = userInput.nextLine();
        while (!(eingabeUser.equals(":c") || eingabeUser.equals(":d") ||
                eingabeUser.equals(":u") || eingabeUser.equals(":p"))) {
            switch (eingabeUser) {
                case KUCHEN:
                    readKuchen();
                    break;
                case KUCHENFILTER:
                    //TODO Wie wird die Eingabe vom Kunden erfolgen:
                case HERSTELLER:
                    readHersteller();
                    break;
                case ALLERGENE:
                    readAllergene();
                    break;
            }

            eingabeUser = userInput.nextLine();
            switch (eingabeUser) {
                //case ":c" -> einfuegenKuchen();
                case ":d" -> {
                    String herstellername = scanner.nextLine();
                    loeschmodus(herstellername);
                    String kuchendaten = scanner.nextLine();
                    loeschmodus(kuchendaten);
                    break;
                }
                case ":p" -> persistieren();
                case ":u" -> aenderungsmodus();
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

    public void readHersteller() {
        for (Hersteller hersteller : this.model.readHersteller()) {
            System.out.println(hersteller.getName());
        }
    }

/*
    public void readKuchen(String typ){
        //event = new KuchenEvent(this,...);
        for(Kuchen kuchen: this.model.readKuchen(this.model.readKuchen(), typ)){
            System.out.println( "Fachnummer: " + kuchen.getFachnummer() + "\n" +
                    "Inspektionsdatum: " + kuchen.getInspektionsdatum() + "\n" +
                    "verbleibender Haltbarkeit: " + kuchen.getHaltbarkeit());
        }

   }
 */


    public KuchenEvent loeschmodus(String herstellername) {
        event = new KuchenEvent(this, herstellername);
        return event;
    }

    public KuchenEvent kuchenloeschen(String loeschdaten) {
        int kuchenID = Integer.parseInt(loeschdaten);
        event = new KuchenEvent(this, kuchenID);
        return event;
    }


    public KuchenEvent aenderungsmodus() {
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        int fachnummer = Integer.parseInt(userInput);
        event = new KuchenEvent(this, fachnummer);
        return event;
         /*
            boolean result = this.model.editKuchen(fachnummer);

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
                    String herstellername = scanner.nextLine();
                    loeschmodus(herstellername);
                    String kuchendaten = scanner.nextLine();
                    loeschmodus(kuchendaten);
                    break;
                }
                case ":r" -> anzeigemodus();
                case ":u" -> aenderungsmodus();
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
