package geschaeftslogik.verkaufsobjekt;

import geschaeftslogik.dekoratorpatter.vertrag.Kuchenboden;
import geschaeftslogik.dekoratorpatter.vertrag.Kuchenbelag;
import vertrag.Allergene;
import vertrag.Hersteller;
import vertrag.Obsttorte;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.Set;

public abstract class Kuchen implements Obsttorte, Serializable {
    private int naehrwert;
    private int fachnummer;
    private Duration haltbarkeit;
    private Hersteller hersteller;
    private Set<Allergene> allergene;
    private Date einfuegedatum;
    private BigDecimal preis;
    private Date inspektionsdatum;
    private String typ;
    private String name;


    public Kuchen(String kuchentyp, Hersteller hersteller,  BigDecimal preis,  int naehrwert ,
                  Duration haltbarkeit, Set<Allergene> allergene){
        this.naehrwert = naehrwert;
        this.haltbarkeit = haltbarkeit;
        this.hersteller = hersteller;
        this.allergene = allergene;
        this.preis = preis;
        this.typ = kuchentyp;
    }

    public Kuchen(String kuchentyp, Hersteller hersteller,  BigDecimal preis,  int naehrwert ,
                  Duration haltbarkeit){
        this.naehrwert = naehrwert;
        this.haltbarkeit = haltbarkeit;
        this.hersteller = hersteller;
        this.preis = preis;
        this.typ = kuchentyp;
    }

    public Kuchen(){}


    //Konstruktor für die Wiederherstellung von Daten
    public Kuchen(int fachnummer, int naehrwert, Hersteller hersteller,
                  Duration haltbarkeit, Date einfuegedatum, Set<Allergene> allergene,
                  BigDecimal preis){
        this.fachnummer = fachnummer;
        this.naehrwert = naehrwert;
        this.haltbarkeit = haltbarkeit;
        this.hersteller = hersteller;
        this.allergene = allergene;
        this.preis = preis;
        this.einfuegedatum = einfuegedatum;
    }


    public String getTyp(){return this.typ;}
    public Hersteller getHersteller() {
        return this.hersteller;
    }
    protected void setHersteller(Hersteller herstellername) {
        this.hersteller = herstellername;
    }
    public Set<Allergene> getAllergene() { return this.allergene;}
    protected void setAllergene(Set<Allergene> allergene){
        this.allergene = allergene;
    }
    public int getNaehrwert() {
        return this.naehrwert;
    }
    protected void setNaehrwert(int naehrwert) {this.naehrwert = naehrwert; }
    public int getHaltbarkeit() {
        return (int) this.haltbarkeit.toDays();
    }
    protected void setHaltbarkeit(Duration haltbarkiet){this.haltbarkeit = haltbarkeit;}
    public BigDecimal getPreis() {
        return this.preis;
    }
    protected void setPreis(BigDecimal preis) {
        this.preis = preis;
    }
    public Date getInspektionsdatum() { return this.inspektionsdatum; }
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
    public String getName(){return this.name;}
    public String toString(){
        return "\nEinfuegedatum: " + getEinfuegedatum() + "\nKuchenID: " + getFachnummer() + "\nSorte: " + getKremsorte() +
                "\nInspektionsdatum: " + getInspektionsdatum() + "\nHaltbarkeit: " + getHaltbarkeit();
    }
}
