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
    public String select_teacher_on_course_sql = "SELECT users.id, users.user_name, users.login FROM users INNER JOIN user_roles ON users.id = user_roles.user_id WHERE user_roles.role_id = 1 AND users.id = ?";
    public String select_students_on_course_sql = "SELECT users.id, users.user_name, users.login, courses_students.feedback, courses_students.mark FROM courses_students INNER JOIN users ON  courses_students.user_id = users.id INNER JOIN user_roles ON  users.id = user_roles.user_id WHERE user_roles.role_id = 2 AND courses_students.course_id = ?";

    public String insert_courses_sql = "CALL add_course(?, ?);";
    public String insert_feedback_sql = "UPDATE openclassroom.courses_students SET openclassroom.courses_students.feedback = ?, openclassroom.courses_students.mark = ? where openclassroom.courses_students.course_id = ? and  openclassroom.courses_students.user_id = ? ";
    public String insert_courses_students_sql = "CALL entry_to_course(?, ?)";

    public String update_courses_sql = "UPDATE openclassroom.courses SET openclassroom.courses.course_end = 1 WHERE openclassroom.courses.id = ? AND openclassroom.courses.user_id = ?";

    public String select_students_sql = "SELECT students.Id, students.StudentName FROM students";
    public String insert_student_sql = "INSERT students(StudentName) VALUES (?)";

    public String select_teachers_sql = "SELECT teachers.Id, teachers.TeacherName FROM teachers";
    public String insert_teacher_sql = "INSERT teachers (TeacherName) VALUES (?)";

    public String insert_user_sql = "CALL user_registration(?, ?, ?, ?, ?, ?)";
    public String select_user_sql = "CALL user_authorization(?, ?, ?, ?, ?, ?)";

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

            insert_user_sql = properties.getProperty("INSERT_USER_SQL");
            select_user_sql = properties.getProperty("SELECT_USER_SQL");


        } catch (IOException ex) {
            logger.warn(ex.getMessage());
        }
    }

    public static ConfigurationManager getInstance() {
        return configurationManager;
    }
}
