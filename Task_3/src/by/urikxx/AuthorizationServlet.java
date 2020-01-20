package by.urikxx;

import by.urikxx.controllers.UserController;
import org.json.JSONArray;
import org.json.JSONObject;
import by.urikxx.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AuthorizationServlet", urlPatterns = "/authorization")
public class AuthorizationServlet extends HttpServlet {
    private UserController userController;

    @Override
    public void init() throws ServletException {
        userController = UserController.getInstance();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = userController.authorization(login, password);
        if (user != null) {
            JSONObject juser = new JSONObject();
            juser.append("login", user.getLogin());
            resp.getWriter().write(juser.toString());
        }
        else{
            JSONObject juser = new JSONObject();
            juser.append("login", null);
            resp.getWriter().write(juser.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        User user = userController.registration(login, email, password);
        if (user != null) {
            JSONObject juser = new JSONObject();
            juser.append("login", user.getLogin());
            resp.getWriter().write(juser.toString());
        }
        else{
            JSONObject juser = new JSONObject();
            juser.append("login", null);
            resp.getWriter().write(juser.toString());
        }
    }
}
