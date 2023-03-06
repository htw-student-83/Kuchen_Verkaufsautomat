package eventsystem.listener;

import eventsystem.controller.events.EventHerstellerEinfuegen;
import eventsystem.controller.listener.EventListenerHerstellerEinfuegen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;

public class InsertHerstellerListener implements EventListenerHerstellerEinfuegen {
    private Verwaltung model;

    //Konstruktor erstellen
    public InsertHerstellerListener(Verwaltung model) {
        this.model = model;
    }

    @Override
    public void run(KuchenEvent event) {
        this.model.insertHersteller(event.getkuchenhersteller());
    }
}
