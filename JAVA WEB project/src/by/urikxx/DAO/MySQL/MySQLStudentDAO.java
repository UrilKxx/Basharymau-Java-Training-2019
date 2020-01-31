package by.urikxx.DAO.MySQL;

import by.urikxx.DAO.Interfaces.StudentDAO;
import by.urikxx.DAO.MySQL.MySQLJDBC;
import by.urikxx.classes.Student;
import by.urikxx.util.ConfigurationManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLStudentDAO extends MySQLJDBC implements StudentDAO {
    private static final Logger logger = Logger.getLogger(MySQLCourseDAO.class);

    @Override
    public int insertStudent(Student student) {
        int row = 0;
        try (Connection conn = getConnection()) {
            System.out.println("Connection to DB successful!");
            PreparedStatement preparedStatement = conn.prepareStatement(ConfigurationManager.getInstance().insert_student_sql);
            preparedStatement.setString(1, student.getName());
            row = preparedStatement.executeUpdate();
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }
        return row;
    }

    @Override
    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = null;
        try (Connection conn = getConnection()) {
            System.out.println("Connection to DB successful!");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(ConfigurationManager.getInstance().select_students_sql);
            students = new ArrayList<>();
            while(resultSet.next()){
                students.add(new Student( resultSet.getInt(1),resultSet.getString(2)));
            }
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
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
        try (Connection conn = getConnection()){
            System.out.println("Connection to DB successful!");
            Statement statement = conn.createStatement();
            //row = statement.executeUpdate("INSERT feedbacks(StudentId, Mark, Feedback) VALUES (" + student.getId() + " , " + student.getMark()+ " , '"+ student.getFeedback() +"')");
        }
        catch(Exception ex){
            logger.warn(ex.getMessage());
    }
        return row;
    }
}
