package EmployeeEvents.DocFeel;

public class SeniorDocFeel implements DocFeelingEvent {
    @Override
    public void feelDoc() {
        System.out.println("Попросите лаборанта или кого-нибудь из обычных учёных, у меня ещё куча дел");
    }
}
