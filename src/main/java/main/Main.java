package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tools.ImageKit;

import java.net.URL;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL url = getClass().getResource("/fxml/main.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Pane p = loader.load();
        Controller controller = loader.getController();
        Platform.runLater(controller::init);
        Scene main = new Scene(Objects.requireNonNull(p), 600, 400);
        stage.getIcons().add(ImageKit.get("900"));
        stage.setScene(main);
        stage.setResizable(false);
        stage.setTitle("Cloud9");
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
