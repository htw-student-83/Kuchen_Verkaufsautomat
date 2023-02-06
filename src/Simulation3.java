import geschaeftslogik.verkaufsobjekt.Kuchenautomat;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import observerpatter.Beobachter;
import simulation3.DeleteSimulation3;
import simulation3.InsertSimulation3;

public class Simulation3 {
    public static void main(String[] args) throws InterruptedException {
        //Beide Klassen sollen nur den einen monitor verwenden, um sich richtig abzustimmen
        final Object monitor=new Object();
        int waitingTime = 0;
        while (true){
            Verwaltung model = new Verwaltung(3);
            Beobachter beobachter = new Beobachter(model);
            model.addObserver(beobachter);
            Thread t1 = new Thread(new InsertSimulation3(model, monitor));
            Thread t2 = new Thread(new DeleteSimulation3(model, monitor));
            t1.start();
            Thread.sleep(waitingTime);
            t2.start();
        }
    }
}
