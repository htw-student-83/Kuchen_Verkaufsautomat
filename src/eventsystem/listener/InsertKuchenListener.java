package eventsystem.listener;

import eventsystem.controller.EventListener;
import eventsystem.controller.KuchenEvent;
import geschaeftslogik.verkaufsobjekt.Verwaltung;

public class InsertKuchenListener implements EventListener {

    //Der GL dem Listener bekannt machen
    private Verwaltung model;

    //Konstruktor erstellen
    public InsertKuchenListener(Verwaltung model) {
        this.model = model;
    }

    @Override
    public void run(KuchenEvent event) {
         this.model.insertKuchen2(event.getKuchenBoden(), event.getkuchenhersteller(), event.getKuchenBelaege());
    }
}
