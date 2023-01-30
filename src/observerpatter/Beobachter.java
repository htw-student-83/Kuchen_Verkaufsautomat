package observerpatter;

import geschaeftslogik.verkaufsobjekt.Kuchenautomat;
import geschaeftslogik.verkaufsobjekt.Verwaltung;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class Beobachter implements Observer {

    private Verwaltung model;
    private Kuchenautomat automat;

    private int bekannteListengroesseKuchen;
    private int bekannteSetgroesseHersteller;
    private int bekannteSetgroesseAllergene;
    private Date bekannteInspektion;

    public Beobachter(Verwaltung model, Kuchenautomat automat) {
        this.model = model;
        this.automat = automat;
        bekannteListengroesseKuchen = this.model.getKuchenlisteSize();
        bekannteSetgroesseHersteller = this.model.getHerstellerSetSize();
        bekannteSetgroesseAllergene = this.model.getAllergenenSetSize();
        bekannteInspektion = this.model.getInspektion();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(bekannteListengroesseKuchen != this.model.getKuchenlisteSize()){
            System.out.println("In der Kuchenliste hat sich etwas verändert.");
        }

        if(bekannteSetgroesseHersteller != this.model.getHerstellerSetSize()){
            System.out.println("Im Herstellerset hat sich etwas verändert.");
        }

        if(bekannteSetgroesseAllergene != this.model.getAllergenenSetSize()){
            System.out.println("Im Allergenenset hat sich etwas verändert.");
        }

        if(bekannteInspektion != this.model.getInspektion()){
            System.out.println("Die Inspektion hat sich verändert.");
        }

        // - Prozentualer Anteil der Kuchen in der Liste ermitteln
        if(bekannteListengroesseKuchen==(this.automat.getKapazity()*0.9)){
            System.out.println("Jetzt sind 90% der Kapazität erreicht.");
        }
    }
}
