package eventsystem.controller.events;

import geschaeftslogik.Hersteller;

import java.util.EventObject;

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
