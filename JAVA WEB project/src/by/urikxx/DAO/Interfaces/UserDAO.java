package by.urikxx.DAO.Interfaces;

import by.urikxx.models.User;

import java.util.ArrayList;

public interface UserDAO {
    User login (String login, String password);
    User singUp (String userName, String login, String password, String role);
    ArrayList<User> getTeachers();
    ArrayList<User> getStudents();
}
