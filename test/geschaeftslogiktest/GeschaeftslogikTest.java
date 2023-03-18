package geschaeftslogiktest;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import vertrag.Allergene;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GeschaeftslogikTest {
    Set<Allergene> allergeneSet = null;
    Set<Allergene> allergeneSet2 = null;
    Set<Allergene> allergeneSet3 = null;


    @Test
    @DisplayName("Kuchenliste erhaelt einen weiteren Eintrag")
    public void insert1() {
        Verwaltung vw = new Verwaltung(1);
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1, 3.44,386,
                Duration.ofDays(23), allergeneSet, "", "Butter");
        List<Kuchen> result = vw.readKuchen();
        assertNotNull(result);
    }

    @Test
    @DisplayName("Kuchenliste bekommt zwei neue Kuchensorten")
    public void insert2(){
        Verwaltung vw = new Verwaltung(2);
      //
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        vw.insertHersteller(hersteller1);
        vw.insertHersteller(hersteller2);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1, 2.44,344,
                Duration.ofDays(23), allergeneSet,"", "Sahne");
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller2,1.34,333,
                Duration.ofDays(25), allergeneSet, "", "Butter");
        assertEquals(2, vw.getKuchenlisteSize());
    }

    @Test
    @DisplayName("Zwei Kuchen des gleichen Typs mit zwei Herstellern werden hinzugefuegt")
    public void mockitoTest(){
        Verwaltung vw = new Verwaltung(2);
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        Hersteller hersteller1 = mock(Hersteller.class);
        Hersteller hersteller2 = mock(Hersteller.class);
        //Wert von beiden Herstellerobjekten ist null
        //Um Methodenaufrufe zu testen -> mit Spy arbeiten
        Mockito.when(hersteller1.getName()).thenReturn("OTTO");
        Mockito.when(hersteller2.getName()).thenReturn("hersteller");
        vw.insertHersteller(hersteller1);
        vw.insertHersteller(hersteller2);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,2.56,233,
                Duration.ofDays(33), allergeneSet, "", "Butter");
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller2,1.34,120,
                Duration.ofDays(12), allergeneSet, "", "Sahne");
        assertEquals(2, vw.getKuchenlisteSize());
    }


    @Test
    @DisplayName("Innerhalb der insertKuchen() wird die insertAllergene() aufgerufen")
    public void mockitoTestt2(){
        Verwaltung vw = Mockito.spy(Verwaltung.class);
        Hersteller hersteller1 = new Hersteller("Hersteller");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), allergeneSet, "", "Sahne");
        //verify(vw).insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,Duration.ofDays(12), allergeneSet, "", "Sahne");
        verify(vw).insertAllergen(allergeneSet);
    }



    @Test
    @DisplayName("Einfuegen von zwei Kremkuchen")
    public void insert3() {
        Verwaltung vw = new Verwaltung(2);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertHersteller(hersteller1);
        boolean result1 = vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), allergeneSet, "", "Sahne");
        boolean result2 =  vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), allergeneSet, "", "Butter");
        assertTrue(result1);
        assertTrue(result2);
    }


    @Test
    @DisplayName("Ein Objekt Obstkuchen wird instanziiert")
    public void insert6() {
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), allergeneSet, "", "Sahne");
        Kuchen obstkuchen = mock(Obstkuchen.class);
        assertInstanceOf(Kuchen.class,obstkuchen);
    }


    @Test
    @DisplayName("Ein Objekt Obsttorte wird instanziiert")
    public void insert7() {
        Verwaltung vw = new Verwaltung(2);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        vw.insertKuchen(Kuchentyp.Obsttorte, hersteller1,1.34,120,
                Duration.ofDays(12), allergeneSet, "", "Sahne");
        Kuchen obsttorte = mock(Obsttorte.class);
        assertInstanceOf(Kuchen.class,obsttorte);
    }


    @Test
    @DisplayName("Zwei verschiedene Kuchen werden eingefuegt.")
    public void insert8() {
        Verwaltung vw = new Verwaltung(2);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        vw.insertHersteller(hersteller1);
        vw.insertHersteller(hersteller2);
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller2,1.34,120,
                Duration.ofDays(12), allergeneSet, "","Sahne");
        allergeneSet2 = new HashSet<>();
        vw.insertKuchen(Kuchentyp.Obstkuchen, hersteller2,1.34,120,
                Duration.ofDays(12),allergeneSet2,"Erdbeere", "");
        assertEquals(2, vw.getKuchenlisteSize());
    }



    @Test
    @DisplayName("Kuchen mit unbekanntem Hersteller wird hinzugefuegt")
    public void insert9() {
        Verwaltung vw = new Verwaltung(2);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertHersteller(hersteller1);
        boolean isValid =  vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), allergeneSet, "", "Sahne");
        boolean isValid2 =  vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller2,1.34,120,
                Duration.ofDays(12), allergeneSet,"", "Butter");
        assertTrue(isValid);
        assertFalse(isValid2);
    }


    @Test
    @DisplayName("Einfuegen von drei verschiedenen Kuchen")
    public void insert10(){
        Verwaltung vw = new Verwaltung(3);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        Hersteller hersteller3 = new Hersteller("hersteller3");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertHersteller(hersteller1);
        vw.insertHersteller(hersteller2);
        vw.insertHersteller(hersteller3);
        boolean isValid = vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1, 1.34,344,
                Duration.ofDays(23), allergeneSet, "", "Sahne");
        allergeneSet2 = new HashSet<>();
        boolean isValid2 = vw.insertKuchen(Kuchentyp.Obstkuchen, hersteller2,1.22,133,
                Duration.ofDays(23), allergeneSet2,"Banane", "");
        allergeneSet3 = new HashSet<>();
        allergeneSet3.add(Allergene.Gluten);
        boolean isValid3 = vw.insertKuchen(Kuchentyp.Obsttorte, hersteller3,4.56,455,
                Duration.ofDays(23), allergeneSet3, "Apfel", "Sahne");
        assertTrue(isValid);
        assertTrue(isValid2);
        assertTrue(isValid3);
    }


    @Test
    @DisplayName("Es werden zu viele Kuchen eingefuegt.")
    public void insert11() {
        Verwaltung vw = new Verwaltung(3);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        Hersteller hersteller3 = new Hersteller("hersteller3");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertHersteller(hersteller1);
        vw.insertHersteller(hersteller2);
        vw.insertHersteller(hersteller3);
        boolean isValid =vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1, 1.34,344,
                Duration.ofDays(23), allergeneSet, "", "Sahne");
        allergeneSet2 = new HashSet<>();
        boolean isValid2 = vw.insertKuchen(Kuchentyp.Obstkuchen, hersteller2,1.22,133,
                Duration.ofDays(23), allergeneSet2,"Banane","");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        boolean isValid3 = vw.insertKuchen(Kuchentyp.Obsttorte, hersteller3,4.56,455,
                Duration.ofDays(23), allergeneSet,"Apfel", "Butter");
        boolean isValid4 = vw.insertKuchen(Kuchentyp.Obsttorte, hersteller2,3.444,444,
                Duration.ofDays(23), allergeneSet,"Kirsch", "Sahne");
        assertTrue(isValid);
        assertTrue(isValid2);
        assertTrue(isValid3);
        assertFalse(isValid4);
    }

    @Test
    @DisplayName("Ein Kuchen mit einem Allergen wird eingefügt.")
    public void insert12() {
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1, 1.34,344,
                Duration.ofDays(23), allergeneSet,"", "Sahne");
        int groesse = vw.getAllergenenSetSize();
        Assertions.assertEquals(1, groesse);
    }

    @Test
    @DisplayName("Ein Kuchen mit unbekanntem Typ wird eingefügt.")
    public void insert13() {
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Erdnuss);
        boolean result = vw.insertKuchen(Kuchentyp.unbekannt, hersteller1, 1.34,344,
                Duration.ofDays(23), allergeneSet,"Erdbeere", "Sahne");
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Die Kremsorte eines Kremkuchens wird initialisiert.")
    public void insert14() {
        Hersteller hersteller = new Hersteller("Bosch");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        Kremkuchen kremkuchen = new Kremkuchen("Kremkuchen", hersteller, BigDecimal.valueOf(3.44),
                340, Duration.ofDays(33), allergeneSet,"Sahne");
        Assertions.assertEquals("Sahne", kremkuchen.getKremsorte());
    }


    @Test
    @DisplayName("Die Sorte eines Obstkuchens wird initialisiert.")
    public void insert15() {
        Hersteller hersteller = new Hersteller("Bosch");
        Kuchen obstkuchen = new Obstkuchen("Obstkuchen", hersteller, BigDecimal.valueOf(3.44),
                340, Duration.ofDays(33),"Apfel", "");
        //TODO die obstsorte ist kein Array sondern ein String
        Assertions.assertEquals("Apfel", obstkuchen.getKremsorte());
    }


    @Test
    @DisplayName("Die Sorte einer Obsttorte wird initialisiert.")
    public void insert16() {
        Hersteller hersteller = new Hersteller("Bosch");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        Obsttorte kremkuchen = new Obsttorte("Obsttorte", hersteller, BigDecimal.valueOf(3.44), 340,
                Duration.ofDays(33), allergeneSet,"Apfel" ,"Sahne");
        Assertions.assertEquals("Apfel", kremkuchen.getKremsorte());
    }

    @Test
    @DisplayName("Die Kremsorte wird initialisiert.")
    public void einenKuchenLesen() {
        Verwaltung vw = new Verwaltung(2);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1, 4.34,244,
                Duration.ofDays(33), allergeneSet,"Erdbeere", "Sahne");
        allergeneSet2 = new HashSet<>();
        vw.insertKuchen(Kuchentyp.Obstkuchen, hersteller1, 1.34,344,
                Duration.ofDays(23), allergeneSet2,"Erdbeere","");
        List<Kuchen> kuchen = vw.readKuchen(vw.readKuchen(), "Kremkuchen");
        Assertions.assertNotNull(kuchen);
    }

    @Test
    @DisplayName("Einen Kuchen filtern.")
    public void einenKuchenLesen2() {
        Verwaltung vw = new Verwaltung(2);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1, 4.34,244,
                Duration.ofDays(33), allergeneSet,"Erdbeere", "Sahne");
        List<Kuchen> kuchen = vw.readKuchen(vw.readKuchen(), "Obstkuchen");
        Assertions.assertTrue(kuchen.isEmpty());
    }


    @Test
    @DisplayName("Leere Kuchenliste wird ausgelesen")
    public void read(){
        Verwaltung vw = new Verwaltung(0);
        List result = vw.readKuchen();
        assertTrue(result.isEmpty());
    }



    @Test
    @DisplayName("Kuchenliste wird ausgelesen")
    public void read2() {
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), allergeneSet,"Banane", "Sahne");
        List result = vw.readKuchen();
        assertNotNull(result);
    }


    @Test
    @DisplayName("Kuchenliste mit meheren Kuchen wird ausgelesen")
    public void read3() {
        Verwaltung vw = new Verwaltung(3);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        Hersteller hersteller3 = new Hersteller("hersteller3");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertHersteller(hersteller1);
        vw.insertHersteller(hersteller2);
        vw.insertHersteller(hersteller3);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,3.77, 290,
                Duration.ofDays(23), allergeneSet,"Erdbeere", "Sahne");
        allergeneSet2 = new HashSet<>();
        vw.insertKuchen(Kuchentyp.Obstkuchen, hersteller2,2.21,350,
                Duration.ofDays(23), allergeneSet2,"Birne", "");
        allergeneSet3 = new HashSet<>();
        allergeneSet3.add(Allergene.Gluten);
        vw.insertKuchen(Kuchentyp.Obsttorte, hersteller3,4.32,230,
                Duration.ofDays(23), allergeneSet3,"Schoko", "Sahne");
        List result = vw.readKuchen();
        assertNotNull(result);
    }

    @Test
    @DisplayName("Kuchn wird inspiziert")
    public void edit() {
        int fachnummer = 1;
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,3.77, 290,
                Duration.ofDays(23), allergeneSet,"Erdbeere", "Sahne");
        boolean isChecked = vw.editKuchen(fachnummer);
        assertTrue(isChecked);
    }

    @Test
    @DisplayName("Unbekannter Kuchn wird inspiziert")
    public void edit2() {
        int fachnummer = 2;
        Verwaltung vw = new Verwaltung(2);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,3.77, 290,
                Duration.ofDays(23), allergeneSet,"Erdbeere", "Sahne");
        boolean isChecked = vw.editKuchen(fachnummer);
        assertFalse(isChecked);
    }

    @Test
    @DisplayName("Allergenenliste wird ausgelesen")
    public void read4() {
        Verwaltung vw = new Verwaltung(3);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        Hersteller hersteller3 = new Hersteller("hersteller3");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertHersteller(hersteller1);
        vw.insertHersteller(hersteller2);
        vw.insertHersteller(hersteller3);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,3.77, 290,
                Duration.ofDays(23), allergeneSet,"Erdbeere", "Butter");
        allergeneSet2 = new HashSet<>();
        vw.insertKuchen(Kuchentyp.Obstkuchen, hersteller2,2.21,350,
                Duration.ofDays(23),allergeneSet2,"Birne", "");
        allergeneSet3 = new HashSet<>();
        allergeneSet3.add(Allergene.Gluten);
        vw.insertKuchen(Kuchentyp.Obsttorte, hersteller3,4.32,230,
                Duration.ofDays(23), allergeneSet3,"Schoko", "Sahne");
        Set result = vw.readAllergener();
        assertNotNull(result);
    }


    @Test
    @DisplayName("Ein Kuchen wird geloescht")
    public void delete()  {
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), allergeneSet,"Banane", "Sahne");
        vw.deleteKuchen(1);
        assertEquals(0, vw.getKuchenlisteSize());
    }

    @Test
    @DisplayName("Kuchen wird aus leerer Liste gelöscht")
    public void delete2()  {
        Verwaltung vw = new Verwaltung(1);
        boolean result = vw.deleteKuchen(1);
        assertFalse(result);
    }

    @Test
    @DisplayName("Unbekannter Kuchen wird geloescht")
    public void delete3()  {
        Verwaltung vw = new Verwaltung(2);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), allergeneSet,"Banane", "Sahne");
        boolean result = vw.deleteKuchen(2);
        assertFalse(result);
    }

    @Test
    @DisplayName("Ein unbekannter Hersteller wird geloescht")
    public void delete4()  {
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), allergeneSet, "Banane", "Sahne");
        boolean result = vw.deleteHersteller("Hersteller2");
        assertFalse(result);
    }

    @Test
    @DisplayName("Zu Beginn ist die Kapazität unverändert")
    public void capazityTest() {
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        Assertions.assertEquals(1, vw.getKapazity());
    }

    @Test
    @DisplayName("Nach einfügen eines Kuchens verändert sich das urspr. allergenSet.")
    public void originAllertSetTest() {
        Set<Allergene> newList = new HashSet<>();
        newList.add(Allergene.Sesamsamen);
        newList.add(Allergene.Haselnuss);
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.fillAlleAllergeneSet();
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), allergeneSet, "", "Sahne");
        Assertions.assertEquals(newList, vw.readAllergeneNotinCakes());
    }

    @Test
    @DisplayName("Es wird kein Kuchen eingefügt.")
    public void NumberOfBoxexTest() {
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.fillAlleAllergeneSet();
        vw.insertHersteller(hersteller1);
        Assertions.assertEquals(0, vw.getAnzahlBelgeteFaecher());
    }

    @Test
    @DisplayName("Ein Kuchen wird eingefügt.")
    public void NumberOfBoxexTest2() {
        Verwaltung vw = new Verwaltung(1);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        vw.fillAlleAllergeneSet();
        vw.insertHersteller(hersteller1);
        vw.insertKuchen(Kuchentyp.Kremkuchen, hersteller1,1.34,120,
                Duration.ofDays(12), allergeneSet, "", "Sahne");
        Assertions.assertEquals(1, vw.getAnzahlBelgeteFaecher());
    }

    /***********************Herstellertests**************************/

    @Test
    @DisplayName("Neuer Hersteller wird hinzugefuegt")
    public void insertHersteller(){
        Verwaltung vw = new Verwaltung(0);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        assertEquals(1, vw.getHerstellerSetSize());
    }


    @Test
    @DisplayName("Hersteller wird mehrfach hinzugefuegt")
    public void insertHersteller2() {
        Verwaltung vw = new Verwaltung(0);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        boolean result1 = vw.insertHersteller(hersteller1);
        boolean result2 = vw.insertHersteller(hersteller1);
        assertTrue(result1);
        assertFalse(result2);
    }


    @Test
    @DisplayName("zwei Hersteller werden hinzugefuegt")
    public void  insertHersteller3() {
        Verwaltung vw = new Verwaltung(0);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        Hersteller hersteller2 = new Hersteller("hersteller2");
        vw.insertHersteller(hersteller1);
        vw.insertHersteller(hersteller2);
        assertEquals(2, vw.getHerstellerSetSize());
    }


    @Test
    @DisplayName("Herstellerliste wird ausgelesen")
    public void readHersteller2(){
        Verwaltung vw = new Verwaltung(0);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        Set<Hersteller> result = vw.readHersteller();
        assertNotNull(result);
    }


    @Test
    @DisplayName("Hersteller wird geloesscht")
    public void deleteHersteller() {
        Verwaltung vw = new Verwaltung(0);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        vw.insertHersteller(hersteller1);
        vw.deleteHersteller("hersteller1");
        assertEquals(0, vw.getHerstellerSetSize());
    }

    @Test
    @DisplayName("Ein Kuchen wird geloescht")
    public void delete5()  {
        Verwaltung vw = new Verwaltung(0);
        boolean result = vw.deleteHersteller("Hersteller1");
        assertFalse(result);
    }
}
