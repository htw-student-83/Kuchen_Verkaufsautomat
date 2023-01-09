package netzwerk.tcp;
/*
import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.Verwaltung;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import vertrag.Allergene;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.util.Date;

public class Kuchenautomat_Server_TCP implements Runnable {
    static Verwaltung model = new Verwaltung();

    public static void main(String[] args) throws IOException {
        //Verwaltung model = new Verwaltung();
        Kuchenautomat_Server_TCP server = new Kuchenautomat_Server_TCP();
        server.run();
        //server.startServer();
    }


    @Override
    public void run() {
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(5002)) {
                try (Socket socket = serverSocket.accept();
                     DataInputStream in = new DataInputStream(socket.getInputStream());
                     DataOutputStream out = new DataOutputStream(socket.getOutputStream());) {
                    String options = in.readUTF();
                    switch (options) {
                        case "c":
                            // - Hersteller einfügen
                            out.writeUTF("Welcher Herstellername?");
                            String hersteller1 = in.readUTF();
                            String resultH = herstellerEinfuegen(hersteller1);
                            out.writeUTF(resultH);
                            // - Kuchen einfügen
                            out.writeUTF("Welcher Kuchentyp?");
                            String typ = in.readUTF();
                            out.writeUTF("Welcher Herstellername?");
                            String herstellerinput = in.readUTF();
                            out.writeUTF("Welche Haltbarkeit?");
                            Duration haltbar = Duration.ofDays(in.readInt());
                            out.writeUTF("Welcher Preis?");
                            double preis = in.readDouble();
                            out.writeUTF("Welcher Nährwert?");
                            int naehrwert = in.readInt();
                            out.writeUTF("Welche Allergene?");
                            String allergene = in.readUTF();
                            out.writeUTF("Welche Sorte?");
                            String sorte = in.readUTF();
                            String kresult = kuchenEinfuegen(Kuchentyp.valueOf(typ), herstellerinput,
                                    haltbar, preis, naehrwert, Allergene.valueOf(allergene), sorte);
                            out.writeUTF(kresult);
                            break;
                        case "u":
                            out.writeUTF("Welche Fachnummer?");
                            int idforI = in.readInt();
                            String iResult = kuchenInspizieren(idforI);
                            out.writeUTF(iResult);
                            break;
                        case "d":
                            out.writeUTF("Welche Fachnummer?");
                            int idForDeleting = in.readInt();
                            String dResult = kuchenloeschen(idForDeleting);
                            out.writeUTF(dResult);
                            break;
                        case "r":
                            if(model.readKuchen().isEmpty()){
                                out.writeUTF("null");
                                out.writeInt(0);
                                out.writeUTF("null");
                                out.writeInt(0);
                                out.writeLong(0);
                            }else{
                                for(Kuchen kuchen: model.readKuchen()){
                                    out.writeUTF("Kuchen:");
                                    out.writeInt(kuchen.getFachnummer());
                                    out.writeUTF(kuchen.getKremsorte());
                                    Date eDatum = kuchen.getEinfuegedatum();
                                    long leDatum = eDatum.getTime();
                                    out.writeInt(kuchen.getNaehrwert());
                                    out.writeLong(leDatum);
                                }
                            }
                    }
                } catch (EOFException e) {
                    System.out.println("client disconnect");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private void sendeinhaltKuchen(Verwaltung model) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5002);
        Socket socket = serverSocket.accept();
        ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream());
        ous.writeObject(model);
    }




    publicString herstellerEinfuegen(String herstellername) {
        Hersteller hersteller = new Hersteller(herstellername);
        boolean hInsert = model.insertH(hersteller);
        if (hInsert) {
            return "Hersteller eingefügt.";
        } else {
            return "Hersteller nicht eingefügt..";
        }
    }

    public String kuchenEinfuegen(Kuchentyp typ, String herstellername, Duration haltbarkeit, double preis,
                                  int naehrwert, Allergene allgene, String sorte) {
        Hersteller hersteller = new Hersteller(herstellername);
        boolean kInsert = model.insert(typ, hersteller, haltbarkeit, preis, naehrwert, allgene, sorte);
        if (kInsert) {
            return "Habe den Kuchen eingefuegt.";
        } else {
            return "Habe den Kuchen nicht eingefuegt.";
        }
    }


    private String kuchenInspizieren(int input) {
        boolean isput = model.edit(input);
        if (isput) {
            return "Habe den Kuchen inspiziert.";
        } else {
            return "Kuchen zur Inspizierung nicht gefunden.";
        }
    }

    private String kuchenloeschen(int input) {
        boolean isput = model.delete(input);
        if (isput) {
            return "Habe den Kuchen geloescht.";
        } else {
            return "Habe keinen passenden Kuchen gefunden.";
        }
    }
}
*/

