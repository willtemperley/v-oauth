package org.vaadin.addons.oauth;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class OAuthCredentials {

	@SerializedName("auth_uri")
	@Expose
	private String authUri;
	@SerializedName("client_secret")
	@Expose
	private String clientSecret;
	@SerializedName("token_uri")
	@Expose
	private String tokenUri;
	@SerializedName("client_email")
	@Expose
	private String clientEmail;
	@SerializedName("redirect_uris")
	@Expose
	private List<String> redirectUris = new ArrayList<String>();
	@SerializedName("client_x509_cert_url")
	@Expose
	private String clientX509CertUrl;
	@SerializedName("client_id")
	@Expose
	private String clientId;
	@SerializedName("auth_provider_x509_cert_url")
	@Expose
	private String authProviderX509CertUrl;
	@SerializedName("javascript_origins")
	@Expose
	private List<String> javascriptOrigins = new ArrayList<String>();

	public String getAuthUri() {
		return authUri;
	}

	public void setAuthUri(String authUri) {
		this.authUri = authUri;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getTokenUri() {
		return tokenUri;
	}

	public void setTokenUri(String tokenUri) {
		this.tokenUri = tokenUri;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	public List<String> getRedirectUris() {
		return redirectUris;
	}

	public void setRedirectUris(List<String> redirectUris) {
		this.redirectUris = redirectUris;
	}

	public String getClientX509CertUrl() {
		return clientX509CertUrl;
	}

	public void setClientX509CertUrl(String clientX509CertUrl) {
		this.clientX509CertUrl = clientX509CertUrl;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getAuthProviderX509CertUrl() {
		return authProviderX509CertUrl;
	}

	public void setAuthProviderX509CertUrl(String authProviderX509CertUrl) {
		this.authProviderX509CertUrl = authProviderX509CertUrl;
	}

	public List<String> getJavascriptOrigins() {
		return javascriptOrigins;
	}

	public void setJavascriptOrigins(List<String> javascriptOrigins) {
		this.javascriptOrigins = javascriptOrigins;
	}

}