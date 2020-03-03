package by.urikxx.DAO.Interfaces;

import by.urikxx.models.Course;
import by.urikxx.models.Feedback;
import by.urikxx.models.Student;
import by.urikxx.models.Teacher;

import java.util.ArrayList;
import java.util.Map;

public interface CourseDAO {
    int insertCourse(Course course);
    int insertCourse(String courseName, int teacherId);
    int entryToCourse(int courseId, int studentId);
    ArrayList<Course> getCourses();
    Course getCourseById(int id);
    Teacher getTeacherOnCourse(int id);
    Map<Student, Feedback> getStudentsOnCourse(int id);
    int finishCourse(int courseId, int teacherId);
    int setFeedbacks(int courseId, int studentId, String feedback, int mark);

}
