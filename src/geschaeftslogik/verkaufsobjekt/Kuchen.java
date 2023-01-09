package geschaeftslogik.verkaufsobjekt;

import vertrag.Allergene;
import vertrag.Hersteller;
import vertrag.Obsttorte;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public abstract class Kuchen implements Obsttorte, Serializable {
    private int naehrwert;
    private int fachnummer;
    private Duration haltbarkeit;
    private Hersteller hersteller;
    private Allergene allergene;
    private Date einfuegedatum;
    private BigDecimal preis;
    private Date inspektionsdatum;


    public Kuchen(int naehrwert, Hersteller hersteller, Duration haltbarkeit,
                    Allergene allergene, BigDecimal preis){
        this.naehrwert = naehrwert;
        this.haltbarkeit = haltbarkeit;
        this.hersteller = hersteller;
        this.allergene = allergene;
        this.preis = preis;
    }

    //Konstruktor für die Wiederherstellung von Daten
    public Kuchen(int fachnummer, int naehrwert, Hersteller hersteller,
                  Duration haltbarkeit, Date einfuegedatum, Allergene allergene,
                  BigDecimal preis){
        this.fachnummer = fachnummer;
        this.naehrwert = naehrwert;
        this.haltbarkeit = haltbarkeit;
        this.hersteller = hersteller;
        this.allergene = allergene;
        this.preis = preis;
        this.einfuegedatum = einfuegedatum;
    }


    public Hersteller getHersteller() {
        return this.hersteller;
    }
    protected void setHersteller(Hersteller herstellername) {
        this.hersteller = herstellername;
    }
    public Collection<Allergene> getAllergene() {
        return Collections.singleton(this.allergene);
    }
    protected void setAllergene(Allergene allergene){
        this.allergene = allergene;
    }
    public int getNaehrwert() {
        return this.naehrwert;
    }
    protected void setNaehrwert(int naehrwert) {this.naehrwert = naehrwert;}
    public Duration getHaltbarkeit() {
        return this.haltbarkeit;
    }
    protected void setHaltbarkeit(Duration haltbarkiet){this.haltbarkeit = haltbarkeit;}
    public BigDecimal getPreis() {
        return this.preis;
    }
    protected void setPreis(BigDecimal preis) {
        this.preis = preis;
    }
    public Date getInspektionsdatum() {
        return this.inspektionsdatum;
    }
    protected void setInspektionsdatum(Date iDatum) {
        this.inspektionsdatum = iDatum;
    }
    public int getFachnummer() {
        return this.fachnummer;
    }
    protected void setFachnummer(int newNr){
        this.fachnummer = newNr;
    }
    public Date getEinfuegedatum(){
        return this.einfuegedatum;
    }
    protected void setEinfuegedatum(Date datum){
        this.einfuegedatum = datum;
    }
    public abstract String getKremsorte();

}
