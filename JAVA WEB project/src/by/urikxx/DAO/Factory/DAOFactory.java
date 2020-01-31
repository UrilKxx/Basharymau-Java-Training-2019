package by.urikxx.DAO.Factory;

import by.urikxx.DAO.Interfaces.CourseDAO;
import by.urikxx.DAO.Interfaces.StudentDAO;
import by.urikxx.DAO.Interfaces.TeacherDAO;

public abstract class DAOFactory {

    public abstract CourseDAO getCourseDAO();
    public abstract StudentDAO getStudentDAO();
    public abstract TeacherDAO getTeacherDAO();

    public static DAOFactory getDAOFactory(String connection){
        switch (connection){
            case "MySQL":
                return new MySQLDAOFactory();
            default:
                return  null ;
        }
    }

}
