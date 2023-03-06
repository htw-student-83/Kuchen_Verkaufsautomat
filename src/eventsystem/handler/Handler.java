package eventsystem.handler;

import eventsystem.controller.EventListener;
import eventsystem.controller.KuchenEvent;

import java.util.LinkedList;
import java.util.List;

public class Handler {
    private List<EventListener> listenerList = new LinkedList<>();
    public void add(EventListener listener){
        this.listenerList.add(listener);
    }
    public void remove(EventListener listener){
        this.listenerList.remove(listener);
    }

    public void remove(EventListener_Kuchen_Inspizierung listener){
        this.kuchenInspizierenListener.remove(listener);
    }

    public void remove(EventListenerKuchenLoeschen listener){
        this.kuchenLoeschenListener.remove(listener);
    }

    public void remove(EventListenerHerstellerLoeschen listener){
        this.herstellerLoeschenListener.remove(listener);
    }

    public void distribute(KuchenEvent event){
        for(EventListener listener: listenerList){
            listener.run(event);
        }
    }
}
