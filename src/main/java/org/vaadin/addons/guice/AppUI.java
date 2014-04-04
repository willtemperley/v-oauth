package org.vaadin.addons.guice;

import org.vaadin.addons.guice.ui.ScopedUI;

import com.google.inject.Inject;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Theme("runo")
public class AppUI extends ScopedUI  {
	
    
	private final SessionScopedBean sessionScopedBean;
	
	private final CssLayout content = new CssLayout();

	@Inject
	public AppUI(SessionScopedBean sessionScopedBean, GuicedViewProvider viewProvider) {
		
		this.sessionScopedBean = sessionScopedBean;

		Navigator nav = new Navigator(this, content);
		nav.addProvider(viewProvider);
	}

	@Override
	protected void init(VaadinRequest request) {
		
		VerticalLayout rootLayout = new VerticalLayout();
		setContent(rootLayout);
		
		TextField textField = new TextField();
		textField.setImmediate(true);
		textField.setValue(sessionScopedBean.getSessionScopedData());
		rootLayout.addComponent(textField);
		rootLayout.addComponent(content);
		
		textField.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				
				sessionScopedBean.setSessionScopedData(event.getProperty().getValue().toString());
				
			}
		});
	}
}
