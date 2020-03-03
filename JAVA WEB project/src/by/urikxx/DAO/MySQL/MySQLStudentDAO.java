package by.urikxx.DAO.MySQL;

import by.urikxx.DAO.Interfaces.StudentDAO;
import by.urikxx.models.Student;
import by.urikxx.util.ConfigurationManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MySQLStudentDAO extends MySQLJDBC implements StudentDAO {
    private static final Logger logger = Logger.getLogger(MySQLCourseDAO.class);

    @Override
    public int insertStudent(Student student) {
        int row = 0;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(ConfigurationManager.getInstance().insert_student_sql);
            preparedStatement.setString(1, student.getName());
            row = preparedStatement.executeUpdate();
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }finally {
            connectionPool.closeConnection(conn);
        }
        return row;
    }

    @Override
    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(ConfigurationManager.getInstance().select_students_sql);
            students = new ArrayList<>();
            while(resultSet.next()){
                students.add(new Student( resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3)));
            }
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }finally {
            connectionPool.closeConnection(conn);
        }
        return students;
    }

    @Override
    public Student getStudentById(int id) {
        return null;
    }

    @Override
    public int setFeedback(Student student, String feedback, int mark) {
        int row = 0;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            Statement statement = conn.createStatement();
            //row = statement.executeUpdate("INSERT feedbacks(StudentId, Mark, Feedback) VALUES (" + student.getId() + " , " + student.getMark()+ " , '"+ student.getFeedback() +"')");
        }
        catch(Exception ex){
            logger.warn(ex.getMessage());
        }finally {
            connectionPool.closeConnection(conn);
        }
        return row;
    }
}
