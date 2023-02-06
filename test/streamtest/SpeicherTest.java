package streamtest;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import jbp.ObjektSpeicherungJBP;
import jos.ObjektLadenJOS;
import jos.ObjektSpeicherungJOS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import vertrag.Allergene;

import java.io.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SpeicherTest {
    Verwaltung model;
    Set<Allergene> allergeneSet = null;
    @Test
    @DisplayName("Daten werden ein- und ausgelesen")
    public void streamTest() throws IOException {
        byte[] expectedOutput  = "Das ist ein Test".getBytes();
        InputStream inputStream = new ByteArrayInputStream(expectedOutput);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int data = inputStream.read();
        while (data!=-1){
            outputStream.write(data);
            data = inputStream.read();
        }
        byte[] actualOutput = outputStream.toByteArray();
        assertArrayEquals(expectedOutput, actualOutput);//Hier wird das ByteArray vergleichen
        Assertions.assertEquals("Das ist ein Test", outputStream.toString());//Hier wird der Inhalt vergleichen
    }

    @Test
    @DisplayName("Zustand des Kuchenautomaten mit JBP speichern")
    public void automatenSpeichern(){
        model = new Verwaltung(1);
        Hersteller hersteller1 = new Hersteller("hersteller1");
        allergeneSet = new HashSet<>();
        allergeneSet.add(Allergene.Gluten);
        allergeneSet.add(Allergene.Erdnuss);
        model.insertHersteller(hersteller1);
        model.insertKuchen(Kuchentyp.Kremkuchen, hersteller1, 2.45,123,
                Duration.ofDays(23), allergeneSet, "","Butter");
        ObjektSpeicherungJOS.persistiereAutomaten(model, "automaten.txt");
    }

/*
    @Test
    @DisplayName("Zustand des Kuchenautomaten mit JBP speichern")
    public void automatenzustandLaden(){
        this.model = ObjektLadenJOS.reloadAutomt();
        List<Kuchen> kuchen = this.model.readKuchen();

    }
 */

}
