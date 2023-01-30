package simulationstest;

import geschaeftslogik.Hersteller;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import simulation2.DeleteSimulation2;
import simulation2.InsertSimulation2;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

public class Simulation2Test {

    @Test
    @DisplayName("Ein nicht vorhandener Kuchen wird gelöscht.")
    public void delete(){
        int kuchenID = 1;
        final Object monitor=new Object();
        Verwaltung model = new Verwaltung();
        DeleteSimulation2 sim2 = new DeleteSimulation2(model, monitor);
        boolean isDeleted =  sim2.deleteKuchen(kuchenID);
        Assertions.assertFalse(isDeleted);
    }


    @Test
    @DisplayName("Befüllte Kuchenliste wird ausgelesen.")
    public void delete2() throws InterruptedException {
        final Object monitor=new Object();
        Verwaltung model = new Verwaltung();
        InsertSimulation2 sim = new InsertSimulation2(model, monitor);
        sim.insertForInspection();
        List<Kuchen> kuchen = model.readKuchen();
        Assertions.assertNotNull(kuchen);
    }

    @Test
    @DisplayName("Ein nicht vorhandener Kuchen wird nicht inspiziert.")
    public void inspizieren() throws ParseException, InterruptedException {
        final Object monitor=new Object();
        Verwaltung model = new Verwaltung();
        DeleteSimulation2 sim2 = new DeleteSimulation2(model, monitor);
        boolean isChecked = sim2.editInspektiondate();
        Assertions.assertFalse(isChecked);
    }

    @Test
    @DisplayName("Vorhandene Kuchen werden inspiziert und gelöscht.")
    public void inspizieren2() throws ParseException, InterruptedException {
        Verwaltung model = new Verwaltung();
        final Object monitor=new Object();
        InsertSimulation2 sim = new InsertSimulation2(model, monitor);
        DeleteSimulation2 sim2 = new DeleteSimulation2(model, monitor);
        sim.insertForInspection();
        boolean isChecked = sim2.editInspektiondate();
        Assertions.assertTrue(isChecked);
    }


    @Test
    @DisplayName("Befülltes Herstellerset wird ausgelesen.")
    public void inspizieren3() throws InterruptedException {
        Verwaltung model = new Verwaltung();
        final Object monitor=new Object();
        InsertSimulation2 sim = new InsertSimulation2(model, monitor);
        sim.insertForInspection();
        Set< Hersteller> hersteller = model.readHersteller();
        Assertions.assertNotNull(hersteller);
    }
}
