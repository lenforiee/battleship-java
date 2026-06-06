package views;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuView extends AbstractView {

    public MenuView(Stage stage) {
        super("Projekt Java - Gra w Statki", 400, 550, 10, stage);

        Text title = new Text("GRA W STATKI");
        title.setFill(Color.rgb(223, 233, 235));
        title.setEffect(DS_BLACK);
        title.setFont(FONT_MAIN_36);

        Button localBtn = new Button();
        Text localText = new Text("GRA LOKALNA (1v1)");
        localText.setEffect(DS_BLACK);
        localText.setFont(FONT_MAIN_25);
        localText.setFill(Color.rgb(172, 199, 200));
        localBtn.setGraphic(localText);

        localBtn.setOnAction(e -> {
            NameInputView input = new NameInputView(stage, true);
            input.setOnAcceptCallback(() -> {
                System.out.println(input.name1);
                System.out.println(input.name2);
            });
        });

        localBtn.setEffect(DS_BLUE);
        localBtn.setStyle("-fx-background-color: #1D2E36; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: #63888E; -fx-pref-width: 300px; -fx-pref-height: 50px; -fx-border-width: 2px");

        Button computerBtn = new Button();
        Text computerText = new Text("GRA Z KOMPUTEREM");
        computerText.setEffect(DS_BLACK);
        computerText.setFont(FONT_MAIN_25);
        computerText.setFill(Color.rgb(172, 199, 200));
        computerBtn.setGraphic(computerText);

        computerBtn.setOnAction(e -> {
            NameInputView input = new NameInputView(stage, false);
            input.setOnAcceptCallback(() -> {
                System.out.println(input.name1);
            });
        });

        computerBtn.setEffect(DS_BLUE);
        computerBtn.setStyle("-fx-background-color: #1D2E36; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: #63888E; -fx-pref-width: 300px; -fx-pref-height: 50px; -fx-border-width: 2px");

        Button onlineBtn = new Button();
        Text onlineText = new Text("GRA ONLINE");
        onlineText.setEffect(DS_BLACK);
        onlineText.setFont(FONT_MAIN_25);
        onlineText.setFill(Color.rgb(172, 199, 200));
        onlineBtn.setGraphic(onlineText);

        onlineBtn.setOnAction(e -> {
            NameInputView input = new NameInputView(stage, false);
            input.setOnAcceptCallback(() -> {
                System.out.println(input.name1);
            });
        });

        onlineBtn.setEffect(DS_GREEN);
        onlineBtn.setStyle("-fx-background-color: #314644; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: #8CB59D; -fx-pref-width: 300px; -fx-pref-height: 50px; -fx-border-width: 2px");

        Button settingsBtn = new Button();
        Text settingsText = new Text("OPCJE");
        settingsText.setEffect(DS_BLACK);
        settingsText.setFont(FONT_MAIN_25);
        settingsText.setFill(Color.rgb(172, 199, 200));
        settingsBtn.setGraphic(settingsText);

        settingsBtn.setOnAction(e -> {
            new SettingsView(stage);
        });

        settingsBtn.setEffect(DS_BLUE);
        settingsBtn.setStyle("-fx-background-color: #1D2E36; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: #63888E; -fx-pref-width: 300px; -fx-pref-height: 50px; -fx-border-width: 2px");

        Button exitBtn = new Button();
        Text exitText = new Text("WYJŚCIE");
        exitText.setEffect(DS_BLACK);
        exitText.setFont(FONT_MAIN_25);
        exitText.setFill(Color.rgb(172, 199, 200));
        exitBtn.setEffect(DS_RED);
        exitBtn.setGraphic(exitText);

        exitBtn.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        exitBtn.setStyle("-fx-background-color: #1D2E36; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: #9F7A79; -fx-pref-width: 300px; -fx-pref-height: 50px; -fx-border-width: 2px");

        root.getChildren().addAll(title, localBtn, computerBtn, onlineBtn, settingsBtn, exitBtn);
        stage.show();
    }
}
