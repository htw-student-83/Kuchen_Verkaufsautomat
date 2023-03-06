package eventsystem.listener;

import eventsystem.controller.events.EventKuchenEinfuegen;
import eventsystem.controller.listener.EventListenerKuchenEinfuegen;

public class InfoListenerKuchenEinfuegen implements EventListenerKuchenEinfuegen {

    @Override
    public void onCakeAdded(EventKuchenEinfuegen event) {
        System.out.println("Event Kuchen einfügen wurde empfangen.");
    }
}
