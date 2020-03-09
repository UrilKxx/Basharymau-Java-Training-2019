package by.urikxx.web;

import by.urikxx.web.actions.*;
import by.urikxx.web.interfaces.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Servlet", urlPatterns = "/api/*")
public class Servlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(Servlet.class);
    private Map<String, Action> actionMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();
        actionMap.put("login", new LogInAction());
        actionMap.put("logout", new LogOut());
        actionMap.put("signup", new SignUpAction());
        actionMap.put("addcourse", new AddCoursesAction());
        actionMap.put("getcourses", new GetCoursesAction());
        actionMap.put("entrytocourse", new EntryToCourseAction());
        actionMap.put("finishcourse", new FinishCourseAction());
        actionMap.put("setfeedback", new SetFeedbackAction());
    }

    @Override
   protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json ; charset=utf-8");

        String actionKey;
        actionKey = req.getParameter("action");

        if (actionKey != null){
            if (actionMap.containsKey(actionKey)){
                Action action = actionMap.get(actionKey);
                try {
                    action.execute(req, resp);
                } catch (Exception e) {
                    logger.warn(e.getStackTrace());
                }
            }else
                //405 Method Not Allowed («метод не поддерживается»)
                resp.sendError(405);

        }else
            //400 Bad Request («плохой, неверный запрос»)
            resp.sendError(400);

    }
}
