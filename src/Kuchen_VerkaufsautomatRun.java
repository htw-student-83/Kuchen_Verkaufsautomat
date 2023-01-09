import geschaeftslogik.verkaufsobjekt.Verwaltung;

public class Kuchen_VerkaufsautomatRun {
    public static void main(String[]args){
        Verwaltung model = new Verwaltung();
        CLI automat = new CLI(model);
        automat.startAutomat();
    }
}
