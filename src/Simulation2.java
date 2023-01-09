import geschaeftslogik.verkaufsobjekt.Verwaltung;
import observerpatter.observer.Beobachter;
import simulation2.DeleteSimulation2;
import simulation2.InspektionSimulation2;

public class Simulation2 {
    public static void main(String[]args) throws InterruptedException {
        final Object monitor=new Object();
        int waitingTime = 0;
        while (true){
            Verwaltung model = new Verwaltung();
            Beobachter beobachter = new Beobachter(model);
            model.addObserver(beobachter);
            Thread t1 = new Thread(new InspektionSimulation2(model, monitor));
            Thread t2 = new Thread(new DeleteSimulation2(model, monitor));
            t1.start();
            Thread.sleep(waitingTime);
            t2.start();
        }
    }
}
