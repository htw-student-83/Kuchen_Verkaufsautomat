package eventsystem.controller.listener;

import eventsystem.controller.events.EventKuchenLoeschen;

public interface EventListenerKuchenLoeschen {
    void onCakeDeleted(EventKuchenLoeschen event);
}
