package sample;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Controller {
    private Stage stage;

    private Scene scene;

//    Controller() {
//        stage = new Stage();
//    }

    @FXML
    private void start () {
        stage = new Stage();
        Group group = new Group();

        // A line in Ox Axis
        Line oxLine1 = new Line(0, 0, 400, 0);

        // Stroke Width
        oxLine1.setStrokeWidth(5);
        oxLine1.setStroke(Color.BLUE);

        // A line in Oy Axis
        Line oyLine = new Line(0, 0, 0, 200);

        // Stroke Width
        oyLine.setStrokeWidth(5);
        oyLine.setStroke(Color.BLUEVIOLET);

        // An other Line
        Line line = new Line();
        line.setStartX(100.0f);
        line.setStartY(200.0f);
        line.setEndX(300.0f);
        line.setEndY(70.0f);
        line.setStrokeWidth(10);
        line.setStroke(Color.PINK);

        group.getChildren().addAll(oxLine1, oyLine, line);
        scene = new Scene(group, 800, 600, true);
        stage.setScene(scene);
        stage.show();
    }
}
