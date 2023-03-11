package jbp;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import java.beans.XMLDecoder;
import java.io.*;

public class ObjektLadenJBP {
    public static void automatenzustandLaden(String filename) {
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)))) {
            Verwaltung verwaltung = (Verwaltung) decoder.readObject();
            System.out.print("Groesse Herstellerset: " + verwaltung.readHersteller().size());
            System.out.print("Groesse Kuchenliste: " + verwaltung.readKuchen().size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}