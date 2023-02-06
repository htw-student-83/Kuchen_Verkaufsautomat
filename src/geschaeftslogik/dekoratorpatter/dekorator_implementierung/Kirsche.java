package geschaeftslogik.dekoratorpatter.dekorator_implementierung;

import geschaeftslogik.Kuchenbestandteile;
import geschaeftslogik.dekoratorpatter.vertrag.Kuchenbelag;
import vertrag.Allergene;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class Kirsche extends Kuchenbelag implements Serializable {
    Set<Allergene> allergene = new HashSet<>();
    String name = "Kirsche";
    BigDecimal preis = BigDecimal.valueOf(3.10);
    Duration haltbarkeit = Duration.ofDays(2);

    //TODO sollen die vorgebenen Allergene benutzt werden oder gehen auch andere?
    int naehrwert = 216;


    public Kirsche(Kuchenbestandteile bestandteil) {
        super(bestandteil);
    }

    public String getName() {
        return super.getName() + ", " + this.name;
    }

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

    public int getNaehrwert() {
        return super.getNaehrwert() + this.naehrwert;
    }

    public BigDecimal getPreis() {
        return super.getPreis().add(this.preis);
    }
}
