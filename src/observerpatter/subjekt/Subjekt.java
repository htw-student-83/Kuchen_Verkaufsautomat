package observerpatter.subjekt;

import geschaeftslogik.Verwaltung;
import vertrag.Beobachter;
import vertrag.ISubjekt;

import java.util.LinkedList;
import java.util.List;

public class Subjekt implements ISubjekt {

    private Verwaltung verwaltung;
    public List<Beobachter> beobachterList = new LinkedList<>();
    private int zustandKuchenliste;
    private int zustandHerstellerliste;

    public Subjekt(Verwaltung verwaltung){
        this.verwaltung = verwaltung;
        this.zustandKuchenliste = verwaltung.getKuchenlistsize();
        this.zustandHerstellerliste = verwaltung.getHerstellerlistsize();
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
        this.zustandHerstellerliste = neuerHersteller;
        benachrichtige();
    }

    @Override
    public int gibZustandKuchenliste() {
        return this.zustandKuchenliste;
    }

    @Override
    public int gibZustandHerstellerliste() {
        return this.zustandHerstellerliste;
    }

    @Override
    public void benachrichtige() {
        for(Beobachter beobachter: beobachterList){
            beobachter.aktualisiere();
        }
    }
}
