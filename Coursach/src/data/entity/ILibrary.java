package data.entity;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface ILibrary {
    String getName();

    void addBook(IBook book);
    void createUser(IUser user);

    void initialization() throws FileNotFoundException;

    public ArrayList<IUser> getUsers();
    Object findBook(String author, String name, String Year);

}