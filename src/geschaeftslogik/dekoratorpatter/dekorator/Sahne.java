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
    //TODO sollen die vorgebenen ALlergene benutzt werden oder gehen auch andere?
    Set<Allergene> allergen = new HashSet<>();
    int naehrwert = 200;


    //public Sahne(Kuchenboden boden) {super(boden);}

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

    public Set<Allergene> getAllAllergene(){
        allergen.addAll(super.getAllergen());
        allergen.addAll(getAllergen());
        return allergen;
    }

    public Set<Allergene> getAllergen() {
        allergen.add(Allergene.Gluten);
        allergen.add(Allergene.Erdnuss);
        return allergen;
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
