package geschaeftslogik.verkaufsobjekt;

import vertrag.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;

public class Obstkuchen extends Kuchen  {

    String obstsorte;
    String kremsorte;

    public Obstkuchen(String kuchentyp, Hersteller hersteller, BigDecimal preis,
                      int naehrwert, Duration haltbarkeit, String obstsorte, String kremsorte) {
        super(kuchentyp, hersteller,preis, naehrwert, haltbarkeit);
        this.obstsorte = obstsorte;
        this.kremsorte = kremsorte;

    }

    @Override
    public String getKremsorte() {
        return obstsorte;
    }
}
