package eventsystem.controller;

import eventsystem.controller.events.EventKuchenInspizierung;
import eventsystem.handler.Handler;

public class Handler_Kuchen_Inspizierung{
    Handler handler;

    public Handler_Kuchen_Inspizierung(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run(KuchenEvent event) {
        this.handler.distribute(event);
    }

}
