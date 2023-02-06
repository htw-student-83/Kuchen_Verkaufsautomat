package jos;

import geschaeftslogik.verkaufsobjekt.Verwaltung;

import java.io.*;

public class ObjektSpeicherungJOS {

    //Von außen muss noch die Info des Speicherplatzes erfolgen
    //Quelle chatGPT
    public static void persistiereAutomaten(Verwaltung model, String filename) {
        //TODO wenn nicht über das Netzwerk, wie können über Stream Daten übertragen werden?
        try (OutputStream fous=new FileOutputStream(filename)){
            ObjectOutputStream outputStream = new ObjectOutputStream(fous);{
                outputStream.writeObject(model);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
