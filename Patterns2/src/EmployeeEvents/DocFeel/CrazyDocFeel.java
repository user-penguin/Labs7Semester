package EmployeeEvents.DocFeel;

public class CrazyDocFeel implements DocFeelingEvent {
    @Override
    public void feelDoc() {
        System.out.println("Ой, ну прошу вас, кому нужна эта писанина");
    }
}
