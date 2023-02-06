package simulation1;

import geschaeftslogik.IKuchen;
import geschaeftslogik.verkaufsobjekt.DekoKuchen;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;

public class DeleteSimulation1 extends Thread implements Runnable {
    private Verwaltung model;
    private final Object monitor;


    public DeleteSimulation1(Verwaltung kl, Object monitor){
        this.model = kl;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        deleteKuchenSimulation();
    }

    public boolean deleteKuchenSimulation(){
        synchronized (this.monitor){
            for(DekoKuchen kuchen: this.model.readKuchen()){
                int fachnummer = kuchen.getFachnummer();
                boolean result = this.model.deleteKuchen(fachnummer);
                if(result){
                    System.out.println("Thread2 hat einen Kuchen gelöscht.");
                    return true;
                }
            }
        }
        return false;
    }
}
