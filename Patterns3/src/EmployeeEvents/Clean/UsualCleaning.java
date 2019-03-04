package EmployeeEvents.Clean;

public class UsualCleaning implements CleaningAction {
    @Override
    public void clean() {
        System.out.println("Мы выше этого по званию, обратитесь к лаборанту");
    }
}
