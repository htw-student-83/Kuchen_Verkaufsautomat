package geschaeftslogik.verkaufsobjekt;

import vertrag.Allergene;
import vertrag.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Set;

public class Obsttorte extends Kuchen {

    String obstsorte;
    String kremsorte;

    public Obsttorte(String kuchentyp, Hersteller hersteller, BigDecimal preis,
                     int naehrwert, Duration haltbarkeit, Set<Allergene> allergene,
                     String sorte, String kremsorte) {
        super(kuchentyp, hersteller,preis, naehrwert, haltbarkeit, allergene);
        this.obstsorte = sorte;
        this.kremsorte = kremsorte;
    }

    @Override
    public String getKremsorte() {
        return obstsorte;
    }
}
