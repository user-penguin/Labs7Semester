package sample;

import compute.Dots;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import transform.TransformLib;

import static transform.TransformLib.calculateOrto;
import static transform.TransformLib.calculateScale;

public class Controller {
    private Stage stage;
    private Scene scene;

    @FXML
    private void start () {
        stage = new Stage();
        Group group = new Group();

        double dots[][] = calculateOrto(calculateScale(Dots.dots, 20, 20, 10));
        for (int i = 0; i < dots.length - 1; i++) {
            Line lineD = new Line();
            lineD.setStartX(dots[i][0]);
            lineD.setStartY(dots[i][1]);
            lineD.setEndX(dots[i + 1][0]);
            lineD.setEndY(dots[i + 1][1]);
            lineD.setStrokeWidth(3);
            lineD.setStroke(Color.PINK);
            group.getChildren().addAll(lineD);
        }

        scene = new Scene(group, 800, 600, true);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void acceptXRot () {
        stage = new Stage();
        Group group = new Group();

        double dots[][] = calculateOrto(calculateScale(Dots.dots, 20, 20, 10));

        for (int i = 0; i < dots.length - 1; i++) {
            Line lineD = new Line();
            lineD.setStartX(dots[i][0]);
            lineD.setStartY(dots[i][1]);
            lineD.setEndX(dots[i + 1][0]);
            lineD.setEndY(dots[i + 1][1]);
            lineD.setStrokeWidth(3);
            lineD.setStroke(Color.PINK);
            group.getChildren().addAll(lineD);
        }

        scene = new Scene(group, 800, 600, true);
        stage.setScene(scene);
        stage.show();
    }
}
