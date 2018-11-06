import Employees.Assistant;
import Employees.CrazyScientist;
import Employees.OrdinaryScientist;
import Employees.SeniorEmployee;

public class Main {
    public static void main(String[] args) {
        LabBox firstBox = new LabBox(3);
        CrazyScientist crazy = new CrazyScientist("Bob", 2);
        OrdinaryScientist ordinary = new OrdinaryScientist("Jack", 1);
        Assistant assist = new Assistant("Mark", 0);
        SeniorEmployee senior = new SeniorEmployee("Daniel", 8);

        crazy.doClean();
        ordinary.doDocFeel();
        assist.doBrainstorm();
        crazy.relaxing();
        senior.experimenting();
        crazy.experimenting();
    }

}
