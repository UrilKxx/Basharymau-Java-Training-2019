package by.urikxx.classes;

import java.util.Map;

public class Student {
    static int counter = 1;
    private int id;
    private String name;


    public Student(String name) {
        id = ++counter;
        this.name = name;
    }

    public Student(int id, String name) {
        this.id = id;
        counter = id++;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}


