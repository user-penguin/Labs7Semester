package data.realisation;

import data.entity.IBook;
import data.entity.Tag;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class StandardBook implements IBook {
    private int id;
    private String name;
    private  String year;
    private String author;
    private ArrayList<Integer> users;
    private ArrayList<Tag> tags;

    public StandardBook(String name, String author, String year, ArrayList<Tag> tags) {
        setName(name);
        setAuthor(author);
        setYear(year);
        this.tags = new ArrayList<>();
        putTags(tags);
        this.users = new ArrayList<>();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void putTags(ArrayList<Tag> tags) {
            this.tags.addAll(tags);
    }

    @Override
    public void putUser(int id) {
        this.users.add(id);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getAuthor() {
        return this.author;
    }

    @Override
    public String getYear() {
        return this.year;
    }

    @Override
    public ArrayList<Tag> getTaglist() {
        return this.tags;
    }

    @Override
    public ArrayList<Integer> getAllUserNum() {
        return this.users;
    }

    @Override
    // перевод в JSON
    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        result.put("id", this.id);
        result.put("name", this.name);
        result.put("author", this.author);
        result.put("year", this.year);
        // вбиваем тэги в лист
        JSONArray tagsJSON = new JSONArray();
        for (Tag tag: this.tags) {
            tagsJSON.add(tag.toString());
        }
        result.put("tags", tagsJSON);
        JSONArray usersJSON = new JSONArray();
        for (int id: this.users) {
            usersJSON.add(id);
        }
        result.put("users", usersJSON);
        return  result;
    }

    // перевод из JSON в объект книги
    public static StandardBook toBook(JSONObject BookJSON) {
        ArrayList<Tag> tagList = new ArrayList<>();
        JSONArray tagListJSON = (JSONArray) BookJSON.get("tags");
        for (Object tag: tagListJSON) {
            tagList.add(Tag.valueOf(tag.toString()));
        }
        StandardBook book = new StandardBook(
                BookJSON.get("name").toString(),
                BookJSON.get("author").toString(),
                BookJSON.get("year").toString(),
                tagList);
        book.setId(Integer.parseInt(BookJSON.get("id").toString()));
        JSONArray usersJSON = (JSONArray) BookJSON.get("users");
        for (Object user: usersJSON) {
            book.putUser(Integer.parseInt(user.toString()));
        }
        return book;
    }
}
