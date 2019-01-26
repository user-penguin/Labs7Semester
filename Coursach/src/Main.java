import data.realisation.Library;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Worked");
        Library library = new Library("kokos");
        library.initialization();
    }
}
