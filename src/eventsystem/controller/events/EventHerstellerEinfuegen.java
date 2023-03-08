package eventsystem.controller.events;

import geschaeftslogik.Hersteller;

import java.util.EventObject;

public class EventHerstellerEinfuegen extends EventObject {

    private Hersteller kuchenhersteller;

    public EventHerstellerEinfuegen(Object source, Hersteller hersteller) {
        super(source);
        this.kuchenhersteller = hersteller;
    }

    public Hersteller getkuchenhersteller() { return this.kuchenhersteller; }
}
