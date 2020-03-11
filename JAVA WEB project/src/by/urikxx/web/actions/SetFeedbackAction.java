package by.urikxx.web.actions;

import by.urikxx.DAO.Factory.DAOFactory;
import by.urikxx.web.interfaces.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetFeedbackAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DAOFactory daoFactory = DAOFactory.getDAOFactory("MySQL");
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            String role = (String) session.getAttribute("role");
            if (role.equals("t")) {
                String courseIdStr = request.getParameter("courseid");
                String studentIdStr = request.getParameter("studentid");
                String feedback = request.getParameter("feedback");
                String markStr = request.getParameter("mark");
                if (courseIdStr == null || studentIdStr == null || feedback == null || markStr == null) {
                    response.setStatus(400);
                    return;
                }
                int courseId;

                try {
                    courseId = Integer.parseInt(courseIdStr);
                } catch (NumberFormatException e) {
                    response.setStatus(400);
                    return;
                }
                int studentId;

                try {
                    studentId = Integer.parseInt(studentIdStr);
                } catch (NumberFormatException e) {
                    response.setStatus(400);
                    return;
                }

                int mark;
                try {
                    mark = Integer.parseInt(markStr);
                } catch (NumberFormatException e) {
                    response.setStatus(400);
                    return;
                }
                int row = daoFactory.getCourseDAO().setFeedbacks(courseId, studentId, feedback, mark);

                if (row > 0) {
                    response.setStatus(201);
                    return;
                }
            } else if (role.equals("s")) {
                response.setStatus(403);
                return;
            }
        } else{
            response.setStatus(403);
            return;
        }
    }
}
