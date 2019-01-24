package data;

public abstract class User {
    private String userName;
    private String password;

    User(String name, String pass) {
        this.userName = name;
        this.password = pass;
    }

    User() {}

    public String getUserName() {
        return this.userName;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }
}
