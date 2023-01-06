package geschaeftslogik;

import geschaeftslogik.verkaufsobjekt.Kremkuchen;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import geschaeftslogik.verkaufsobjekt.Obstkuchen;
import geschaeftslogik.verkaufsobjekt.Obsttorte;
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
    Set<Hersteller> herstellerset = new HashSet<>();
    Set<Allergene> allergenset = new HashSet<>();


    //Prototypsischer Entwurf des Systems
    //Fehlerquellen von der eigentlichen Methode trennen, um Seiteneffekte zu vermeiden
    //mehrfache Zuständigkeit umgehen, durch trennen des Einfügens in die Liste von
    //der Kuchendefinition
    @Override
    public boolean insert(Kuchentyp typ, Hersteller hersteller,
                          Duration haltbarkeit, double preis,
                          int naehrwert , Allergene allergene,
                          String sorte ) {
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

        if(checkTablehersteller(hersteller) == Status.Hersteller_bekannt &&
                isNotFull()){
            kuchen.setFachnummer(getCurrentFachnummerAndIncrement());
            kuchen.setEinfuegedatum(getDatum());
            kuchenliste.add(kuchen);
            insertA(allergene);
            //automat.setAnzahlbelegteFaecher(1);
            automat.boxincrement();
            return true;
        }
        return false;
    }




    @Override
    public boolean insertH(Hersteller neuerHersteller) {
        if(checkTablehersteller(neuerHersteller)==Status.Keine_Fehler_gefunden){
            herstellerset.add(neuerHersteller);
            return true;
        }
        return false;
    }

    private Status checkTablehersteller(Hersteller herstellername){
        if(herstellerset.contains(herstellername)) {
            return Status.Hersteller_bekannt;
        }
        return Status.Keine_Fehler_gefunden;
    }

    @Override
    public boolean insertA(Allergene allergene) {
        if(checkTableAllergene(allergene )== AStatus.Allergen_unbekannt){
            allergenset.add(allergene);
            return true;
        }
        return false;
    }

    private AStatus checkTableAllergene(Allergene allergene) {
        if(allergenset.contains(allergene)) {
            return AStatus.Allergen_bekannt;
        }
        return AStatus.Allergen_unbekannt;
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
    public boolean edit(int fachnummer) {
        for (Kuchen kuchen: this.kuchenliste) {
            if (kuchen.getFachnummer() == fachnummer) {
                kuchen.setInspektionsdatum(getInspektion());
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean delete(int fachnummer){
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

    public int getKuchenlistsize(){
        return this.kuchenliste.size();
    }

    public int getAllergenlistsize(){
        return this.allergenset.size();
    }

    public int getHerstellerlistsize(){
        return this.herstellerset.size();
    }

    private Date getInspektion() {
        return getDatum();
    }

    public boolean isNotFull(){
        return automat.getAnzahlbelegteFaecher()<automat.getKapazity();
    }

    //Quelle: https://www.delftstack.com/de/howto/java/how-to-get-the-current-date-time-in-java/
    public Date getDatum() {
        return new Date();
    }

    public int getCurrentFachnummerAndIncrement(){
        currentFachnummer++;
        return currentFachnummer;
    }
}
