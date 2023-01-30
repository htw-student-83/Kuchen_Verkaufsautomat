package geschaeftslogik.verkaufsobjekt;

import geschaeftslogik.*;
import geschaeftslogik.dekoratorpatter.dekorator.Muerbeteig;
import geschaeftslogik.dekoratorpatter.dekorator.*;
import vertrag.Allergene;
import vertrag.KuchenlistManagement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class Verwaltung extends Observable implements KuchenlistManagement, Serializable {
    Kuchenautomat automat = new Kuchenautomat();
    private int currentFachnummer = 0;
    List<Kuchen> kuchenliste = new ArrayList<>();

    List<IKuchen> kuchenliste2 = new ArrayList<>();
    Set<Hersteller> herstellerset = new HashSet<>();
    Set<Allergene> allergenset = new HashSet<>();
    ArrayList<Kuchenbestandteile> belagliste = new ArrayList<>();

    //Quelle: chatGPT
    public boolean insetKuchen2(String kuchenboden, Hersteller hersteller, String... kuchenbelag){
        if(checkTablehersteller(hersteller) == HerstellerStatus.Hersteller_bekannt && isNotFull()) {
            //TODO alle wesentlichen Prüfungen, Hersteller bekannt genug Kapazität vorhanden!
            IKuchen kuchen;
            Kuchenbestandteile boden = null;
            Kuchenbestandteile kBelag = null;

            switch (kuchenboden) {
                case "Hefeteig" -> {
                    boden = new Hefeteig(hersteller);
                    break;
                }
                case "Muerbeteig" -> {
                    boden = new Muerbeteig(hersteller);
                    break;
                }
            }

            for(String belagelement: kuchenbelag){
                switch (belagelement) {
                    case "Kirsche" -> {
                        kBelag = new Kirsche(boden);
                        belagliste.add(kBelag);
                        break;
                    }
                    case "Birne" -> {
                        kBelag = new Birne(boden);
                        belagliste.add(kBelag);
                        break;
                    }
                    case "Apfel" -> {
                        kBelag = new Apfel(boden);
                        belagliste.add(kBelag);
                        break;
                    }
                    case "Pudding" -> {
                        kBelag = new Pudding(boden);
                        belagliste.add(kBelag);
                        break;
                    }
                    case "Sahne" -> {
                        kBelag = new Sahne(boden);
                        belagliste.add(kBelag);
                        break;
                    }
                }
            }

            //TODO Wie kann ein Kuchen mit seinen bereits erzeugten Bestandteilen angelegt werden?
            kuchen = new DekoKuchen(boden, hersteller, belagliste);
            kuchen.setFachnummer(getCurrentFachnummerAndIncrement());
            kuchen.setEinfuegedatum(getDatum());
            automat.boxincrement();
            kuchenliste2.add(kuchen);
            return true;
            }
        return false;
    }


    @Override
    public boolean insertKuchen(Kuchentyp typ, Hersteller hersteller, double preis,
                                int naehrwert, Duration haltbarkeit, Set<Allergene> allergene,
                                String obstsorte, String kremsorte) {
        Kuchen kuchen = null;
        switch (typ){
            case Kremkuchen:
                kuchen = new Kremkuchen("Kremkuchen",hersteller, BigDecimal.valueOf(preis), naehrwert,
                        haltbarkeit, allergene, kremsorte);
                break;
            case Obstkuchen:
                kuchen = new Obstkuchen( "Obstkuchen", hersteller, BigDecimal.valueOf(preis), naehrwert,
                        haltbarkeit, obstsorte, kremsorte);
                break;
            case Obsttorte:
                kuchen = new Obsttorte("Obsttorte",hersteller,BigDecimal.valueOf(preis), naehrwert,
                        haltbarkeit, allergene, obstsorte, kremsorte);
                break;
            default:
                return false;
        }
        // - besser -> pruefeHersteller()
        if(checkTablehersteller(hersteller) == HerstellerStatus.Hersteller_bekannt &&
                isNotFull()){
            kuchen.setFachnummer(getCurrentFachnummerAndIncrement());
            kuchen.setEinfuegedatum(getDatum());
            kuchenliste.add(kuchen);
            insertAllergen(allergene);
            automat.boxincrement();
            this.setChanged();
            this.notifyObservers();
            return true;
        }
        this.notifyObservers();
        return false;
    }



    // - Besserer Methodennamen: insertHersteller()
    @Override
    public boolean insertHersteller(Hersteller neuerHersteller) {
        if(checkTablehersteller(neuerHersteller)== HerstellerStatus.Hersteller_unbekannt){
            herstellerset.add(neuerHersteller);
            this.setChanged();
            this.notifyObservers();
            return true;
        }
        this.notifyObservers();
        return false;
    }


    //TODO Wie kann geprüft werden, ob es im Set bereits Elemente gibt?
    private HerstellerStatus checkTablehersteller(Hersteller herstellername){
        if(herstellerset.contains(herstellername)) {
            this.notifyObservers();
            return HerstellerStatus.Hersteller_bekannt;
        }
        this.notifyObservers();
        return HerstellerStatus.Hersteller_unbekannt;
    }


    @Override
    public boolean insertAllergen(Set<Allergene> allergene) {
        for(Allergene allergen: allergene){
            if(checkTableAllergene(allergen)== AllergenStatus.Allergen_unbekannt){
                allergenset.add(allergen);
                this.setChanged();
                this.notifyObservers();
                return true;
            }
        }
        this.notifyObservers();
        return false;
    }

    private AllergenStatus checkTableAllergene(Allergene allergene) {
        if(allergenset.contains(allergene)){
            this.notifyObservers();
            return AllergenStatus.Allergen_bekannt;
        }
        this.notifyObservers();
        return AllergenStatus.Allergen_unbekannt;
    }

    @Override
    public Set<Hersteller> readHersteller() {
        this.notifyObservers();
        return this.herstellerset;
    }


    @Override
    public List<Kuchen> readKuchen() {
        this.notifyObservers();
        return this.kuchenliste;
    }


    @Override
    public Set<Allergene> readAllergener() {
        this.notifyObservers();
        return this.allergenset;
    }


    public int getHerstellerSetSize (){
        this.notifyObservers();
        return this.herstellerset.size();
    }


    public int getKuchenlisteSize (){
        this.notifyObservers();
        return this.kuchenliste.size();
    }

    public int getKuchenlisteSize2 (){
        this.notifyObservers();
        return this.kuchenliste2.size();
    }

    public int getAllergenenSetSize (){
        this.notifyObservers();
        return this.allergenset.size();
    }



    //TODO überarbeiten?
    public List<Kuchen> readKuchen(List<Kuchen> kuchen, String typ){
        List<Kuchen> gefiltertekuchenliste = new ArrayList<>();
        if(typ.length()!=0) {
            for (Kuchen kuchenelement : kuchen) {
                if (kuchenelement.getTyp().equals(typ)) {
                    gefiltertekuchenliste.add(kuchenelement);
                }
            }
        }
        this.notifyObservers();
        return gefiltertekuchenliste;
   }

    @Override

    public boolean editKuchen(int fachnummer) {
        for (Kuchen kuchen: this.kuchenliste) {
            if (kuchen.getFachnummer() == fachnummer) {
                kuchen.setInspektionsdatum(getInspektion());
                this.setChanged();
                this.notifyObservers();
                return true;
            }
        }
        this.notifyObservers();
        return false;
    }


    @Override
    public boolean deleteKuchen(int fachnummer){
        if(kuchenliste.isEmpty()){
            this.notifyObservers();
            return false;
        }
        for(Kuchen kuchen: this.kuchenliste) {
            if(kuchen.getFachnummer() == fachnummer){
                kuchenliste.remove(kuchen);
                automat.boxdecrement();
                this.setChanged();
                this.notifyObservers();
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean deleteHersteller(String herstellername){
        if(herstellerset.isEmpty()){
            this.notifyObservers();
            return false;
        }
        for(Hersteller hersteller: herstellerset) {
            if(hersteller.getName().equals(herstellername)){
                herstellerset.remove(hersteller);
                this.setChanged();
                this.notifyObservers();
                return true;
            }
        }
        return false;
    }


    public Date getInspektion() {
        return getDatum();
    }


    protected boolean isNotFull(){
        return automat.getAnzahlbelegteFaecher()<automat.getKapazity();
    }

    public void setKapazitaet(int newKapazitaet){
        this.automat.setKapazitaet(newKapazitaet);
    }

    //Quelle: https://www.delftstack.com/de/howto/java/how-to-get-the-current-date-time-in-java/
    protected Date getDatum() {
        return new Date();
    }

    public Duration convertHaltbarkeit(int haltbarkeit){
        return Duration.ofDays(haltbarkeit);
    }

    //TODO - eventuell überarebiten
    protected int getCurrentFachnummerAndIncrement(){
        currentFachnummer++;
        return currentFachnummer;
    }
}