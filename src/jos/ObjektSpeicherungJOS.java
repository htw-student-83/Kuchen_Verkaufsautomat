package jos;

import geschaeftslogik.Verwaltung;

import java.io.*;

public class ObjektSpeicherungJOS {

    static String filenameAutomat = "automaten.txt";

    public static void persistiereAutomaten(){
        Verwaltung model = new Verwaltung();
        try (FileOutputStream fous=new FileOutputStream(filenameAutomat)){
            ObjectOutputStream outputStream = new ObjectOutputStream(fous);{
                outputStream.writeObject(model);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
