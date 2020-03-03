package by.urikxx.DAO.MySQL;

import by.urikxx.DAO.Interfaces.TeacherDAO;
import by.urikxx.models.Teacher;
import by.urikxx.util.ConfigurationManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MySQLTeacherDAO extends MySQLJDBC implements TeacherDAO {
    private static final Logger logger = Logger.getLogger(MySQLCourseDAO.class);

    @Override
    public int insertTeacher(Teacher teacher) {
        int rows = 0;
        try (Connection conn = getConnection()) {
            System.out.println("Connection to DB successful!");
            Statement statement = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(ConfigurationManager.getInstance().insert_teacher_sql);
            preparedStatement.setString(1, teacher.getName());
            rows = preparedStatement.executeUpdate();
         }
        catch(Exception ex){
            logger.warn(ex.getMessage());
        }
        return rows;
    }

    @Override
    public ArrayList<Teacher> getTeachers() {
        ArrayList<Teacher> teachers = null;
        try (Connection conn = getConnection()) {
            System.out.println("Connection to DB successful!");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(ConfigurationManager.getInstance().select_teachers_sql);
            teachers = new ArrayList<>();
            while(resultSet.next()){
                teachers.add(new Teacher(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
            }
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }
        return teachers;
    }

    @Override
    public Teacher getTeacherById(int id) {
        return null;
    }
}
