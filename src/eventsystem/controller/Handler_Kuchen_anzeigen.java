package eventsystem.controller;

import eventsystem.controller.events.EventKuchenAnzeigen;
import eventsystem.handler.Handler;

public class Handler_Kuchen_anzeigen {
    Handler handler;

    public Handler_Kuchen_anzeigen(Handler handler){
        this.handler = handler;
    }


    public void run(EventKuchenAnzeigen event) {
       //this.handler.distribute(event);
    }

}
