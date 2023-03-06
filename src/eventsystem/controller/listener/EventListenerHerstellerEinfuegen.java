package eventsystem.controller.listener;

import eventsystem.controller.events.EventHerstellerEinfuegen;

public interface EventListenerHerstellerEinfuegen {

    void onManufacturerAdded(EventHerstellerEinfuegen event);
}
