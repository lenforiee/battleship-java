package views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import config.GameConfig;

public class SettingsView extends AbstractView {

    public SettingsView(Stage parentStage) {
        super("Statki - Ustawienia", 400, 350, 30);
        this.enableModal(parentStage);

        Text title = new Text("USTAWIENIA");
        title.setFill(Color.rgb(223, 233, 235));
        title.setEffect(DS_BLACK);
        title.setFont(FONT_MAIN_36);

        Region separator = new Region();
        separator.setPrefHeight(1);
        separator.setMinHeight(1);
        separator.setMaxHeight(1);

        separator.setStyle("-fx-background-color: linear-gradient(to right, transparent, #3a5555 20%, #3a5555 80%, transparent);");

        Text matrixTitle = new Text("WYMIARY SIATKI");
        matrixTitle.setFill(Color.rgb(223, 233, 235));
        matrixTitle.setEffect(DS_BLACK);
        matrixTitle.setFont(FONT_MAIN_25);

        Text matrixSliderLabel = new Text();
        matrixSliderLabel.setFill(Color.rgb(223, 233, 235));
        matrixSliderLabel.setEffect(DS_BLACK);
        matrixSliderLabel.setFont(FONT_MAIN_16);

        Slider matrixSlider = new Slider(8, 15, GameConfig.matrixSize);
        matrixSlider.setBlockIncrement(1);
        matrixSlider.setMajorTickUnit(1);
        matrixSlider.setMinorTickCount(0);
        matrixSlider.setSnapToTicks(true);
        matrixSlider.setEffect(DS_BLACK);
        matrixSlider.setStyle("-fx-pref-width: 300px; -fx-pref-height: 50px");

        matrixSlider.setOnMouseReleased(e -> {
            GameConfig.matrixSize = (int)matrixSlider.getValue();
        });

        matrixSliderLabel.textProperty().bind(matrixSlider.valueProperty().asString("%.0f"));

        HBox sliderSpace = new HBox(10);
        sliderSpace.setAlignment(Pos.CENTER);
        sliderSpace.getChildren().addAll(matrixSlider, matrixSliderLabel);

        CheckBox bonusMoveCheckbox = new CheckBox("Gracz ma dodatkowy ruch po trafieniu?");
        bonusMoveCheckbox.setSelected(GameConfig.bonusMoveOnHit);
        bonusMoveCheckbox.setTextFill(Color.rgb(223, 233, 235));
        bonusMoveCheckbox.setEffect(DS_BLACK);
        bonusMoveCheckbox.setFont(FONT_MAIN_16);

        bonusMoveCheckbox.setOnAction(e -> {
            GameConfig.bonusMoveOnHit = !GameConfig.bonusMoveOnHit;
        });

        Button abortBtn = new Button();
        Text abortText = new Text("COFNIJ");
        abortText.setEffect(DS_BLACK);
        abortText.setFont(FONT_MAIN_25);
        abortText.setFill(Color.rgb(172, 199, 200));
        abortBtn.setEffect(DS_RED);
        abortBtn.setGraphic(abortText);
        abortBtn.setStyle("-fx-background-color: #1D2E36; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: #9F7A79; -fx-pref-width: 300px; -fx-pref-height: 50px; -fx-border-width: 2px");

        abortBtn.setOnAction(e -> {
            this.stage.close();
        });

        this.root.getChildren().addAll(title, separator, matrixTitle, sliderSpace, bonusMoveCheckbox, abortBtn);
        this.stage.show();
    }
}
