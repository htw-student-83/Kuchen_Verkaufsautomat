package eventsystem.controller;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import vertrag.Allergene;

import java.time.Duration;
import java.util.EventObject;

public class KuchenEvent extends EventObject {
    private Hersteller kuchenhersteller;
    private String kuchensorte;
    private Kuchentyp kuchentyp;
    private double kuchenpreis;
    private Duration kuchenhaltbarkeit;
    private int kuchennaehrwert;
    private Allergene kuchenallergene;
    private int fachnumer;

    public KuchenEvent(Object source,Kuchentyp kuchentyp,Hersteller hersteller,
                       String kuchensorte, double kuchenpreis,
                       int kuchennaehrwert, Allergene kuchenallergene){
        super(source);
        this.kuchenhersteller = hersteller;
        this.kuchentyp = kuchentyp;
        this.kuchensorte = kuchensorte;
        this.kuchenpreis = kuchenpreis;
        this.kuchenhaltbarkeit = kuchenhaltbarkeit;
        this.kuchennaehrwert = kuchennaehrwert;
        this.kuchenallergene = kuchenallergene;
    }

    public KuchenEvent(Object source, int kuchenfachnummer){
        super(source);
        this.fachnumer = kuchenfachnummer;
    }

    public KuchenEvent(Object source, Hersteller hersteller){
        super(source);
        this.kuchenhersteller = hersteller;
    }

    public Hersteller getkuchenhersteller(){return this.kuchenhersteller;}

    public Kuchentyp getkuchentyp(){return this.kuchentyp;}

    public String getkuchensorte(){
        return this.kuchensorte;
    }

    public double getkuchenpreis(){
       return this.kuchenpreis;
    }

    public Duration getkuchenhaltbarkeit(){return this.kuchenhaltbarkeit;}

    public int getkuchennaehrwert(){
        return this.kuchennaehrwert;
    }

    public Allergene getkuchenallergene(){
        return this.kuchenallergene;
    }

    public int getkuchenfachnummer(){
        return this.fachnumer;
    }

}
