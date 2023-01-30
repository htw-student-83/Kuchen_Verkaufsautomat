package geschaeftslogik;

import vertrag.Allergene;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Set;

public interface Kuchenbestandteile {
    String getName();
    Duration gethaltbarkeit();
    Set<Allergene> getAllergen();
    Set<Allergene> getAllAllergene();
    int getNaehrwert();
    BigDecimal getPreis();
}
