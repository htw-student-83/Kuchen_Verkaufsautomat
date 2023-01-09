package vertrag;

public interface Subjekt {

    /**
     * Registriert den Beobachter.
     */
    void meldeAn(Beobachter beobachter);


    /**
     * Hebt die Registierung eines Beobachters wieder auf.
     */
    void meldeAb(Beobachter beobachter);


    /**
     * aktualisiert den aktullen Zustand
     * @param state neuer Zustand
     */
    void setZustandKuchenliste(int state);


    void setZustandHerstellerliste(int neuerHersteller);



    /**
     * Gibt den aktuellen Zustand zurueck
     * @return aktueller Zustand
     */
    int gibZustandKuchenliste();


    /**
     * informiert die Beobachter, wenn sich der Zustand aendert.
     */
    void benachrichtige();


    int gibZustandHerstellerliste();
}
