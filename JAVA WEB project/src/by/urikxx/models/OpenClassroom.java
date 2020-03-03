package by.urikxx.models;

import java.util.ArrayList;
import java.util.Map;

public class OpenClassroom {

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    private ArrayList<Course> courses = new ArrayList<>();

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }

    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Teacher> teachers = new ArrayList<>();

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course){
        if (course == null) return;
        courses.add(course);
    }

    public ArrayList<Course> getEndetCourses() {
        ArrayList<Course> endetCourse = new ArrayList<>();
        for (Course course:courses) {
            if (course.isEndCourse()){
                endetCourse.add(course);
            }
        }
        return  endetCourse;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student){
        if (student == null) return;
        students.add(student);
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public void addTeacher(Teacher teacher){
        if (teacher == null) return;
        teachers.add(teacher);
    }

    public Student getStudentByID(int courseId, int studentId) throws StudentException {
        Map<Student, Feedback>  students = getEndetCourses().get(courseId).getStudents();
        Student studentById = null;
        for(Map.Entry<Student, Feedback> student : students.entrySet()){
            if(student.getKey().getId() == studentId)
                studentById = student.getKey();
        }
        if (studentById == null)
            throw new StudentException("");
        return studentById;
    }
}
