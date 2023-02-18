import eventsystem.controller.EventListener;
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
        //Kuchenautomat automat = new Kuchenautomat();
        Verwaltung model = new Verwaltung(kapazitaetAutomat);
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
                EventListener listenerInsertHersteller = new InsertHerstellerListener(model);
                EventListener infoListener = new InfoListener();
                handlerInsertHersteller.add(listenerInsertHersteller);
                handlerInsertHersteller.add(infoListener);
                cli.setInsertHerstellerHandler(handlerInsertHersteller);

                handlerInsertKuchen = new Handler();
                EventListener listenerInsertKuchen = new InsertKuchenListener(model);
                handlerInsertKuchen.add(listenerInsertKuchen);
                handlerInsertKuchen.add(infoListener);
                cli.setInsertKuchenHandler(handlerInsertKuchen);

                handlerHerstellerloeschen = new Handler();
                EventListener listenerDeleteHersteller = new DeleteHerstellerListener(model);
                handlerHerstellerloeschen.add(listenerDeleteHersteller);
                handlerHerstellerloeschen.add(infoListener);
                cli.setDeleteHerstellerHandler(handlerHerstellerloeschen);

                handlerKuchenloeschen = new Handler();
                EventListener listenerDeleteKuchen = new DeleteKuchenListener(model);
                handlerKuchenloeschen.add(listenerDeleteKuchen);
                handlerKuchenloeschen.add(infoListener);
                cli.setDeleteKuchenHandler(handlerKuchenloeschen);

                handlerEditKuchen = new Handler();
                EventListener listenerEditKuchen = new EditKuchenListener(model);
                handlerEditKuchen.add(listenerEditKuchen);
                handlerEditKuchen.add(infoListener);
                cli.setEditKuchenHandler(handlerEditKuchen);

                cli.startCLI();
        }
    }
}
