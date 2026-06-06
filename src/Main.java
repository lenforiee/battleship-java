import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import views.MenuView;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("/assets/main_font.ttf"), 36); // 36 nie ma znaczenia, ale ładuje czcionke do pamięci
        new MenuView(stage);
    }
}
