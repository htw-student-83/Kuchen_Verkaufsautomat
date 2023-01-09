package eventsystem;

import eventsystem.Befehle;

public class Command {

    //operator wird mit den konkreten Events aus enums verbunden
    public final Befehle operator;

    public Command(String text){
        String op = text.substring(0,1);
        //try { n=Integer.parseInt(text.substring(1)); }
        //catch (NumberFormatException e){ op=""; }
        switch (op){
            case "c":
                this.operator = Befehle.INSERT;
                break;
            case "d":
                this.operator = Befehle.DELETE;
                break;
            case "r":
                this.operator = Befehle.READ;
                break;
            default:
                this.operator = Befehle.ERROR;
                break;
        }
    }
}
