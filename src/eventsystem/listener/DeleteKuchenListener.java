package eventsystem.listener;

import eventsystem.controller.EventListener;
import eventsystem.controller.KuchenEvent;
import geschaeftslogik.verkaufsobjekt.Verwaltung;

public class DeleteKuchenListener implements EventListener {
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
