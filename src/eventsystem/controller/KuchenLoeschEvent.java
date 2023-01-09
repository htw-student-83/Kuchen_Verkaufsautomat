package eventsystem.controller;

import eventsystem.handler.Handler;

public class KuchenLoeschEvent implements EventListener {
    Handler handler;

    public KuchenLoeschEvent(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run(KuchenEvent event) {
        this.handler.distribute(event);
    }
}
