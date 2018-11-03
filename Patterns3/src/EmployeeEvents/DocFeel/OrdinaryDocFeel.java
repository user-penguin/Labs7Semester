package EmployeeEvents.DocFeel;

public class OrdinaryDocFeel implements DocFeelingEvent {
    @Override
    public void feelDoc() {
        System.out.println("Будет сделано!");
    }
}
