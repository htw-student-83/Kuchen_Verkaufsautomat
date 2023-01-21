package simulation1;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.*;
import vertrag.Allergene;
import java.time.Duration;

public class InsertSimulation1 extends Thread implements Runnable{

    private Verwaltung model;
    private final Object monitor;

    public InsertSimulation1(Verwaltung kl, Object monitor){
        this.model = kl;
        this.monitor = monitor;
    }

    //Instanzen von Hersteller erzeugen
    Hersteller hersteller1 = new Hersteller("Hersteller1");
    Hersteller hersteller2 = new Hersteller("Hersteller2");
    Hersteller hersteller3 = new Hersteller("Hersteller3");


    @Override
    public void run() {
        insertKuchenSimulation();
    }

    public void insertKuchenSimulation(){
        synchronized (this.monitor){
            //eine Zufallszahl zw. 1 und 3 ermitteln
            int zufallszahl = creationRandomNumber();
            //Auszwahl und einfügen eines Kuchens
            switch (zufallszahl){
                case 1:
                    this.model.insertHersteller(hersteller1);
                    this.model.insertKuchen(Kuchentyp.Obstkuchen, hersteller1, 5.44, 256,
                            Duration.ofDays(34), Allergene.Gluten, "Orange");

                    System.out.println("Thread1 hat einen Kremkuchen eingefügt.");
                    break;
                case 2:
                    this.model.insertHersteller(hersteller2);
                    this.model.insertKuchen(Kuchentyp.Kremkuchen, hersteller2, 3.44, 230,
                            Duration.ofDays(24), Allergene.Gluten, "Zitrone");
                    System.out.println("Thread1 hat einen Obstkuchen eingefügt.");
                    break;
                case 3:
                    this.model.insertHersteller(hersteller3);
                    this.model.insertKuchen(Kuchentyp.Obsttorte, hersteller3, 1.44, 330,
                            Duration.ofDays(44), Allergene.Gluten, "Bananentorte");
                    System.out.println("Thread1 hat eine Obsttorte eingefügt.");
                    break;
            }
        }
    }



    //Quelle: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Math/random?retiredLocale=de
    public int creationRandomNumber() {
        int minRandomNumber = 1;
        int maxRandomNumber = 3;
        int randomNumber = (int) Math.floor(Math.random()*(maxRandomNumber-minRandomNumber+1)+minRandomNumber);
        return randomNumber;
    }
}
