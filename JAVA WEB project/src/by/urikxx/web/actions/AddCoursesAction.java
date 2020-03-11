package by.urikxx.web.actions;

import by.urikxx.DAO.Factory.DAOFactory;
import by.urikxx.web.interfaces.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddCoursesAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        DAOFactory daoFactory = DAOFactory.getDAOFactory("MySQL");
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            String role = (String) session.getAttribute("role");
            if (role.equals("t")){
                String courseName = request.getParameter("coursename");
                if (courseName == null) {
                    response.setStatus(400);
                    return;
                }

                int teacherId = (Integer)session.getAttribute("userId");
                int row = daoFactory.getCourseDAO().insertCourse(courseName, teacherId);

                if (row > 0){
                    response.setStatus(201);
                }
            }
            else if (role.equals("s")){
                response.setStatus(403);
            }
        } else
            response.setStatus(403);

    }
}
