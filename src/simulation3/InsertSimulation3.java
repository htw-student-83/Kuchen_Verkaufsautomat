package simulation3;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import vertrag.Allergene;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class InsertSimulation3 implements Runnable{

    private Verwaltung model;
    private final Object monitor;

    public InsertSimulation3(Verwaltung kl, Object monitor){
        this.model = kl;
        this.monitor = monitor;
    }


    // Instanzen von Hersteller erzeugen
    Hersteller hersteller1 = new Hersteller("Hersteller1");
    Hersteller hersteller2 = new Hersteller("Hersteller2");
    Hersteller hersteller3 = new Hersteller("Hersteller3");
    Hersteller hersteller4 = new Hersteller("Hersteller4");


    @Override
    public void run() {
        try {
            insertForInspection();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertForInspection() throws InterruptedException{
        synchronized (this.monitor){//Beginn des kritischen Bereichs
            Set<Allergene> allergeneSet = null;
            this.model.insertHersteller(hersteller1);
            this.model.insertHersteller(hersteller2);
            this.model.insertHersteller(hersteller3);
            this.model.insertHersteller(hersteller4);
            allergeneSet = new HashSet<>();
            this.model.insertKuchen(Kuchentyp.Obstkuchen, hersteller1, 3.44, 230,
                    Duration.ofDays(34), allergeneSet, "Banane","");
            allergeneSet.add(Allergene.Gluten);
            allergeneSet.add(Allergene.Erdnuss);
            this.model.insertKuchen(Kuchentyp.Kremkuchen, hersteller2, 2.56, 120,
                    Duration.ofDays(22), allergeneSet, "","Sahne");
            allergeneSet.add(Allergene.Gluten);
            this.model.insertKuchen(Kuchentyp.Obsttorte, hersteller3, 1.34, 340,
                    Duration.ofDays(30), allergeneSet, "Apfel","Sahne");
        }
    }
}
