import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CLITest {

    @Test
    @DisplayName("Hersteller wird über das CLI eingefügt.")
    public void userInputTest() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("c".getBytes());//Usereingabe wird in bytes gewandelt
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//Ein Objekt vom Ausgabestrom wird instanziiert
        //Ein Objekt vom Printstream wird erzeugt, um den
        //Ausgabestrom auf den Display zu produzieren
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        //Objekt der zu testenen Klasse wird erzeugt
        Kuchen_VerkaufsautomatRun userInputExample = new Kuchen_VerkaufsautomatRun(inputStream, ps);
        //die eigentliche Methode wird nun gestartet..
        userInputExample.startAutomat();
        //die Displayausgabe vom System wird als String gespeichert
        String outputText = byteArrayOutputStream.toString();
        String out1 = "Ausgabe:";
        String output = outputText.substring(outputText.indexOf(out1) + out1.length()).trim();
        Assertions.assertEquals( "Hersteller einfügen", output);
    }


    @Test
    @DisplayName("Hersteller wird über das CLI angezeigt.")
    public void userInputTest2() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("r".getBytes());//Usereingaben werden in Bytes gewandelt
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//Ein Objekt vom Ausgabestrom wird instanziiert
        //Ein Objekt vom Printstream wird erzeugt, um den
        //Ausgabestrom auf den Display zu produzieren
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        Kuchen_VerkaufsautomatRun userInputExample = new Kuchen_VerkaufsautomatRun(inputStream, ps);//Objekt der zu testenen Klasse wird erzeugt
        userInputExample.startAutomat(); //die eigentliche Methode wird nun gestartet..
        String outputText = byteArrayOutputStream.toString();// die Displayausgabe vom System wird als String gespeichert
        String out1 = "Ausgabe:";
        String output = outputText.substring(outputText.indexOf(out1) + out1.length()).trim();
        Assertions.assertEquals( "Hersteller anzeigen.", output);
    }
}
