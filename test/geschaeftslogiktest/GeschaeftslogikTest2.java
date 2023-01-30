package geschaeftslogiktest;

import geschaeftslogik.Hersteller;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class GeschaeftslogikTest2 {

    // - Ein Kuchen mit Muerbeteig wird eingefügt.
    @Test
    @DisplayName("Ein neuer Muerbeteig mit Kirsche wird eingefügt")
    public void insertKuchenAlsDekorator1() {
        String[] belagarray = {"Kirsche"};
        Verwaltung vw = new Verwaltung();
        vw.setKapazitaet(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insertHersteller(hersteller);
        vw.insetKuchen2("Muerbeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(1, listSize);
    }

    @Test
    @DisplayName("Ein neuer Muerbeteig ohne Belag wird eingefügt")
    public void insertKuchenAlsDekorator2() {
        String[] belagarray = {};
        Verwaltung vw = new Verwaltung();
        vw.setKapazitaet(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insertHersteller(hersteller);
        vw.insetKuchen2("Muerbeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(1, listSize);
    }

    @Test
    @DisplayName("Ein neuer Muerbeteig mit Birne aber unbekanntem Hersteller wird eingefügt")
    public void insertKuchenAlsDekorator3() {
        String[] belagarray = {"Birne"};
        Verwaltung vw = new Verwaltung();
        vw.setKapazitaet(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insetKuchen2("Muerbeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(0, listSize);
    }

    @Test
    @DisplayName("Ein neuer Muerbeteig mit allen 5 Belägen wird eingefügt")
    public void insertKuchenAlsDekorator4() {
        String[] belagarray = {"Birne", "Apfel", "Kirsche", "Sahne", "Pudding"};
        Verwaltung vw = new Verwaltung();
        vw.setKapazitaet(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insertHersteller(hersteller);
        vw.insetKuchen2("Muerbeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(1, listSize);
    }

    @Test
    @DisplayName("Ein neuer Muerbeteig mit allen 5 Belägen wird eingefügt")
    public void insertKuchenAlsDekorator5() {
        Verwaltung vw = new Verwaltung();
        vw.setKapazitaet(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insertHersteller(hersteller);
        String[] belagarray = {"Birne", "Apfel"};
        vw.insetKuchen2("Muerbeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(1, listSize);
    }

    @Test
    @DisplayName("Ein neuer Muerbeteig mit 5 Belägen aber unbekanntem Hersteller wird eingefügt")
    public void insertKuchenAlsDekorator6(){
        String[] belagarray = {"Birne", "Apfel", "Kirsche", "Sahne", "Pudding"};
        Verwaltung vw = new Verwaltung();
        vw.setKapazitaet(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insetKuchen2("Muerbeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(0, listSize);
    }

    @Test
    @DisplayName("Ein neuer Hefeteig mit Kirsche wird eingefügt")
    public void insertKuchenAlsDekorator7() {
        String[] belagarray = {"Kirsche"};
        Verwaltung vw = new Verwaltung();
        vw.setKapazitaet(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insertHersteller(hersteller);
        vw.insetKuchen2("Hefeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(1, listSize);
    }

    @Test
    @DisplayName("Ein neuer Hefeteig ohne Belag wird eingefügt")
    public void insertKuchenAlsDekorator8() {
        String[] belagarray = {};
        Verwaltung vw = new Verwaltung();
        vw.setKapazitaet(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insertHersteller(hersteller);
        vw.insetKuchen2("Hefeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(1, listSize);
    }

    @Test
    @DisplayName("Ein neuer Hefeteig mit Birne aber unbekanntem Hersteller wird eingefügt")
    public void insertKuchenAlsDekorator9() {
        String[] belagarray = {"Birne"};
        Verwaltung vw = new Verwaltung();
        vw.setKapazitaet(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insetKuchen2("Hefeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(0, listSize);
    }

    @Test
    @DisplayName("Ein neuer Kuchen ohne Boden wird eingefügt")
    public void insertKuchenAlsDekorator10(){
        String[] belagarray = {};
        Verwaltung vw = new Verwaltung();
        vw.setKapazitaet(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insetKuchen2("", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(0, listSize);
    }

    @Test
    @DisplayName("Ein neuer Kuchen ohne Boden wird eingefügt")
    public void insertKuchenAlsDekorator11(){
        int zahl  = 2;
        Verwaltung vw = new Verwaltung();
        assertEquals(Duration.ofDays(2), vw.convertHaltbarkeit(zahl));
    }
}
