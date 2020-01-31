package by.urikxx.DAO.Interfaces;

import by.urikxx.classes.Course;
import by.urikxx.classes.Feedback;
import by.urikxx.classes.Student;
import by.urikxx.classes.Teacher;

import java.util.ArrayList;
import java.util.Map;

public interface CourseDAO {
    int insertCourse(Course course);
    ArrayList<Course> getCourses();
    Course getCourseById(int id);
    Teacher getTeacherOnCourse(int id);
    Map<Student, Feedback> getStudentsOnCourse(int id);
    int finishCourse(int courseId);
    int setFeedbacks(int courseId, int studentId, String feedback, int mark);

}
