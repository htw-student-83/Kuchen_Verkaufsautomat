package geschaeftslogik.dekoratorpatter.dekorator_implementierung;

import geschaeftslogik.Kuchenbestandteile;
import geschaeftslogik.dekoratorpatter.vertrag.Kuchenbelag;
import geschaeftslogik.verkaufsobjekt.DekoKuchen;
import vertrag.Allergene;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class Birne extends Kuchenbelag implements Serializable {
    String name = "Birne";
    BigDecimal preis = BigDecimal.valueOf(2.22);
    Duration haltbarkeit = Duration.ofDays(4);
    //TODO sollen die vorgebenen ALlergene benutzt werden oder gehen auch andere?
    Set<Allergene> allergene = new HashSet<>();
    int naehrwert = 306;

    public Birne(Kuchenbestandteile bestandteil) {
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
        allergene.add(Allergene.Sesamsamen);
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
