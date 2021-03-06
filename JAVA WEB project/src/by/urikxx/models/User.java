package by.urikxx.models;

public abstract class User  {

    private int id;
    private String name;
    private String login;

    public User(int id, String name, String login) {
        this.id = id;
        this.name = name;
        this.login = login;
    }

    public User(String name, String login) {
        this.name = name;
        this.login = login;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getLogin() {
        return login;
    }


}
