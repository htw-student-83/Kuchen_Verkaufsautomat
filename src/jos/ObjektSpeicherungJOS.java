package jos;

import geschaeftslogik.verkaufsobjekt.Verwaltung;

import java.io.*;

public class ObjektSpeicherungJOS {

    //Von außen muss noch die Info des Speicherplatzes erfolgen
    //Quelle chatGPT
    public static void persistiereAutomaten(Verwaltung model, OutputStream os){
        try {ObjectOutputStream oOutputStream = new ObjectOutputStream(os);
            oOutputStream.writeObject(model);
            oOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
