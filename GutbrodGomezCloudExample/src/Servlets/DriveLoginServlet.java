package Servlets;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;

public class DriveLoginServlet extends AbstractAuthorizationCodeServlet {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static FileDataStoreFactory dataStoreFactory;


    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@Override
	protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
		GenericUrl url = new GenericUrl(req.getRequestURL().toString());
	    url.setRawPath("/DriveLoginServletCallback");
	    return url.build();
	}

	@Override
	protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
		// return user ID
		return null;
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
		return new GoogleAuthorizationCodeFlow.Builder(
		        new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
		        "411423638817-c1t56uhdrqr9loe4lvdhhg5g0geg4e16.apps.googleusercontent.com", "Ks8IO0Ed_wP_PWCXRQHpOU_g",
		        Collections.singleton(DriveScopes.DRIVE_FILE)).setDataStoreFactory(
		        		dataStoreFactory).setAccessType("offline").build();
	}

}
