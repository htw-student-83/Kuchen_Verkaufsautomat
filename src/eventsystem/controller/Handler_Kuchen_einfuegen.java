package eventsystem.controller;

import eventsystem.controller.events.EventKuchenEinfuegen;
import eventsystem.handler.Handler;

public class Handler_Kuchen_einfuegen {

    Handler handler;

    public Handler_Kuchen_einfuegen(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run(KuchenEvent event) {
        this.handler.distribute(event);
    }
}
