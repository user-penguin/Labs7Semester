package data.entity;

public interface IUser {
    public String getUserName();
    public int getUserId();

    void setPassword(String password);
    void setUserName(String userName);
}
