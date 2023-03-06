package eventsystem.listener;

import eventsystem.controller.events.EventHerstellerLoeschen;
import eventsystem.controller.listener.EventListenerHerstellerLoeschen;

public class InfoListenerHerstellerLoeschen implements EventListenerHerstellerLoeschen {

    @Override
    public void onManufacturerDeleted(EventHerstellerLoeschen event) {
        System.out.println("Event Hersteller löschen wurde empfangen.");
    }
}
