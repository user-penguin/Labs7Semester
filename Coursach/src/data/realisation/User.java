package data.realisation;

import data.entity.IUser;

public class User implements IUser {
    private int id;
    private String name;
    private String password;


    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public int getUserId() {
        return 0;
    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public void setUserName(String userName) {

    }
}
