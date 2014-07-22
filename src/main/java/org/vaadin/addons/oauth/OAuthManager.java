package org.vaadin.addons.oauth;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.inject.Inject;

/**
 * 
 * https://developers.google.com/accounts/docs/OAuth2Login
 * 
 * TODO: figure out and fix state issues in flow - it seems credentials are stored.
 * 
 * @author will
 *
 */
public final class OAuthManager {

	Logger logger = LoggerFactory.getLogger(OAuthManager.class);

	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	
	
	private String securityToken;

	private final AuthorizationCodeFlow flow;

	private OAuthCredentials oauthCredentials;

	@Inject
	public OAuthManager(Map<String, OAuthCredentials> oAuthCredentialMap) {
		
		System.out.println("OAUTH MANAGER CREATED: " + this.hashCode());
		
		this.oauthCredentials = oAuthCredentialMap.get("google");

		flow = getCodeFlow(oauthCredentials);

		generateStateToken();
	}

	/**
	 * Builds a login URL based on client ID, secret, callback URI, and scope
	 */
	public String buildLoginUrl() {
	
		final AuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
	
		return url.setRedirectUri(oauthCredentials.getRedirectUris().get(0)).setState(securityToken).build();
	}

	public OAuthCredentials getOauthCredentials() {
		return oauthCredentials;
	}

	/**
	 * TODO organize scopes properly.
	 * 
	 * @param oauth
	 * @return
	 */
	private AuthorizationCodeFlow getCodeFlow(OAuthCredentials oauth) {
		
		AuthorizationCodeFlow codeFlow = new AuthorizationCodeFlow.Builder(
				BearerToken.queryParameterAccessMethod(), HTTP_TRANSPORT,
				JSON_FACTORY, new GenericUrl(oauth.getTokenUri()),
				new ClientParametersAuthentication(oauth.getClientEmail(),
						oauth.getClientSecret()), oauth.getClientId(),
				oauth.getAuthUri()).setScopes(oauth.getScopes()).build();
		return codeFlow;
	}

	/**
	 * Generates a secure state token
	 */
	private void generateStateToken() {
		SecureRandom sr = new SecureRandom();
		logger.debug(securityToken);
		securityToken = "google;" + sr.nextInt();
	}

	/**
	 * Expects an Authentication Code, and makes an authenticated request for
	 * the user's profile information
	 * 
	 * @return JSON formatted user profile information
	 * @param authCode
	 *            authentication code provided by google
	 */
	public String getAuthToken(final String authCode) throws IOException {

		final TokenResponse response = flow.newTokenRequest(authCode)
				.setRedirectUri(oauthCredentials.getRedirectUris().get(0)).execute();
		final Credential credential = flow.createAndStoreCredential(response,
				null);
		final HttpRequestFactory requestFactory = HTTP_TRANSPORT
				.createRequestFactory(credential);
		// Make an authenticated request
		String t = credential.getAccessToken();
		
		String userInfoUrl = oauthCredentials.getUserInfoUrl();
		userInfoUrl = userInfoUrl + "?oauth2_access_token=" +t; 
		final GenericUrl url = new GenericUrl(userInfoUrl);

		

		final HttpRequest request = requestFactory.buildGetRequest(url);
		request.getHeaders().setContentType("application/json");
		final String jsonIdentity = request.execute().parseAsString();
		System.out.println(jsonIdentity);

		return jsonIdentity;

	}

	/**
	 * The OAuth provider should send back a security token with the post-authentication callback.
	 * This must match the one the server sent.
	 * 
	 * @param securityToken
	 * @return
	 */
	public boolean verifySecurityToken(String securityToken) {
		logger.debug("retrieved: " + securityToken);
		logger.debug("sent: " + this.securityToken);
		return this.securityToken.equals(securityToken);
	}
	
	public String getRedirect() {
		return oauthCredentials.getRedirectUris().get(0);
	}

}
