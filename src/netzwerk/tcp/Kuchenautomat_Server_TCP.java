package netzwerk.tcp;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import jos.ObjektLadenJOS;
import jos.ObjektSpeicherungJOS;
import vertrag.Allergene;
import java.io.*;
import java.net.Socket;
import java.time.Duration;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Kuchenautomat_Server_TCP implements Runnable{
    Verwaltung model = new Verwaltung();

    Set<Allergene> allergeneSet = new HashSet<>();
    private Socket socket;

    Socket getData;

    ObjectInputStream ois = null;

    public Kuchenautomat_Server_TCP(Socket socket, int kapazitaet)  {
        this.model.setKapazitaet(kapazitaet);
        this.socket = socket;
    }

    @Override public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                DataInputStream in = new DataInputStream(socket.getInputStream())) {
            System.out.println("clientTCP@"+socket.getInetAddress()+":"+socket.getPort()+" connected");
            this.manageKuchenObjekts(in, out);
        } catch (IOException e) {
                e.printStackTrace();
        }
        //System.out.println("client@"+socket.getInetAddress()+":"+socket.getPort()+" disconnected");
    }


    public void manageKuchenObjekts(DataInputStream in, PrintWriter out) throws IOException {
        while (true){
            String command = in.readUTF();
            try {
                switch (command) {
                    case ":c":
                        einfuegeprozess(in, out);
                    case ":u":
                        aenderungsmodus(in, out);
                        break;
                    case ":d":
                        loeschmodus(in, out);
                        break;
                    case ":r":
                        anzeigemodus(in);
                        break;
                    case ":p":
                        persistenzmodus(in, out);
                    default:
                        System.out.println("unbekannte Eingabe");
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void einfuegeprozess(DataInputStream in, PrintWriter out) throws IOException {
        System.out.println("Der Einfügeprozess wurde gestartet..");
        int maxLengthOfKuchendaten = 10;
        String herstellername = in.readUTF();
        Hersteller hersteller = new Hersteller(herstellername);
        boolean herstellerSaved = this.model.insertHersteller(hersteller);
        System.out.println("Hersteller eingefügt: " + herstellerSaved);
        String kuchendata = in.readUTF();
        while (kuchendata.length()>maxLengthOfKuchendaten) {
            String kucheninderliste = kuchendatenform(kuchendata);
            System.out.println(kucheninderliste);
            kuchendata = in.readUTF();
            switch (kuchendata) {
                case ":u" -> aenderungsmodus(in, out);
                case ":r" -> anzeigemodus(in);
                case ":d" -> loeschmodus(in, out);
                case ":p" -> persistenzmodus(in, out);
            }
        }
    }

    private void anzeigemodus(DataInputStream in) throws IOException {
        System.out.println("Anzeigemodus");
        PrintWriter outP = new PrintWriter(this.socket.getOutputStream(), true);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        String input = in.readUTF();
        switch (input) {
            case "kuchen" -> {
                //TODO Errormessage, wenn die Verbindung nach außen geschlossen wird
                while (!(input.equals("hersteller") || input.equals("allergene"))){
                    for (Kuchen kuchen : this.model.readKuchen()) {
                        String sorte = kuchen.getKremsorte();
                        String kuchenID = String.valueOf(kuchen.getFachnummer());
                        Date eDate = kuchen.getEinfuegedatum();
                        String edate = dateFormat.format(eDate);
                        Date iDate = kuchen.getInspektionsdatum();
                        if(iDate == null){
                            Date inspektion = null;
                            outP.println("Sorte: " + sorte + " Einfuegedatum: " +
                                    edate +  " Inspekionsdatum: " + inspektion +  " ID: " + kuchenID);
                        }else {
                            String idate = dateFormat.format(iDate);
                            outP.println("Sorte: " + sorte + " Einfuegedatum: " +
                                    edate +  " Inspekionsdatum: " + idate +  " ID: " + kuchenID);
                        }
                    }
                    //TODO Nach dem Versandt der Daten soll der Server die Verbindung zum Client nicht beenden.
                    input = in.readUTF();
                }
            }

            case "hersteller" -> {
                while (input.equals("hersteller")){
                    for (Hersteller hersteller : this.model.readHersteller()) {
                        String herstellername = hersteller.getName();
                        outP.println(herstellername);
                    }
                    outP.close();
                    input = in.readUTF();
                }
            }
            case "allergene" -> {
                while (input.equals("allergene")){
                    for (Allergene allergen : this.model.readAllergener()) {
                        outP.println(allergen);
                    }
                    outP.close();
                    input = in.readUTF();
                }
            }
        }
    }


    private String kuchendatenform(String kuchendaten){
            String sorte = "";
            String ksorte = "";
            String[] kuchendatenarray = kuchendaten.split(" ");
            Kuchentyp typ = Kuchentyp.valueOf(kuchendatenarray[0]);
            String newhersteller = kuchendatenarray[1];
            Hersteller hersteller = new Hersteller(newhersteller);
            double preis = Double.parseDouble(kuchendatenarray[2]);
            int naehrwert = Integer.parseInt(kuchendatenarray[3]);
            int haltbarkeitin = Integer.parseInt(kuchendatenarray[4]);
            Duration haltbarkeit = Duration.ofDays(haltbarkeitin);
            String allergene = kuchendatenarray[5];
            String [] allergenenarray = allergene.split(",");
            sorte = kuchendatenarray[6];
            if(kuchendatenarray[7] != null){
                ksorte = kuchendatenarray[7];
            }
            if(allergene.equals(",")){
                this.model.insertKuchen(typ, hersteller, preis, naehrwert, haltbarkeit, this.allergeneSet, sorte, ksorte);
            }else{
                for(String allergen: allergenenarray){
                    this.allergeneSet.add(Allergene.valueOf(allergen));
                }
            }
            boolean kuchensaved = this.model.insertKuchen(typ, hersteller, preis, naehrwert, haltbarkeit, this.allergeneSet, sorte, ksorte);
            return "Kuchen eingefügt: " + kuchensaved;
    }


    private void aenderungsmodus(DataInputStream in, PrintWriter out) throws IOException {
        System.out.println("Der Inspizierungsprozess wurde gestartet..");
        String userInput = in.readUTF();
        while (!(userInput.equals(":c") || userInput.equals(":r") ||
                userInput.equals(":d") || userInput.equals(":p"))){
            int kuchenID = Integer.parseInt(userInput);
            boolean kuchenChecked = this.model.editKuchen(kuchenID);
            System.out.println("Kuchen inspiziert: " + kuchenChecked);
            userInput = in.readUTF();
            switch (userInput) {
                case ":c" -> einfuegeprozess(in, out);
                case ":r" -> anzeigemodus(in);
                case ":d" -> loeschmodus(in, out);
                case ":p" -> persistenzmodus(in, out);
            }
        }
    }

    private void loeschmodus(DataInputStream in, PrintWriter out) throws IOException {
        System.out.println("Der Löschprozess wurde gestartet..");
        String herstellername = in.readUTF();
        while (!(herstellername.equals(":c") || herstellername.equals(":r") ||
                herstellername.equals(":u") || herstellername.equals(":p"))){
            boolean herstellerDeleted = this.model.deleteHersteller(herstellername);
            System.out.println("Hersteller gelöscht: " + herstellerDeleted);
            String kuchennummer = in.readUTF();
            int kuchenID = Integer.parseInt(kuchennummer);
            boolean kuchenDeleted = this.model.deleteKuchen(kuchenID);
            System.out.println("Kuchen gelöscht: " + kuchenDeleted);
            herstellername = in.readUTF();
            switch (herstellername) {
                case ":c" -> einfuegeprozess(in, out);
                case ":r" -> anzeigemodus(in);
                case ":u" -> aenderungsmodus(in, out);
                case ":p" -> persistenzmodus(in, out);
            }
        }
    }



    private void persistenzmodus(DataInputStream in, PrintWriter out) throws IOException {
        System.out.println("Der Persistenzprozess wurde gestartet..");
        String pBefehl = in.readUTF();
        switch (pBefehl) {
            case "saveJOS":
                saveStatusOfSystem(in, out, pBefehl);
                break;
            case "loadJOS":
                loadStatusOfSystem(in, out, pBefehl);
                break;
        }
    }


    private void saveStatusOfSystem(DataInputStream in, PrintWriter out, String befehlPersistierung) throws IOException {
        System.out.println("saveMode");
        String userInput = "";
        while (befehlPersistierung.equals("saveJOS")){
            System.out.println("In der while-Schleife");
            ObjektSpeicherungJOS.persistiereAutomaten(this.model);
            userInput = in.readUTF();
            switch (userInput) {
                case ":c" -> einfuegeprozess(in, out);
                case ":r" -> anzeigemodus(in);
                case ":u" -> aenderungsmodus(in, out);
                case ":d" -> loeschmodus(in, out);
                case ":p" -> persistenzmodus(in, out);
            }
        }
    }


    private void loadStatusOfSystem(DataInputStream in, PrintWriter out, String persistierungsbefehl) throws IOException {
        System.out.println("Ladevorgang mit JOS");
        String userInput = "";
        while (persistierungsbefehl.equals("loadJOS")){
           this.model = ObjektLadenJOS.reloadAutomt();
           if (this.model != null) {
               for (Kuchen kuchen : this.model.readKuchen()) {
                   SimpleDateFormat dateFormat =
                           new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   String sorte = kuchen.getKremsorte();
                   String fachnummer = String.valueOf(kuchen.getFachnummer());
                   Date eDate = kuchen.getEinfuegedatum();
                   String date = dateFormat.format(eDate);
                   out.println("Sorte: " + sorte + " Einfuegedatum: " +
                           date +  " ID: " + fachnummer);
               }
               out.close();

               for (Hersteller hersteller : this.model.readHersteller()) {
                   String herstellername = hersteller.getName();
                   out.println(herstellername);
               }
               out.close();

               for (Allergene allergen : this.model.readAllergener()) {
                   out.println(allergen);
               }
               out.close();
               userInput = in.readUTF();
               switch (userInput) {
                   case ":c" -> einfuegeprozess(in, out);
                   case ":r" -> anzeigemodus(in);
                   case ":u" -> aenderungsmodus(in, out);
                   case ":d" -> loeschmodus(in, out);
                   case ":p" -> persistenzmodus(in, out);
               }
           }
       }
    }
}

