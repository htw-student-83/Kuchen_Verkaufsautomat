package jbp;

import geschaeftslogik.verkaufsobjekt.Verwaltung;

import java.beans.XMLEncoder;
import java.io.*;

public class ObjektSpeicherungJBP {
    public static void persistiereAutomaten(Verwaltung model, OutputStream os) {
        try(XMLEncoder encoder = new XMLEncoder(os)){
            encoder.writeObject(model);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
