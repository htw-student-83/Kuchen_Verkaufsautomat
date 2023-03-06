package eventsystem.listener;

import eventsystem.controller.listener.EventListener_Kuchen_Inspizierung;
import eventsystem.controller.events.EventKuchenInspizierung;
import geschaeftslogik.verkaufsobjekt.Verwaltung;

public class EditKuchenListener implements EventListener_Kuchen_Inspizierung {
    //Der GL dem Listener bekannt machen
    private Verwaltung model;

    //Konstruktor erstellen
    public EditKuchenListener(Verwaltung model) {
        this.model = model;
    }

    @Override
    public void run(EventKuchenInspizierung event) {
        this.model.editKuchen(event.getkuchenfachnummer());
    }
}
