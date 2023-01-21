package geschaeftslogiktest;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import vertrag.Allergene;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

public class geschaeftslogikTest {
    @Test
    @DisplayName("Kuchenliste erhaelt einen weiteren Eintrag")
    public void insert1() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1, 3.44,386,
                Duration.ofDays(23), Allergene.Gluten, "Erdbeere");
        List<Kuchen> result = vw.readKuchen();
        assertNotNull(result);
    }

    @Test
    @DisplayName("Kuchenliste bekommt zwei neue Kuchensorten")
    public void insert2(){
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        vw.insertHersteller(hersteller1);
        vw.insertHersteller(hersteller2);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1, 2.44,344,
                Duration.ofDays(23),Allergene.Gluten,"Erdbeere");
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller2,1.34,333,
                Duration.ofDays(25), Allergene.Gluten, "Banane");
        assertEquals(2, vw.getKuchenlisteSize());
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
        vw.insertHersteller(hersteller1);
        vw.insertHersteller(hersteller2);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,2.56,233,
                Duration.ofDays(33), Allergene.Gluten, "Erdbeere");
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller2,1.34,120,
                Duration.ofDays(12), Allergene.Gluten, "Banane");
        assertEquals(2, vw.getKuchenlisteSize());
    }


    @Test
    @DisplayName("Neuer Kuchen bekommt eine Fachnummer")
    public void mockitoTestt(){
        Verwaltung vw = Mockito.spy(Verwaltung.class);
        Hersteller hersteller1 = new Hersteller("Hersteller");
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), Allergene.Gluten, "Banane");
        verify(vw).insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), Allergene.Gluten, "Banane");
        Assertions.assertEquals(1, vw.getKuchenlisteSize());
    }



    @Test
    @DisplayName("Zuweisung eines Einfuegedatums")
    public void mockitoTestt2(){
        Verwaltung vw = Mockito.spy(Verwaltung.class);
        Hersteller hersteller1 = new Hersteller("Hersteller");
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), Allergene.Gluten, "Banane");
        verify(vw).insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), Allergene.Gluten, "Banane");
    }



    @Test
    @DisplayName("Einfuegen von zwei Kremkuchen")
    public void insert3() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        boolean result1 = vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), Allergene.Gluten, "Banane");
        boolean result2 =  vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), Allergene.Gluten, "Banane");
        assertTrue(result1);
        assertTrue(result2);
    }


    @Test
    @DisplayName("Ein Objekt Obstkuchen wird instanziiert")
    public void insert6() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), Allergene.Gluten, "Banane");
        Kuchen obstkuchen = mock(Obstkuchen.class);
        assertInstanceOf(Kuchen.class,obstkuchen);
    }


    @Test
    @DisplayName("Ein Objekt Obsttorte wird instanziiert")
    public void insert7() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), Allergene.Gluten, "Banane");
        Kuchen obsttorte = mock(Obsttorte.class);
        assertInstanceOf(Kuchen.class,obsttorte);
    }


    @Test
    @DisplayName("Zwei verschiedene Kuchen werden eingefuegt.")
    public void insert8() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        vw.insertHersteller(hersteller1);
        vw.insertHersteller(hersteller2);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller2,1.34,120,
                Duration.ofDays(12), Allergene.Gluten, "Banane");
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller2,1.34,120,
                Duration.ofDays(12), Allergene.Gluten, "Banane");
        assertEquals(2, vw.getKuchenlisteSize());
    }



    @Test
    @DisplayName("Kuchen mit unbekanntem Hersteller wird hinzugefuegt")
    public void insert9() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        vw.insertHersteller(hersteller1);
        boolean isValid =  vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), Allergene.Gluten, "Banane");
        boolean isValid2 =  vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller2,1.34,120,
                Duration.ofDays(12), Allergene.Gluten, "Banane");
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
        vw.insertHersteller(hersteller1);
        vw.insertHersteller(hersteller2);
        vw.insertHersteller(hersteller3);
        boolean isValid = vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1, 1.34,344,
                Duration.ofDays(23), Allergene.Gluten, "Erdbeere");
        boolean isValid2 = vw.insertKuchen(Kuchentyp.Obstkuchen, hersteller2,1.22,133,
                Duration.ofDays(23), Allergene.Gluten, "Banane");
        boolean isValid3 = vw.insertKuchen(Kuchentyp.Obsttorte, hersteller3,4.56,455,
                Duration.ofDays(23), Allergene.Erdnuss, "Apfel-Sahne");
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
        vw.insertHersteller(hersteller1);
        vw.insertHersteller(hersteller2);
        vw.insertHersteller(hersteller3);
        boolean isValid =vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1, 1.34,344,
                Duration.ofDays(23), Allergene.Gluten, "Erdbeere");
        boolean isValid2 = vw.insertKuchen(Kuchentyp.Obstkuchen, hersteller2,1.22,133,
                Duration.ofDays(23), Allergene.Gluten, "Banane");
        boolean isValid3 = vw.insertKuchen(Kuchentyp.Obsttorte, hersteller3,4.56,455,
                Duration.ofDays(23), Allergene.Erdnuss, "Apfel-Sahne");
        boolean isValid4 = vw.insertKuchen(Kuchentyp.Obsttorte, hersteller2,3.444,444,
                Duration.ofDays(23), Allergene.Erdnuss, "Kirsch");
        assertTrue(isValid);
        assertTrue(isValid2);
        assertTrue(isValid3);
        assertFalse(isValid4);
    }

    @Test
    @DisplayName("Ein Kuchen mit einem Allergen wird eingefügt.")
    public void insert12() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1, 1.34,344,
                Duration.ofDays(23), Allergene.Gluten, "Erdbeere");
        int groesse = vw.getAllergenenListeSize();
        Assertions.assertEquals(1, groesse);
    }

    @Test
    @DisplayName("Ein Kuchen mit unbekanntem Typ wird eingefügt.")
    public void insert13() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        boolean result = vw.insertKuchen(Kuchentyp.unbekannt, hersteller1, 1.34,344,
                Duration.ofDays(23), Allergene.Gluten, "Erdbeere");
        Assertions.assertFalse(result);
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
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), Allergene.Gluten, "Banane");
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
        vw.insertHersteller(hersteller1);
        vw.insertHersteller(hersteller2);
        vw.insertHersteller(hersteller3);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,3.77, 290,
                Duration.ofDays(23), Allergene.Gluten, "Erdbeere");
        vw.insertKuchen(Kuchentyp.Obstkuchen, hersteller2,2.21,350,
                Duration.ofDays(23), Allergene.Gluten, "Birne");
        vw.insertKuchen(Kuchentyp.Obsttorte, hersteller3,4.32,230,
                Duration.ofDays(23), Allergene.Gluten, "Schoko-Vanille");
        List result = vw.readKuchen();
        assertNotNull(result);
    }

    @Test
    @DisplayName("Allergenenliste wird ausgelesen")
    public void read4() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        Hersteller hersteller3 = new Hersteller("hersteller3");
        vw.insertHersteller(hersteller1);
        vw.insertHersteller(hersteller2);
        vw.insertHersteller(hersteller3);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,3.77, 290,
                Duration.ofDays(23), Allergene.Gluten, "Erdbeere");
        vw.insertKuchen(Kuchentyp.Obstkuchen, hersteller2,2.21,350,
                Duration.ofDays(23), Allergene.Gluten, "Birne");
        vw.insertKuchen(Kuchentyp.Obsttorte, hersteller3,4.32,230,
                Duration.ofDays(23), Allergene.Gluten, "Schoko-Vanille");
        Set result = vw.readAllergener();
        assertNotNull(result);
    }

    @Test
    @DisplayName("Ein Kuchen wird geloescht")
    public void delete()  {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), Allergene.Gluten, "Banane");
        vw.deleteKuchen(1);
        assertEquals(0, vw.getKuchenlisteSize());
    }

    @Test
    @DisplayName("Kuchen wird aus leerer Liste gelöscht")
    public void delete2()  {
        Verwaltung vw = new Verwaltung();
        boolean result = vw.deleteKuchen(1);
        assertFalse(result);
    }

    @Test
    @DisplayName("Unbekannter Kuchen wird geloescht")
    public void delete3()  {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), Allergene.Gluten, "Banane");
        boolean result = vw.deleteKuchen(2);
        assertFalse(result);
    }

    @Test
    @DisplayName("Ein unekannter Hersteller wird geloescht")
    public void delete4()  {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), Allergene.Gluten, "Banane");
        boolean result = vw.deleteHersteller("Hersteller2");
        assertFalse(result);
    }

    /***********************Herstellertests**************************/

    @Test
    @DisplayName("Neuer Hersteller wird hinzugefuegt")
    public void insertHersteller(){
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        assertEquals(1, vw.getHerstellersetSize());
    }


    @Test
    @DisplayName("Hersteller wird mehrfach hinzugefuegt")
    public void insertHersteller2() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        boolean result1 = vw.insertHersteller(hersteller1);
        boolean result2 = vw.insertHersteller(hersteller1);
        assertTrue(result1);
        assertFalse(result2);
    }


    @Test
    @DisplayName("zwei Hersteller werden hinzugefuegt")
    public void  insertHersteller3() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        vw.insertHersteller(hersteller1);
        vw.insertHersteller(hersteller2);
        assertEquals(2, vw.getHerstellersetSize());
    }


    @Test
    @DisplayName("Herstellerliste wird ausgelesen")
    public void readHersteller2(){
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        Set<Hersteller> result = vw.readHersteller();
        assertNotNull(result);
    }


    @Test
    @DisplayName("Hersteller wird geloesscht")
    public void deleteHersteller() {
        Verwaltung vw = new Verwaltung();
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        vw.deleteHersteller("hersteller1");
        assertEquals(0, vw.getHerstellersetSize());
    }

    @Test
    @DisplayName("Ein Kuchen wird geloescht")
    public void delete5()  {
        Verwaltung vw = new Verwaltung();
        boolean result = vw.deleteHersteller("Hersteller1");
        assertFalse(result);
    }
}
