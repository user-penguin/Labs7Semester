package data.entity;

import java.util.ArrayList;

public interface IBook {
    ArrayList<Tag> tagList = new ArrayList<>();

    void setName(String name);
    void setAuthor(String author);
    void setYear(String year);
    void putTags(ArrayList<Tag> tags);

    public String getName();
    public String getAuthor();
    public String getYear();
    public ArrayList<Tag> getTaglist();
}
