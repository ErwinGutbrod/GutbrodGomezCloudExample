package Servlets;

import com.google.api.client.auth.oauth2.TokenResponse;;

public interface OAuthTokenDao {
	/**
	   * Stores the given AccessTokenResponse using the {@code username}, the OAuth
	   * {@code clientID} and the tokens scopes as keys.
	   *
	   * @param tokens The AccessTokenResponse to store
	   * @param userName The userName associated wit the token
	   */
	  public void saveKeys(TokenResponse tokens, String userName); 

	  /**
	   * Returns the AccessTokenResponse stored for the given username, clientId and
	   * scopes. Returns {@code null} if there is no AccessTokenResponse for this
	   * user and scopes.
	   *
	   * @param userName The username of which to get the stored AccessTokenResponse
	   * @return The AccessTokenResponse of the given username
	   */
	  public TokenResponse getKeys(String userName);
}
