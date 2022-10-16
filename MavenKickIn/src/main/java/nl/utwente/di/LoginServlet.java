package nl.utwente.di;

import java.io.*;
import java.sql.SQLException;
import java.util.Base64;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import nl.utwente.di.dao.EncyptionDao;
import nl.utwente.di.dao.UserDao;
import nl.utwente.di.model.User;



@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
        String u = request.getParameter("uname");
        String p = request.getParameter("psw");
        //Hashing
        UserDao userDao = new UserDao();
        String salt = EncyptionDao.getSalt(u);
        String hash = "Incorrect password";
        if(salt != null) {
        	hash = EncyptionDao.HashPassword(p, Base64.getDecoder().decode(salt));
        }
        
        
        
        
        User user = userDao.checkLogin(u, hash);
		String destPage = "index.jsp";
		 
		if (user != null && user.getisAdmin().equals("True")) {
		    HttpSession session = request.getSession();
		    session.setAttribute("user", user);
		    destPage = "search.html";
		} else if(user != null && !user.getisAdmin().equals("True")) {
		    HttpSession session = request.getSession();
		    session.setAttribute("user", user);
		    destPage = "limitedsearch.html";
			
		} else {
		    String message = "Invalid email or username/password";
		    request.setAttribute("message", message);
		}
		 
		RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
		dispatcher.forward(request, response);
		
	}

	
	

}
