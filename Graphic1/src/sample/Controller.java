package sample;

import compute.Dots;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import transform.TransformLib;

import static transform.TransformLib.*;

public class Controller {
    private Stage stage = new Stage();
    private Scene scene;

    @FXML
    private Slider xRotate;

    @FXML
    private Slider yRotate;

    @FXML
    private Slider zRotate;

    @FXML
    private Slider XScale;

    @FXML
    private Slider YScale;

    @FXML
    private Slider ZScale;

    @FXML
    private Slider XTrans;

    @FXML
    private Slider YTrans;

    @FXML
    private Slider ZTrans;

    @FXML
    private CheckBox UsePoint;

    @FXML
    private TextField XPoint;

    @FXML
    private TextField YPoint;

    @FXML
    private TextField ZPoint;

    private double dots[][];

    @FXML
    private void start() {
        dots = calculateScale(Dots.dotsK, 20, 20, 20);
        dots = calculateTransfer(dots, 200, 250, 0);
        dots = calculateRotateZ(dots, -90);
        draw(dots);
    }

    @FXML
    private void XRot() {
        dots = calculateRotateX(dots, xRotate.getValue());
        draw(dots);
    }

    @FXML
    private void YRot() {
        dots = calculateRotateY(dots, yRotate.getValue());
        draw(dots);
    }

    @FXML
    private void ZRot() {
        dots = calculateRotateZ(dots, zRotate.getValue());
        draw(dots);
    }

    @FXML
    private void Scale() {
        // масштабируем относительно точки или нет
        if (UsePoint.isSelected()) {
            dots = calculateScaleByDot(dots, XScale.getValue(), YScale.getValue(), ZScale.getValue(),
                    Double.parseDouble(XPoint.getText()),
                    Double.parseDouble(YPoint.getText()),
                    Double.parseDouble(ZPoint.getText()));
        } else {
            dots = calculateScale(dots, XScale.getValue(), YScale.getValue(), ZScale.getValue());
        }
        draw(dots);
    }

    @FXML
    private void ReflectXY() {
        dots = calculateReflectionXY(dots);
        draw(dots);
    }

    @FXML
    private void ReflectYZ() {
        dots = calculateReflectionYZ(dots);
        draw(dots);
    }

    @FXML
    private void ReflectZX() {
        dots = calculateReflectionZX(dots);
        draw(dots);
    }

    @FXML
    private void Trans() {
        dots = calculateTransfer(dots, XTrans.getValue(), YTrans.getValue(), ZTrans.getValue());
        draw(dots);
    }

    private void draw(double dots[][]) {
        Group group = new Group();
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
