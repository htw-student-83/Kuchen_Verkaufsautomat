package simulation1;

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
        synchronized (this.monitor){
            boolean result = this.model.delete(creationRandomNumber());
            if(result){
                System.out.println("Thread2 hat einen Kuchen gelöscht.");
            }
        }
    }


    //Quelle heraussuchen!
    public int creationRandomNumber() {
        int minRandomNumber = 1;
        int maxRandomNumber = 3;
        return (int) Math.floor(Math.random()*(maxRandomNumber-minRandomNumber+1)+minRandomNumber);
    }
}
