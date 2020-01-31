package by.urikxx.classes;

import java.util.ArrayList;

public class Teacher {
    static int counter = 1;


    private int id;
    private String name;


    public Teacher(String name) {
        id =  ++counter;
        this.name = name;
    }

    public  Teacher(int id, String name){
        this.id = id;
        counter = id++;
        this.name = name;
    }


    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
