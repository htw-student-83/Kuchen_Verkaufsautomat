package eventsystem.listener;

import eventsystem.controller.EventListener;
import eventsystem.controller.KuchenEvent;

public class InfoListener implements EventListener {
    @Override
    public void run(KuchenEvent event) {
        System.out.println("Ein Event wurde empfangen.");
    }
}
