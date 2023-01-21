package geschaeftslogik;

import java.io.Serializable;

public class Kuchenautomat implements Serializable {
    private int belegtekuchenfeacher = 0;
    private int KUCHENFAECHER_MAX;
    public int getAnzahlbelegteFaecher() {
        return this.belegtekuchenfeacher;
    }


    public int getKapazity(){
        return this.KUCHENFAECHER_MAX;
    }

    public void setKapazitaet(int newKapazitaet){
        this.KUCHENFAECHER_MAX = newKapazitaet;
    }

    public void boxdecrement() {
        this.belegtekuchenfeacher--;
    }

    public void boxincrement(){
        this.belegtekuchenfeacher++;
    }

}
