package eventsystem.controller.events;

import geschaeftslogik.Hersteller;

import java.util.EventObject;

public class EventHerstellerEinfuegen extends EventObject {

    private Hersteller kuchenhersteller;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EventHerstellerEinfuegen(Object source, Hersteller hersteller) {
        super(source);
        this.kuchenhersteller = hersteller;
    }

    public Hersteller getkuchenhersteller() { return this.kuchenhersteller; }
}
