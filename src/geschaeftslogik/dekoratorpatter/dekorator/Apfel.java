package geschaeftslogik.dekoratorpatter.dekorator;

import geschaeftslogik.Kuchenbestandteile;
import geschaeftslogik.dekoratorpatter.componente.Kuchenbelag;
import vertrag.Allergene;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Apfel extends Kuchenbelag {
    String name  = "Apfel";
    BigDecimal preis = BigDecimal.valueOf(1.55);
    Duration haltbarkeit = Duration.ofDays(3);
    //TODO sollen die vorgebenen ALlergene benutzt werden oder gehen auch andere?
    Set<Allergene> allergene = new HashSet<>();
    int naehrwert = 140;


    public Apfel(Kuchenbestandteile bestandteil) {
        super(bestandteil);
    }

    //public Apfel(Kuchenbelag belag) {super(belag);}

    @Override
    public String getName() {
        return super.getName() + ", " +  this.name;
    }

    public Duration gethaltbarkeit() {
        if(super.gethaltbarkeit().compareTo(this.haltbarkeit)<0){
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
