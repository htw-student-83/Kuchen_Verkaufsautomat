package eventsystem.controller;

import eventsystem.handler.Handler;

public class KuchenAnzeigenEvent implements EventListener {
    Handler handler;

    public KuchenAnzeigenEvent(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run(KuchenEvent event) {
        this.handler.distribute(event);
    }
}
