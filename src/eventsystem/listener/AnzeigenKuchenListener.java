package eventsystem.listener;

import eventsystem.controller.listener.EventListener_Kuchen_Anzeigen;
import geschaeftslogik.verkaufsobjekt.Verwaltung;

public class AnzeigenKuchenListener implements EventListener_Kuchen_Anzeigen {
    private Verwaltung model;

    //Konstruktor erstellen
    public AnzeigenKuchenListener(Verwaltung model){
        this.model = model;
    }

    @Override
    public void run(EventListener_Kuchen_Anzeigen event) {
        this.model.readKuchen();
    }
}
