package geschaeftslogik.dekoratorpatter.dekorator;

import geschaeftslogik.Kuchenbestandteile;
import geschaeftslogik.dekoratorpatter.componente.Kuchenbelag;
import vertrag.Allergene;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class Pudding extends Kuchenbelag {

    String name = "Pudding";
    BigDecimal preis = BigDecimal.valueOf(4.10);
    Duration haltbarkeit = Duration.ofDays(5);
    //TODO sollen die vorgebenen ALlergene benutzt werden oder gehen auch andere?
    Set<Allergene> allergen = new HashSet<>();
    Set<Allergene> allergen2 = new HashSet<>(super.getAllergen());
    int naehrwert = 235;


    public Pudding(Kuchenbestandteile bestandteil) {
        super(bestandteil);
    }

    //public Pudding(Kuchenbelag belag) {super(belag);}

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
