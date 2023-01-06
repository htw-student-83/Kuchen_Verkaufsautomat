package geschaeftslogik;

import java.io.Serializable;

public class Kuchenautomat implements Serializable {
    private int belegtekuchenfeacher = 0;
    private int KUCHENFAECHER_MAX = 3;

    public int getAnzahlbelegteFaecher() {
        return this.belegtekuchenfeacher;
    }

    //public void setAnzahlbelegteFaecher(int zahl){this.belegtekuchenfeacher = this.belegtekuchenfeacher + zahl;}

    public int getKapazity(){
        return this.KUCHENFAECHER_MAX;
    }

    public void boxdecrement() {
        this.belegtekuchenfeacher--;
    }

    public void boxincrement(){
        this.belegtekuchenfeacher++;
    }
}
