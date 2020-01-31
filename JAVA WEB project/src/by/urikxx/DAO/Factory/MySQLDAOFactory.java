package by.urikxx.DAO.Factory;

import by.urikxx.DAO.Interfaces.CourseDAO;
import by.urikxx.DAO.Interfaces.StudentDAO;
import by.urikxx.DAO.Interfaces.TeacherDAO;
import by.urikxx.DAO.MySQL.MySQLCourseDAO;
import by.urikxx.DAO.MySQL.MySQLStudentDAO;
import by.urikxx.DAO.MySQL.MySQLTeacherDAO;

public class MySQLDAOFactory extends DAOFactory {

    @Override
    public CourseDAO getCourseDAO() {
        return new MySQLCourseDAO();
    }

    @Override
    public StudentDAO getStudentDAO() {
        return new MySQLStudentDAO();
    }

    @Override
    public TeacherDAO getTeacherDAO() {
        return new MySQLTeacherDAO();
    }
}
