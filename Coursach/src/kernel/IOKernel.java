package kernel;

import data.entity.ILibrary;
import data.realisation.StandardBook;
import data.entity.Tag;

import java.util.ArrayList;

public class IOKernel {
    // добавление новой книги
    public void addNewBook(ILibrary library, String name, String author, String year, ArrayList<Tag> tags) {
        library.addBook(new StandardBook(name, author, year, tags));
    }
}
