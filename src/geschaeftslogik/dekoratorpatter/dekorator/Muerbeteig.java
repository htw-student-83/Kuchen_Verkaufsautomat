package geschaeftslogik.dekoratorpatter.dekorator;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchenbestandteile;
import geschaeftslogik.dekoratorpatter.componente.Kuchenboden;
import vertrag.Allergene;
import java.math.BigDecimal;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class Muerbeteig extends Kuchenboden implements Kuchenbestandteile {

    String name  = "Muerbeteig";
    BigDecimal preis = BigDecimal.valueOf(2.55);
    Duration haltbarkeit = Duration.ofDays(3);
    //TODO sollen die vorgebenen ALlergene benutzt werden oder gehen auch andere?
    Set<Allergene> allergen = new HashSet<>();
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
        allergen.add(Allergene.Sesamsamen);
        return allergen;
    }

    @Override
    public Set<Allergene> getAllAllergene() {
        return this.allergen;
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
