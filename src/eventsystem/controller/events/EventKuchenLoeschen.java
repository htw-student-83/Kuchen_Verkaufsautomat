package eventsystem.controller.events;

import java.util.EventObject;

public class EventKuchenLoeschen extends EventObject {

    private int fachnumer;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EventKuchenLoeschen(Object source, int kuchenID) {
        super(source);
        this.fachnumer = kuchenID;
    }

    public int getkuchenfachnummer(){
        return this.fachnumer;
    }
}
