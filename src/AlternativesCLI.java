import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import jos.ObjektLadenJOS;
import jos.ObjektSpeicherungJOS;
import vertrag.Allergene;

import java.time.Duration;
import java.util.Scanner;

public class AlternativesCLI {
    static Verwaltung model;
    private static final String EINFUEGEN = ":c";
    private static final String LOESCHEN = ":d";
    private static final String AENDERN = ":u";
    private static final String ANZEIGEN = ":r";
    private static final String PERSISTIEREN = ":p";

    public AlternativesCLI(Verwaltung model) {
        this.model = model;
    }


    public static void main(String[]args){
        Verwaltung model = new Verwaltung();
        AlternativesCLI acli = new AlternativesCLI(model);
        acli.startAutomat();
    }

    public void startAutomat(){
            /*System.out.println("##########Kuchen-Verkaufsautomat###############");
            System.out.println();
            System.out.println("Bitte wählen Sie eines der folgenden Optionen aus:");
            System.out.println(":c Einfügemodus");
            System.out.println(":d Löschmodus");
            System.out.println(":r Anzeigemodus");
            System.out.println(":u Änderungsmodus");
            System.out.println(":p Persistenzmodus");
            */
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
                    //die Methode um einen Hersteller einzufügen wurde deaktiviert.
                    loeschmodus();
                    break;
                case PERSISTIEREN:
                    persistieren();
                    break;
                default:
                    System.err.println("Ihre Eingabe ist ungueltig. Versuchen Sie es erneut.");
                    break;
                }
    }

    private static boolean checkinput(String inputUser) {
        return inputUser.equals("c") || inputUser.equals("d") ||
                inputUser.equals("u") || inputUser.equals("p") ||
                inputUser.equals("r") || inputUser.equals("n") ||
                inputUser.equals("e");
    }
/*
    private void netzwerkauswaehlen() {
        final String UDP = "UDP";
        final String TCP = "TCP";
        System.out.println("Wie soll der Automat bedient werden?");
        System.out.println("(TCP)");
        System.out.println("(UDP)");
        Scanner scanner = new Scanner(System.in);
        String userNetzwerk = scanner.next();
        switch (userNetzwerk){
            case UDP:
                Kuchenautomat_Client_UDP.clientStart();
                break;
            case TCP:
                Kuchenautomat_Client_TCP.startClient();
            default:
                System.err.println("Ihre Eingabe ist ungueltig. Versuchen Sie es erneut.");
                netzwerkauswaehlen();
        }
    }
 */



    public void einfuegemodus(){
        Scanner scanner = new Scanner(System.in);
        String herstellername  = scanner.nextLine();
        Hersteller hersteller = new Hersteller(herstellername);
        boolean result = this.model.insertHersteller(hersteller);
        if(result){
            System.out.println(result);
        }
        String kuchendaten = scanner.nextLine();
        while (!(kuchendaten.equals(":u")|| kuchendaten.equals(":d") ||
                kuchendaten.equals(":p") || kuchendaten.equals(":r"))){
            String[] array = kuchendaten.split(" ");
            while (array.length<7){
                System.err.println("Zu wenig Argumente!");
                break;
            }
            String typ = array[0];
            Kuchentyp kuchentyp = Kuchentyp.valueOf(typ);
            String herstellern = array[1];
            String preis = array[2];
            double preisd = Double.parseDouble(preis);
            String naehrwert = array[3];
            int naehrwertint = Integer.parseInt(naehrwert);
            String haltbarkeitS = array[4];
            int haltbarkeit = Integer.parseInt(haltbarkeitS);
            String allergene = array[5];
            String allergenAsNull = null;
            String sorte = array[6];
            Hersteller hersteller2 = new Hersteller(herstellern);
            if(allergene.equals(",")){
                //TODO Sollen dann keins eingefügt werden oder das "Komma"?
               // this.model.insertKuchen(kuchentyp, hersteller2, preisd, naehrwertint, Duration.ofDays(haltbarkeit), Allergene.valueOf(allergenAsNull), sorte);
            }
            Allergene allergen = Allergene.valueOf(allergene);
           // boolean result2 = this.model.insertKuchen(kuchentyp, hersteller2, preisd, naehrwertint, Duration.ofDays(haltbarkeit), allergen, sorte);
            //if(result2){
             //   System.out.println(result2);
            //}
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
        final String HERSTELLER = "hersteller";
        final String ALLERGENE = "allergene";

        Scanner userInput = new Scanner(System.in);
        String eingabeUser = userInput.nextLine();
        while (!(eingabeUser.equals(":c")|| eingabeUser.equals(":d") ||
                eingabeUser.equals(":u") || eingabeUser.equals(":p"))) {
            switch (eingabeUser) {
                case KUCHEN:
                    for (Kuchen kuchen : model.readKuchen()) {
                        System.out.println("Sorte: " + kuchen.getKremsorte() + "\n" +
                                "Fachnummer: " + kuchen.getFachnummer() + "\n" +
                                "Inspektionsdatum: " + kuchen.getInspektionsdatum() + "\n" +
                                "Einfügedatum: " + kuchen.getEinfuegedatum() + "\n" +
                                "verbleibender Haltbarkeit: " + kuchen.getHaltbarkeit());
                    }
                    break;
                case HERSTELLER:
                    for (Hersteller hersteller : model.readHersteller()) {
                        System.out.println(hersteller.getName());
                    }
                    break;
                case ALLERGENE:
                    //TODO Funktion deaktivieren
                    //for (Allergene allergene : model.readAllergener()) {
                    //    System.out.println(allergene);
                    //}
                    System.out.println("Funktion zur Allergenenauflistung deaktiviert.");
                    disable();
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
    /*
        if(kuchentyp == null){
            readKuchen();
        }else{
            Kuchentyp typ = Kuchentyp.valueOf(kuchentyp);
            for (Kuchen kuchen : this.model.readKuchen()) {
                if (typ  == Kuchentyp.Kremkuchen){
                    System.out.println("Kuchentyp:" + kuchentyp + "\n" +
                            //TODO Wie weiß das System, welcher Kuchen ein Kremkuchen ist?
                            "Fachnummer: " + kuchen.getFachnummer() + "\n" +
                            "Inspektionsdatum: " + kuchen.getInspektionsdatum() + "\n" +
                            "Einfügedatum: " + kuchen.getEinfuegedatum() + "\n" +
                            "verbleibender Haltbarkeit: " + kuchen.getHaltbarkeit());
                    System.out.println("---------------------------------------------");
                    break;
                } else if(typ == Kuchentyp.Obstkuchen){
                    System.out.println("Kuchentyp:" + kuchentyp + "\n" +
                            "Fachnummer: " + kuchen.getFachnummer() + "\n" +
                            "Inspektionsdatum: " + kuchen.getInspektionsdatum() + "\n" +
                            "Einfügedatum: " + kuchen.getEinfuegedatum() + "\n" +
                            "verbleibender Haltbarkeit: " + kuchen.getHaltbarkeit());
                    System.out.println("---------------------------------------------");
                    break;
                }else if(typ == Kuchentyp.Obsttorte){
                    System.out.println("Kuchentyp:" + kuchentyp + "\n" +
                            "Fachnummer: " + kuchen.getFachnummer() + "\n" +
                            "Inspektionsdatum: " + kuchen.getInspektionsdatum() + "\n" +
                            "Einfügedatum: " + kuchen.getEinfuegedatum() + "\n" +
                            "verbleibender Haltbarkeit: " + kuchen.getHaltbarkeit());
                    System.out.println("---------------------------------------------");
                    break;
                }
            }
        }

        //TODO Hersteller mit Anzahl ihrer Kuchen listen

        //TODO Kuchen listen
        System.out.println("Allergene");
        for (Allergene allergene : this.model.readAllergener()) {
            System.out.println(allergene);
        }
         */

    public void readKuchen(){
        System.out.println("Kuchen");
        for (Kuchen kuchen : model.readKuchen()) {
            System.out.println("Sorte: " + kuchen.getKremsorte() + "\n" +
                            "Fachnummer: " + kuchen.getFachnummer() + "\n" +
                            "Inspektionsdatum: " + kuchen.getInspektionsdatum() + "\n" +
                            "Einfügedatum: " + kuchen.getEinfuegedatum() + "\n" +
                            "verbleibender Haltbarkeit: " + kuchen.getHaltbarkeit());
        }
    }

    public void loeschmodus(){
        //TODO Funktion Hersteller löschen deaktivieren
        Scanner scanner = new Scanner(System.in);
        String herstellername = scanner.nextLine();
        model.deleteHersteller(herstellername);;
        int fachnummer = scanner.nextInt();
        model.deleteKuchen(fachnummer);
    }

    public static void disable()
    {
    }

    public void aenderungsmodus(){
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        while (!(userInput.equals(":p") || userInput.equals(":r") || userInput.equals(":c"))){
            int fachnummer = Integer.parseInt(userInput);
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
                    ObjektSpeicherungJOS.persistiereAutomaten(this.model);
                    break;
                case LOADJOS:
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
