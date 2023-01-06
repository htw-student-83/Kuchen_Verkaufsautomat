package vertrag;

import geschaeftslogik.Hersteller;

import java.time.Duration;
import java.util.Collection;

public interface Kuchen {
    Hersteller getHersteller();
    Collection<Allergene> getAllergene();
    int getNaehrwert();
    Duration getHaltbarkeit();
}
