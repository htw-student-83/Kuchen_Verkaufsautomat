package simulationstest;

import geschaeftslogik.verkaufsobjekt.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import simulation2.DeleteSimulation2;
import simulation2.InsertSimulation2;
import java.text.ParseException;

public class Simulation2 {

    @Test
    @DisplayName("Ein nicht vorhandener Kuchen wird gelöscht.")
    public void delete(){
        final Object monitor=new Object();
        Verwaltung model = new Verwaltung();
        DeleteSimulation2 sim2 = new DeleteSimulation2(model, monitor);
        sim2.aeltestesInspektionsdatum();
    }

    @Test
    @DisplayName("Ein nicht vorhandener Kuchen wird inspiziert.")
    public void inspizieren() throws ParseException, InterruptedException {
        final Object monitor=new Object();
        Verwaltung model = new Verwaltung();
        DeleteSimulation2 sim2 = new DeleteSimulation2(model, monitor);
        sim2.editInspektiondate();
    }

    @Test
    @DisplayName("Vorhandene Kuchen werden inspiziert und gelöscht.")
    public void inspizieren2() throws ParseException, InterruptedException {
        Verwaltung model = new Verwaltung();
        final Object monitor=new Object();

        InsertSimulation2 sim2 = new InsertSimulation2(model, monitor);
        DeleteSimulation2 sim22 = new DeleteSimulation2(model, monitor);
        sim2.insertForInspection();
        sim22.editInspektiondate();
    }

}
