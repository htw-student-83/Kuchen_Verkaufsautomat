package eventsystem;

import java.util.EventListener;

public interface Event extends EventListener {
    void runEvent();
}
