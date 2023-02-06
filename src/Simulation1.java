import geschaeftslogik.verkaufsobjekt.Verwaltung;
import observerpatter.Beobachter;
import simulation1.DeleteSimulation1;
import simulation1.InsertSimulation1;

public class Simulation1 {
    public static void main(String[]args) throws InterruptedException {
        final Object monitor=new Object();

        int waitingTime = 0;
        while (true){
            Verwaltung model = new Verwaltung(3);
            Beobachter beobachter = new Beobachter(model);
            model.addObserver(beobachter);
            Thread t1 = new Thread(new InsertSimulation1(model, monitor));
            Thread t2 = new Thread(new DeleteSimulation1(model, monitor));
            t1.start();
            Thread.sleep(waitingTime);
            t2.start();
        }
    }
}
