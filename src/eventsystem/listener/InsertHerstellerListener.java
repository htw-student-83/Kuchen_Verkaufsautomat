package eventsystem.listener;

import eventsystem.controller.EventListener;
import eventsystem.controller.KuchenEvent;
import geschaeftslogik.verkaufsobjekt.Verwaltung;

public class InsertHerstellerListener implements EventListener {
    private Verwaltung model;

    //Konstruktor erstellen
    public InsertHerstellerListener(Verwaltung model) {
        this.model = model;
    }

    @Override
    public void run(KuchenEvent event) {
        this.model.insertH(event.getkuchenhersteller());
    }
}
