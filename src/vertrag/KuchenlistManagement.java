package vertrag;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.Kuchen;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public interface KuchenlistManagement {

     /**
     * Fügt einen Kuchen in die Liste ein.
     * @param typ, Kuchensorte, die von Kuchen bzw. Verkausobjekt ableitet
     * @param herstellername
     * @param preis
     * @param naehrwert
     * @param allergene
     * @param sorte Kuchensorte
     * @return true, wenn Kuchen eingefügt wurde, sonst false
     */
    boolean insert(Kuchentyp typ, Hersteller herstellername,
                   double preis, int naehrwert, Duration haltbarkeit,
                   Allergene allergene, String sorte );

    /**
     * Fügt einen Hersteller hinzu.
     * @param neuerHersteller
     * @return true, wenn Hersteller eingefügt wurde, sonst false
     */
    boolean insertH(Hersteller neuerHersteller);

    /**
     * Gibt vorhandene Kuchen aus.
     * @return Liste von Kuchen
     */
    List<Kuchen> readKuchen();

    /**
     * Gibt vorhandene Kuchen eines bestimmtes Typs aus.
     * @return Liste aller Kuchen eines Typs
     */
    List<Kuchen> readKuchen(Kuchentyp typ);

    /**
     * Gibt alle vorhandenen Hersteller aus.
     * @return Herstellername
     */
    Set<Hersteller> readHersteller();

    /**
     * Gibt vorhandene Allergenen aus.
     * @return Liste der Allergene
     */
    Set<Allergene> readAllergener();

    /**
     * setzt anhand der Fachnummer eines Kuchens das Datum der Inspektion
     * @param fachnummer
     * @return true, wenn Fachnummer bekannt ist, sonst false
     */
    boolean edit(int fachnummer);

    /**
     * loescht anhand der fachnummer den Kuchen aus der Liste
     * @param fachnummer nummer des Kuchens
     * @return true, wenn Kuchen gelöscht wurde, sonst false
     */
    boolean delete(int fachnummer);

    /**
     * loescht anhand des Namens den Hersteller aus der Liste
     * @param herstellername
     * @return true, wenn Hersteller entfernt wurde, sonst false
     */
    boolean deleteHersteller(String herstellername);

    /**
     * Fügt neue Allgene hinzu
     * @param allergene
     * @return true, wenn Allergen hinzugefügt wurde, sonst false
     */
    boolean insertA(Allergene allergene);

}
