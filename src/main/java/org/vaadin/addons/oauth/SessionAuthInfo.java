package org.vaadin.addons.oauth;

public class SessionAuthInfo {
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private Long userPrincipal = 0l;


	public Long getUserPrincipal() {
		return userPrincipal;
	}

	public void setUserPrincipal(Long userPrincipal) {
		this.userPrincipal = userPrincipal;
	}

	private String email = "not yet";

}
