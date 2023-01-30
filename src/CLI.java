import eventsystem.controller.KuchenEvent;
import eventsystem.handler.Handler;
import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import jos.ObjektLadenJOS;
import jos.ObjektSpeicherungJOS;
import vertrag.Allergene;
import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class CLI {
    Verwaltung model = new Verwaltung();
    Set<Allergene> newallergeneSet = new HashSet<>();

    private KuchenEvent event = null;


    private Handler insertHerstellerHandler, insertKuchenHandler,
    insertAllergenHandler, editKuchenHandler,
    anzeigeKuchenHandler, anzeigeHerstellerHandler,
    anzeigeAllergeneHandler, speichernAutomatenHandler,
    ladenAutomatenHandler, deleteHerstellerHandler,
    deleteKuchenHandler;

    private static final String EINFUEGEN = ":c";
    private static final String LOESCHEN = ":d";
    private static final String AENDERN = ":u";
    private static final String ANZEIGEN = ":r";
    private static final String PERSISTIEREN = ":p";

    public CLI(int kapazitaetAutomat){
        this.model.setKapazitaet(kapazitaetAutomat);
    }


    public void setInsertHerstellerHandler(Handler handler) {
        this.insertHerstellerHandler = handler;
    }

    public void setInsertKuchenHandler(Handler handler) {
        this.insertKuchenHandler = handler;
    }

    public void setInsertAllergen(Handler handler) {
        this.insertAllergenHandler = handler;
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

    public void setDeleteHerstellerHandler(Handler handler) {this.deleteHerstellerHandler = handler;}

    public void setDeleteKuchenHandler(Handler handler) {
        this.deleteKuchenHandler = handler;
    }

    public void setSpeichernAutomatenHandler(Handler handler) {this.speichernAutomatenHandler = handler;}

    public void setLadenAutomatenHandler(Handler handler) {
        this.ladenAutomatenHandler = handler;
    }


    public void startCLI(){
            Scanner scanner = new Scanner(System.in);
            String options = scanner.next();
            switch (options) {
                case EINFUEGEN:
                    einfuegemodus();
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
                    loeschmodus();
                    startCLI();
                    break;
                case PERSISTIEREN:
                    persistieren();
                    break;
                default:
                    break;
                }
    }

    private static boolean checkinput(String inputUser) {
        return inputUser.equals("c") || inputUser.equals("d") ||
                inputUser.equals("u") || inputUser.equals("p") ||
                inputUser.equals("r") || inputUser.equals("n") ||
                inputUser.equals("e");
    }


    public void einfuegemodus(){
        Scanner scanner = new Scanner(System.in);
        String herstellername  = scanner.nextLine();
        Hersteller hersteller = new Hersteller(herstellername);
        //event = new KuchenEvent(this, herstellername);
        //return event;
        Allergene newAllergen = null;
        boolean result = this.model.insertHersteller(hersteller);
        if(result){
            System.out.println(result);
        }
        String kuchendaten = scanner.nextLine();
        while (!(kuchendaten.equals(":u")|| kuchendaten.equals(":d") ||
                kuchendaten.equals(":p") || kuchendaten.equals(":r"))){
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
            if(array[7] != null){
                ksorte = array[7];
            }

            //TODO Evtl. löschen?
            if(allergen.equals(",")){
                //TODO eine leere Liste von Allergenen wird übergeben
                //this.model.insertAllergen(this.newallergeneSet);
            }

            String[] allergenarray = allergen.split(",");
            if(allergenarray.length>0){
                for(String allergene: allergenarray){
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
            if(kuchendaten.equals(":u")){
                aenderungsmodus();
            }else if(kuchendaten.equals(":d")){
                loeschmodus();
            }else if(kuchendaten.equals(":r")){
                anzeigemodus();
            }else if(kuchendaten.equals(":p")){
                persistieren();
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
        while (!(eingabeUser.equals(":c")|| eingabeUser.equals(":d") ||
                eingabeUser.equals(":u") || eingabeUser.equals(":p"))) {
            switch (eingabeUser) {
                case KUCHEN:
                    readKuchen();
                    break;
                case KUCHENFILTER:
                    //TODO Wie wird die Eingabe vom Kunden erfolgen:
                    //kuchen kuchentyp oder
                    //kuchen [[kuchentyp]] ?
                    readKuchen("Kremkuchen");
                case HERSTELLER:
                    for (Hersteller hersteller : this.model.readHersteller()) {
                        System.out.println(hersteller.getName());
                    }
                    break;
                case ALLERGENE:
                    //event = new KuchenEvent(this, allergene);
                    //return event;
                    for (Allergene allergene : this.model.readAllergener()) {
                        System.out.println(allergene);
                    }
                    break;
            }

            eingabeUser = userInput.nextLine();
            if (eingabeUser.equals(":c")) {
                einfuegemodus();
            } else if (eingabeUser.equals(":d")) {
                loeschmodus();
            } else if (eingabeUser.equals(":p")) {
                persistieren();
            } else if (eingabeUser.equals(":u")) {
                aenderungsmodus();
            }
        }
    }

    public void readKuchen(){
        System.out.println("Kuchen");
        //event = new KuchenEvent(this,...);
        for (Kuchen kuchen : this.model.readKuchen()) {
            System.out.println("Sorte: " + kuchen.getKremsorte() + "\n" +
                            "Fachnummer: " + kuchen.getFachnummer() + "\n" +
                            "Inspektionsdatum: " + kuchen.getInspektionsdatum() + "\n" +
                            "Einfügedatum: " + kuchen.getEinfuegedatum() + "\n" +
                            "verbleibender Haltbarkeit: " + kuchen.getHaltbarkeit());
        }
    }

    public void readKuchen(String typ){
        //event = new KuchenEvent(this,...);
        for(Kuchen kuchen: this.model.readKuchen(this.model.readKuchen(), typ)){
            System.out.println( "Fachnummer: " + kuchen.getFachnummer() + "\n" +
                    "Inspektionsdatum: " + kuchen.getInspektionsdatum() + "\n" +
                    "verbleibender Haltbarkeit: " + kuchen.getHaltbarkeit());
        }
    }

    public void loeschmodus(){
        Scanner scanner = new Scanner(System.in);
        String herstellername = scanner.nextLine();
        //event = new KuchenEvent(this, herstellername);
        //return event;
        this.model.deleteHersteller(herstellername);
        int fachnummer = scanner.nextInt();
        //event = new KuchenEvent(this, fachnummer);
        //return event;
        this.model.deleteKuchen(fachnummer);
    }

    public void aenderungsmodus(){
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        while (!(userInput.equals(":p") || userInput.equals(":r") || userInput.equals(":c"))){
            int fachnummer = Integer.parseInt(userInput);
            //event = new KuchenEvent(this,fachnummer);
            //return event;
            boolean result = this.model.editKuchen(fachnummer);
            if(result){
                System.out.println(result);
            }
            userInput = scanner.nextLine();
            if(userInput.equals(":d")){
                loeschmodus();
            }else if(userInput.equals(":r")){
                anzeigemodus();
            }else if(userInput.equals(":c")){
                einfuegemodus();
            }else if(userInput.equals(":p")){
                persistieren();
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
                    //event = new KuchenEvent(this, model);
                    //return event;
                    ObjektSpeicherungJOS.persistiereAutomaten(this.model);
                    break;
                case LOADJOS:
                    //return event;
                    ladeAutomaten();
                    break;
                case LOADJBP:
                    //ObjektSpeicherungJBP.
            }
            userInput = scanner.nextLine();
        }

        if(userInput.equals(":c")){
            einfuegemodus();
        }else if(userInput.equals(":d")){
            loeschmodus();
        }else if(userInput.equals(":r")){
            anzeigemodus();
        }else if(userInput.equals(":u")){
            aenderungsmodus();
        }
    }

    public void ladeAutomaten() {
        //event = new KuchenEvent(this, model);
        //return event;
            this.model = ObjektLadenJOS.reloadAutomt();
            System.out.println("Hersteller:");
            for (Hersteller hersteller : this.model.readHersteller()) {
                System.out.println(hersteller.getName());
            }

            System.out.println("Kuchen:");
            for (Kuchen kuchen : this.model.readKuchen()) {
                System.out.println("Fachnummer: " + kuchen.getFachnummer() + "\n" +
                        "Kuchensorte: " + kuchen.getKremsorte() + "\n" +
                        "Einfuegedatum: " + kuchen.getEinfuegedatum() + "\n" +
                        "Naehrwert: " + kuchen.getNaehrwert() + "\n" +
                        "Inspektionsdatum: " + kuchen.getInspektionsdatum());
            }

            System.out.println("Allergene:");
            for (Allergene allergen : this.model.readAllergener()) {
                System.out.println(allergen);
            }
        }
}
