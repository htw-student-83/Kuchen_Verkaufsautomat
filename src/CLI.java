import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import jos.ObjektLadenJOS;
import jos.ObjektSpeicherungJOS;
import netzwerk.tcp.Kuchenautomat_Client_TCP;
//import netzwerk.tcp.Kuchenautomat_Server_TCP;
import netzwerk.udp.Kuchenautomat_Client_UDP;
import vertrag.Allergene;

import java.time.Duration;
import java.util.Scanner;

public class CLI {
    Verwaltung model;
    private static final String EINFUEGEN = ":c";
    private static final String LOESCHEN = ":d";
    private static final String AENDERN = ":u";
    private static final String ANZEIGEN = ":r";
    private static final String PERSISTIEREN = ":p";
    private static final String NETZWERK = ":n";
    private static final String BEENDEN = ":e";

    public CLI(Verwaltung model) {
        this.model = model;
    }

    public void startAutomat(){
            System.out.println("##########Kuchen-Verkaufsautomat###############");
            System.out.println();
            System.out.println("Bitte wählen Sie eines der folgenden Optionen aus:");
            System.out.println(":c Einfügemodus");
            System.out.println(":d Löschmodus");
            System.out.println(":r Anzeigemodus");
            System.out.println(":u Änderungsmodus");
            System.out.println(":p Persistenzmodus");
            System.out.println(":e Programm beenden");
            Scanner scanner = new Scanner(System.in);
            String options = scanner.next();
            switch (options) {
                case EINFUEGEN:
                    einfuegemodus();
                    startAutomat();
                    break;
                case AENDERN:
                    aenderungsmodus();
                    startAutomat();
                    break;
                case ANZEIGEN:
                    anzeigemodus();
                    startAutomat();
                    break;
                case LOESCHEN:
                    loeschmodus();
                    startAutomat();
                    break;
                case PERSISTIEREN:
                    persistieren();
                    startAutomat();
                    break;
                case NETZWERK:
                    netzwerkauswaehlen();
                    break;
                case BEENDEN:
                    cancle();
                    break;
                default:
                    System.err.println("Ihre Eingabe ist ungueltig. Versuchen Sie es erneut.");
                    startAutomat();
                    break;
                }
    }

    private static boolean checkinput(String inputUser) {
        return inputUser.equals("c") || inputUser.equals("d") ||
                inputUser.equals("u") || inputUser.equals("p") ||
                inputUser.equals("r") || inputUser.equals("n") ||
                inputUser.equals("e");
    }

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



    public void einfuegemodus(){
        System.out.println("Einfügemodus: ");
        Scanner scanner = new Scanner(System.in);
        String herstellername  = scanner.nextLine();
        Hersteller hersteller = new Hersteller(herstellername);
        this.model.insertH(hersteller);
        String kuchendaten = scanner.nextLine();
        String[] array = kuchendaten.split(" ");
        while (array.length<7){
            System.err.println("Zu wenig Argumente!");
            startAutomat();
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
        Allergene allergen = Allergene.valueOf(allergene);
        String sorte = array[6];
        Hersteller hersteller2 = new Hersteller(herstellern);
        this.model.insert(kuchentyp, hersteller2, preisd, naehrwertint,
                Duration.ofDays(haltbarkeit), allergen, sorte);
    }


    public void anzeigemodus(){
        System.out.println("Anzeigemodus: ");
        System.out.println("Hersteller");
        for(Hersteller hersteller: this.model.readHersteller()){
            System.out.println(hersteller.getName());
        }
        //TODO Hersteller mit Anzahl ihrer Kuchen listen
        System.out.println("Kuchen");
        for(Kuchen kuchen: this.model.readKuchen()){
            System.out.println("Fachnummer: " + kuchen.getFachnummer() + "\n" +
                                "Inspektionsdatum: " + kuchen.getInspektionsdatum() + "\n" +
                                "Einfügedatum: " + kuchen.getEinfuegedatum() + "\n" +
                                "verbleibender Haltbarkeit: " + kuchen.getHaltbarkeit());
        }
        //TODO Kuchen listen
        System.out.println("Allergene");
        for(Allergene allergene: this.model.readAllergener()){
            System.out.println(allergene);
        }
    }

    public void loeschmodus(){
        System.out.println("Löschmodus: ");
        Scanner scanner = new Scanner(System.in);
        String herstellername = scanner.nextLine();
        this.model.deleteHersteller(herstellername);
        int fachnummer = scanner.nextInt();
        this.model.delete(fachnummer);
    }

    public void aenderungsmodus(){
        System.out.println("Änderungsmodus: ");
        Scanner scanner = new Scanner(System.in);
        int fachnummer = scanner.nextInt();
        this.model.edit(fachnummer);
    }

    private void persistieren() {
        final String SAVEJOS = "saveJOS";
        final String LOADJOS = "loadJOS";
        final String SAVEJBP = "saveJBP";
        final String LOADJBP = "loadJBP";

        System.out.println("Persistenzmodus: ");
        System.out.println("saveJOS"  + " speichert mittels JOS");
        System.out.println("loadJOS" + " lädt mittels JOS");
        System.out.println("saveJBP" + " speichert mittels JBP");
        System.out.println("loadJBP" + " lädt mittels JBP");
        Scanner scanner = new Scanner(System.in);
        String eingabe = scanner.next();
        switch (eingabe){
            case SAVEJOS:
                ObjektSpeicherungJOS.persistiereAutomaten();
                startAutomat();
            case LOADJOS:
                ObjektLadenJOS.reloadAutomt();
                startAutomat();
            case LOADJBP:
                //ObjektSpeicherungJBP.
            default:
                System.err.println("Ihre Eingabe ist ungueltig. Versuchen Sie es erneut.");
                netzwerkauswaehlen();
        }
    }

    private static void cancle () {
        System.exit(0);
    }
}
