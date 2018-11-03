package Employees;

import EmployeeEvents.Brainstorm.AssistBrainstorm;
import EmployeeEvents.Clean.AssistClean;
import EmployeeEvents.DocFeel.OrdinaryDocFeel;
import EmployeeEvents.Experiment.AssistantExperiment;
import EmployeeEvents.Relax.RelaxForAll;

public class Assistant extends Scientist {
    public Assistant(String name, int rank) {
        super(name, rank);
        cleaningAction = new AssistClean();
        relaxAction = new RelaxForAll();
        experimentAction = new AssistantExperiment();
        docFeelingAction = new OrdinaryDocFeel();
        brainstormAction = new AssistBrainstorm();
    }
}
