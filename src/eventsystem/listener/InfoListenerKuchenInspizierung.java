package eventsystem.listener;

import eventsystem.controller.events.EventKuchenInspizierung;
import eventsystem.controller.listener.EventListener_Kuchen_Inspizierung;

public class InfoListenerKuchenInspizierung implements EventListener_Kuchen_Inspizierung {

    @Override
    public void run(EventKuchenInspizierung event) {
        System.out.println("Event Kuchen inspizieren wurde empfangen.");
    }
}
