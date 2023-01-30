package geschaeftslogik.dekoratorpatter.componente;

import geschaeftslogik.Kuchenbestandteile;
import vertrag.Allergene;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Set;

public class Kuchenbelag implements Kuchenbestandteile {
    Kuchenbestandteile bestandteil;
    Kuchenboden boden;

    public Kuchenbelag(Kuchenbestandteile bestandteil){
        this.bestandteil = bestandteil;
    }

    public Kuchenbelag(Kuchenboden boden){
        this.boden = boden;
    }

    @Override
    public String getName() {return bestandteil.getName();}

    @Override
    public Duration gethaltbarkeit() { return bestandteil.gethaltbarkeit(); }

    @Override
    public Set<Allergene> getAllergen() { return bestandteil.getAllergen(); }

    @Override
    public Set<Allergene> getAllAllergene() {
        return bestandteil.getAllAllergene();
    }

    @Override
    public int getNaehrwert() { return  bestandteil.getNaehrwert(); }

    @Override
    public BigDecimal getPreis() { return bestandteil.getPreis(); }
}
