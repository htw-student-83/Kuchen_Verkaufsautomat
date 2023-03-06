package eventsystem.controller.listener;

import eventsystem.controller.events.EventKuchenEinfuegen;

public interface EventListenerKuchenEinfuegen {
    void onCakeAdded(EventKuchenEinfuegen event);
}
