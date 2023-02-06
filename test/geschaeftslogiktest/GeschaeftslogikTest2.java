package geschaeftslogiktest;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchenbestandteile;
import geschaeftslogik.dekoratorpatter.dekorator_implementierung.Hefeteig;
import geschaeftslogik.dekoratorpatter.dekorator_implementierung.Sahne;
import geschaeftslogik.verkaufsobjekt.DekoKuchen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import vertrag.Allergene;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class GeschaeftslogikTest2 {

    @Test
    @DisplayName("Ein neuer Muerbeteig mit Kirsche wird eingefügt")
    public void insertKuchenAlsDekorator1() {
        String[] belagarray = {"Kirsche"};
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insertHersteller(hersteller);
        vw.insertKuchen2("Muerbeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(1, listSize);
    }

    @Test
    @DisplayName("Ein neuer Muerbeteig ohne Belag wird eingefügt")
    public void insertKuchenAlsDekorator2() {
        String[] belagarray = {};
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insertHersteller(hersteller);
        vw.insertKuchen2("Muerbeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(1, listSize);
    }

    @Test
    @DisplayName("Ein neuer Muerbeteig mit Birne aber unbekanntem Hersteller wird eingefügt")
    public void insertKuchenAlsDekorator3() {
        String[] belagarray = {"Birne"};
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insertKuchen2("Muerbeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(0, listSize);
    }

    @Test
    @DisplayName("Ein neuer Muerbeteig mit allen 5 Belägen wird eingefügt")
    public void insertKuchenAlsDekorator4() {
        String[] belagarray = {"Birne", "Apfel", "Kirsche", "Sahne", "Pudding"};
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insertHersteller(hersteller);
        vw.insertKuchen2("Muerbeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(1, listSize);
    }

    @Test
    @DisplayName("Ein neuer Muerbeteig mit allen 5 Belägen wird eingefügt")
    public void insertKuchenAlsDekorator5() {
        String[] belagarray = {"Birne", "Apfel"};
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insertHersteller(hersteller);
        vw.insertKuchen2("Muerbeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(1, listSize);
    }

    @Test
    @DisplayName("Ein neuer Muerbeteig mit 5 Belägen aber unbekanntem Hersteller wird eingefügt")
    public void insertKuchenAlsDekorator6(){
        String[] belagarray = {"Birne", "Apfel", "Kirsche", "Sahne", "Pudding"};
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insertKuchen2("Muerbeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(0, listSize);
    }

    @Test
    @DisplayName("Ein neuer Hefeteig mit Kirsche wird eingefügt")
    public void insertKuchenAlsDekorator7() {
        String[] belagarray = {"Kirsche"};
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insertHersteller(hersteller);
        vw.insertKuchen2("Hefeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(1, listSize);
    }

    @Test
    @DisplayName("Ein neuer Hefeteig ohne Belag wird eingefügt")
    public void insertKuchenAlsDekorator8() {
        String[] belagarray = {};
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insertHersteller(hersteller);
        vw.insertKuchen2("Hefeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(1, listSize);
    }

    @Test
    @DisplayName("Ein neuer Hefeteig mit Birne aber unbekanntem Hersteller wird eingefügt")
    public void insertKuchenAlsDekorator9() {
        String[] belagarray = {"Birne"};
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insertKuchen2("Hefeteig", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(0, listSize);
    }

    @Test
    @DisplayName("Ein neuer Kuchen ohne Boden wird eingefügt")
    public void insertKuchenAlsDekorator10(){
        String[] belagarray = {};
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller = new Hersteller("Hersteller1");
        vw.insertKuchen2("", hersteller,belagarray);
        int listSize = vw.getKuchenlisteSize2();
        assertEquals(0, listSize);
    }

    @Test
    @DisplayName("Ein neuer Kuchen ohne Boden wird eingefügt")
    public void insertKuchenAlsDekorator11(){
        int zahl  = 2;
        Verwaltung vw = new Verwaltung(1);
        assertEquals(Duration.ofDays(2), vw.convertHaltbarkeit(zahl));
    }

    @Test
    public void insertKuchenAlsDekorator12(){
        ArrayList<Kuchenbestandteile> belaege = new ArrayList<>();
        Hersteller hersteller = new Hersteller("Hersteller1");
        Kuchenbestandteile boden = new Hefeteig(hersteller);
        Verwaltung vw = new Verwaltung(1);
        belaege.add(new Sahne(boden));
        DekoKuchen kuchen = new DekoKuchen(boden, hersteller, belaege);
        int id = kuchen.getFachnummer();
        assertEquals(0, id);
    }

    @Test
    public void insertKuchenAlsDekorator13(){
        ArrayList<Kuchenbestandteile> belaege = new ArrayList<>();
        Hersteller hersteller = new Hersteller("Hersteller1");
        Kuchenbestandteile boden = new Hefeteig(hersteller);
        Verwaltung vw = new Verwaltung(1);
        belaege.add(new Sahne(boden));
        DekoKuchen kuchen = new DekoKuchen(boden, hersteller, belaege);
        assertEquals(null, kuchen.getEinfuegedatum());
    }

    @Test
    public void funktionstestderDekokuchenKlasse2(){
        DekoKuchen kuchen = mock(DekoKuchen.class);
        when(kuchen.getPreis()).thenReturn(BigDecimal.valueOf(1.55));
    }

    @Test
    public void funktionstestderDekokuchenKlasse3(){
        DekoKuchen kuchen = mock(DekoKuchen.class);
        when(kuchen.getAllergene()).thenReturn(Collections.singleton(Allergene.Sesamsamen));
    }

    @Test
    public void funktionstestderDekokuchenKlasse4(){
        DekoKuchen kuchen = mock(DekoKuchen.class);
        Verwaltung verwaltung = new Verwaltung(0);
        when(kuchen.getInspektionsdatum()).thenReturn(verwaltung.getInspektion());
    }

    @Test
    public void funktionstestderDekokuchenKlasse5(){
        Hefeteig hefeteig = mock(Hefeteig.class);
        when(hefeteig.getName()).thenReturn("Hefeteig");
    }
}
