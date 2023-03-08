package eventsystem.controller.events;

import java.time.Duration;
import java.util.EventObject;


public class EventKuchenAnzeigen extends EventObject {

    private int kuchenID;
    private String kuchenobstsorte;
    private String kuchenkrem;
    private double kuchenpreis;
    private Duration kuchenhaltbarkeit;
    private int kuchennaehrwert;


    public EventKuchenAnzeigen(Object source, int kuchenID, double kuchenpreis, int kuchennaehrwert,
                               Duration kuchenhaltbarkeit, String kuchenobstsorte, String kuchenkrem) {
        super(source);
        this.kuchenID = kuchenID;
        this.kuchenpreis = kuchenpreis;
        this.kuchennaehrwert = kuchennaehrwert;
        this.kuchenhaltbarkeit = kuchenhaltbarkeit;
        this.kuchenobstsorte = kuchenobstsorte;
        this.kuchenkrem = kuchenkrem;
    }

    public int getKuchenID(){
        return this.kuchenID;
    }

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
}
