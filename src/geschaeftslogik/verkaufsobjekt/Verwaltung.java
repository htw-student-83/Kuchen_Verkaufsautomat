package geschaeftslogik.verkaufsobjekt;

import geschaeftslogik.*;
import vertrag.Allergene;
import vertrag.KuchenlistManagement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class Verwaltung extends Observable implements KuchenlistManagement,
        Serializable {
    int anzahlKuchen = 3;
    Kuchenautomat automat = new Kuchenautomat();
    private int currentFachnummer = 0;
    List<Kuchen> kuchenliste = new ArrayList<>();
    Set<Hersteller> herstellerset = new HashSet<>();
    Set<Allergene> allergenset = new HashSet<>();

    @Override
    public boolean insertKuchen(Kuchentyp typ, Hersteller hersteller,
                          double preis, int naehrwert, Duration haltbarkeit,
                          Allergene allergene, String sorte ) {
        Kuchen kuchen = null;
        switch (typ){
            case Kremkuchen:
                kuchen = new Kremkuchen(sorte, naehrwert, hersteller,
                        haltbarkeit, allergene, BigDecimal.valueOf(preis));
                break;
            case Obstkuchen:
                kuchen = new Obstkuchen(sorte, naehrwert, hersteller,
                        haltbarkeit, allergene, BigDecimal.valueOf(preis));
                break;
            case Obsttorte:
                kuchen = new Obsttorte(sorte, naehrwert, hersteller,
                        haltbarkeit, allergene, BigDecimal.valueOf(preis));
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
            return true;
        }
        return false;
    }



    // - Besserer Methodennamen: insertHersteller()
    @Override
    public boolean insertHersteller(Hersteller neuerHersteller) {
        if(checkTablehersteller(neuerHersteller)== HerstellerStatus.Hersteller_unbekannt){
            herstellerset.add(neuerHersteller);
            return true;
        }
        return false;
    }


    //TODO Wie kann geprüft werden, ob es im Set bereits Elemente gibt?
    private HerstellerStatus checkTablehersteller(Hersteller herstellername){
        if(herstellerset.contains(herstellername)) {
            return HerstellerStatus.Hersteller_bekannt;
        }
        return HerstellerStatus.Hersteller_unbekannt;
    }

    // - insertAllergen()
    @Override
    public boolean insertAllergen(Allergene allergene) {
        if(checkTableAllergene(allergene )== AllergenStatus.Allergen_unbekannt){
            allergenset.add(allergene);
            return true;
        }
        return false;
    }

    private AllergenStatus checkTableAllergene(Allergene allergene) {
        if(allergenset.contains(allergene)) {
            return AllergenStatus.Allergen_bekannt;
        }
        return AllergenStatus.Allergen_unbekannt;
    }

    @Override
    public Set<Hersteller> readHersteller() { return this.herstellerset; }
    @Override
    public List<Kuchen> readKuchen() {
        return this.kuchenliste;
    }
    @Override
    public Set<Allergene> readAllergener() {
        return this.allergenset;
    }

    public int getHerstellersetSize (){
        return this.herstellerset.size();
    }

    public int getKuchenlisteSize (){
        return this.kuchenliste.size();
    }

    public int getAllergenenListeSize (){
        return this.allergenset.size();
    }

    //TODO überarbeiten!
    @Override
    public List<Kuchen> readKuchen(Kuchentyp typ){
        switch (typ){
            case Kremkuchen:
                Kremkuchen.class.getName();
                break;
            case Obstkuchen:
                Obstkuchen.class.getName();
                break;
            case Obsttorte:
                Obsttorte.class.getName();
                break;
        }
        return kuchenliste;
    }

    @Override
    public boolean editKuchen(int fachnummer) {
        for (Kuchen kuchen: this.kuchenliste) {
            if (kuchen.getFachnummer() == fachnummer) {
                kuchen.setInspektionsdatum(getInspektion());
                return true;
            }
        }
        return false;
    }


    // - deleteKuchen()
    @Override
    public boolean deleteKuchen(int fachnummer){
        if(kuchenliste.isEmpty()){
            return false;
        }
        for(Kuchen kuchen: this.kuchenliste) {
            if(kuchen.getFachnummer() == fachnummer){
                kuchenliste.remove(kuchen);
                automat.boxdecrement();
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean deleteHersteller(String herstellername){
        if(herstellerset.isEmpty()){
            return false;
        }
        for(Hersteller hersteller: herstellerset) {
            if(hersteller.getName().equals(herstellername)){
                herstellerset.remove(hersteller);
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

    //Quelle: https://www.delftstack.com/de/howto/java/how-to-get-the-current-date-time-in-java/
    protected Date getDatum() {
        return new Date();
    }

    protected Duration convertHaltbarkeit(int haltbarkeit){
        return Duration.ofDays(haltbarkeit);
    }

    //TODO - eventuell überarebiten
    protected int getCurrentFachnummerAndIncrement(){
        currentFachnummer++;
        return currentFachnummer;
    }
}
