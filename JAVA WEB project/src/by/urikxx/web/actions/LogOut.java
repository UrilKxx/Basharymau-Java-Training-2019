package by.urikxx.web.actions;

import by.urikxx.web.interfaces.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOut implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            session.invalidate();
        } else {
            response.setStatus(403);
        }
    }
}
