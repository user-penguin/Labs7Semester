import data.realisation.Library;

import java.io.FileNotFoundException;

public class Main {
    public boolean isDegree(int a) {
        if (a == 2) return true;
        if (a % 2 != 0 && a > 1) return false;
        return isDegree(a / 2);
    }

    public int count3(int a) {
        String word = String.valueOf(a);
        char[] bukva = word.toCharArray();
        int count = 0;
        for (char m: bukva)
            if (m == '3')
                count++;
        return count;
    }

    public static void main(String[] args) throws FileNotFoundException {
//        System.out.println("Worked");
//        Library library = new Library("kokos");
//        library.initialization();
        boolean a = new Main().isDegree(16);
        if (a) {
            System.out.println("Yeee");
        } else
        {
            System.out.println("Nooo");
        }
        System.out.println(new Main().count3(123333));
    }
}
