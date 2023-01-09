import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class KuchenautomatGUIRUn extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui/Ressoures.fxml"));
        Parent conent = fxmlLoader.load();
        Scene scene = new Scene(conent, 980, 400);
        stage.setTitle("Kuchenautomat");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        KuchenautomatGUIRUn.launch();
    }

}