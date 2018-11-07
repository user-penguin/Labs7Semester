package sample;

import compute.Dots;
import compute.Math;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import transform.DefaultTransform;
import transform.OrtographProjection;

public class Controller {
    private Stage stage;

    private Scene scene;

    // Отжать у матрицы z-координату
    public double[][] calculateOrto (double[][] stock) {
        double[][] dots = new double[stock.length][];
        for (int i = 0; i < stock.length; i++) {
            dots[i] = Math.multiple(stock[i], OrtographProjection.getProjectionXY());
        }
        return dots;
    }

    // Расятнуть x,y,z
    public double[][] calculateScale(double[][] stock, double xS, double yS, double zS) {
        double[][] dots = new double[stock.length][];
        for (int i = 0; i < stock.length; i++) {
            dots[i] = Math.multiple(stock[i], DefaultTransform.getScaleMatrix(xS, yS, zS));
        }
        return dots;
    }

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

        double dots[][] = calculateOrto(calculateScale(Dots.dots, 20, 20, 10));
        for (int i = 0; i < dots.length - 1; i++) {
            Line lineD = new Line();
            lineD.setStartX(dots[i][0]);
            lineD.setStartY(dots[i][1]);
            lineD.setEndX(dots[i + 1][0]);
            lineD.setEndY(dots[i + 1][1]);
            lineD.setStrokeWidth(10);
            lineD.setStroke(Color.PINK);
            group.getChildren().addAll(lineD);
        }




        scene = new Scene(group, 800, 600, true);
        stage.setScene(scene);
        stage.show();
    }
}
