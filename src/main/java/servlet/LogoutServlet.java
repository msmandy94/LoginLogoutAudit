package servlet;

import beens.ActionType;
import services.UserAuditService;
import services.UserAuditServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserAuditService userAuditService = UserAuditServiceImpl.getInstance();
        // todo get userID from somewhere -- cookie or session.
        response.setContentType("text/html");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    System.out.println("JSESSIONID=" + cookie.getValue());
                    break;
                }
            }
        }
        //invalidate the session if exists
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            String userId = (String) session.getAttribute("user");
            System.out.println("User=" + userId);
            // save audit
            userAuditService.saveAuditAction(userId, ActionType.LOGGED_OUT.name());
        }

        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("login.html");
    }

}
