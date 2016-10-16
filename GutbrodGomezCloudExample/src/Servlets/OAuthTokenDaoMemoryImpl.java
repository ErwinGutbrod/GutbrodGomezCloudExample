package Servlets;

import java.util.HashMap;
import java.util.Map;

import com.google.api.client.auth.oauth2.TokenResponse;

public class OAuthTokenDaoMemoryImpl implements OAuthTokenDao {

	/** Object where all the Tokens will be stored */
	  private static Map<String,TokenResponse> tokenPersistance = new HashMap<String, TokenResponse>();
	  
	@Override
	public void saveKeys(TokenResponse tokens, String userName) {
		tokenPersistance.put(userName, tokens);

	}    

	@Override
	public TokenResponse getKeys(String userName) {
		return tokenPersistance.get(userName);
	}

}
