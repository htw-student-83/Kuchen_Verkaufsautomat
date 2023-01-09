package streamtest;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import jos.ObjektLadenJOS;
import jos.ObjektSpeicherungJOS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import vertrag.Allergene;

import java.time.Duration;

public class SpeicherTest {
    Verwaltung model;

    @Test
    public void automatenspeichern() {
        model = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        model.insertH(hersteller1);
        model.insert(Kuchentyp.Kremkuchen, hersteller1, 2.45,123,
                Duration.ofDays(23), Allergene.Gluten, "Erdbeere");
        ObjektSpeicherungJOS.persistiereAutomaten();
        Verwaltung vw = ObjektLadenJOS.reloadAutomt();
        Assertions.assertNotNull(vw);
    }
}
