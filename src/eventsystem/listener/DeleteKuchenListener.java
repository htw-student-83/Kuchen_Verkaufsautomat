package eventsystem.listener;

import eventsystem.controller.listener.EventListenerKuchenLoeschen;
import eventsystem.controller.events.EventKuchenLoeschen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;

public class DeleteKuchenListener implements EventListenerKuchenLoeschen {
    private Verwaltung model;

    //Konstruktor erstellen
    public DeleteKuchenListener(Verwaltung model){
        this.model = model;
    }

    @Override
    public void run(KuchenEvent event) {
        this.model.deleteKuchen(event.getkuchenfachnummer());
    }

}
