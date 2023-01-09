package eventsystem.listener;

import eventsystem.controller.EventListener;
import eventsystem.controller.KuchenEvent;
import geschaeftslogik.verkaufsobjekt.Verwaltung;

public class DeleteListener implements EventListener {
    //Der GL dem Listener bekannt machen
    private Verwaltung model;

    //Konstruktor erstellen
    public DeleteListener(Verwaltung model){
        this.model = model;
    }


    @Override
    public void run(KuchenEvent event) {
        this.model.delete(event.getkuchenfachnummer());
    }
}
