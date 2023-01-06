package geschaeftslogik.verkaufsobjekt;

import vertrag.Allergene;
import vertrag.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;

public class Obsttorte extends Kuchen {

    String obsttortensorte;

    public Obsttorte(String sorte, int naehrwert, Hersteller hersteller,
                     Duration haltbarkeit, Allergene allergene, BigDecimal preis) {
        super(naehrwert, hersteller, haltbarkeit, allergene, preis);
        this.obsttortensorte = sorte;
    }

    @Override
    public String getKremsorte() {
        return obsttortensorte;
    }
}
