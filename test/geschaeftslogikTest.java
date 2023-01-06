import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.Verwaltung;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import geschaeftslogik.verkaufsobjekt.Obstkuchen;
import geschaeftslogik.verkaufsobjekt.Obsttorte;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import vertrag.Allergene;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class geschaeftslogikTest {
    @Test
    @DisplayName("Kuchenliste erhaelt einen weiteren Eintrag")
    public void insert1() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertH(hersteller1);
        vw.insert(Kuchentyp.Kremkuchen, hersteller1, Duration.ofDays(23),
                1.49, 400, Allergene.Gluten, "Erdbeere");
        assertEquals(1, vw.getKuchenlistsize());
    }


    @Test
    @DisplayName("Kuchenliste bekommt zwei neue Kuchensorten")
    public void insert2(){
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        vw.insertH(hersteller1);
        vw.insertH(hersteller2);
        vw.insert(Kuchentyp.Kremkuchen, hersteller1, Duration.ofDays(23),  3.99, 400,
                Allergene.Gluten, "Erdbeere");
        vw.insert(Kuchentyp.Kremkuchen, hersteller2,Duration.ofDays(25), 4.20, 300,
                Allergene.Gluten, "Banane");
        assertEquals(2, vw.getKuchenlistsize());
    }

    @Test
    @DisplayName("Zwei Kuchen des gleichen Typs mit zwei Herstellern werdne hinzugefuegt")
    public void mockitoTest(){
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = mock(Hersteller.class);
        Hersteller hersteller2 = mock(Hersteller.class);
        //Wert von beiden Herstellerobjekten ist null
        //Um Methodenaufrufe zu testen -> mit Spy arbeiten
        Mockito.when(hersteller1.getName()).thenReturn("OTTO");
        Mockito.when(hersteller2.getName()).thenReturn("hersteller");
        vw.insertH(hersteller1);
        vw.insertH(hersteller2);
        vw.insert(Kuchentyp.Kremkuchen, hersteller1, Duration.ofDays(33), 3.99, 400,
                Allergene.Gluten, "Erdbeere");
        vw.insert(Kuchentyp.Kremkuchen, hersteller2, Duration.ofDays(12), 4.20, 300,
                Allergene.Gluten, "Banane");
        assertEquals(2, vw.getKuchenlistsize());
    }


    @Test
    @DisplayName("Neuer Kuchen bekommt eine Fachnummer")
    public void mockitoTestt(){
        Verwaltung vw = Mockito.spy(Verwaltung.class);
        Hersteller hersteller1 = new Hersteller("Hersteller");
        vw.insertH(hersteller1);
        vw.insert(Kuchentyp.Kremkuchen, hersteller1,Duration.ofDays(13), 3.99, 400,
                Allergene.Gluten, "Erdbeere");
        verify(vw).insert(Kuchentyp.Kremkuchen, hersteller1,Duration.ofDays(30), 3.99, 400,
                Allergene.Gluten, "Erdbeere");
        verify(vw).getCurrentFachnummerAndIncrement();
        Assertions.assertEquals(1, vw.getKuchenlistsize());
    }

    @Test
    @DisplayName("Zuweisung eines Einfuegedatums")
    public void mockitoTestt2(){
        Verwaltung vw = Mockito.spy(Verwaltung.class);
        Hersteller hersteller1 = new Hersteller("Hersteller");
        vw.insertH(hersteller1);
        vw.insert(Kuchentyp.Kremkuchen, hersteller1, Duration.ofDays(11), 3.99, 400,
                Allergene.Gluten, "Erdbeere");
        verify(vw).insert(Kuchentyp.Kremkuchen, hersteller1, Duration.ofDays(23), 3.99, 400,
                Allergene.Gluten, "Erdbeere");
        verify(vw).getDatum();
    }



    @Test
    @DisplayName("Einfuegen von zwei Kremkuchen")
    public void insert3() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertH(hersteller1);
        boolean result1 = vw.insert(Kuchentyp.Kremkuchen, hersteller1,
                Duration.ofDays(43), 1.34, 400,
                Allergene.Gluten, "Banane");
        boolean result2 = vw.insert(Kuchentyp.Kremkuchen, hersteller1, Duration.ofDays(13), 3.23,
                420, Allergene.Gluten, "Banane");
        assertTrue(result1);
        assertTrue(result2);
    }


    @Test
    @DisplayName("Variable Haltbarkeit initialisiert")
    public void insert4() {
        Verwaltung vw = new Verwaltung();
        Kuchen k = mock(Kuchen.class);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insert(Kuchentyp.Kremkuchen, hersteller1,Duration.ofDays(23),3.50,400,
                Allergene.Gluten, "Erdbeere");
        Duration haltbarkeit = k.getHaltbarkeit();
        assertNotNull(haltbarkeit);
        fail();
    }


    @Test
    @DisplayName("Ein Objekt Obstkuchen wird instanziiert")
    public void insert6() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertH(hersteller1);
        vw.insert(Kuchentyp.Obstkuchen, hersteller1,Duration.ofDays(23),3.50,
                400, Allergene.Gluten, "Schokos");
        Kuchen obstkuchen = mock(Obstkuchen.class);
        assertInstanceOf(Kuchen.class,obstkuchen);
    }


    @Test
    @DisplayName("Ein Objekt Obsttorte wird instanziiert")
    public void insert7() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insert(Kuchentyp.Obsttorte, hersteller1, Duration.ofDays(23),3.50,400,
                Allergene.Gluten, "Schokos");
        Kuchen obsttorte = mock(Obsttorte.class);
        assertInstanceOf(Kuchen.class,obsttorte);
    }


    @Test
    @DisplayName("Zwei verschiedene Kuchen werden eingefuegt.")
    public void insert8() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        vw.insertH(hersteller1);
        vw.insertH(hersteller2);
        vw.insert(Kuchentyp.Kremkuchen, hersteller1, Duration.ofDays(23), 8.99, 299,
                Allergene.Gluten, "Erdbeere");
        vw.insert(Kuchentyp.Obstkuchen, hersteller2, Duration.ofDays(23), 4.20, 300,
                Allergene.Gluten, "Banane");
        assertEquals(2, vw.getKuchenlistsize());
    }



    @Test
    @DisplayName("Kuchen mit unbekanntem Hersteller wird hinzugefuegt")
    public void insert9() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        vw.insertH(hersteller1);
        boolean isValid = vw.insert(Kuchentyp.Kremkuchen, hersteller1, Duration.ofDays(23),
                8.99, 299, Allergene.Gluten, "Erdbeere");
        boolean isValid2 = vw.insert(Kuchentyp.Obstkuchen, hersteller2, Duration.ofDays(23),
                4.20, 300, Allergene.Gluten, "Banane");
        assertTrue(isValid);
        assertFalse(isValid2);
    }


    @Test
    @DisplayName("Einfuegen von drei verschiedenen Kuchen")
    public void insert10(){
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        Hersteller hersteller3 = new Hersteller("hersteller3");
        vw.insertH(hersteller1);
        vw.insertH(hersteller2);
        vw.insertH(hersteller3);
        boolean isValid = vw.insert(Kuchentyp.Kremkuchen, hersteller1, Duration.ofDays(23),
                8.99, 299, Allergene.Gluten, "Erdbeere");
        boolean isValid2 = vw.insert(Kuchentyp.Obstkuchen, hersteller2, Duration.ofDays(23),
                4.20, 300, Allergene.Gluten, "Banane");
        boolean isValid3 = vw.insert(Kuchentyp.Obsttorte, hersteller3, Duration.ofDays(23),
                1.20, 100, Allergene.Erdnuss, "Nuss");
        assertTrue(isValid);
        assertTrue(isValid2);
        assertTrue(isValid3);
    }


    @Test
    @DisplayName("Es werden zu viele Kuchen eingefuegt.")
    public void insert11() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        Hersteller hersteller3 = new Hersteller("hersteller3");
        vw.insertH(hersteller1);
        vw.insertH(hersteller2);
        vw.insertH(hersteller3);
        boolean isValid = vw.insert(Kuchentyp.Kremkuchen, hersteller1, Duration.ofDays(23),
                8.99, 299, Allergene.Gluten, "Erdbeere");
        boolean isValid2 = vw.insert(Kuchentyp.Obstkuchen, hersteller2, Duration.ofDays(23),
                4.20, 300, Allergene.Gluten, "Banane");
        boolean isValid3 = vw.insert(Kuchentyp.Obsttorte, hersteller3, Duration.ofDays(23),
                1.20, 100, Allergene.Erdnuss, "Nuss");
        boolean isValid4 = vw.insert(Kuchentyp.Obsttorte, hersteller2, Duration.ofDays(23),
                5.20, 340, Allergene.Erdnuss, "Kirsch");
        assertTrue(isValid);
        assertTrue(isValid2);
        assertTrue(isValid3);
        assertFalse(isValid4);
    }


    @Test
    @DisplayName("Leere Kuchenliste wird ausgelesen")
    public void read(){
        Verwaltung vw = new Verwaltung();
        List result = vw.readKuchen();
        assertTrue(result.isEmpty());
    }



    @Test
    @DisplayName("Kuchenliste wird ausgelesen")
    public void read2() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insert(Kuchentyp.Kremkuchen, hersteller1, Duration.ofDays(23),
                3.44,400, Allergene.Gluten, "Kirsch");
        List result = vw.readKuchen();
        assertNotNull(result);
    }


    @Test
    @DisplayName("Kuchenliste mit meheren Kuchen wird ausgelesen")
    public void read3() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        Hersteller hersteller3 = new Hersteller("hersteller3");
        vw.insert(Kuchentyp.Kremkuchen, hersteller1, Duration.ofDays(23),
                2.22,400, Allergene.Gluten, "Erdbeere");
        vw.insert(Kuchentyp.Obstkuchen, hersteller2, Duration.ofDays(23),
                3.45,250, Allergene.Gluten, "Birne");
        vw.insert(Kuchentyp.Obsttorte, hersteller3, Duration.ofDays(23),
                3.59,300, Allergene.Gluten, "Schoko-Vanille");
        List result = vw.readKuchen();
        assertNotNull(result);
    }


    @Test
    @DisplayName("Ein Kuchen wird geloescht")
    public void delete()  {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insert(Kuchentyp.Kremkuchen, hersteller1, Duration.ofDays(23),
                3.59,400, Allergene.Gluten, "Erdbeere");
        vw.delete(1);
        assertEquals(0, vw.getKuchenlistsize());
    }

    /***********************Herstellertests**************************/

    @Test
    @DisplayName("Neuer Hersteller wird hinzugefuegt")
    public void insertHersteller(){
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertH(hersteller1);
        assertEquals(1, vw.getHerstellerlistsize());
    }


    @Test
    @DisplayName("Hersteller wird mehrfach hinzugefuegt")
    public void insertHersteller2() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        boolean result1 = vw.insertH(hersteller1);
        boolean result2 = vw.insertH(hersteller1);
        assertTrue(result1);
        assertFalse(result2);
    }


    @Test
    @DisplayName("zwei Hersteller werden hinzugefuegt")
    public void  insertHersteller3() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        vw.insertH(hersteller1);
        vw.insertH(hersteller2);
        assertEquals(2, vw.getHerstellerlistsize());
    }


    @Test
    @DisplayName("Herstellerliste wird ausgelesen")
    public void readHersteller2(){
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertH(hersteller1);
        Set<Hersteller> result = vw.readHersteller();
        assertNotNull(result);
    }


    @Test
    @DisplayName("Hersteller wird geloesscht")
    public void deleteHersteller() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertH(hersteller1);
        vw.deleteHersteller("hersteller1");
        assertEquals(0, vw.getHerstellerlistsize());
    }
}
