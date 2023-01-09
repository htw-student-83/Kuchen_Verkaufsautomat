package simulationstest;

import geschaeftslogik.Hersteller;
import geschaeftslogik.verkaufsobjekt.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import simulation1.InsertSimulation1;
import vertrag.Allergene;

import java.math.BigDecimal;

public class Simulation1 {

    @Test
    @DisplayName("Ein Kremkuchen wird eingefügt.")
    public void insert1(){
        // Instanz von Hersteller erzeugt
        Hersteller hersteller1 = new Hersteller("Hersteller1");
        //Instanzen von Kuchen erzeugen
        Kuchen kremkuchen = new Kremkuchen("Banane",300,
                hersteller1, Allergene.Gluten, BigDecimal.valueOf(3.4));//1

        Verwaltung model = new Verwaltung();
        InsertSimulation1 sim1 = new InsertSimulation1(model);
        sim1.insertH(hersteller1);
        boolean isadded = sim1.insert(kremkuchen, hersteller1);
        Assertions.assertEquals(true, isadded);
    }

    // ein Kremkuchen wird eingefügt.
    @Test
    public void insert2(){
        // Instanz von Hersteller erzeugt
        Hersteller hersteller1 = new Hersteller("Hersteller1");
        //Instanzen von Kuchen erzeugen
        Kuchen obstkuchen = new Obstkuchen("Orange",240, hersteller1,
                Allergene.Gluten, BigDecimal.valueOf(13.4));//2

        Verwaltung model = new Verwaltung();
        InsertSimulation1 sim1 = new InsertSimulation1(model);
        sim1.insertH(hersteller1);
        boolean isadded = sim1.insertSimulation(obstkuchen, hersteller1);
        Assertions.assertEquals(true, isadded);
    }

    // eine Obsttorte wird eingefügt.
    @Test
    public void insert3() {
        // Instanz von Hersteller erzeugt
        Hersteller hersteller1 = new Hersteller("Hersteller1");

        //Instanzen von Kuchen erzeugen
        Kuchen obsttorte = new Obsttorte("Zitrone",120,
                hersteller1, Allergene.Gluten, BigDecimal.valueOf(23.4));//3

        Verwaltung model = new Verwaltung();
        InsertSimulation1 sim1 = new InsertSimulation1(model);
        sim1.insertH(hersteller1);
        boolean isadded = sim1.insert(obsttorte, hersteller1);
        Assertions.assertEquals(true, isadded);

    }


    // ein Kremkuchen wird eingefügt bei unbekannten Hersteller.
    @Test
    public void insert4(){
        // Instanzen von Hersteller werden erzeugt
        Hersteller hersteller1 = new Hersteller("Hersteller1");
        Hersteller hersteller2 = new Hersteller("Hersteller2");

        //Instanzen von Kuchen erzeugen
        Kuchen kremkuchen = new Kremkuchen("Banane", 150, hersteller1,
                Allergene.Gluten, BigDecimal.valueOf(3.4));//1

        Verwaltung model = new Verwaltung();
        InsertSimulation1 sim1 = new InsertSimulation1(model);
        sim1.insertH(hersteller1);
        boolean isadded = sim1.insertSimulation(kremkuchen, hersteller2);
        Assertions.assertEquals(false, isadded);
    }

    // ein Kremkuchen wird gelöscht
    @Test
    public void insert5(){
        int fachnumer = 1;

        // Instanzen von Hersteller werden erzeugt
        Hersteller hersteller1 = new Hersteller("Hersteller1");

        //Instanzen von Kuchen erzeugen
        Kuchen kremkuchen = new Kremkuchen("Banane",145, hersteller1,
                Allergene.Gluten, BigDecimal.valueOf(3.4));//1

        Verwaltung model = new Verwaltung();
        InsertSimulation1 sim1 = new InsertSimulation1(model);
        sim1.insertH(hersteller1);
        sim1.insert(kremkuchen, hersteller1);
        boolean isremoved = sim1.delete(fachnumer);
        Assertions.assertEquals(true, isremoved);

    }
}
