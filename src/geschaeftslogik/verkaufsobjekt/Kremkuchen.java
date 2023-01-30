package geschaeftslogik.verkaufsobjekt;

import vertrag.Allergene;
import vertrag.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.Set;

public class Kremkuchen extends Kuchen{
    String kremsorte;

    public Kremkuchen(String kuchentyp, Hersteller hersteller, BigDecimal preis, int naehrwert,
                      Duration haltbarkeit, Set<Allergene> allergene, String kremsorte){
        super(kuchentyp , hersteller, preis,naehrwert, haltbarkeit, allergene);
        this.kremsorte = kremsorte;
    }

    public Kremkuchen(int Fachnummer, String kremsorte, int naehrwert,
                      Hersteller hersteller, Duration haltbarkeit, Date einfuegedatum,
                      Set<Allergene> allergene, BigDecimal preis){
        super(Fachnummer, naehrwert, hersteller, haltbarkeit, einfuegedatum, allergene, preis);
        this.kremsorte = kremsorte;
    }


    @Override
    public String getKremsorte() {
        return this.kremsorte;
    }
}
