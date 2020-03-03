package by.urikxx.DAO.Factory;

import by.urikxx.DAO.Interfaces.CourseDAO;
import by.urikxx.DAO.Interfaces.StudentDAO;
import by.urikxx.DAO.Interfaces.TeacherDAO;
import by.urikxx.DAO.Interfaces.UserDAO;

public abstract class DAOFactory {

    public abstract CourseDAO getCourseDAO();
    public abstract StudentDAO getStudentDAO();
    public abstract TeacherDAO getTeacherDAO();
    public abstract UserDAO getUserDAO();

    public static DAOFactory getDAOFactory(String connection){
        switch (connection){
            case "MySQL":
                return new MySQLDAOFactory();
            default:
                return  null ;
        }
    }

}
