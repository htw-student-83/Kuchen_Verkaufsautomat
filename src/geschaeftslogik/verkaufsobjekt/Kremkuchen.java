package geschaeftslogik.verkaufsobjekt;

import vertrag.Allergene;
import vertrag.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;

public class Kremkuchen extends Kuchen{
    String kremsorte;

    boolean isID = false;

    public Kremkuchen(String kremsorte, int naehrwert, Hersteller hersteller,
                      Duration haltbarkeit, Allergene allergene, BigDecimal preis){
        super(naehrwert, hersteller, haltbarkeit, allergene, preis);

        this.kremsorte= kremsorte;
        /*
        this.kuchenobjekt.setNaehrwert(naehrwert);
        this.kuchenobjekt.setHaltbarkeit(haltbarkeit);
        this.kuchenobjekt.setAllergene(allergene);
        this.kuchenobjekt.setPreis(preis);
        this.kuchenobjekt.setHersteller(hersteller);
         */
    }

    public Kremkuchen(int Fachnummer, String kremsorte, int naehrwert,
                      Hersteller hersteller, Duration haltbarkeit, Date einfuegedatum,
                      Allergene allergene, BigDecimal preis){
        super(Fachnummer, naehrwert, hersteller, haltbarkeit, einfuegedatum, allergene, preis);
        this.kremsorte = kremsorte;
    }

    @Override
    public String getKremsorte() {
        return this.kremsorte;
    }
}
