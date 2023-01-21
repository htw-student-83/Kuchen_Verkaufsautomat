package netzwerk.tcp;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchenautomat;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import vertrag.Allergene;
import java.io.*;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class Kuchenautomat_Server_TCP implements Runnable{
    Verwaltung model = new Verwaltung();
    Kuchenautomat automat = new Kuchenautomat();
    private Socket socket;

    Socket getData;

    ObjectInputStream ois = null;

    public Kuchenautomat_Server_TCP(Socket socket, int kapazitaet)  {
        this.automat.setKapazitaet(kapazitaet);
        this.socket = socket;
    }

    @Override public void run() {
        try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
            System.out.println("client@"+socket.getInetAddress()+":"+socket.getPort()+" connected");
            this.createKuchenObjekt(in);
        } catch (IOException e) {
                e.printStackTrace();
        }
        //System.out.println("client@"+socket.getInetAddress()+":"+socket.getPort()+" disconnected");
    }

    public void createKuchenObjekt(DataInputStream in) throws IOException {
        String command = in.readUTF();
            try {
                switch (command) {
                    case ":c":
                        System.out.println("Der Einfügeprozess wurde gestartet..");
                        command = in.readUTF();
                        System.out.println("Herstellername: " + command);
                        command = in.readUTF();
                        System.out.println("Kuchendaten: " + command);
                        break;
                    default:
                        System.out.println("unbekannte Eingabe");
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
    }

/*
    public void run2(){
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
                            if(this.model.readKuchen().isEmpty()){
                                out.writeUTF("null");
                                out.writeInt(0);
                                out.writeUTF("null");
                                out.writeInt(0);
                                out.writeLong(0);
                            }else{
                                for(Kuchen kuchen: this.model.readKuchen()){
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
 */



    public String herstellerEinfuegen(String herstellername) {
        Hersteller hersteller = new Hersteller(herstellername);
        boolean hInsert = this.model.insertHersteller(hersteller);
        if (hInsert) {
            return "Hersteller eingefügt.";
        } else {
            return "Hersteller nicht eingefügt..";
        }
    }

    public String kuchenEinfuegen(Kuchentyp typ, String herstellername, Duration haltbarkeit, double preis,
                                  int naehrwert, Allergene allgene, String sorte) {
        Hersteller hersteller = new Hersteller(herstellername);
        boolean kInsert = this.model.insertKuchen(typ, hersteller, preis, naehrwert, haltbarkeit, allgene, sorte);
        if (kInsert) {
            return "Habe den Kuchen eingefuegt.";
        } else {
            return "Habe den Kuchen nicht eingefuegt.";
        }
    }


    private String kuchenInspizieren(int input) {
        boolean isput = this.model.editKuchen(input);
        if (isput) {
            return "Habe den Kuchen inspiziert.";
        } else {
            return "Kuchen zur Inspizierung nicht gefunden.";
        }
    }

    private String kuchenloeschen(int input) {
        boolean isput = this.model.deleteKuchen(input);
        if (isput) {
            return "Habe den Kuchen geloescht.";
        } else {
            return "Habe keinen passenden Kuchen gefunden.";
        }
    }
}

