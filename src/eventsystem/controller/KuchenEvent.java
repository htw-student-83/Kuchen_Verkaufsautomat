package eventsystem.controller;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.dekoratorpatter.vertrag.Kuchenboden;
import vertrag.Allergene;

import java.time.Duration;
import java.util.EventObject;

public class KuchenEvent extends EventObject {
    private Hersteller kuchenhersteller;
    private String kuchensorte;
    private Kuchentyp kuchentyp;
    private String boden;
    private String[] belaege;
    private double kuchenpreis;
    private Duration kuchenhaltbarkeit;
    private int kuchennaehrwert;
    private Allergene kuchenallergene;
    private int fachnumer;

    public KuchenEvent(Object source, Kuchentyp kuchentyp, Hersteller hersteller,
                       double kuchenpreis, int kuchennaehrwert, Duration haltbarkeit,
                       Allergene kuchenallergene, String obstsorte, String kremsorte){
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

    public KuchenEvent(Object source, String boden, Hersteller hersteller, String[] belagarray){
        super(source);
        this.kuchenhersteller = hersteller;
        this.boden = boden;
        this.belaege = belagarray;
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

    public String getKuchenBoden(){
        return this.boden;
    }
    public String[] getKuchenBelaege(){
        return this.belaege;
    }

}
