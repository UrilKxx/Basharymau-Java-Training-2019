package by.urikxx.util;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {

    private static final Logger logger = Logger.getLogger(ConfigurationManager.class);

    private static final String DB_RESOURCES_FILEPATH = "src/util/db_resources.properties";

    public String mysqlConnection = "jdbc:mysql://localhost:3306/openclassroom?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public String mysqlUsername = "root";
    public String mysqlPassword = "JedwGerd176FG";

    public String select_courses_sql = "SELECT * FROM courses";
    public String select_teacher_on_course_sql = "SELECT teachers.Id, teachers.TeacherName FROM teachers Where teachers.Id =?";
    public String select_students_on_course_sql = "SELECT students.Id, students.StudentName, courses_students.Feedback, courses_students.Mark FROM courses INNER JOIN courses_students ON courses_students.CourseId = courses.Id INNER JOIN students ON courses_students.StudentId = students.Id WHERE courses.Id = ?";

    public String insert_courses_sql = "INSERT courses (CourseName, TeacherId, CourseEnd) VALUES (?, ? , ? )";
    public String insert_feedback_sql = "NSERT courses_students (Feedback, Mark) VALUES (?, ?) WHERE CourseId = ? AND StudentId = ?";
    public String insert_courses_students_sql = "INSERT courses_students (CourseId, StudentId, Feedback, Mark) VALUES (?,?,?,?)";

    public String update_courses_sql = "UPDATE courses SET CourseEnd = 1 WHERE Id =?";

    public String select_students_sql = "SELECT students.Id, students.StudentName FROM students";
    public String insert_student_sql = "INSERT students(StudentName) VALUES (?)";

    public String select_teachers_sql = "SELECT teachers.Id, teachers.TeacherName FROM teachers";
    public String insert_teacher_sql = "INSERT teachers (TeacherName) VALUES (?)";

    private static final ConfigurationManager configurationManager = new ConfigurationManager();

    private ConfigurationManager() {
        try (FileInputStream inputStream = new FileInputStream(DB_RESOURCES_FILEPATH)) {
            Properties properties = new Properties();
            properties.load(inputStream);

            mysqlConnection = properties.getProperty("MYSQL_CONNECTION");
            mysqlUsername = properties.getProperty("MYSQL_USERNAME");
            mysqlPassword = properties.getProperty("MYSQL_PASSWORD");

            select_courses_sql = properties.getProperty("SELECT_COURSES_SQL");
            select_teacher_on_course_sql = properties.getProperty("SELECT_TEACHER_ON_COURSE_SQL");
            select_students_on_course_sql = properties.getProperty("SELECT_STUDENTS_ON_COURSE_SQL");

            insert_courses_sql = properties.getProperty("INSERT_COURSES_SQL");
            insert_feedback_sql = properties.getProperty("INSERT_FEEDBACK_SQL");
            insert_courses_students_sql = properties.getProperty("INSERT_COURSES_STUDENTS_SQL");

            update_courses_sql = properties.getProperty("UPDATE_COURSES_SQL");

            select_students_sql = properties.getProperty("SELECT_STUDENTS_SQL");
            insert_student_sql = properties.getProperty("INSERT_STUDENT_SQL");

            select_teachers_sql = properties.getProperty("SELECT_TEACHERS_SQL");
            insert_teacher_sql = properties.getProperty("INSERT_TEACHER_SQL");

        } catch (IOException ex) {
            logger.warn(ex.getMessage());
        }
    }

    public static ConfigurationManager getInstance() {
        return configurationManager;
    }
}
