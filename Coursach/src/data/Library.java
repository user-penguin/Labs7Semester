package data;

import java.util.ArrayList;

public interface Library {
    String name = "default";

    ArrayList<Book> bookList = new ArrayList<>();
    Book findBook(String author, String name, String Year);
}