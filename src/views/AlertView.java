package views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertView extends AbstractView {

    private boolean confirmed = false;

    private AlertView(Stage parentStage, String title, String headerText, String contentText, boolean showCancelButton) {
        super(title, 450, 250, 0, new Stage(StageStyle.TRANSPARENT));

        this.enableModal(parentStage);

        this.root.setStyle("-fx-background-color: #141D22; -fx-background-radius: 12px; -fx-border-radius: 12px; -fx-border-color: #4D6066; -fx-border-width: 2px; -fx-padding: 24px;");
        this.root.setAlignment(Pos.TOP_CENTER);
        this.root.setSpacing(15);

        Label lblHeader = new Label(headerText.toUpperCase());
        lblHeader.setFont(FONT_MAIN_25);
        lblHeader.setStyle("-fx-text-fill: #E63946; -fx-font-weight: bold; -fx-letter-spacing: 1px;");

        Label lblContent = new Label(contentText);
        lblContent.setFont(FONT_MAIN_16);
        lblContent.setStyle("-fx-text-fill: #94A3B8; -fx-alignment: center; -fx-text-alignment: center; -fx-line-spacing: 4px;");
        lblContent.setWrapText(true);

        VBox textContainer = new VBox(8, lblHeader, lblContent);
        textContainer.setAlignment(Pos.CENTER);

        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);

        Button btnConfirm = this.createStyledButton(
                "OK",
                Color.rgb(82, 183, 136),
                "#1A2D2B",
                "#253E3B",
                DS_GREEN,
                DS_BLACK,
                FONT_MAIN_16
        );
        btnConfirm.setPrefWidth(120);
        btnConfirm.setOnAction(e -> {
            this.confirmed = true;
            closeStage();
        });

        buttonContainer.getChildren().add(btnConfirm);

        if (showCancelButton) {
            Button btnCancel = this.createStyledButton(
                    "ANULUJ",
                    Color.rgb(230, 57, 70),
                    "#1E1E24",
                    "#2D2D35",
                    DS_RED,
                    DS_BLACK,
                    FONT_MAIN_16
            );
            btnCancel.setPrefWidth(120);
            btnCancel.setOnAction(e -> {
                this.confirmed = false;
                closeStage();
            });
            buttonContainer.getChildren().add(btnCancel);
        }

        this.root.getChildren().addAll(textContainer, buttonContainer);

        this.stage.setScene(this.scene);
    }

    public static void showInfo(Stage owner, String title, String header, String content) {
        AlertView alert = new AlertView(owner, title, header, content, false);
        alert.stage.showAndWait();
    }

    public static boolean showConfirm(Stage owner, String title, String header, String content) {
        AlertView alert = new AlertView(owner, title, header, content, true);
        alert.stage.showAndWait();
        return alert.confirmed;
    }
}