package by.urikxx.models;

public class Student extends User{
    static int counter = 1;
    private int id;
    private String name;
    private String login;


    public Student(String name, String login) {
        super(name, login);
        id = ++counter;
    }

    public Student(int id, String name, String login) {
        super(id, name, login);
        counter = id++;
    }
}


