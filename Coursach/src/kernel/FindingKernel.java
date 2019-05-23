package kernel;

import data.entity.IBook;

import java.util.ArrayList;

public class FindingKernel {
    // по id книг юзера находим все id читателей,
    // которые читают эти же книги
    public static ArrayList<Integer> findReadersByBookList(ArrayList<IBook> sourceBooks) {
        ArrayList<Integer> userIdNums = new ArrayList<>();
        for (IBook book: sourceBooks) {
            for (int id: book.getAllUserNum()) {
                userIdNums.add(id);
            }
        }
        return userIdNums;
    }

    // нахождение всех повторений юзеров и их количества
//    public static ArrayList<MapForUsersFrequency> calculateFrequency()
}
