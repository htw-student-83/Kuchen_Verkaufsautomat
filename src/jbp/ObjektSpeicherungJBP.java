package jbp;

import geschaeftslogik.verkaufsobjekt.Verwaltung;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class ObjektSpeicherungJBP {
    public static void persistiereAutomaten(Verwaltung model, String filename) {
        try(XMLEncoder encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)))){
            encoder.writeObject(model);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
