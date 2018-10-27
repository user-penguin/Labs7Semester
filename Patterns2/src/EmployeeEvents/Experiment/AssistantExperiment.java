package EmployeeEvents.Experiment;

public class AssistantExperiment implements ExperimentAction {
    @Override
    public void doExperiment() {
        System.out.println("У меня нет допуска.");
    }
}
