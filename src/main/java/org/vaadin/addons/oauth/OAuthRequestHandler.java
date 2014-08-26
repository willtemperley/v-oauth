package org.vaadin.addons.oauth;

import java.io.IOException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.vaadin.server.RequestHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.server.VaadinServletResponse;
import com.vaadin.server.VaadinSession;

public class OAuthRequestHandler implements RequestHandler {
	
	private Logger logger = LoggerFactory.getLogger(OAuthRequestHandler.class);
	
	private final OAuthManager oAuthManager;

	private OAuthSubject oAuthSubject;

    @Inject
	public OAuthRequestHandler(OAuthManager oAuthManager, OAuthSubject oAuthSubject) {
		this.oAuthManager = oAuthManager;
		this.oAuthSubject = oAuthSubject;
	}

	@Override
	public boolean handleRequest(VaadinSession session, VaadinRequest request,
			VaadinResponse response) throws IOException {
		VaadinServletRequest vsRequest = (VaadinServletRequest) request;
		VaadinServletResponse vsResponse = (VaadinServletResponse) response;
		
		if (oAuthSubject.getUserPrincipal() > 1) {
			//Do nothing
			return false;
		}

		String code = vsRequest.getParameter("code");
		String securityToken = vsRequest.getParameter("state");
		if (code != null) {

			boolean verified = oAuthManager.verifySecurityToken(securityToken);
			
			if (verified) {
					
				oAuthManager.getAuthToken(code);
				
				String j = oAuthManager.getUserInfo();

				Gson g = new Gson();
				UserInfo userInfo = g.fromJson(j, UserInfo.class);
			
				
				/*
				 * The implementation should manage the credentials
				 */
				oAuthSubject.setUserInfo(userInfo);

				/*
				 * This is just here to get rid of all the oauth parameters
				 * Important to return true as the response has already been committed.
				 */
				vsResponse.sendRedirect(oAuthManager.getRedirect());
				return true;
				
			} else {
				logger.error("Invalid security token.  Possible malicious attack.");
			}

		}

		return false; // No response was written
	}

}
