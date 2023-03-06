package eventsystem.controller;

import eventsystem.controller.events.EventHerstellerLoeschen;
import eventsystem.controller.events.EventKuchenLoeschen;
import eventsystem.handler.Handler;

public class Handler_Kuchen_loeschen{
    Handler handler;

    public Handler_Kuchen_loeschen(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run(KuchenEvent event) {
        this.handler.distribute(event);
    }

}
