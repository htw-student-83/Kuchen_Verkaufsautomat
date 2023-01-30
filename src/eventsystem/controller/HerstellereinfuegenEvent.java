package eventsystem.controller;

import eventsystem.handler.Handler;

public class HerstellereinfuegenEvent implements EventListener {

    Handler handler;

    public HerstellereinfuegenEvent(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run(KuchenEvent event) {
        this.handler.distribute(event);
    }
}
