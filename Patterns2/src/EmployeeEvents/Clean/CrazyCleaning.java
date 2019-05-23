package EmployeeEvents.Clean;

public class CrazyCleaning implements CleaningAction {
    @Override
    public void clean() {
        System.out.println("Я не для уборки сюда приехал, а чтобы творить!");
    }
}
