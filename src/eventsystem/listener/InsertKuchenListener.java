package eventsystem.listener;

import eventsystem.controller.listener.EventListenerKuchenEinfuegen;
import eventsystem.controller.events.EventKuchenEinfuegen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;

public class InsertKuchenListener implements EventListenerKuchenEinfuegen {

    //Der GL dem Listener bekannt machen
    private Verwaltung model;

    //Konstruktor erstellen
    public InsertKuchenListener(Verwaltung model) {
        this.model = model;
    }

    @Override
    public void run(KuchenEvent event) {
        this.model.insertKuchen(event.getkuchentyp(), event.getkuchenhersteller(),
                                event.getkuchenpreis(), event.getkuchennaehrwert(),
                                event.getkuchenhaltbarkeit(), event.getkuchenallergene(),
                                event.getKuchenobstsorte(), event.getKuchenkremsorte());
    }
}
