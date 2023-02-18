package geschaeftslogik.dekoratorpatter.vertrag;

import geschaeftslogik.Kuchenbestandteile;
import geschaeftslogik.verkaufsobjekt.DekoKuchen;
import vertrag.Allergene;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Set;

public class Kuchenbelag implements Kuchenbestandteile {
    Kuchenbestandteile bestandteil;

    public Kuchenbelag(Kuchenbestandteile bestandteil){
        this.bestandteil = bestandteil;
    }

    //public Kuchenbelag(Kuchenboden boden){this.boden = boden;}

    @Override
    public String getName() {return bestandteil.getName();}

    @Override
    public Duration gethaltbarkeit() { return bestandteil.gethaltbarkeit(); }

    @Override
    public Set<Allergene> getAllergen() { return bestandteil.getAllergen(); }

    @Override
    public int getNaehrwert() { return  bestandteil.getNaehrwert(); }

    @Override
    public BigDecimal getPreis() { return bestandteil.getPreis(); }
}
