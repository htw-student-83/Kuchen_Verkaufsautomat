package eventsystem.listener;

import eventsystem.controller.EventListener;
import eventsystem.controller.KuchenEvent;
import geschaeftslogik.verkaufsobjekt.Verwaltung;

public class InsertAllergenListener implements EventListener {
    private Verwaltung model;

    //Konstruktor erstellen
    public InsertAllergenListener(Verwaltung model){
        this.model = model;
    }

    @Override
    public void run(KuchenEvent event) {
        this.model.insertAllergen(event.getkuchenallergene());
    }
}
