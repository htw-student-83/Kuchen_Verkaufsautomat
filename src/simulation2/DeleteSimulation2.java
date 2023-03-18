package simulation2;

import geschaeftslogik.IKuchen;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;

import java.text.ParseException;
import java.util.Date;

public class DeleteSimulation2 extends Thread implements Runnable {
      private Verwaltung model;
      private final Object monitor;
      long aeltesteinspektionzeit = 0;
      Date aeltesteZeitDate = null;

    public DeleteSimulation2(Verwaltung kl, Object monitor){
        this.model = kl;
        this.monitor = monitor;
    }
    // durch die Kuchenliste iterieren
    // dabei das kleinste Inspektionsdatum ermitteln
    // die dazugehörige Fachnummer holen
    // die delete() aufrufen

    @Override
    public void run() {
        try {
            editInspektiondate();
        } catch (InterruptedException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public  boolean editInspektiondate() throws InterruptedException, ParseException {
        synchronized(this.monitor){
            // prüfen, ob die Liste 3 Kuchen enthält, falls nein -> warten
            // für jeden Kuchen wird ein Inspektionsdatum gesetzt
            final int kapazity = 4;
            for(int i = 1; i<kapazity; i++){
                boolean isedied = this.model.editKuchen(i);
                if (isedied){
                    System.out.println("Kuchen inspiziert.");
                    return true;
                }
            }
            aeltestesInspektionsdatum();
        }
        return false;
    }

    public void aeltestesInspektionsdatum(){
        int fachnummer = 0;
        long juengereinspektionszeit = 0;
        for(Kuchen kuchen: this.model.readKuchen()){
            Date insertDate = kuchen.getInspektionsdatum();
            System.out.println("Inspektionsdatum: " + insertDate);
            juengereinspektionszeit = insertDate.getTime();
            if(aeltesteinspektionzeit == 0){
                aeltesteinspektionzeit = juengereinspektionszeit;
                aeltesteZeitDate = insertDate;
                fachnummer = kuchen.getFachnummer();
            }else{
                if(aeltesteinspektionzeit>juengereinspektionszeit){
                    aeltesteinspektionzeit = juengereinspektionszeit;
                    aeltesteZeitDate = insertDate;
                    fachnummer = kuchen.getFachnummer();
                }
            }
        }
        deleteKuchen(fachnummer);
    }


    public boolean deleteKuchen(int fachnummer){
        boolean isDeleted = this.model.deleteKuchen(fachnummer);
        if(isDeleted){
            System.out.println("Thread2: Kuchen mit dem Inspektionsdatum " + aeltesteZeitDate + " gelöscht.");
            return true;
        }else {
            System.out.println("Keine Löschung stattgefunden.");
            return  false;
        }
    }
}
