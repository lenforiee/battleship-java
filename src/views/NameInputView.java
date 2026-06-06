package views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NameInputView extends AbstractView {
    public String name1 = "";
    public String name2 = "";

    private final Button acceptBtn;

    public NameInputView(Stage parentStage, Boolean twoPlayers) {
        super("Statki - Podaj nick", twoPlayers ? 500 : 400, twoPlayers ? 450 : 350, 30);
        this.enableModal(parentStage);

        Text title = new Text("WPROWADŹ NICK");
        title.setFill(Color.rgb(223, 233, 235));
        title.setEffect(DS_BLACK);
        title.setFont(FONT_MAIN_36);

        Region separator = new Region();
        separator.setPrefHeight(1);
        separator.setMinHeight(1);
        separator.setMaxHeight(1);

        separator.setStyle("-fx-background-color: linear-gradient(to right, transparent, #3a5555 20%, #3a5555 80%, transparent);");

        Text titleNick1 = new Text("WPISZ SWÓJ NICK (GRACZ 1)");
        titleNick1.setFill(Color.rgb(223, 233, 235));
        titleNick1.setEffect(DS_BLACK);
        titleNick1.setFont(FONT_MAIN_25);

        TextField nick1 = new TextField();
        nick1.setPromptText("(Twój nick...)");
        nick1.setEffect(DS_BLACK);
        nick1.setFont(FONT_MAIN_25);

        nick1.setEffect(DS_BLUE);
        nick1.setStyle("-fx-text-fill: #acc7c8; -fx-background-color: #1D2E36; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: #63888E; -fx-pref-width: 200px; -fx-pref-height: 50px; -fx-border-width: 2px");
        nick1.setOnKeyTyped(e -> {
            this.name1 = nick1.getText();
        });

        Text titleNick2 = new Text("WPISZ NICK PRZECIWNIKA (GRACZ 2)");
        titleNick2.setFill(Color.rgb(223, 233, 235));
        titleNick2.setEffect(DS_BLACK);
        titleNick2.setFont(FONT_MAIN_25);

        TextField nick2 = new TextField();
        nick2.setPromptText("(Nick przeciwnika...)");
        nick2.setEffect(DS_BLACK);
        nick2.setFont(FONT_MAIN_25);

        nick2.setEffect(DS_BLUE);
        nick2.setStyle("-fx-text-fill: #acc7c8; -fx-background-color: #1D2E36; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: #63888E; -fx-pref-width: 200px; -fx-pref-height: 50px; -fx-border-width: 2px");
        nick2.setOnKeyTyped(e -> {
            this.name2 = nick2.getText();
        });

        HBox buttonSpace = new HBox(20);
        buttonSpace.setAlignment(Pos.CENTER);

        this.acceptBtn = new Button();
        Text acceptText = new Text("POTWIERDŹ");
        acceptText.setEffect(DS_BLACK);
        acceptText.setFont(FONT_MAIN_25);
        acceptText.setFill(Color.rgb(172, 199, 200));
        this.acceptBtn.setGraphic(acceptText);
        this.acceptBtn.setEffect(DS_GREEN);
        this.acceptBtn.setStyle("-fx-background-color: #314644; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: #8CB59D; -fx-pref-width: 300px; -fx-pref-height: 50px; -fx-border-width: 2px");

        Button abortBtn = new Button();
        Text abortText = new Text("ANULUJ");
        abortText.setEffect(DS_BLACK);
        abortText.setFont(FONT_MAIN_25);
        abortText.setFill(Color.rgb(172, 199, 200));
        abortBtn.setEffect(DS_RED);
        abortBtn.setGraphic(abortText);
        abortBtn.setStyle("-fx-background-color: #1D2E36; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: #9F7A79; -fx-pref-width: 300px; -fx-pref-height: 50px; -fx-border-width: 2px");

        abortBtn.setOnAction(e -> {
            this.stage.close();
        });

        buttonSpace.getChildren().addAll(this.acceptBtn, abortBtn);

        this.root.getChildren().addAll(title, separator, titleNick1, nick1);
        if (twoPlayers) {
            this.root.getChildren().addAll(titleNick2, nick2);
        }
        this.root.getChildren().add(buttonSpace);

        this.stage.show();
    }

    public void setOnAcceptCallback(Runnable callback) {
        this.acceptBtn.setOnAction(e -> {
            callback.run();
        });
    }
}
