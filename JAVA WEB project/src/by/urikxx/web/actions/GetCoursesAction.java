package by.urikxx.web.actions;

import by.urikxx.DAO.Factory.DAOFactory;
import by.urikxx.models.Course;
import by.urikxx.models.Feedback;
import by.urikxx.models.Student;
import by.urikxx.web.interfaces.Action;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

public class GetCoursesAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DAOFactory daoFactory = DAOFactory.getDAOFactory("MySQL");

        ArrayList<Course> courses = daoFactory.getCourseDAO().getCourses();

        JSONArray jcourses = new JSONArray();
        JSONObject jcourse = null;

        for (Course course:courses ) {
            jcourse = new JSONObject();
            JSONObject jstudent = null;
            JSONArray jarray = new JSONArray();
            jcourse.put("courseId", course.getId());
            jcourse.put("courseName", course.getName());
            jcourse.put("teacherName", course.getTeacher().getName());
            jcourse.put("courseEnd", course.isEndCourse());

            for (Map.Entry<Student, Feedback> student : course.getStudents().entrySet()){
                jstudent = new JSONObject();
                jstudent.put("studentId", student.getKey().getId());
                jstudent.put("studentName", student.getKey().getName());
                jstudent.put("feedback", student.getValue().getFeedback());
                jstudent.put("mark", student.getValue().getMark());
                jarray.put(jstudent);
            }
            jcourse.put("students",jarray);

            jcourses.put(jcourse);
        }

        response.getWriter().write(jcourses.toString());
        return;
    }
}
