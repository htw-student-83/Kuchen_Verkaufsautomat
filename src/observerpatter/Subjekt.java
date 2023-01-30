package observerpatter;
/*
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import vertrag.Beobachter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Subjekt implements vertrag.Subjekt {

    private Verwaltung verwaltung;
    public List<Beobachter> beobachterList = new LinkedList<>();
    private int zustandKuchenliste;
    private int zustandHerstellerset;
    private int zustandAllergenset;
    private Date inspektionsdatum;

    public Subjekt(Verwaltung verwaltung){
        this.verwaltung = verwaltung;
        this.zustandKuchenliste = verwaltung.getKuchenlisteSize();
        this.zustandHerstellerset = verwaltung.getHerstellerSetSize();
        this.zustandAllergenset = verwaltung.getAllergenenSetSize();
        this.inspektionsdatum = verwaltung.getInspektion();
    }

    @Override
    public void meldeAn(Beobachter beobachter){
        beobachterList.add(beobachter);
    }

    @Override
    public void meldeAb(Beobachter beobachter){
        beobachterList.remove(beobachter);
    }

    @Override
    public void setZustandKuchenliste(int neuerKuchen) {
        this.zustandKuchenliste = neuerKuchen;
        benachrichtige();
    }

    @Override
    public void setZustandHerstellerliste(int neuerHersteller) {
        this.zustandHerstellerset = neuerHersteller;
        benachrichtige();
    }

    @Override
    public int gibZustandKuchenliste() {
        return this.zustandKuchenliste;
    }

    @Override
    public int gibZustandHerstellerSet() {
        return this.zustandHerstellerset;
    }

    @Override
    public int gibZustandAllergenenSet() {
        return this.zustandAllergenset;
    }

    @Override
    public Date gibZustandInpektionsdatum() {
        return this.inspektionsdatum;
    }

    @Override
    public void benachrichtige() {
        for(Beobachter beobachter: beobachterList){
            beobachter.aktualisiere();
        }
    }
}

 */
