package Servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;


/**
 * Servlet implementation class DriveRootFilesServlet
 */
@SuppressWarnings("serial")
public class DriveRootFilesServlet extends HttpServlet {
       
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// (Receive authCode via HTTPS POST)
		StringBuffer jb = new StringBuffer();
		  String line = null;
		  try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { /*report an error*/ }

		 String authCode = jb.toString();

		// Set path to the Web application client_secret_*.json file you downloaded from the
		// Google API Console: https://console.developers.google.com/apis/credentials
		// You can also find your Web application client ID and client secret from the
		// console and specify them directly when you create the GoogleAuthorizationCodeTokenRequest
		// object.
		String CLIENT_SECRET_FILE = this.getServletContext().getRealPath("/WEB-INF/Resources/client_secret_411423638817-c1t56uhdrqr9loe4lvdhhg5g0geg4e16.apps.googleusercontent.com.json");

		
		// Exchange auth code for access token
		GoogleClientSecrets clientSecrets =
		    GoogleClientSecrets.load(
		        JacksonFactory.getDefaultInstance(), new FileReader(CLIENT_SECRET_FILE));
		GoogleTokenResponse tokenResponse =
		          new GoogleAuthorizationCodeTokenRequest(
		              new NetHttpTransport(),
		              JacksonFactory.getDefaultInstance(),
		              "https://www.googleapis.com/oauth2/v4/token",
		              clientSecrets.getDetails().getClientId(),
		              clientSecrets.getDetails().getClientSecret(),
		              authCode,
		              "postmessage")  // Specify the same redirect URI that you use with your web
		                             // app. If you don't have a web version of your app, you can
		                             // specify an empty string.
		              .execute();

		String accessToken = tokenResponse.getAccessToken();

		// Use access token to call API
		GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
		Drive drive =
		    new Drive.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
		        .setApplicationName("Auth Code Exchange Demo")
		        .build();
		//File file = drive.files().get("root").execute();

		// Get profile info from ID token
		GoogleIdToken idToken = tokenResponse.parseIdToken();
		GoogleIdToken.Payload payload = idToken.getPayload();
		String userId = payload.getSubject();  // Use this value as a key to identify a user.
		String email = payload.getEmail();
		boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
		String name = (String) payload.get("name");
		String pictureUrl = (String) payload.get("picture");
		String locale = (String) payload.get("locale");
		String familyName = (String) payload.get("family_name");
		String givenName = (String) payload.get("given_name");
		
		List<String> fleNames = new ArrayList<String>();
		   List<File> result = new ArrayList<File>();
		    Files.List requestListFiles = drive
                    .files()
                    .list()
                    .setQ("'root' in parents  and trashed=false");

		    do {
		      try {
		        FileList files = requestListFiles.execute();

		        result.addAll(files.getItems());
		        System.out.println(result);
		        requestListFiles.setPageToken(files.getNextPageToken());
		      } catch (IOException e) {
		        System.out.println("An error occurred: " + e);
		        requestListFiles.setPageToken(null);
		      }
		    } while (requestListFiles.getPageToken() != null &&
		    		requestListFiles.getPageToken().length() > 0);

			for(int i=0; i<result.size(); i++){
		        fleNames.add(result.get(i).getTitle());
		        System.out.println(fleNames.get(i));
		      }
		  }



}
