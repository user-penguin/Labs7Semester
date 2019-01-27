package data.realisation;

import data.database.DBTools;
import data.entity.IBook;
import data.entity.ILibrary;
import data.entity.IUser;

import data.entity.Tag;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Library implements ILibrary {
    private String name;
    private ArrayList<IBook> books;
    private ArrayList<IUser> users;

    public Library(String name) {
        this.name = name;
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void initialization() {
        JSONObject booksFromFile = DBTools.getBooksJSON();
        ArrayList<IBook> bookArrayList = new ArrayList<>();
        // выдёргиваем массив книг в джсон формате
        JSONArray allBooksFromJSON = (JSONArray) booksFromFile.get("books");
        for (Object book: allBooksFromJSON) {
            // создадим новую книгу
            JSONObject bookFrame = (JSONObject) book;
            // создадим лист тегов для новой книги
            ArrayList<Tag> tagList = new ArrayList<>();
            JSONArray tagListJSON = (JSONArray) bookFrame.get("tags");
            for (Object tag: tagListJSON) {
                tagList.add(Tag.valueOf(tag.toString()));
            }
            StandardBook realBook = new StandardBook(
                    bookFrame.get("name").toString(),
                    bookFrame.get("author").toString(),
                    bookFrame.get("year").toString(),
                    tagList);
            bookArrayList.add(realBook);
        }

        JSONObject usersFromFile = DBTools.getUsersJSON();

    }

    public ArrayList<IUser> getUsers() {
        return this.users;
    }

    @Override
    public Object findBook(String author, String name, String year) {
        ArrayList<IBook> authorSort = new ArrayList<>(books);
        // поиск книг по автору
        if (author.compareTo("") != 0) {
            for(IBook book: books) {
                if (book.getAuthor().compareTo(author) != 0) {
                    authorSort.remove(book);
                }
            }
        }
        // поиск книг по имени
        ArrayList<IBook> nameSort = new ArrayList<>(authorSort);
        if (name.compareTo("") != 0) {
            for(IBook book: books) {
                if (book.getName().compareTo(name) != 0) {
                    nameSort.remove(book);
                }
            }
        }
        // поиск книг по году
        ArrayList<IBook> yearSort = new ArrayList<>(nameSort);
        if (year.compareTo("") != 0) {
            for (IBook book: books) {
                if (book.getYear().compareTo(year) != 0) {
                    yearSort.remove(book);
                }
            }
        }

        return yearSort;
    }

    @Override
    public void addBook(IBook book) {
        this.books.add(book);
    }

    @Override
    public void createUser(IUser user) {
        this.users.add(user);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
