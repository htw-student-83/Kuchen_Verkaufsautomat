package eventsystem.controller;

public interface EventListener extends java.util.EventListener {
    void run(KuchenEvent event);
}
