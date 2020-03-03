package by.urikxx.models;

public class Teacher extends User {
    static int counter = 1;

    private int id;
    private String name;
    private String login;


    public Teacher(String name , String login) {
        super(name, login);
        id =  ++counter;
    }

    public  Teacher(int id, String name, String login){
        super(id, name, login);
        counter = id++;
    }
}
