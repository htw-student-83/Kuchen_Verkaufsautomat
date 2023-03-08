package eventsystem.controller.events;

import java.util.EventObject;

public class EventKuchenInspizierung extends EventObject {

    private int fachnumer;

    public EventKuchenInspizierung(Object source, int fachnumer) {
        super(source);
        this.fachnumer = fachnumer;
    }

    public int getkuchenfachnummer(){
        return this.fachnumer;
    }
}
