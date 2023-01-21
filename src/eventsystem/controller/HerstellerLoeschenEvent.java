package eventsystem.controller;

import eventsystem.handler.Handler;

public class HerstellerLoeschenEvent implements EventListener {
    Handler handler;

    public HerstellerLoeschenEvent(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run(KuchenEvent event) {
        this.handler.distribute(event);
    }


}
