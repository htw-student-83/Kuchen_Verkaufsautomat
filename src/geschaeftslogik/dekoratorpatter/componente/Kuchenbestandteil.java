package geschaeftslogik.dekoratorpatter.componente;

import geschaeftslogik.Hersteller;
import vertrag.Allergene;
import java.math.BigDecimal;
import java.util.Set;

public abstract class Kuchenbestandteil{
    private Hersteller hersteller;
    private BigDecimal preis;
    private int haltbarkeit;
    private int naehrwert;
    private String name;
    private Set<Allergene> allergene;

    public Kuchenbestandteil(Hersteller hersteller, BigDecimal preis, int haltbarkeit,
                             int naehrwert, String name, Set<Allergene> allergeneSet){
        this.hersteller = hersteller;
        this.preis = preis;
        this.haltbarkeit = haltbarkeit;
        this.naehrwert = naehrwert;
        this.name = name;
        this.allergene = allergeneSet;
    }

    public Kuchenbestandteil(Hersteller hersteller){
        this.hersteller = hersteller;
    }

    public abstract BigDecimal getPreis();
    public abstract int getHaltbarkeit();
    public abstract int getNaehrwert();
    public abstract String getName();
    public abstract Set<Allergene> getAllergene();
}
