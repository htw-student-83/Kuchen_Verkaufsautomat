package eventsystem.controller;

import eventsystem.handler.Handler;

public class KucheneinfuegenEvent implements EventListener {
    Handler handler;

    public KucheneinfuegenEvent(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run(KuchenEvent event) {
        this.handler.distribute(event);
    }
}
