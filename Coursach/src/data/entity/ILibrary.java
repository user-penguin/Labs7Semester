package data.entity;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface ILibrary {
    Object findBook(String author, String name, String Year);
    void addBook(IBook book);
    String getName();
    void initialization() throws FileNotFoundException;
}