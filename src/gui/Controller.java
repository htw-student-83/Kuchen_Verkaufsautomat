package gui;
/*
import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.Verwaltung;
import geschaeftslogik.verkaufsobjekt.Kuchen;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import vertrag.Allergene;

import java.math.BigDecimal;
import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    //Hier muss das Verhalten der jeweiligen Buttons programmiert werden

    //Quelle: https://www.youtube.com/watch?v=fnU1AlyuguE
    @FXML
    private TableView<Hersteller> herstellertable;
    @FXML
    private TableColumn<Hersteller,String> name;
    @FXML
    private TableView<Allergene> allergenetable;
    @FXML
    private TableColumn<Allergene,String> allergene;
    @FXML
    private TableView<Kuchen> kuchentable;
    @FXML
    private TableColumn<Kuchen,Integer> fachnummer;
    @FXML
    private TableColumn<Kuchen,String> kremsorte;
    @FXML
    private TableColumn<Kuchen,Hersteller> hersteller;
    @FXML
    private TableColumn<Kuchen,Duration> haltbarkeit;
    @FXML
    private TableColumn<Kuchen, BigDecimal> preis;
    @FXML
    private TableColumn<Kuchen, Integer> naehrwert;
    @FXML
    private TableColumn<Kuchen, Date> einfuegedatum;
    @FXML
    private TableColumn<Kuchen, Date> inspektionsdatum;


    ObservableList<Hersteller> herstellerlist = FXCollections.observableArrayList();
    ObservableList<Allergene> allergenelist = FXCollections.observableArrayList();
    ObservableList<Kuchen> kuchenlist = FXCollections.observableArrayList();
    Verwaltung model = new Verwaltung();
    Hersteller newhersteller = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        allergene.setCellValueFactory(new PropertyValueFactory<>("allergene"));
        fachnummer.setCellValueFactory(new PropertyValueFactory<>("fachnummer"));
        //TODO warum kann auf die jeweilige Kuchensorte nicht zugegriffen werden?
        //Lösung: der Variablename der jeweiligen Unterklasse muss identisch mit der fix:id sein!!
        kremsorte.setCellValueFactory(new PropertyValueFactory<>("kremsorte"));
        hersteller.setCellValueFactory(new PropertyValueFactory<>("hersteller"));
        haltbarkeit.setCellValueFactory(new PropertyValueFactory<>("haltbarkeit"));
        preis.setCellValueFactory(new PropertyValueFactory<>("preis"));
        naehrwert.setCellValueFactory(new PropertyValueFactory<>("naehrwert"));
        einfuegedatum.setCellValueFactory(new PropertyValueFactory<>("einfuegedatum"));
        inspektionsdatum.setCellValueFactory(new PropertyValueFactory<>("inspektionsdatum"));
        kuchentable.setItems(kuchenlist);
        herstellertable.setItems(herstellerlist);
        allergenetable.setItems(allergenelist);
    }


    @FXML
    private void herstellereinfuegen(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Neues Element");
        dialog.setHeaderText("Neuen Hersteller einfügen");
        dialog.setContentText("Name:");
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        newhersteller = new Hersteller(dialog.getResult());
        if(result.isPresent()){
            if(!herstellerlist.contains(newhersteller)){
                this.model.insertH(newhersteller);
                herstellerlist.add(newhersteller);
            }
        }
    }

    @FXML
    private void allergeneeinfuegen(ActionEvent actionEvent, Allergene allergene) {
        if(!allergenelist.contains(allergene)){
            allergenelist.add(allergene);
        }
    }


    @FXML
    private void kucheneinfuegen(ActionEvent actionEvent) {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Neues Element");
        dialog.setHeaderText("Einen Kuchen einfügen");

        // Set the button types.
        ButtonType confirmbuttonTyp = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmbuttonTyp, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField typ = new TextField();
        TextField sorte = new TextField();
        TextField hersteller = new TextField();
        TextField allergene = new TextField();
        TextField haltbarkeit = new TextField();
        TextField preis = new TextField();
        TextField naehrwert = new TextField();

        grid.add(new Label("Typ:"), 0, 0);
        grid.add(typ, 1, 0);
        grid.add(new Label("Sorte:"), 0, 1);
        grid.add(sorte, 1, 1);
        grid.add(new Label("Hersteller:"), 0, 2);
        grid.add(hersteller, 1, 2);
        grid.add(new Label("Allergene:"), 0, 3);
        grid.add(allergene, 1, 3);
        grid.add(new Label("Haltbarkeit:"), 0, 4);
        grid.add(haltbarkeit, 1, 4);
        grid.add(new Label("Preis:"), 0, 5);
        grid.add(preis, 1, 5);
        grid.add(new Label("Naehrwert:"), 0, 6);
        grid.add(naehrwert, 1, 6);

        // Enable/Disable login button depending on whether a username was entered.
        Node confirmbutton = dialog.getDialogPane().lookupButton(confirmbuttonTyp);
        confirmbutton.setDisable(false);

        // Do some validation (using the Java 8 lambda syntax).
        typ.textProperty().addListener((observable, oldValue, newValue) -> {
            confirmbutton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(typ::requestFocus);

        Optional<Pair<String, String>> result = dialog.showAndWait();

        String newHersteller = hersteller.getText();
        Hersteller newhersteller = new Hersteller(newHersteller);

        double userinputPreis = Double.parseDouble(preis.getText());
        Duration haltbarkeitkuchen = Duration.valueOf(haltbarkeit.getText());

        dialog.getDialogPane().setContent(grid);

        Kuchentyp userInputtyp = Kuchentyp.valueOf(typ.getText());

        //Die Einfüg-Methode der GL soll verwendet werden nicht die Bestandteile der Methode!!!!
        this.model.insert(userInputtyp, newhersteller,,
                userinputPreis, Integer.parseInt(naehrwert.getText()),
                Allergene.valueOf(allergene.getText()), sorte.getText());
        List<Kuchen> kuchen = this.model.readKuchen();
        //TODO Wie kann nur jeweils ein Objekt von Kuchen geladen werden?
        kuchenlist.addAll(kuchen);
        if(!allergenelist.contains(Allergene.valueOf(allergene.getText()))){
            allergenelist.add(Allergene.valueOf(allergene.getText()));
        }
        System.out.println("Listengroeße Allergene: " + allergenelist.size());
    }


    //Quelle: https://www.youtube.com/watch?v=qQcr_JMxWRw
    @FXML
    private void kuchenloeschen(ActionEvent actionEvent) {
        if(kuchenlist.size()==0){
        }else {
            int kuchenID = kuchentable.getSelectionModel().getSelectedIndex();
            kuchentable.getItems().remove(kuchenID);
        }
    }



    @FXML
    private void setInspektionsdatum(ActionEvent event){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Kuchen kontrollieren");
        dialog.setHeaderText("Einen kuchen inspizieren");
        dialog.setContentText("Fachnummer:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            this.model.edit(Integer.parseInt(result.get()));
            kuchentable.refresh();
            kuchentable.getColumns().get(8).setVisible(true);
        }
    }

    @FXML
    private void speicherKuchen(ActionEvent event){
        kuchentable.getItems().iterator();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Kuchen speichern");
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){

        }
    }
}

 */
