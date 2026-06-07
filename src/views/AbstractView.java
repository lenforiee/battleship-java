package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class AbstractView {
    protected final static Font FONT_MAIN_36 = Font.font("Bricolage Grotesque 36pt Medium", 36);
    protected final static Font FONT_MAIN_25 = Font.font("Bricolage Grotesque 36pt Medium", 25);
    protected final static Font FONT_MAIN_16 = Font.font("Bricolage Grotesque 36pt Medium", 16);

    protected final static DropShadow DS_BLACK = new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 10, 0.3, 0, 0);
    protected final static DropShadow DS_BLUE = new DropShadow(BlurType.GAUSSIAN, Color.rgb(99, 136, 142), 10, 0.3, 0, 0);
    protected final static DropShadow DS_GREEN = new DropShadow(BlurType.GAUSSIAN, Color.rgb(140, 181, 157), 10, 0.3, 0, 0);
    protected final static DropShadow DS_RED = new DropShadow(BlurType.GAUSSIAN, Color.rgb(184, 119, 118), 10, 0.3, 0, 0);

    private double startX = 0;
    private double startY = 0;
    private double windowStartX = 0;
    private double windowStartY = 0;

    protected VBox root;
    protected Stage stage;
    protected Scene scene;

    public AbstractView(String title, int width, int height, int padding, Stage mainStage) {
        this.initRoot(padding);
        this.initScene(width, height);
        this.initStage(title, mainStage);

        this.scene.setOnMousePressed(e -> {
            this.startX = e.getScreenX();
            this.startY = e.getScreenY();

            this.windowStartX = this.stage.getX();
            this.windowStartY = this.stage.getY();
        });

        this.scene.setOnMouseDragged(e -> {
            this.stage.setX(windowStartX + (e.getScreenX() - this.startX));
            this.stage.setY(windowStartY + (e.getScreenY() - this.startY));
        });
    }

    public AbstractView(String title, int width, int height, int padding) {
        this(title, width, height, padding, null);
    }

    public void enableModal(Stage parentStage) {
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.initOwner(parentStage);
    }

    public void closeStage() {
        this.stage.close();
    }

    private void initRoot(int padding) {
        this.root = new VBox(15);
        this.root.setAlignment(Pos.CENTER);
        this.root.setPadding(new Insets(padding));
        this.root.setStyle("-fx-background-color: #141D22; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-color: #4D6066; -fx-border-width: 3px");
    }

    private void initStage(String title, Stage mainStage) {
        this.stage = mainStage;
        if (this.stage == null) {
            this.stage = new Stage();
        }

        this.stage.setTitle(title);
        this.stage.setScene(this.scene);
        this.stage.initStyle(StageStyle.TRANSPARENT);
    }

    private void initScene(int width, int height) {
        this.scene = new Scene(this.root, width, height);
        this.scene.setFill(Color.TRANSPARENT);
    }

    public Button createStyledButton(String text, Paint textColour, String bgColour, String borderColour, Effect btnEffect, Effect textEffect, Font font) {
        Button btn = new Button();
        Text btnText = new Text(text);
        btnText.setEffect(textEffect);
        btnText.setFont(font);
        btnText.setFill(textColour);
        btn.setGraphic(btnText);

        btn.setEffect(btnEffect);
        btn.setStyle("-fx-background-color:" + bgColour + "; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: " + borderColour + "; -fx-pref-width: 300px; -fx-pref-height: 50px; -fx-border-width: 2px");

        btn.setOnMouseEntered(e -> {
            btn.setOpacity(0.8);
        });

        btn.setOnMouseExited(e -> {
            btn.setOpacity(1);
        });

        return btn;
    }
}
