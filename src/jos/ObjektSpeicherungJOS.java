package jos;

import geschaeftslogik.verkaufsobjekt.Verwaltung;

import java.io.*;

public class ObjektSpeicherungJOS {

    static String filenameAutomat = "automaten.txt";

    public static void persistiereAutomaten(Verwaltung model){
        try (FileOutputStream fous=new FileOutputStream(filenameAutomat)){
            ObjectOutputStream outputStream = new ObjectOutputStream(fous);{
                outputStream.writeObject(model);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
