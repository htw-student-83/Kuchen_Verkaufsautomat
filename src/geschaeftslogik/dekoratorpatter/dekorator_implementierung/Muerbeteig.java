package geschaeftslogik.dekoratorpatter.dekorator_implementierung;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchenbestandteile;
import geschaeftslogik.dekoratorpatter.vertrag.Kuchenboden;
import vertrag.Allergene;

import java.io.Serializable;
import java.math.BigDecimal;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class Muerbeteig extends Kuchenboden implements Kuchenbestandteile, Serializable {

    String name  = "Muerbeteig";
    BigDecimal preis = BigDecimal.valueOf(2.55);
    Duration haltbarkeit = Duration.ofDays(7);
    Set<Allergene> allergene = new HashSet<>();
    int naehrwert = 140;

    public Muerbeteig(Hersteller hersteller){
        super(hersteller);
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Duration gethaltbarkeit() {
        return this.haltbarkeit;
    }

    @Override
    public Set<Allergene> getAllergen() {
        allergene.add(Allergene.Sesamsamen);
        return allergene;
    }

    @Override
    public int getNaehrwert() {
        return this.naehrwert;
    }

    @Override
    public BigDecimal getPreis() {
        return this.preis;
    }
}
