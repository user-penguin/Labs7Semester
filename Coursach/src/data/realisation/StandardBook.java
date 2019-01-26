package data.realisation;

import data.entity.IBook;
import data.entity.Tag;

import java.util.ArrayList;

public class StandardBook implements IBook {
    private String name;
    private  String year;
    private String author;
    private ArrayList<Tag> tags;

    public StandardBook(String name, String author, String year, ArrayList<Tag> tags) {
        setName(name);
        setAuthor(author);
        setYear(year);
        this.tags = new ArrayList<>();
        putTags(tags);
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
    public void putTags(ArrayList<Tag> tags) {
            this.tags.addAll(tags);
    }

    @Override
    public String getName() {
        return this.name;
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
}
