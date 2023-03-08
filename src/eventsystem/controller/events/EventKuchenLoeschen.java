package eventsystem.controller.events;

import java.util.EventObject;

public class EventKuchenLoeschen extends EventObject {

    private int fachnumer;

    public EventKuchenLoeschen(Object source, int kuchenID) {
        super(source);
        this.fachnumer = kuchenID;
    }

    public int getkuchenfachnummer(){
        return this.fachnumer;
    }
}
