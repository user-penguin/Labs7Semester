package sample;

import Employees.CrazyScientist;
import Employees.Scientist;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class Controller {
    private ArrayList<Scientist> workShift;

    @FXML
    private Button starter;

    @FXML
    private Label log;

    @FXML
    private void testWorking() {
        log.setText("it's working!");
        workShift = new ArrayList<>();
    }

    @FXML
    private void addNewCrazy() {
        workShift.add(new CrazyScientist("Evgen", 4));
        System.out.println("Evgen added");
        log.setText("Evgen added");
    }
}
