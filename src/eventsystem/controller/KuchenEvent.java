package eventsystem.controller;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import vertrag.Allergene;

import java.time.Duration;
import java.util.EventObject;
import java.util.Set;

public class KuchenEvent extends EventObject {
    private Hersteller kuchenhersteller;
    private String herstellername;
    private String kuchenobstsorte;
    private String kuchenkrem;
    private Kuchentyp kuchentyp;
    private String boden;
    private String[] belaege;
    private double kuchenpreis;
    private Duration kuchenhaltbarkeit;
    private int kuchennaehrwert;
    private Set<Allergene> kuchenallergene;
    private int fachnumer;

    public KuchenEvent(Object source, Kuchentyp kuchentyp, Hersteller hersteller,
                       double kuchenpreis, int kuchennaehrwert, Duration haltbarkeit,
                       Set<Allergene> kuchenallergene, String obstsorte, String kremsorte){
        super(source);
        this.kuchentyp = kuchentyp;
        this.kuchenhersteller = hersteller;
        this.kuchenpreis = kuchenpreis;
        this.kuchennaehrwert = kuchennaehrwert;
        this.kuchenhaltbarkeit = haltbarkeit;
        this.kuchenallergene = kuchenallergene;
        this.kuchenobstsorte = obstsorte;
        this.kuchenkrem = kremsorte;
    }


    public KuchenEvent(Object source, int kuchenfachnummer){
        super(source);
        this.fachnumer = kuchenfachnummer;
    }

    public KuchenEvent(Object source, Hersteller hersteller){
        super(source);
        this.kuchenhersteller = hersteller;
    }

    public KuchenEvent(Object source, String hersteller){
        super(source);
        this.herstellername = hersteller;
    }

    public KuchenEvent(Object source, String boden, Hersteller hersteller, String[] belagarray){
        super(source);
        this.kuchenhersteller = hersteller;
        this.boden = boden;
        this.belaege = belagarray;
    }


    public Hersteller getkuchenhersteller(){return this.kuchenhersteller;}
    public Kuchentyp getkuchentyp(){return this.kuchentyp;}
    public String getKuchenobstsorte(){
        return this.kuchenobstsorte;
    }
    public String getKuchenkremsorte() { return this.kuchenkrem; }
    public double getkuchenpreis(){
       return this.kuchenpreis;
    }
    public Duration getkuchenhaltbarkeit(){return this.kuchenhaltbarkeit;}
    public int getkuchennaehrwert(){
        return this.kuchennaehrwert;
    }
    public Set<Allergene> getkuchenallergene(){
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
    public String getNameHersteller() { return this.herstellername; }
}
