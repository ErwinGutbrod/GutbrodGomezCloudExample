package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Servlet implementation class DriveRootFilesServlet
 */
@SuppressWarnings("serial")
public class DriveRootFilesServlet extends HttpServlet {
	
	public static OAuthTokenDao oauthTokenDao = new OAuthTokenDaoMemoryImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DriveRootFilesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Getting the current user
	    // This is using App Engine's User Service but you should replace this to
	    // your own user/login implementation
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();

	    // If the user is not logged-in it is redirected to the login service, then back to this page
	    if (user == null) {
	      response.sendRedirect(userService.createLoginURL(getFullRequestUrl(request)));
	      return;
	    }

	    // Checking if we already have tokens for this user in store
	    TokenResponse accessTokenResponse = oauthTokenDao.getKeys(user.getEmail());

//	    // If we don't have tokens for this user
//	    if (accessTokenResponse == null) {
//	      OAuthProperties oauthProperties = new OAuthProperties();
//	      // Redirect to the Google OAuth 2.0 authorization endpoint
//	      response.sendRedirect(new GoogleAuthorizationCodeRequestUrl(oauthProperties.getClientId(),
//	    		  DriveLoginServletCallback.getOAuthCodeCallbackHandlerUrl(request), oauthProperties
//	              .getScopesAsString()).build());
//	      return;
//	    }
	}
	
	/**
	   * Construct the request's URL without the parameter part.
	   *
	   * @param req the HttpRequest object
	   * @return The constructed request's URL
	   */
	  public static String getFullRequestUrl(HttpServletRequest req) {
	    String scheme = req.getScheme() + "://";
	    String serverName = req.getServerName();
	    String serverPort = (req.getServerPort() == 80) ? "" : ":" + req.getServerPort();
	    String contextPath = req.getContextPath();
	    String servletPath = req.getServletPath();
	    String pathInfo = (req.getPathInfo() == null) ? "" : req.getPathInfo();
	    String queryString = (req.getQueryString() == null) ? "" : "?" + req.getQueryString();
	    return scheme + serverName + serverPort + contextPath + servletPath + pathInfo + queryString;
	  }



}
