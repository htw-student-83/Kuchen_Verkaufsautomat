package eventsystem.controller.events;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import vertrag.Allergene;

import java.time.Duration;
import java.util.EventObject;
import java.util.Set;

public class EventKuchenEinfuegen extends EventObject {

    private Hersteller hersteller;
    private String kuchenobstsorte;
    private String kuchenkrem;
    private Kuchentyp kuchentyp;
    private double kuchenpreis;
    private Duration kuchenhaltbarkeit;
    private int kuchennaehrwert;
    private Set<Allergene> kuchenallergene;


    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EventKuchenEinfuegen(Object source, Kuchentyp kuchentyp, Hersteller hersteller, double kuchenpreis,
                                int kuchennaehrwert, Duration kuchenhaltbarkeit, Set<Allergene> kuchenallergene,
                                String kuchenobstsorte, String kuchenkrem) {
        super(source);
        this.kuchentyp = kuchentyp;
        this.hersteller = hersteller;
        this.kuchenpreis = kuchenpreis;
        this.kuchennaehrwert = kuchennaehrwert;
        this.kuchenhaltbarkeit = kuchenhaltbarkeit;
        this.kuchenallergene = kuchenallergene;
        this.kuchenobstsorte = kuchenobstsorte;
        this.kuchenkrem = kuchenkrem;
    }

    public Kuchentyp getkuchentyp(){return this.kuchentyp;}

    public Hersteller getHersteller(){return this.hersteller;}

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

}
