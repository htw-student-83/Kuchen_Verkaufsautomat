package simulation2;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.*;
import vertrag.Allergene;
import java.time.Duration;

public class InsertSimulation2 extends Thread implements Runnable {
    private Verwaltung model;
    private final Object monitor;

    public InsertSimulation2(Verwaltung kl, Object monitor){
        this.model = kl;
        this.monitor = monitor;
    }


    // Instanzen von Hersteller erzeugen
    Hersteller hersteller1 = new Hersteller("Hersteller1");
    Hersteller hersteller2 = new Hersteller("Hersteller2");
    Hersteller hersteller3 = new Hersteller("Hersteller3");

    @Override
    public void run() {
        try {
            insertForInspection();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertForInspection() throws InterruptedException{
        synchronized(this.monitor){
            this.model.insertHersteller(hersteller1);
            this.model.insertHersteller(hersteller2);
            this.model.insertHersteller(hersteller3);
            this.model.insertKuchen(Kuchentyp.Obstkuchen, hersteller1, 3.44, 230,
                    Duration.ofDays(34), Allergene.Gluten, "Banane");
            this.model.insertKuchen(Kuchentyp.Kremkuchen, hersteller2, 5.23, 122,
                    Duration.ofDays(34), Allergene.Haselnuss, "Nuss-Creme");
            this.model.insertKuchen(Kuchentyp.Obsttorte, hersteller3, 2.44, 344,
                    Duration.ofDays(34), Allergene.Sesamsamen, "Tortencreme");
        }
    }
}
