package Employees;

import EmployeeEvents.Brainstorm.BrainstormAction;
import EmployeeEvents.Clean.CleaningAction;
import EmployeeEvents.DocFeel.DocFeelingEvent;
import EmployeeEvents.Experiment.ExperimentAction;
import EmployeeEvents.Relax.RelaxAction;

public abstract class Scientist {
    // координатный вопрос открыт
    private String name;
    private int rank;

    public CleaningAction cleaningAction;
    public BrainstormAction brainstormAction;
    public ExperimentAction experimentAction;
    public RelaxAction relaxAction;
    public DocFeelingEvent docFeelingAction;

    Scientist(String name, int rank) {
        this.name = name;
        this.rank = rank;
    }

    public void doClean() {
        cleaningAction.clean();
    }
    public void doBrainstorm() { brainstormAction.doBrainsorm(); }
    public void experimenting() { experimentAction.doExperiment(); }
    public void relaxing() { relaxAction.goRelax(); }
    public void doDocFeel() { docFeelingAction.feelDoc(); }
}
