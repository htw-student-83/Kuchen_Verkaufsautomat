package eventsystem.controller.events;

import java.util.EventObject;

public class EventKuchenInspizierung extends EventObject {

    private int fachnumer;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EventKuchenInspizierung(Object source, int fachnumer) {
        super(source);
        this.fachnumer = fachnumer;
    }

    public int getkuchenfachnummer(){
        return this.fachnumer;
    }
}
