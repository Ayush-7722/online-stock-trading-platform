package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Home() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	RequestDispatcher requestDispatcher;
	String action=request.getServletPath();
	
	switch(action)
	{
	case "/register":
		requestDispatcher=request.getRequestDispatcher("registration.jsp");
		requestDispatcher.forward(request, response);
		break;
		
	case "/login":
		requestDispatcher=request.getRequestDispatcher("login.jsp");
		requestDispatcher.forward(request, response);
		break;
		
	}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ethod stub
		doGet(request, response);
	}

}
