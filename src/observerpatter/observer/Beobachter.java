package observerpatter.observer;

import geschaeftslogik.Kuchenautomat;
import geschaeftslogik.verkaufsobjekt.Verwaltung;

import java.util.Observable;
import java.util.Observer;

public class Beobachter implements Observer {
    Kuchenautomat automat = new Kuchenautomat();
    private Verwaltung model;

    public Beobachter(Verwaltung model) {
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        //int aktuellerZustandlistekuchen = this.model.getKuchenlistsize();

        // - Prozentualer Anteil der Kuchen in der Liste ermitteln
        //if(aktuellerZustandlistekuchen==automat.getKapazity()){
            System.out.println("Jetzt sind mehr als 90% der Kapazitaet erreicht.");
        //}
    }
}
