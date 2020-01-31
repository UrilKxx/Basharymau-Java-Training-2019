package by.urikxx.DAO.Interfaces;

import by.urikxx.classes.Student;

import java.util.ArrayList;

public interface StudentDAO {
    int insertStudent(Student student);
    ArrayList<Student> getStudents();
    Student getStudentById(int id);
    int setFeedback(Student student, String feedback, int mark);
  //  int setMark(int mark);
}
