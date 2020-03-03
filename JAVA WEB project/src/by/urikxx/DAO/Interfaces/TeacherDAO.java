package by.urikxx.DAO.Interfaces;

import by.urikxx.models.Teacher;

import java.util.ArrayList;

public interface TeacherDAO {
    int insertTeacher(Teacher teacher);
    ArrayList<Teacher> getTeachers();
    Teacher getTeacherById(int id);

}
