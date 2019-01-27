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

    public String getName();
    public int getId();
    public String getAuthor();
    public String getYear();
    public ArrayList<Tag> getTaglist();

    JSONObject toJSON();
}
