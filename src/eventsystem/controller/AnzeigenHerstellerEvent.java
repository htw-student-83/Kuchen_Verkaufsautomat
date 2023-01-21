package eventsystem.controller;

import eventsystem.handler.Handler;

public class AnzeigenHerstellerEvent implements EventListener {
    Handler handler;

    public AnzeigenHerstellerEvent(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run(KuchenEvent event) {
        this.handler.distribute(event);
    }
}
