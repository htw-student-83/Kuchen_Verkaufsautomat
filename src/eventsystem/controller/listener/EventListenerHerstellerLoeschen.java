package eventsystem.controller.listener;

import eventsystem.controller.events.EventHerstellerLoeschen;

public interface EventListenerHerstellerLoeschen {
    void onManufacturerDeleted(EventHerstellerLoeschen event);
}
