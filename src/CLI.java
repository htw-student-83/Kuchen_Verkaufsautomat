import eventsystem.controller.KuchenEvent;
import eventsystem.handler.Handler;
import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.DekoKuchen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import jbp.ObjektLadenJBP;
import jbp.ObjektSpeicherungJBP;
import jos.ObjektLadenJOS;
import jos.ObjektSpeicherungJOS;
import vertrag.Allergene;
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


    public void startCLI() {
        String options = scanner.nextLine();
        switch (options) {
            case EINFUEGEN:
                if(insertHerstellerHandler != null) insertHerstellerHandler.distribute(einfuegenHersteller());
                String kuchendaten = scanner.nextLine();
                while (!(kuchendaten.equals(":u") || kuchendaten.equals(":d") ||
                        kuchendaten.equals(":p") || kuchendaten.equals(":r"))) {
                    if(insertKuchenHandler != null) insertKuchenHandler.distribute(einfuegenKuchen(kuchendaten));
                    kuchendaten = scanner.nextLine();
                    switch (kuchendaten){
                        case ":u":
                            aenderungsmodus();
                        case ":d":
                            loeschmodus();
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
                String kuchentyp = scanner.next();
                anzeigemodus();
                break;
            case LOESCHEN:
                //if(deleteHerstellerHandler != null) deleteHerstellerHandler.distribute(loeschmodus());
                //if(deleteKuchenHandler != null) deleteKuchenHandler.distribute(loeschmodus());
                startCLI();
                break;
            case PERSISTIEREN:
                persistieren();
                break;
        }
    }


    //TODO ein Einfuegevent für einen Hersteller
    public KuchenEvent einfuegenHersteller(){
        String herstellername = scanner.nextLine();
        Hersteller hersteller = new Hersteller(herstellername);
        event = new KuchenEvent(this, hersteller);
        return event;
    }

    public KuchenEvent einfuegenKuchen(String kuchendaten) {
        String[] array = kuchendaten.split(" ");
        String boden = array[0];
        String newhersteller = array[1];
        Hersteller nextHersteller = new Hersteller(newhersteller);
        String[] belagarray = new String[array.length - 2];
        System.arraycopy(array, 2, belagarray, 0, belagarray.length);
        event = new KuchenEvent(this, boden, nextHersteller, belagarray);
        return event;
    }

    public void einfuegemodus() {
        Scanner scanner = new Scanner(System.in);
        String herstellername = scanner.nextLine();
        Hersteller hersteller = new Hersteller(herstellername);
        boolean result = this.model.insertHersteller(hersteller);
        System.out.println(result);
        String kuchendaten = scanner.nextLine();
        while (!(kuchendaten.equals(":u") || kuchendaten.equals(":d") ||
                kuchendaten.equals(":p") || kuchendaten.equals(":r"))) {
            String[] array = kuchendaten.split(" ");
            String boden = array[0];
            String newhersteller = array[1];
            Hersteller nextHersteller = new Hersteller(newhersteller);
            String[] belagarray = new String[array.length - 2];
            System.arraycopy(array, 2, belagarray, 0, belagarray.length);
            boolean result2 = this.model.insertKuchen2(boden, nextHersteller, belagarray);
            System.out.println(result2);
            kuchendaten = scanner.nextLine();
            switch (kuchendaten) {
                case ":u" -> aenderungsmodus();
                case ":d" -> loeschmodus();
                case ":r" -> anzeigemodus();
                case ":p" -> persistieren();
            }
        }
    }

    public void einfuegemodus0() {
        Scanner scanner = new Scanner(System.in);
        String herstellername = scanner.nextLine();
        Hersteller hersteller = new Hersteller(herstellername);
        Allergene newAllergen = null;
        boolean result = this.model.insertHersteller(hersteller);
        if (result) {
            System.out.println(result);
        }
        String kuchendaten = scanner.nextLine();
        while (!(kuchendaten.equals(":u") || kuchendaten.equals(":d") ||
                kuchendaten.equals(":p") || kuchendaten.equals(":r"))) {
            String sorte = "";
            String ksorte = "";
            String[] array = kuchendaten.split(" ");
            String typ = array[0];
            Kuchentyp kuchentyp = Kuchentyp.valueOf(typ);
            String herstellern = array[1];
            String preis = array[2];
            double preisd = Double.parseDouble(preis);
            String naehrwert = array[3];
            int naehrwertint = Integer.parseInt(naehrwert);
            String haltbarkeitS = array[4];
            int haltbarkeit = Integer.parseInt(haltbarkeitS);
            String allergen = array[5].trim();
            sorte = array[6];
            if (array[7] != null) {
                ksorte = array[7];
            }

            //TODO Evtl. löschen?
            if (allergen.equals(",")) {
                //TODO eine leere Liste von Allergenen wird übergeben
                //this.model.insertAllergen(this.newallergeneSet);
            }

            String[] allergenarray = allergen.split(",");
            if (allergenarray.length > 0) {
                for (String allergene : allergenarray) {
                    allergene = allergene.trim();
                    //TODO Jedes einzelene Allergen wird dem Set übergeben
                    this.newallergeneSet = Collections.singleton(Allergene.valueOf(allergene));
                    //TODO Das Set (Aufzählung von Allergenen) wird übergeben
                    //this.model.insertAllergen(this.newallergeneSet);
                }
            }

            //String[] sorte = new String[kuchendaten.length() - 6];
            //String[] ksorte = new String[kuchendaten.length() - 7];
            // copy remaining inputs to argss array
            //System.arraycopy(array, 6, sorte, 0, array.length - 6);
            //System.arraycopy(array, 7, ksorte, 0, array.length - 7);
            Hersteller hersteller2 = new Hersteller(herstellern);
            //event = new KuchenEvent()
            // return event;
            boolean result2 = this.model.insertKuchen(kuchentyp, hersteller2, preisd, naehrwertint,
                    Duration.ofDays(haltbarkeit), this.newallergeneSet, sorte, ksorte);
            System.out.println(result2);
            kuchendaten = scanner.nextLine();
            switch (kuchendaten) {
                case ":u" -> aenderungsmodus();
                case ":d" -> loeschmodus();
                case ":r" -> anzeigemodus();
                case ":p" -> persistieren();
            }
        }
    }


    public void anzeigemodus() {
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
                    //kuchen kuchentyp oder
                    //kuchen [[kuchentyp]] ?
                    //readKuchen("Kremkuchen");
                case HERSTELLER:
                    readHersteller();
                    break;
                case ALLERGENE:
                    readAllergene();
                    break;
            }

            eingabeUser = userInput.nextLine();
            switch (eingabeUser) {
                case ":c" -> einfuegemodus();
                case ":d" -> loeschmodus();
                case ":p" -> persistieren();
                case ":u" -> aenderungsmodus();
            }
        }
    }

    public void readKuchen() {
        for (DekoKuchen kuchen : this.model.readKuchen()) {
            System.out.println("Fachnummer: " + kuchen.getFachnummer() + "\n" +
                    "Einfügedatum: " + kuchen.getEinfuegedatum() + "\n" +
                    "Inspektionsdatum: " + kuchen.getInspektionsdatum() + "\n" +
                    "Preis: " + kuchen.getPreis() + "\n" +
                    "Nährwert: " + kuchen.getNaehrwert());
        }
    }

    public void readAllergene() {
        System.out.println("Allergene");
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

    public void loeschmodus() {
        System.out.println("Löschen");
        String herstellername = scanner.nextLine();
        while (!(herstellername.equals(":c") || herstellername.equals(":r") ||
                herstellername.equals(":u") || herstellername.equals(":p"))) {
            boolean result = this.model.deleteHersteller(herstellername);
            System.out.println(result);
            String fachnummer = scanner.nextLine();
            boolean result2 = this.model.deleteKuchen(Integer.parseInt(fachnummer));
            System.out.println(result2);
            herstellername = scanner.nextLine();
            switch (herstellername) {
                case ":d" -> loeschmodus();
                case ":r" -> anzeigemodus();
                case ":c" -> einfuegemodus();
                case ":p" -> persistieren();
            }
        }
    }


    public void aenderungsmodus() {
        System.out.println("Inspizierung");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        while (!(userInput.equals(":p") || userInput.equals(":r") ||
                userInput.equals(":c") || userInput.equals(":d"))) {
            int fachnummer = Integer.parseInt(userInput);
            boolean result = this.model.editKuchen(fachnummer);
            System.out.println(result);
            userInput = scanner.nextLine();
            switch (userInput) {
                case ":d" -> loeschmodus();
                case ":r" -> anzeigemodus();
                case ":c" -> einfuegemodus();
                case ":p" -> persistieren();
            }
        }
    }

    private void persistieren() {
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
                case ":c" -> einfuegemodus();
                case ":d" -> loeschmodus();
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
        for (DekoKuchen kuchen : this.model.readKuchen()) {
            System.out.println("Fachnummer: " + kuchen.getFachnummer() + "\n" +
                    "Einfuegedatum: " + kuchen.getEinfuegedatum() + "\n" +
                    "Inspektionsdatum: " + kuchen.getInspektionsdatum() + "\n" +
                    "Preis: " + kuchen.getPreis() + "\n" +
                    "Nährwert: " + kuchen.getNaehrwert() + "\n" +
                    "Allergene: " + kuchen.getAllergene());
        }


/*
            for (DekoKuchen kuchen : this.model.readKuchen()) {
                System.out.println("Preis: " + kuchen.getBoden().getPreis() + "\n" +
                        "Name: " + kuchen.getBoden().getName() + "\n" +
                        "Nährwert: " + kuchen.getBoden().getNaehrwert() + "\n" +
                        "verbleibene Haltbarkeit: " + kuchen.getBoden().gethaltbarkeit() + "\n" +
                        "Allergene: " + kuchen.getBoden().getAllergen() + "\n");
            }

            for (DekoKuchen kuchen : this.model.readKuchen()) {
                for(Kuchenbestandteile belag: kuchen.getBelag()){
                    System.out.println("Preis: " + belag.getPreis() + "\n" +
                            "Name: " + belag.getName() + "\n" +
                            "Nährwert: " + belag.getNaehrwert() + "\n" +
                            "verbleibene Haltbarkeit: " + belag.gethaltbarkeit() + "\n" +
                            "Allergene: " + belag.getAllergen() + "\n");
                }
            }
 */
    }

    public void automatenzustandmitJBPSpeichern() {
        System.out.println("Automatenzustand vor dem Speichervorgang:");
        System.out.println("Herstellerset: " + this.model.readHersteller().size());
        System.out.println("Kuchenliste: " + this.model.readKuchen().size());
        ObjektSpeicherungJBP.persistiereAutomaten(this.model, "automaten.xml");
    }
    public void automatenzustandmitJBPLaden() {
        //TODO Wieso werden keine Daten geladen?
        ObjektLadenJBP.automatenzustandLaden("automaten.xml");
    }
}
