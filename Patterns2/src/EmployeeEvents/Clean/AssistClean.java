package EmployeeEvents.Clean;

public class AssistClean implements CleaningAction {
    @Override
    public void clean() {
        System.out.println("Хорошо, я сейчас уберусь");
    }
}
