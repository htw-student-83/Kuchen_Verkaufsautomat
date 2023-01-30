package geschaeftslogik.dekoratorpatter.dekorator;

import eventsystem.controller.AllergenAnzeigenEvent;
import geschaeftslogik.Kuchenbestandteile;
import geschaeftslogik.dekoratorpatter.componente.Kuchenbelag;
import vertrag.Allergene;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class Apfel extends Kuchenbelag {
    String name  = "Apfel";
    BigDecimal preis = BigDecimal.valueOf(1.55);
    Duration haltbarkeit = Duration.ofDays(3);
    //TODO sollen die vorgebenen ALlergene benutzt werden oder gehen auch andere?
    Set<Allergene> allergen = new HashSet<>();//Allergenenset für die Klasse Apfel
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
