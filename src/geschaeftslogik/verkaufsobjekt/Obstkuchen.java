package geschaeftslogik.verkaufsobjekt;

import vertrag.Allergene;
import vertrag.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;

public class Obstkuchen extends Kuchen  {

    String obstkuchensorte;

    public Obstkuchen(String sorte, int naehrwert, Hersteller hersteller,
                      Duration haltbarkeit, Allergene allergene, BigDecimal preis) {
        super(naehrwert, hersteller, haltbarkeit, allergene, preis);
        this.obstkuchensorte = sorte;
    }

    @Override
    public String getKremsorte() {
        return obstkuchensorte;
    }
}
