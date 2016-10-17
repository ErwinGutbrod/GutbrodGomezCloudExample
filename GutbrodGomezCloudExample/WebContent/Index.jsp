<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>DSSD Login</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js">
  </script>
  <script src="https://apis.google.com/js/client:platform.js?onload=start" async defer>
  </script>
  <script>
    function start() {
      gapi.load('auth2', function() {
        auth2 = gapi.auth2.init({
          client_id: '411423638817-c1t56uhdrqr9loe4lvdhhg5g0geg4e16.apps.googleusercontent.com',
          // Scopes to request in addition to 'profile' and 'email'
          scope: 'https://www.googleapis.com/auth/drive'
        });
      });
    }
  </script>
</head>
<body>
    
    <br>
    <div class="container-fluid">
        <div class="panel panel-success">
            <div class="panel-heading" align="center">
                <h4><b><font color="black" style="font-family: fantasy;">DSSD Cloud Example login</font> </b></h4>
            </div>
            <div class="panel-body"align="center">
                  
                <div class="container " style="margin-top: 10%; margin-bottom: 10%;">
    
                    <div class="panel panel-success" style="max-width: 35%;" align="left">
                        
                        <div class="panel-heading form-group">
                            <b><font color="white">
                                Login Form</font> </b>
                        </div>
                    
                        <div class="panel-body" >

						<button id="signinButton">Sign in with Google</button>
					<script>
  $('#signinButton').click(function() {
    // signInCallback defined in step 6.
    auth2.grantOfflineAccess({'redirect_uri': 'postmessage'}).then(signInCallback);
  });
</script>

<script>
function signInCallback(authResult) {
  if (authResult['code']) {
    // Hide the sign-in button now that the user is authorized, for example:
    $('#signinButton').attr('style', 'display: none');

    // Send the code to the server
    $.ajax({
      type: 'POST',
      url: 'http://localhost:8080/GutbrodGomezCloudExample/DriveRootFilesServlet',
      contentType: 'application/octet-stream; charset=utf-8',
      success: function(result) {
        // Handle or verify the server response.
      },
      processData: false,
      data: authResult['code']
    });
  } else {
    // There was an error.
  }
}
</script>

                        </div>
                    </div>
                    
                </div>
                
            </div>
          
        </div>
    </div>
    
</body>
</html>