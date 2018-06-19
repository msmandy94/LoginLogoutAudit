package com.example.servlet.session;

import beens.UserCredentials;
import services.UserAuditService;
import services.UserAuditServiceImpl;
import services.UserCredentialsService;
import services.UserCredentialsServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        // get request parameters for userID and password
        String user = request.getParameter("user");
        if (user == null || user.isEmpty()) {
            return;
        }
        String pwd = request.getParameter("pwd");
        if (pwd == null || pwd.isEmpty()) {
            return;
        }
        UserCredentialsService userCredentialsService = new UserCredentialsServiceImpl();
        UserAuditService userAuditService = new UserAuditServiceImpl();
        UserCredentials credentials = new UserCredentials(user, pwd);
        Boolean isAuthorised = userCredentialsService.validateUserCred(credentials);
        if (isAuthorised) {
            userAuditService.saveAuditAction(user, "LOGGED_IN");
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            //setting session to expiry in 3 mins
            session.setMaxInactiveInterval(3 * 60);
            Cookie userName = new Cookie("user", user);
            userName.setMaxAge(3 * 60);
            response.addCookie(userName);
            response.sendRedirect("LoginSuccess.jsp");
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>Either user name or password is wrong.</font>");
            rd.include(request, response);
        }

    }

}
