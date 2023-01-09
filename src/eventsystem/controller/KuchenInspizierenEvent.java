package eventsystem.controller;

import eventsystem.handler.Handler;

public class KuchenInspizierenEvent implements EventListener {
    Handler handler;

    public KuchenInspizierenEvent(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run(KuchenEvent event) {
        this.handler.distribute(event);
    }
}
