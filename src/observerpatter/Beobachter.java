package observerpatter;

import geschaeftslogik.verkaufsobjekt.Verwaltung;
import java.util.Observable;
import java.util.Observer;

public class Beobachter implements Observer {

    private Verwaltung model;
    private int bekannteListengroesseKuchen;
    private int bekannteSetgroesseHersteller;
    private int bekannteSetgroesseAllergene;
    private int kapazity;

    public Beobachter(Verwaltung model) {
        this.model = model;
  //      this.bekannteListengroesseKuchen = this.model.getKuchenlisteSize2();
    //    this.bekannteSetgroesseHersteller = this.model.getHerstellerSetSize();
        this.bekannteSetgroesseAllergene = this.model.getAllergenenSetSize();
        this.kapazity = this.model.getKapazity();
    }

    @Override
    public void update(Observable o, Object arg) {

        if(bekannteListengroesseKuchen != this.model.getKuchenlisteSize2()){
            bekannteListengroesseKuchen = this.model.getKuchenlisteSize2();
            //System.out.println("In der Kuchenliste hat sich etwas verändert.");
        }
/*s
        if(bekannteSetgroesseHersteller != this.model.getHerstellerSetSize()){
            bekannteSetgroesseHersteller = this.model.getHerstellerSetSize();
            System.out.println("Im Herstellerset hat sich etwas verändert.");
        }
 */

        if(bekannteSetgroesseAllergene != this.model.getAllergenenSetSize()){
            bekannteSetgroesseAllergene = this.model.getAllergenenSetSize();
            System.out.println("Im Allergenenset hat sich etwas verändert.");
        }


        // - Prozentualer Anteil der Kuchen in der Liste ermitteln
        if(this.bekannteListengroesseKuchen >= (this.model.getKapazity()*0.9)){
            System.out.println("Jetzt sind mehr als 90% der Kapazität erreicht.");
        }
    }
}
