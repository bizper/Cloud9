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
        Scene main = new Scene(Objects.requireNonNull(p), 600, 701);
        stage.getIcons().add(ImageKit.get("900"));
        stage.setScene(main);
        stage.setResizable(false);
        stage.setTitle("ToolsBox");
        stage.show();
        Platform.runLater(() -> controller.init(stage));
    }


    public static void main(String[] args) {
        launch(args);
    }

}
