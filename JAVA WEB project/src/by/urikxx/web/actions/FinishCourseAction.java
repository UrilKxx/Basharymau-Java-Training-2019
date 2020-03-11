package by.urikxx.web.actions;

import by.urikxx.DAO.Factory.DAOFactory;
import by.urikxx.web.interfaces.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FinishCourseAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        DAOFactory daoFactory = DAOFactory.getDAOFactory("MySQL");
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            String role = (String) session.getAttribute("role");
            if (role.equals("t")){
                String courseIdStr = request.getParameter("courseid");
                if (courseIdStr == null) {
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
                int teacherId = (Integer) session.getAttribute("userId");

                int row = daoFactory.getCourseDAO().finishCourse(courseId, teacherId);

                if (row > 0){
                    response.setStatus(201);
                    return;
                }
            }
            else if (role.equals("s")){
                response.setStatus(403);
                return;
            }
        } else{
            response.setStatus(403);
            return;
        }
    }
}
