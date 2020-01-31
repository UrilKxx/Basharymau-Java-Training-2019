package by.urikxx;

import by.urikxx.DAO.Factory.DAOFactory;
import by.urikxx.classes.*;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        OpenClassroom openClassrooms = new OpenClassroom();
        DAOFactory daoFactory = DAOFactory.getDAOFactory("XML");
        openClassrooms.setCourses(daoFactory.getCourseDAO().getCourses());
        openClassrooms.setTeachers(daoFactory.getTeacherDAO().getTeachers());
        openClassrooms.setStudents(daoFactory.getStudentDAO().getStudents());

        Teacher teacher = new Teacher("teacherName");
        openClassrooms.addTeacher(teacher);
        daoFactory.getTeacherDAO().insertTeacher(teacher);

        Student student = new Student("studentName");
        openClassrooms.addStudent(student);
        daoFactory.getStudentDAO().insertStudent(student);

        Map<Student, Feedback> studentsOnCourse = null;
        for (Student studentOnCourse: openClassrooms.getStudents()) {
            studentsOnCourse.put(studentOnCourse, new Feedback());
        }
        Course course = new Course("courseName",openClassrooms.getTeachers().get(1),studentsOnCourse,false);
        openClassrooms.addCourse(course);
        daoFactory.getCourseDAO().insertCourse(course);

        openClassrooms.getCourses().get(0).setEndCourse();
        daoFactory.getCourseDAO().finishCourse(openClassrooms.getCourses().get(0).getId());

        Map<Student, Feedback> studentsWithEndCourse = openClassrooms.getEndetCourses().get(0).getStudents();
        daoFactory.getCourseDAO().setFeedbacks(0, 0, "feedback", 10);

    }
}
