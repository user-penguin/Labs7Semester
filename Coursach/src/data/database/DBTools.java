package data.database;

import data.entity.IUser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class DBTools {
    private static final String pathToBooks = "/home/dmitry/Documents/Labs7Semester/Coursach/src/data/database/books.json";
    private static final String pathToUsers = "/home/dmitry/Documents/Labs7Semester/Coursach/src/data/database/books.json";

    public static JSONObject getBooksJSON() {
        try {
            return (JSONObject) new JSONParser().parse(new FileReader(pathToBooks));
        } catch (Exception e) { System.out.println(e); }
        return null;
    }

    public static JSONObject getUsersJSON() {
        try {
            return (JSONObject) new JSONParser().parse(new FileReader(pathToUsers));
        } catch (Exception e) { System.out.println(e); }
        return null;
    }

    // @TODO дописать добавление юзера
    public static void addUser(IUser user) {
        JSONObject oldUsers = getUsersJSON();

    }
}
