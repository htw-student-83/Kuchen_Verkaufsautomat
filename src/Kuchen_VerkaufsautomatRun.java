import eventsystem.controller.listener.*;
import eventsystem.handler.Handler;
import eventsystem.listener.*;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import netzwerk.tcp.Kuchenautomat_Server_TCP;
import netzwerk.udp.Kuchenautomat_Server_UDP;
import observerpatter.Beobachter;

import java.io.IOException;
import java.net.*;

public class Kuchen_VerkaufsautomatRun {
    public static void main(String[]args) throws IOException {
        String argument1 = String.valueOf(args[0]);
        String netzwerk = String.valueOf(args[1]);
        int kapazitaetAutomat = Integer.parseInt(argument1);
        Verwaltung model = new Verwaltung(kapazitaetAutomat);
        model.fillAlleAllergeneSet();
        Kuchen_VerkaufsautomatRun automatRun = new Kuchen_VerkaufsautomatRun();
        automatRun.startAutomat(model, netzwerk);
    }

    public void startAutomat(Verwaltung model, String netzwerk) throws IOException {
        Handler handlerInsertHersteller = null;
        Handler handlerInsertKuchen = null;
        Handler handlerHerstellerloeschen = null;
        Handler handlerKuchenloeschen = null;
        Handler handlerEditKuchen = null;

        switch (netzwerk){
            case "UDP":
                DatagramSocket datagramSocket = new DatagramSocket(5001);
                Kuchenautomat_Server_UDP serverUDP =
                        new Kuchenautomat_Server_UDP(datagramSocket, model);
                serverUDP.startServer();
                break;
            case "TCP":
                try(ServerSocket serverSocket=new ServerSocket(5001);) {
                    Socket socket=serverSocket.accept();
                    Kuchenautomat_Server_TCP serverTCP =
                            new Kuchenautomat_Server_TCP(socket, model);
                    serverTCP.run();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                CLI cli = new CLI(model);
                Beobachter beobachter = new Beobachter(model);
                model.addObserver(beobachter);

                handlerInsertHersteller = new Handler();
                EventListenerHerstellerEinfuegen listenerInsertHersteller = new InsertHerstellerListener(model);
                EventListenerHerstellerEinfuegen infoListenerHerstellerEinfuegen = new InfoListenerHerstellerEinfuegen();
                handlerInsertHersteller.add(listenerInsertHersteller);
                handlerInsertHersteller.add(infoListenerHerstellerEinfuegen);
                cli.setInsertHerstellerHandler(handlerInsertHersteller);

                handlerInsertKuchen = new Handler();
                EventListenerKuchenEinfuegen listenerInsertKuchen = new InsertKuchenListener(model);
                EventListenerKuchenEinfuegen infoListenerKuchenEinfuegen = new InfoListenerKuchenEinfuegen();
                handlerInsertKuchen.add(listenerInsertKuchen);
                handlerInsertKuchen.add(infoListenerKuchenEinfuegen);
                cli.setInsertKuchenHandler(handlerInsertKuchen);

                handlerHerstellerloeschen = new Handler();
                EventListenerHerstellerLoeschen listenerDeleteHersteller = new DeleteHerstellerListener(model);
                EventListenerHerstellerLoeschen infoListenerHerstellerLoeschen = new InfoListenerHerstellerLoeschen();
                handlerHerstellerloeschen.add(listenerDeleteHersteller);
                handlerHerstellerloeschen.add(infoListenerHerstellerLoeschen);
                cli.setDeleteHerstellerHandler(handlerHerstellerloeschen);

                handlerKuchenloeschen = new Handler();
                EventListenerKuchenLoeschen listenerDeleteKuchen = new DeleteKuchenListener(model);
                EventListenerKuchenLoeschen infoListenerKuchenLoeschen = new InfoListenerKuchenLoeschen();
                handlerKuchenloeschen.add(listenerDeleteKuchen);
                handlerKuchenloeschen.add(infoListenerKuchenLoeschen);
                cli.setDeleteKuchenHandler(handlerKuchenloeschen);

                handlerEditKuchen = new Handler();
                EventListener_Kuchen_Inspizierung listenerEditKuchen = new EditKuchenListener(model);
                EventListener_Kuchen_Inspizierung infoListenerKuchenInspizierung = new InfoListenerKuchenInspizierung();
                handlerEditKuchen.add(listenerEditKuchen);
                handlerEditKuchen.add(infoListenerKuchenInspizierung);
                cli.setEditKuchenHandler(handlerEditKuchen);

                cli.startCLI();
        }
    }
}
