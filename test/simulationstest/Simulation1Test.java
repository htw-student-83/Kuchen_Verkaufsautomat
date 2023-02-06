package simulationstest;

import geschaeftslogik.verkaufsobjekt.DekoKuchen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import simulation1.DeleteSimulation1;
import simulation1.InsertSimulation1;
import java.util.List;

public class Simulation1Test {
    Verwaltung model = new Verwaltung(3);
    final Object monitor=new Object();

    @Test
    @DisplayName("Ein Kuchen wird eingefügt.")
    public void insert1(){
        InsertSimulation1 sim1 = new InsertSimulation1(model, monitor);
        sim1.insertKuchenSimulation();
        List<DekoKuchen> kuchen = this.model.readKuchen();
        Assertions.assertNotNull(kuchen);
    }

    @Test
    @DisplayName("Ein Kuchen wird aus leerer Kuchenliste gelöscht.")
    public void deleteKuchen() {
        DeleteSimulation1 sim1 = new DeleteSimulation1(model, monitor);
        boolean result = sim1.deleteKuchenSimulation();
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Ein Kuchen wird aus nicht leerer Kuchenliste gelöscht.")
    public void deleteKuchen2() {
        InsertSimulation1 sim1 = new InsertSimulation1(model, monitor);
        sim1.insertKuchenSimulation();
        DeleteSimulation1 sim2 = new DeleteSimulation1(model, monitor);
        boolean result = sim2.deleteKuchenSimulation();
        Assertions.assertTrue(result);
    }
}
