package eventsystem.listener;

import eventsystem.controller.listener.EventListenerHerstellerLoeschen;
import eventsystem.controller.events.EventHerstellerLoeschen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;

public class DeleteHerstellerListener implements EventListenerHerstellerLoeschen {
    //Der GL dem Listener bekannt machen
    private Verwaltung model;

    //Konstruktor erstellen
    public DeleteHerstellerListener(Verwaltung model) {
        this.model = model;
    }

    @Override
    public void run(KuchenEvent event) {
        this.model.deleteHersteller(event.getNameHersteller());
    }

}