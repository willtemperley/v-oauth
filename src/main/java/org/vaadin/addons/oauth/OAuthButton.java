package org.vaadin.addons.oauth;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class OAuthButton extends Button {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OAuthButton(final OAuthManager oauthHelper) {
//		setIcon(controller.getIconResource(serviceName));
		setCaption("Google");

		addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {

				try {

					final String authenticationUrl = oauthHelper.buildLoginUrl();
					// redirect to provider
					UI.getCurrent().getPage().setLocation(authenticationUrl);

				} catch (final Exception e) {
					e.printStackTrace();
					Notification.show("Error in open ID discovery",
							Notification.Type.ERROR_MESSAGE);
				}

			}
		});

	}

}
