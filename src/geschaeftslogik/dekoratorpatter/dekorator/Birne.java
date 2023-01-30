package geschaeftslogik.dekoratorpatter.dekorator;

import geschaeftslogik.Kuchenbestandteile;
import geschaeftslogik.dekoratorpatter.componente.Kuchenbelag;
import vertrag.Allergene;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class Birne extends Kuchenbelag {
    String name = "Birne";
    BigDecimal preis = BigDecimal.valueOf(2.22);
    Duration haltbarkeit = Duration.ofDays(3);
    //TODO sollen die vorgebenen ALlergene benutzt werden oder gehen auch andere?
    Set<Allergene> allergen = new HashSet<>();

    static Allergene[] allergenenarray = {Allergene.Gluten, Allergene.Sesamsamen};
    int naehrwert = 306;


    public Birne(Kuchenbestandteile bestandteil) {
        super(bestandteil);
    }


   // public Birne(Kuchenbelag belag) {super(belag);}

    //public Birne(Kuchenboden boden) {super(boden);}


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
