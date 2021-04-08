package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label.setText("JavaFX");
        label.setEffect(new DropShadow(10.0, Color.BLACK));
        label.setTextFill(Color.AQUA);
    }
}
