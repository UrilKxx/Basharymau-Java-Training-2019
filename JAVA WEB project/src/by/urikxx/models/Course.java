package by.urikxx.models;

import java.util.Map;


public class Course {
    static int counter = 1;
    private int id;
    private String name;
    private Teacher teacher;
    private Map<Student, Feedback> students;
    private boolean endCourse;

    public Course(String name, Teacher teacher, Map<Student, Feedback> students, boolean endCourse) {
        id = ++counter;
        this.name = name;
        this.teacher = teacher;
        this.students = students;
        this.endCourse = endCourse;
    }



    public Course(int id, String name, Teacher teacher, Map<Student, Feedback> students, boolean endCourse) {
        this.id = id;
        counter = id++;
        this.name = name;
        this.teacher = teacher;
        this.students = students;
        this.endCourse = endCourse;
    }

    public boolean isEndCourse() {
        return endCourse;
    }

    public void setEndCourse() {
        this.endCourse = true;
    }

    private String getStudentsOnString() {
        StringBuilder studentStr = new StringBuilder();
        for (Map.Entry<Student, Feedback> student : students.entrySet()) {
            studentStr.append(student.getKey().getName());
            studentStr.append(" ");
        }
        return studentStr.toString();
    }

    public Map<Student, Feedback> getStudents() {
        return students;
    }

    public void setStudents(Map<Student, Feedback> students) {
        this.students = students;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

}