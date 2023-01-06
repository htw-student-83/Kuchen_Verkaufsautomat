import jbp.ObjektSpeicherungJBP;
import jos.ObjektSpeicherungJOS;
import netzwerk.tcp.Kuchenautomat_Client_TCP;
import netzwerk.tcp.Kuchenautomat_Server_TCP;
import netzwerk.udp.Kuchenautomat_Client_UDP;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Kuchen_VerkaufsautomatRun {
    private Scanner scanner;

    private PrintStream printStream;

    private static final String EINFUEGEN = "c";
    private static final String LOESCHEN = "d";
    private static final String AENDERN = "u";
    private static final String ANZEIGEN = "r";
    private static final String PERSISTIEREN = "p";
    private static final String NETZWERK = "n";
    private static final String BEENDEN = "e";

    public Kuchen_VerkaufsautomatRun(InputStream is, PrintStream os){
        this.scanner = new Scanner(is);
        this.printStream = os;
    }

    public static void main(String[]args){
         Kuchen_VerkaufsautomatRun automat = new Kuchen_VerkaufsautomatRun(System.in, System.out);
         automat.startAutomat();
    }

    public void startAutomat(){
            System.out.println("##########Kuchen-Verkaufsautomat###############");
            System.out.println();
            System.out.println("Bitte wählen Sie eines der folgenden Optionen aus:");
            System.out.println("(c) Einfügemodus");
            System.out.println("(d) Löschmodus");
            System.out.println("(u) Änderungsmodus");
            System.out.println("(r) Anzeigemodus");
            System.out.println("(p) Persistenzmodus");
            System.out.println("(n) Netzwerk");
            System.out.println("(e) Programm beenden");
            String options = scanner.next();
            switch (options) {
                case EINFUEGEN:
                    printStream.println("Ausgabe: " + "Hersteller einfügen");
                    break;
                case AENDERN:
                    break;
                case ANZEIGEN:
                    printStream.println("Ausgabe: " + "Hersteller anzeigen.");
                    break;
                case LOESCHEN:
                    break;
                case PERSISTIEREN:
                    persistieren();
                    break;
                case NETZWERK:
                    netzwerkauswaehlen();
                    break;
                case BEENDEN:
                    cancle();
                    break;
                default:
                    printStream.println("Ihre Eingabe ist ungueltig. Versuchen Sie es erneut.");
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
        String userNetzwerk = scanner.next();
        switch (userNetzwerk){
            case UDP:
                Kuchenautomat_Client_UDP.clientStart();
                break;
            case TCP:
                Thread t1 = new Thread(new Kuchenautomat_Server_TCP());
                t1.start();
                Kuchenautomat_Client_TCP.startClient();
            default:
                printStream.println("Ihre Eingabe ist ungueltig. Versuchen Sie es erneut.");
                netzwerkauswaehlen();
        }
    }

    private void persistieren() {
        final String JOS = "JOS";
        final String JBP = "JBP";

        System.out.println("Wie sollen die Daten gespeichert werden?");
        System.out.println("JOS");
        System.out.println("JBP");
        String eingabe = scanner.next();
        switch (eingabe){
            case JOS:
                ObjektSpeicherungJOS.persistiereAutomaten();
                startAutomat();
            case JBP:
                ObjektSpeicherungJBP.persistiereAutomaten();
                startAutomat();
            default:
                printStream.println("Ihre Eingabe ist ungueltig. Versuchen Sie es erneut.");
                netzwerkauswaehlen();
        }
    }

    private static void cancle () {
        System.exit(0);
    }
}
