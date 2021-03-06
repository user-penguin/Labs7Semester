package data.entity;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public interface IUser {
    String getUserName();
    int getUserId();
    ArrayList<Tag> getTags();
    void putBook(int id);

    void setId(int id);
    void setPassword(String password);
    void setUserName(String userName);
    void setTags(ArrayList<Tag> tags);
    int getNextId(ILibrary library);
    ArrayList<Integer> getAllBooksNum();

    JSONObject toJSON();
}
