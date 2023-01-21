package streamtest;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import jbp.ObjektSpeicherungJBP;
import jos.ObjektLadenJOS;
import jos.ObjektSpeicherungJOS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import vertrag.Allergene;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.fail;

public class SpeicherTest {
    Verwaltung model;

    @Test
    @DisplayName("Zustand des Kuchenautomaten mit JOS speichern")
    public void automatenspeichern() {
        model = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        model.insertHersteller(hersteller1);
        model.insertKuchen(Kuchentyp.Kremkuchen, hersteller1, 2.45,123,
                Duration.ofDays(23), Allergene.Gluten, "Erdbeere");
        ObjektSpeicherungJOS.persistiereAutomaten(model);
        Verwaltung vw = ObjektLadenJOS.reloadAutomt();
        Assertions.assertNotNull(vw);
    }

    @Test
    @DisplayName("Zustand des Kuchenautomaten mit JBP speichern")
    public void automatenSpeichern2(){
        model = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        model.insertHersteller(hersteller1);
        model.insertKuchen(Kuchentyp.Kremkuchen, hersteller1, 2.45,123,
                Duration.ofDays(23), Allergene.Gluten, "Erdbeere");
        ObjektSpeicherungJBP.persistiereAutomaten();
        fail();
    }
}
