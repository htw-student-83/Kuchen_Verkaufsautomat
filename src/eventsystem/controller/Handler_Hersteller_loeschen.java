package eventsystem.controller;

import eventsystem.controller.events.EventHerstellerLoeschen;
import eventsystem.handler.Handler;

public class Handler_Hersteller_loeschen{
    Handler handler;

    public Handler_Hersteller_loeschen(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run(KuchenEvent event) {
        this.handler.distribute(event);
    }
}
