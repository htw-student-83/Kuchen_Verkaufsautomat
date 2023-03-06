package eventsystem.controller.events;

import java.util.EventObject;

public class EventHerstellerLoeschen extends EventObject {

    private String herstellername;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EventHerstellerLoeschen(Object source, String hersteller) {
        super(source);
        this.herstellername = hersteller;
    }

    public String getNameHersteller() { return this.herstellername; }
}
