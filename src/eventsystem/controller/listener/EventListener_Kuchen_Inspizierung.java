package eventsystem.controller.listener;

import eventsystem.controller.events.EventKuchenInspizierung;

public interface EventListener_Kuchen_Inspizierung {
    void run(EventKuchenInspizierung event);
}
