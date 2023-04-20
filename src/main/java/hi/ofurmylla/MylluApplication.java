package hi.ofurmylla;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MylluApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new Pane(), 1920, 1000);
        ViewSwitcher.setScene(scene);
        ViewSwitcher.switchTo(View.VELJA);
        stage.setTitle("League of Mylla!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
