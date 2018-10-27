package Employees;

import EmployeeEvents.Brainstorm.OrdinaryBrainstorm;
import EmployeeEvents.Clean.UsualCleaning;
import EmployeeEvents.DocFeel.OrdinaryDocFeel;
import EmployeeEvents.Experiment.OrdinaryExperiment;
import EmployeeEvents.Relax.RelaxForAll;

public class OrdinaryScientist extends Scientist {
    public OrdinaryScientist(String name, int rank) {
        super(name, rank);
        cleaningAction = new UsualCleaning();
        relaxAction = new RelaxForAll();
        experimentAction = new OrdinaryExperiment();
        docFeelingAction = new OrdinaryDocFeel();
        brainstormAction = new OrdinaryBrainstorm();
    }
}
