package eventsystem.controller.events;

import java.util.EventObject;

public class EventHerstellerLoeschen extends EventObject {

    private String herstellername;

    public EventHerstellerLoeschen(Object source, String hersteller) {
        super(source);
        this.herstellername = hersteller;
    }

    public String getNameHersteller() { return this.herstellername; }
}
