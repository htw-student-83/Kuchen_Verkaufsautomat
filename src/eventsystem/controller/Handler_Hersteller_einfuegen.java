package eventsystem.controller;

import eventsystem.controller.events.EventHerstellerEinfuegen;
import eventsystem.handler.Handler;

public class Handler_Hersteller_einfuegen {

    Handler handler;

    public Handler_Hersteller_einfuegen(Handler handler){
        this.handler = handler;
    }

    public void run(EventHerstellerEinfuegen event) {
        this.handler.distribute(event);
    }
}
