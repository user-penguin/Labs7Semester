package data.realisation;

import data.entity.ILibrary;
import data.entity.IUser;
import data.entity.Tag;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class User implements IUser {
    private int id;
    private String name;
    private String password;
    private ArrayList<Tag> tags;
    private ArrayList<Integer> books;

    public User(String name, String password, ArrayList<Tag> tags) {
        this.tags = new ArrayList<>();
        this.name = name;
        this.password = password;
        this.tags.addAll(tags);
        this.books = new ArrayList<>();
    }

    @Override
    public String getUserName() {
        return this.name;
    }

    @Override
    public int getUserId() {
        return this.id;
    }

    @Override
    public ArrayList<Tag> getTags() {
        return this.tags;
    }

    @Override
    public void putBook(int id) {
        this.books.add(id);
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setUserName(String userName) {
        this.name = userName;
    }

    @Override
    public void setTags(ArrayList<Tag> tags) {
        this.tags.addAll(tags);
    }

    @Override
    public int getNextId(ILibrary library) {
        ArrayList<IUser> users = library.getUsers();
        int max = 0;
        for (IUser user: users) {
            if (user.getUserId() > max) {
                max = user.getUserId();
            }
        }
        return max;
    }

    @Override
    public ArrayList<Integer> getAllBooksNum() {
        return this.books;
    }

    @Override
    // перевод в JSON
    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        result.put("id", this.id);
        result.put("name", this.name);
        result.put("password", this.password);
        // вбиваем тэги в лист
        JSONArray tagsJSON = new JSONArray();
        for (Tag tag: tags) {
            tagsJSON.add(tag.toString());
        }
        result.put("tags", tagsJSON);
        JSONArray usersJSON = new JSONArray();
        for (int id: this.books) {
            usersJSON.add(id);
        }
        result.put("books", usersJSON);
        return  result;
    }

    // перевод из
    public static User toUser(JSONObject UserJSON) {
        ArrayList<Tag> tagList = new ArrayList<>();
        JSONArray tagListJSON = (JSONArray) UserJSON.get("tags");
        for (Object tag: tagListJSON) {
            tagList.add(Tag.valueOf(tag.toString()));
        }
        User user = new User(
                UserJSON.get("name").toString(),
                UserJSON.get("password").toString(),
                tagList);
        user.setId(Integer.parseInt(UserJSON.get("id").toString()));
        JSONArray booksJSON = (JSONArray) UserJSON.get("books");
        for (Object book: booksJSON) {
            user.putBook(Integer.parseInt(book.toString()));
        }
        return user;
    }
}
