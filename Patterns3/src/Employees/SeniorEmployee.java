package Employees;

import EmployeeEvents.Brainstorm.OrdinaryBrainstorm;
import EmployeeEvents.Clean.UsualCleaning;
import EmployeeEvents.DocFeel.SeniorDocFeel;
import EmployeeEvents.Experiment.OrdinaryExperiment;
import EmployeeEvents.Relax.RelaxForAll;

public class SeniorEmployee extends Scientist {
    public SeniorEmployee(String name, int rank) {
        super(name, rank);
        cleaningAction = new UsualCleaning();
        relaxAction = new RelaxForAll();
        experimentAction = new OrdinaryExperiment();
        docFeelingAction = new SeniorDocFeel();
        brainstormAction = new OrdinaryBrainstorm();
    }
}
