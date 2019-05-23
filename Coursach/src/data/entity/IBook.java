package data.entity;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public interface IBook {
    ArrayList<Tag> tagList = new ArrayList<>();

    void setName(String name);
    void setAuthor(String author);
    void setYear(String year);
    void setId(int id);
    void putTags(ArrayList<Tag> tags);
    void putUser(int id);

    String getName();
    int getId();
    String getAuthor();
    String getYear();
    ArrayList<Tag> getTaglist();
    ArrayList<Integer> getAllUserNum();

    JSONObject toJSON();
}
