package geschaeftslogik;

import java.io.Serializable;
import java.util.Objects;

public class Hersteller implements vertrag.Hersteller, Serializable {
    private String name;

    public Hersteller(String herstellername){
        this.name = herstellername;
    }

    public Hersteller(){}

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hersteller that = (Hersteller) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString(){
        return "Name: " + this.getName() + "\n";
    }
}
