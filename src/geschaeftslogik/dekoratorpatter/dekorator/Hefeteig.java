package geschaeftslogik.dekoratorpatter.dekorator;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchenbestandteile;
import geschaeftslogik.dekoratorpatter.componente.Kuchenboden;
import vertrag.Allergene;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class Hefeteig extends  Kuchenboden implements Kuchenbestandteile {

    String name  = "Hefeteig";
    BigDecimal preis = BigDecimal.valueOf(1.55);
    Duration haltbarkeit = Duration.ofDays(3);
    //TODO sollen die vorgebenen ALlergene benutzt werden oder gehen auch andere?
    Set<Allergene> allergen = new HashSet<>();

    int naehrwert = 140;

    public Hefeteig(Hersteller hersteller){
        super(hersteller);
    }

    @Override
    public BigDecimal getPreis() {
        return this.preis;
    }

    @Override
    public int getNaehrwert() {
        return this.naehrwert;
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
        allergen.add(Allergene.Gluten);
        allergen.add(Allergene.Erdnuss);
        return allergen;
    }

    @Override
    public Set<Allergene> getAllAllergene() {
        return this.allergen;
    }
}
