package views;

import components.GameManager;
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

        Button localBtn = this.createStyledButton(
                "GRA LOKALNA (1v1)",
                Color.rgb(172, 199, 200),
                "#1D2E36",
                "#63888E",
                DS_BLUE,
                DS_BLACK,
                FONT_MAIN_25
        );

        localBtn.setOnAction(e -> {
            NameInputView input = new NameInputView(stage, true);
            input.setOnAcceptCallback(() -> {
                input.closeStage();
                this.closeStage();

                GameManager.startGameBoardSetup(this.stage);
            });
        });

        Button computerBtn = this.createStyledButton(
                "GRA Z KOMPUTEREM",
                Color.rgb(172, 199, 200),
                "#1D2E36",
                "#63888E",
                DS_BLUE,
                DS_BLACK,
                FONT_MAIN_25
        );

        computerBtn.setOnAction(e -> {
            NameInputView input = new NameInputView(stage, false);
            input.setOnAcceptCallback(() -> {
            });
        });


        Button onlineBtn = this.createStyledButton(
                "GRA ONLINE",
                Color.rgb(172, 199, 200),
                "#314644",
                "#8CB59D",
                DS_GREEN,
                DS_BLACK,
                FONT_MAIN_25
        );

        onlineBtn.setOnAction(e -> {
            NameInputView input = new NameInputView(stage, false);
            input.setOnAcceptCallback(() -> {
            });
        });

        Button settingsBtn = this.createStyledButton(
                "USTAWIENIA",
                Color.rgb(172, 199, 200),
                "#1D2E36",
                "#63888E",
                DS_BLUE,
                DS_BLACK,
                FONT_MAIN_25
        );

        settingsBtn.setOnAction(e -> {
            new SettingsView(stage);
        });


        Button exitBtn = this.createStyledButton(
                "WYJŚCIE",
                Color.rgb(172, 199, 200),
                "#1D2E36",
                "#9F7A79",
                DS_RED,
                DS_BLACK,
                FONT_MAIN_25
        );

        exitBtn.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        root.getChildren().addAll(title, localBtn, computerBtn, onlineBtn, settingsBtn, exitBtn);
        stage.show();
    }
}
