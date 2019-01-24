package data;

import java.util.ArrayList;

public interface Book {
    String name = "DefaultBook";
    String author = "DefaultAuthor";
    String year = "2000";
    ArrayList<Tag> tagList = new ArrayList<>();

    void setName(String name);
    void setAuthor(String author);
    void setYear(String year);
    void putTag(Tag tag);

    public String getName();
    public String getAuthor();
    public String getYear();
    public ArrayList<Tag> getTaglist();
}
