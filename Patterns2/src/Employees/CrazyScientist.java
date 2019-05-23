package Employees;

import EmployeeEvents.Brainstorm.CrazyBrainstorm;
import EmployeeEvents.Clean.CrazyCleaning;
import EmployeeEvents.DocFeel.CrazyDocFeel;
import EmployeeEvents.Experiment.CrazyExperiment;
import EmployeeEvents.Relax.RelaxForAll;

public class CrazyScientist extends Scientist {
    public CrazyScientist(String name, int rank) {
        super(name, rank);
        cleaningAction = new CrazyCleaning();
        relaxAction = new RelaxForAll();
        experimentAction = new CrazyExperiment();
        docFeelingAction = new CrazyDocFeel();
        brainstormAction = new CrazyBrainstorm();
    }
}
