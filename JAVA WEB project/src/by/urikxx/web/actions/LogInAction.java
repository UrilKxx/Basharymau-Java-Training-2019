package by.urikxx.web.actions;

import by.urikxx.DAO.Factory.DAOFactory;
import by.urikxx.models.Student;
import by.urikxx.models.Teacher;
import by.urikxx.models.User;
import by.urikxx.web.interfaces.Action;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogInAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        DAOFactory daoFactory = DAOFactory.getDAOFactory("MySQL");
        HttpSession session = request.getSession();
        JSONObject juser = new JSONObject();
        if (session.getAttribute("login") == null) {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            if (login == null || password == null) {
                response.setStatus(400);
                return;
            }

            User user = daoFactory.getUserDAO().login(login, password);

            if (user != null){
                juser.append("id", user.getId());
                juser.append("login", user.getLogin());
                juser.append("name", user.getName());
                if (user.getClass() == Student.class){
                    juser.append("role", "s");
                    session.setAttribute("login", user.getLogin());
                    session.setAttribute("name",  user.getName());
                    session.setAttribute("role", "s");
                    session.setAttribute("userId", user.getId());
                }else if (user.getClass() == Teacher.class){
                    juser.append("role", "t");
                    session.setAttribute("login", user.getLogin());
                    session.setAttribute("name",  user.getName());
                    session.setAttribute("role", "t");
                    session.setAttribute("userId", user.getId());
                }
                 response.getWriter().write(juser.toString());
            }
            else
                response.getWriter().write("{\"error\": \"Неверный логин или пароль\"}");
        }
        else
            response.setStatus(403);
    }
}
