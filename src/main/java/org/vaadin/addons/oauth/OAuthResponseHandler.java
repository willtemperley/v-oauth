package org.vaadin.addons.oauth;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.vaadin.server.RequestHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.server.VaadinServletResponse;
import com.vaadin.server.VaadinSession;

public class OAuthResponseHandler implements RequestHandler {
	
	Logger logger = LoggerFactory.getLogger(OAuthResponseHandler.class);
	
	private final OAuthManager oAuthManager;

	private SessionAuthInfo authInfo;
	

    @Inject
	public OAuthResponseHandler(OAuthManager googleAuthHelper, SessionAuthInfo authInfo) {
		this.oAuthManager = googleAuthHelper;
		this.authInfo = authInfo;
	}

	@Override
	public boolean handleRequest(VaadinSession session, VaadinRequest request,
			VaadinResponse response) throws IOException {
		VaadinServletRequest vsRequest = (VaadinServletRequest) request;
		VaadinServletResponse vsResponse = (VaadinServletResponse) response;
		
		if (authInfo.getUserPrincipal() > 1) {
			//Do nothing
			return false;
		}

		String code = vsRequest.getParameter("code");
		String securityToken = vsRequest.getParameter("state");
		if (code != null) {

			boolean verified = oAuthManager.verifySecurityToken(securityToken);
			
			if (verified) {
					
				String j = oAuthManager.getUserInfoJson(code);

				Gson g = new Gson();
				UserInfo userInfo = g.fromJson(j, UserInfo.class);
			
				authInfo.setEmail(userInfo.getEmail());
				
				if (userInfo.getEmail().equals("willtemperley@gmail.com")) {
					authInfo.setUserPrincipal(1l);
				}

				vsResponse.sendRedirect(oAuthManager.getRedirect());
				return true;
				
			} else {
				System.out.println("invalid security token");
				logger.error("Invalid security token.  Possible malicious attack.");
			}

		}

		return false; // No response was written
	}

}
