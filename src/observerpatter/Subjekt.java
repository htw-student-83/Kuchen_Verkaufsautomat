package observerpatter;

import geschaeftslogik.verkaufsobjekt.Verwaltung;
import vertrag.Beobachter;
import vertrag.ISubjekt;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Subjekt implements ISubjekt {

    private Verwaltung verwaltung;
    public List<Beobachter> beobachterList = new LinkedList<>();
    private int zustandKuchenliste;
    private int zustandHerstellerset;
    private int zustandAllergenset;
    private Date inspektionsdatum;

    public Subjekt(Verwaltung verwaltung){
        this.verwaltung = verwaltung;
        this.zustandKuchenliste = verwaltung.getKuchenlisteSize2();
        this.zustandHerstellerset = verwaltung.getHerstellerSetSize();
        this.zustandAllergenset = verwaltung.getAllergenenSetSize();
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
    public Date gibInspektion() {
        return this.inspektionsdatum;
    }

    @Override
    public int gibZustandKuchenliste() {
        return this.zustandKuchenliste;
    }

    @Override
    public int gibZustandHerstellerliste() {
        return this.zustandHerstellerset;
    }

    @Override
    public void benachrichtige() {
        for(Beobachter beobachter: beobachterList){
            beobachter.aktualisiere();
        }
    }

}
