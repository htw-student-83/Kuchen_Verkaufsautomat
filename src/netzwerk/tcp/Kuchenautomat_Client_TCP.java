package netzwerk.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Kuchenautomat_Client_TCP {
    private static final String EINFUEGEN = "c";
    private static final String LOESCHEN = "d";
    private static final String AENDERN = "u";
    private static final String ANZEIGEN = "r";
    private static final String BEENDEN = "e";


    public static void main(String[]args){
        startClient();
    }


    public static void startClient() {
        Scanner input = new Scanner(System.in);
        while (true) {
            try (Socket socket = new Socket("localhost", 5002);
                 DataInputStream in = new DataInputStream(socket.getInputStream());
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());) {
                System.out.println("##########Kuchen-Verkaufsautomat###############");
                System.out.println();
                System.out.println("Bitte wählen Sie eines der folgenden Optionen aus:");
                System.out.println("(c) Einfügen");
                System.out.println("(d) Löschen");
                System.out.println("(u) Ändern");
                System.out.println("(r) Anzeigen");
                System.out.println("(e) Programm beenden");
                String options = input.next();
                switch (options) {
                    case EINFUEGEN:
                        // - Hersteller einfügen
                        out.writeUTF(options);
                        Thread.sleep(1000);
                        System.out.println(in.readUTF());
                        String name = input.next();
                        out.writeUTF(name);
                        Thread.sleep(1000);
                        System.out.println(in.readUTF());
                        Thread.sleep(1000);
                        // - Kuchen einfügen
                        System.out.println(in.readUTF());
                        String typ = input.next();
                        out.writeUTF(typ);
                        System.out.println(in.readUTF());
                        String newhersteller = input.next();
                        out.writeUTF(newhersteller);
                        System.out.println(in.readUTF());
                        double preis = input.nextDouble();
                        out.writeDouble(preis);
                        System.out.println(in.readUTF());
                        int naehrwert = input.nextInt();
                        out.writeInt(naehrwert);
                        System.out.println(in.readUTF());
                        String allergene = input.next();
                        out.writeUTF(allergene);
                        System.out.println(in.readUTF());
                        String sorte = input.next();
                        out.writeUTF(sorte);
                        Thread.sleep(1000);
                        System.out.println(in.readUTF());
                        break;
                    //Quelle: https://javabeginners.de/Schleifen_und_Verzweigungen/switch_-case_-Verzweigung.php
                    case AENDERN:
                    case LOESCHEN:
                        out.writeUTF(options);
                        Thread.sleep(1000);
                        System.out.println(in.readUTF());
                        out.writeInt(input.nextInt());
                        Thread.sleep(1000);
                        System.out.println(in.readUTF());
                        break;
                    case ANZEIGEN:
                        //Quelle: https://www.candidjava.com/tutorial/java-program-to-convert-long-to-date-object-and-date-object-to-long/
                        out.writeUTF(options);
                        Thread.sleep(1000);
                        //Erkenntnis: wenn eines der eingelesenen Werte keinen Wert hat, wird eine Exception geworfen.
                        String kliste = in.readUTF();
                        int fachnummer = in.readInt();
                        String sorteInput = in.readUTF();
                        int naehrwertInput = in.readInt();

                        long eDatum = in.readLong();
                        Date edatum = new Date(eDatum);

                        if (kliste.equals("null")) {
                            System.out.println("Kein Kuchen vorhanden.");
                            break;
                        } else {
                            System.out.println(kliste);
                            System.out.println("Fachnummer: " + fachnummer);
                            System.out.println("Sorte: " + sorteInput);
                            System.out.println("Nährwert: " + naehrwertInput);
                            System.out.println("Einfügedatum: " + edatum);
                            break;
                        }
                    case BEENDEN:
                        cancle();
                        break;
                    default:
                        System.err.println("Ihre Eingabe ist ungueltig. Versuchen Sie es erneut.");
                        startClient();
                        break;
                }
            }catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static void cancle () {
        System.exit(0);
    }
}
