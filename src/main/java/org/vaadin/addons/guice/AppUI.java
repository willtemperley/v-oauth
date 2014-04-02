package org.vaadin.addons.guice;

import org.vaadin.addons.guice.ui.ScopedUI;

import com.google.inject.Inject;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Theme("runo")
public class AppUI extends ScopedUI  {
	
    
	private SessionScopedBean sessionScopedBean;

	@Inject
	public AppUI(SessionScopedBean sessionScopedBean) {
		
		this.sessionScopedBean = sessionScopedBean;

	}

	@Override
	protected void init(VaadinRequest request) {
		
		VerticalLayout rootLayout = new VerticalLayout();
		setContent(rootLayout);
		
		
		TextField textField = new TextField();
		textField.setImmediate(true);
		textField.setValue(sessionScopedBean.getSessionScopedData());
		rootLayout.addComponent(textField);
		
		textField.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				
				sessionScopedBean.setSessionScopedData(event.getProperty().getValue().toString());
				
			}
		});

	}

}
