package jos;

import geschaeftslogik.verkaufsobjekt.Verwaltung;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjektLadenJOS {
    static String filenameAutomat = "automaten.txt";
    public static Verwaltung reloadAutomt() {
        try (FileInputStream inputStream =new FileInputStream(filenameAutomat)){
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return (Verwaltung) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
