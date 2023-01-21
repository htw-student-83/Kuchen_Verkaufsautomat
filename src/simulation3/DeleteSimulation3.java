package simulation3;

import geschaeftslogik.verkaufsobjekt.Kuchen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import java.text.ParseException;
import java.util.Date;

public class DeleteSimulation3 implements Runnable {
    private Date insertDate = null;
    private final Object monitor;
    private Verwaltung model;

    private int anzahlzuLoeschendeKuchen = 0;
    private long aeltesteinspektionzeit = 0;
    private Date aeltesteZeitDate = null;

    public DeleteSimulation3(Verwaltung kl, Object monitor) {
        this.model = kl;
        this.monitor = monitor;
    }

    public Date getInsertDate(){
        return this.insertDate;
    }


    @Override
    public void run() {
        try {
            setInspektionsdatum();
        } catch (InterruptedException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInspektionsdatum() throws InterruptedException, ParseException {
        synchronized(monitor){//Beginn des kritischen Bereichs

            // prüfen, ob die Liste 3 Kuchen enthält, falls nein -> warten
            //while (this.model.getSizekuchenliste() < 3) wait();
            int kontrollzahl1 = 0;

            // für jeden Kuchen wird ein Inspektionsdatum gesetzt
            final int kapazity = 4;
            if(this.model.getKuchenlisteSize()>0){
                for (int i = 1; i < kapazity; i++) {
                    this.model.editKuchen(i);
                }
            }

            int max_number_of_delete_cookies = creationRandomNumber();
            System.out.println("Anzahl zu löschende Kuchen: " +
                    max_number_of_delete_cookies);

            if(max_number_of_delete_cookies == 0){
                System.out.println("Es wurde kein Kuchen gelöscht");
            }else{
                while (kontrollzahl1<max_number_of_delete_cookies){
                    int fachnummer = fachnummerAeltesterKuchen();
                    boolean isremove = deleteKuchen(fachnummer);
                    if (isremove){
                        System.out.println("Kuchen mit dem Datum: " +
                                aeltesteZeitDate + " wurde gelöscht.");
                    }
                    kontrollzahl1++;
                }
            }
        }
    }

    public int fachnummerAeltesterKuchen(){
        int fachnummer = 0;
        long juengereinspektionszeit = 0;
        for (Kuchen kuchen : this.model.readKuchen()){
            insertDate = kuchen.getInspektionsdatum();
            System.out.println("Inspektionsdatum: " + insertDate);
            juengereinspektionszeit = insertDate.getTime();
            if (aeltesteinspektionzeit == 0) {
                aeltesteinspektionzeit = juengereinspektionszeit;
                aeltesteZeitDate = insertDate;
                fachnummer = kuchen.getFachnummer();
            } else {
                if (aeltesteinspektionzeit > juengereinspektionszeit) {
                    aeltesteinspektionzeit = juengereinspektionszeit;
                    aeltesteZeitDate = insertDate;
                    fachnummer = kuchen.getFachnummer();
                }
            }
        }
        aeltesteinspektionzeit = 0;
        return fachnummer;
    }


    protected boolean deleteKuchen ( int fachnummer){
        return this.model.deleteKuchen(fachnummer);
    }


    //Quelle: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Math/random?retiredLocale=de
    protected int creationRandomNumber () {
        int minRandomNumber = 0;
        int maxRandomNumber = 3;
        return (int) Math.floor(Math.random() * (maxRandomNumber - minRandomNumber + 1)
                + minRandomNumber);
    }
}
