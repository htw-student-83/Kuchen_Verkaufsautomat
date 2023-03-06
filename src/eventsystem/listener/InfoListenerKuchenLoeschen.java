package eventsystem.listener;

import eventsystem.controller.events.EventKuchenLoeschen;
import eventsystem.controller.listener.EventListenerKuchenLoeschen;

public class InfoListenerKuchenLoeschen implements EventListenerKuchenLoeschen {

    @Override
    public void onCakeDeleted(EventKuchenLoeschen event) {
        System.out.println("Event Kuchen löschen wurde empfangen.");
    }
}
