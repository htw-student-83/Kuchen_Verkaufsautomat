package geschaeftslogik;

import vertrag.Allergene;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Set;

public interface Kuchenbestandteile {
    String getName();
    Duration gethaltbarkeit();
    Set<Allergene> getAllergen();
    int getNaehrwert();
    BigDecimal getPreis();
}
