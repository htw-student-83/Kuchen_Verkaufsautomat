package eventsystem.controller;

import eventsystem.handler.Handler;

public class AllergenEinfuegenEvent implements EventListener {
    Handler handler;

    public AllergenEinfuegenEvent(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run(KuchenEvent event) {
        this.handler.distribute(event);
    }

}
