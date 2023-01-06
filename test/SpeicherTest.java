import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.Verwaltung;
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
        model.insert(Kuchentyp.Kremkuchen, hersteller1, Duration.ofDays(23), 8.99,
                299, Allergene.Gluten, "Erdbeere");
        ObjektSpeicherungJOS.persistiereAutomaten();
        Verwaltung vw = ObjektSpeicherungJOS.reloadAutomt();
        Assertions.assertNotNull(vw);
    }
}
