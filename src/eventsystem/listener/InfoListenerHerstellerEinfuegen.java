package eventsystem.listener;

import eventsystem.controller.events.*;
import eventsystem.controller.listener.*;

public class InfoListenerHerstellerEinfuegen implements EventListenerHerstellerEinfuegen{
    //TODO für jedes Event einen Infolistener implementieren
    // dazu den passenden Eventlistener als Interface implementieren
    @Override
    public void onManufacturerAdded(EventHerstellerEinfuegen event) {
        System.out.println("Event Hersteller einfügen wurde empfangen.");
    }
}
