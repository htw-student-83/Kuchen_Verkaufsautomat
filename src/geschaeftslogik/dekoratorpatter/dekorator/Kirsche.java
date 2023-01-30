package geschaeftslogik.dekoratorpatter.dekorator;

import geschaeftslogik.Kuchenbestandteile;
import geschaeftslogik.dekoratorpatter.componente.Kuchenbelag;
import vertrag.Allergene;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class Kirsche extends Kuchenbelag {
    Set<Allergene> allergen = new HashSet<>();
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

    //TODO Enumwerte als String ausgeben lassen
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

    public int getNaehrwert() {
        return super.getNaehrwert() + this.naehrwert;
    }

    public BigDecimal getPreis() {
        return super.getPreis().add(this.preis);
    }
}
