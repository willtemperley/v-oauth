package org.vaadin.addons.guice;

import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class SessionScopedBean {
	
	private String sessionScopedData = "Nothing yet.";

	public String getSessionScopedData() {
		return sessionScopedData;
	}

	public void setSessionScopedData(String sessionScopedData) {
		this.sessionScopedData = sessionScopedData;
	}

}
