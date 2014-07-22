package org.vaadin.addons.oauth;

public interface OAuthSubject {

	public abstract String getEmail();

	public abstract Long getUserPrincipal();

	public abstract void setUserInfo(UserInfo userInfo);

}