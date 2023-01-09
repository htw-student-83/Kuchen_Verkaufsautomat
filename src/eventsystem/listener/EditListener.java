package eventsystem.listener;

import eventsystem.controller.EventListener;
import eventsystem.controller.KuchenEvent;
import geschaeftslogik.verkaufsobjekt.Verwaltung;

public class EditListener implements EventListener {
    //Der GL dem Listener bekannt machen
    private Verwaltung model;

    //Konstruktor erstellen
    public EditListener(Verwaltung model) {
        this.model = model;
    }


    @Override
    public void run(KuchenEvent event) {
        this.model.edit(event.getkuchenfachnummer());
    }
}
