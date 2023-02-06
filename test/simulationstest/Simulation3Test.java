package simulationstest;

import geschaeftslogik.Hersteller;
import geschaeftslogik.verkaufsobjekt.DekoKuchen;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import simulation3.DeleteSimulation3;
import simulation3.InsertSimulation3;
import vertrag.Allergene;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Simulation3Test {
    @Test
    @DisplayName("Befüllte Kuchenliste wird ausgelesen.")
    public void insert() throws InterruptedException {
        final Object monitor=new Object();
        Verwaltung model = new Verwaltung(3);
        InsertSimulation3 sim = new InsertSimulation3(model, monitor);
        sim.insertForInspection();
        List<DekoKuchen> kuchen = model.readKuchen();
        Assertions.assertNotNull(kuchen);
    }

    @Test
    @DisplayName("Befülltes Herstellerset wird ausgelesen.")
    public void insert2() throws InterruptedException {
        final Object monitor=new Object();
        Verwaltung model = new Verwaltung(3);
        InsertSimulation3 sim = new InsertSimulation3(model, monitor);
        sim.insertForInspection();
        Set<Hersteller> hersteller = model.readHersteller();
        Assertions.assertNotNull(hersteller);
    }


    @Test
    @DisplayName("Befülltes Allergenenset wird ausgelesen.")
    public void insert3() throws InterruptedException {
        final Object monitor=new Object();
        Verwaltung model = new Verwaltung(3);
        InsertSimulation3 sim = new InsertSimulation3(model, monitor);
        sim.insertForInspection();
        Set<Allergene> allergene = model.readAllergener();
        Assertions.assertNotNull(allergene);
    }


    @Test
    @DisplayName("Eingefügter Kuchen wird inspiziert.")
    public void edit() throws InterruptedException, ParseException {
        final Object monitor=new Object();
        Verwaltung model = new Verwaltung(3);
        InsertSimulation3 sim = new InsertSimulation3(model, monitor);
        DeleteSimulation3 sim2 = new DeleteSimulation3(model, monitor);
        sim.insertForInspection();
        sim2.setInspektionsdatum();
        Date datum = model.getInspektion();
        Assertions.assertNotNull(datum);
    }


    @Test
    @DisplayName("Aeltester Kuchen wird aus leerer Kuchenliste ermittelt.")
    public void edit2() {
        final Object monitor=new Object();
        Verwaltung model = new Verwaltung(3);
        DeleteSimulation3 sim2 = new DeleteSimulation3(model, monitor);
        int fachnummer = sim2.fachnummerAeltesterKuchen();
        Assertions.assertEquals(0,fachnummer);
    }

    @Test
    @DisplayName("Aeltester Kuchen wird ermittelt.")
    public void edit3() throws InterruptedException, ParseException {
        final Object monitor=new Object();
        Verwaltung model = new Verwaltung(3);
        InsertSimulation3 sim = new InsertSimulation3(model, monitor);
        DeleteSimulation3 sim2 = new DeleteSimulation3(model, monitor);
        sim.insertForInspection();
        sim2.setInspektionsdatum();
        int fachnummer = sim2.fachnummerAeltesterKuchen();
        Assertions.assertTrue(fachnummer>0);
    }


    @Test
    @DisplayName("Inspektionsdatum gesetzt.")
    public void edit4() throws InterruptedException, ParseException {
        final Object monitor=new Object();
        Verwaltung model = new Verwaltung(3);
        InsertSimulation3 sim = new InsertSimulation3(model, monitor);
        DeleteSimulation3 sim2 = new DeleteSimulation3(model, monitor);
        sim.insertForInspection();
        Assertions.assertEquals(3, model.getKuchenlisteSize());
        sim2.setInspektionsdatum();
        Assertions.assertNotNull(model.getInspektion());
    }


    @Test
    @DisplayName("Kuchen aus leerer Kuchenliste wird inspiziert.")
    public void edit5() throws InterruptedException, ParseException {
        final Object monitor=new Object();
        Verwaltung model = new Verwaltung(3);
        DeleteSimulation3 sim1 = new DeleteSimulation3(model, monitor);
        sim1.setInspektionsdatum();
        Assertions.assertNull(sim1.getInsertDate());
    }
}
