package geschaeftslogik.dekoratorpatter.dekorator;

import geschaeftslogik.Kuchenbestandteile;
import geschaeftslogik.dekoratorpatter.componente.Kuchenbelag;
import vertrag.Allergene;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class Sahne extends Kuchenbelag {
    String name = "Sahne";
    BigDecimal preis = BigDecimal.valueOf(2.44);
    Duration haltbarkeit = Duration.ofDays(2);
    Set<Allergene> allergene = new HashSet<>();
    int naehrwert = 200;

    public Sahne(Kuchenbestandteile bestandteil) {
        super(bestandteil);
    }

    @Override
    public String getName() {
        return super.getName() + ", " + this.name;
    }

    @Override
    public Duration gethaltbarkeit() {
        if(super.gethaltbarkeit().compareTo(this.haltbarkeit) < 0){
            return super.gethaltbarkeit();
        }
        return this.haltbarkeit;
    }


    public Set<Allergene> getAllergen() {
        allergene.addAll(super.getAllergen());
        allergene.add(Allergene.Gluten);
        allergene.add(Allergene.Erdnuss);
        return allergene;
    }

    @Override
    public int getNaehrwert() {
        return super.getNaehrwert() + this.naehrwert;
    }

    @Override
    public BigDecimal getPreis() {
        return super.getPreis().add(this.preis);
    }
}
