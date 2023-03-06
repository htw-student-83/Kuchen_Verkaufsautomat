package eventsystem.controller.listener;

import eventsystem.controller.events.*;

public interface EventListener extends java.util.EventListener {
    void run(EventHerstellerEinfuegen event);
    void run(EventHerstellerLoeschen event);
    void run(EventKuchenEinfuegen event);
    void run(EventKuchenInspizierung event);
    void run(EventKuchenLoeschen event);
}
