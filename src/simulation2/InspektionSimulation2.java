package simulation2;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import vertrag.Allergene;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class InspektionSimulation2 implements Runnable {

    private Verwaltung model;
    private final Object monitor;

    public InspektionSimulation2(Verwaltung kl, Object monitor){
        this.model = kl;
        this.monitor = monitor;
    }

    //Instanzen von Hersteller erzeugen
    Hersteller hersteller1 = new Hersteller("Hersteller1");
    Hersteller hersteller2 = new Hersteller("Hersteller2");
    Hersteller hersteller3 = new Hersteller("Hersteller3");


    @Override
    public void run() {
        synchronized (this.monitor){
            Set<Allergene> allergeneSet = null;
            // Kuchen- und Herstellerliste werden befüllt
            this.model.insertHersteller(hersteller1);
            this.model.insertHersteller(hersteller2);
            this.model.insertHersteller(hersteller3);
            allergeneSet = new HashSet<>();
            this.model.insertKuchen(Kuchentyp.Obstkuchen, hersteller1, 3.44, 230,
                    Duration.ofDays(34), allergeneSet, "Banane","");
            allergeneSet.add(Allergene.Gluten);
            allergeneSet.add(Allergene.Erdnuss);
            this.model.insertKuchen(Kuchentyp.Kremkuchen, hersteller2, 5.23, 122,
                    Duration.ofDays(34),allergeneSet, "","Butter");
            allergeneSet.add(Allergene.Gluten);
            this.model.insertKuchen(Kuchentyp.Obsttorte, hersteller3, 2.44, 344,
                    Duration.ofDays(34), allergeneSet, "Apfel", "Sahne");

            //eine Zufallszahl zw. 1 und 3 ermitteln
            //Auszwahl eines Kuchens
            int fachnummer = creationRandomNumber();
            boolean dateIsChanged = this.model.editKuchen(fachnummer);
            if(dateIsChanged){
                System.out.println("Thread3 hat den Kuchen mit der Fachnummer " +
                        fachnummer + " inspiziert.");
            }
        }
    }

    //Quelle: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Math/random?retiredLocale=de
    public int creationRandomNumber() {
        int minRandomNumber = 1;
        int maxRandomNumber = 3;
        return (int) Math.floor(Math.random()*(maxRandomNumber-minRandomNumber+1)+
                minRandomNumber);
    }
}
