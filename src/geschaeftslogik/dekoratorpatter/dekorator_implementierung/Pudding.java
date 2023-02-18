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

public class Pudding extends Kuchenbelag implements Serializable {

    String name = "Pudding";
    BigDecimal preis = BigDecimal.valueOf(4.10);
    Duration haltbarkeit = Duration.ofDays(6);
    Set<Allergene> allergene = new HashSet<>();
    int naehrwert = 235;


    public Pudding(Kuchenbestandteile bestandteil) {
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
        allergene.add(Allergene.Sesamsamen);
        return allergene;
    }

    public int getNaehrwert() {
        return super.getNaehrwert() + this.naehrwert;
    }

    public BigDecimal getPreis() {
        return super.getPreis().add(this.preis);
    }
}
