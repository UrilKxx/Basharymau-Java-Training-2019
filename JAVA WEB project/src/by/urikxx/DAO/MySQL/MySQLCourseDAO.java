package by.urikxx.DAO.MySQL;

import by.urikxx.DAO.Interfaces.CourseDAO;
import by.urikxx.Main;
import by.urikxx.classes.Course;
import by.urikxx.classes.Feedback;
import by.urikxx.classes.Student;
import by.urikxx.classes.Teacher;
import by.urikxx.util.ConfigurationManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MySQLCourseDAO extends MySQLJDBC implements CourseDAO {
    private static final Logger logger = Logger.getLogger(MySQLCourseDAO.class);
    @Override
    public int insertCourse(Course course) {
        int row = 0;
        try (Connection conn = getConnection()) {
            System.out.println("Connection to DB successful!");
            Statement statement = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(ConfigurationManager.getInstance().insert_courses_sql);
            preparedStatement.setString(1, course.getName());
            preparedStatement.setInt(2, course.getTeacher().getId());
            preparedStatement.setBoolean(3, course.isEndCourse());
            row = preparedStatement.executeUpdate();
            for (Map.Entry<Student, Feedback> student : course.getStudents().entrySet()) {
                preparedStatement = conn.prepareStatement(ConfigurationManager.getInstance().insert_courses_students_sql);
                preparedStatement.setInt(1, course.getId());
                preparedStatement.setInt(2, student.getKey().getId());
                preparedStatement.setString(3, student.getValue().getFeedback());
                preparedStatement.setInt(4, student.getValue().getMark());

                preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }
        return row;
    }

    @Override
    public ArrayList<Course> getCourses() {
        ArrayList<Course> courses = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection conn = null;
        try {
            System.out.println("Connection to DB successful!");
            conn = connectionPool.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(ConfigurationManager.getInstance().select_courses_sql);
            courses = new ArrayList<>();
            while (resultSet.next()) {
                courses.add(new Course(resultSet.getInt(1), resultSet.getString(2),
                                        getTeacherOnCourse(resultSet.getInt(1)),
                                        getStudentsOnCourse(resultSet.getInt(1)),
                                        resultSet.getBoolean(4)));
            }

        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }
        return courses;
    }

    @Override
    public Course getCourseById(int id) {
        return null;
    }

    @Override
    public Teacher getTeacherOnCourse(int id) {
        Teacher teacher = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            System.out.println("Connection to DB successful!");
            PreparedStatement preparedStatement = conn.prepareStatement(ConfigurationManager.getInstance().select_teacher_on_course_sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                teacher = new Teacher(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }
        return teacher;
    }

    @Override
    public  Map<Student, Feedback> getStudentsOnCourse(int id) {
        Map<Student, Feedback> students = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            System.out.println("Connection to DB successful!");
            PreparedStatement preparedStatement = conn.prepareStatement(ConfigurationManager.getInstance().select_students_on_course_sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            students = new HashMap<>();
            while(resultSet.next()){
                if (resultSet.getString(3) == null || resultSet.getString(4) == null )
                    students.put(new Student( resultSet.getInt(1),resultSet.getString(2)), new Feedback());
                else
                    students.put(new Student( resultSet.getInt(1),resultSet.getString(2)), new Feedback(resultSet.getString(3),(Integer.parseInt(resultSet.getString(4)))));
            }
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }
        return students;
    }

    @Override
    public int finishCourse(int courseId) {
        int row = 0;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            System.out.println("Connection to DB successful!");
            PreparedStatement preparedStatement = conn.prepareStatement(ConfigurationManager.getInstance().update_courses_sql);
            preparedStatement.setInt(1,courseId);
            row = preparedStatement.executeUpdate();
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }
        return row;
    }

    @Override
    public int setFeedbacks(int courseId, int studentId, String feedback, int mark) {
        int row = 0;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            System.out.println("Connection to DB successful!");
            PreparedStatement preparedStatement = conn.prepareStatement(ConfigurationManager.getInstance().insert_feedback_sql);
            preparedStatement.setString(1, feedback);
            preparedStatement.setInt(2, mark);
            preparedStatement.setInt(3, courseId);
            preparedStatement.setInt(4, studentId);
            row = preparedStatement.executeUpdate();
        }
        catch(Exception ex){
            logger.warn(ex.getMessage());
        }
        return row;
    }
}
